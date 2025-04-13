package com.example.booktrack.feature.scan

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.booktrackapplication.utils.ConfirmBookDialog
import com.example.booktrackapplication.viewmodel.MainViewmodel
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalGetImage::class)
@Composable
fun ScanScreen(
    navController: NavController,
    onBarcodeScanned: (String) -> Unit,
    viewModel: MainViewmodel = koinViewModel()
) {

    val scannedBook = viewModel.scannedBook

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val previewView = remember { PreviewView(context) }

    val cameraPermission = Manifest.permission.CAMERA
    val hasPermission = ContextCompat.checkSelfPermission(
        context, cameraPermission
    ) == PackageManager.PERMISSION_GRANTED

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) {
            Toast.makeText(context, "Izin kamera ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            launcher.launch(cameraPermission)
        }
    }

    if (scannedBook != null) {
        ConfirmBookDialog(
            book = scannedBook,
            onDismiss = { viewModel.reset() },
            onConfirm = {
                viewModel.addBook(scannedBook)
                viewModel.reset()
                navController.navigate("list")
            }
        )
    }

    if (hasPermission) {
        val previewView = remember { PreviewView(context) }

        AndroidView(
            factory = { previewView },
            modifier = Modifier.fillMaxSize()
        ) {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(context)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = Preview.Builder().build().also {
                    it.surfaceProvider = previewView.surfaceProvider
                }

                val scanner = BarcodeScanning.getClient()

                val analysis = ImageAnalysis.Builder().build().also {
                    it.setAnalyzer(ContextCompat.getMainExecutor(context)) { imageProxy ->
                        val mediaImage = imageProxy.image
                        if (mediaImage != null) {
                            val inputImage = InputImage.fromMediaImage(
                                mediaImage,
                                imageProxy.imageInfo.rotationDegrees
                            )

                            scanner.process(inputImage)
                                .addOnSuccessListener { code ->
                                    for (code in code) {
                                        code.rawValue?.let { value ->
                                            onBarcodeScanned(value)
                                            scanner.close()
                                            imageProxy.close()
                                            return@addOnSuccessListener
                                        }
                                    }
                                    imageProxy.close()
                                }
                                .addOnFailureListener {
                                    imageProxy.close()
                                }
                        } else {
                            imageProxy.close()
                        }
                    }
                }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        analysis
                    )
                } catch (e: Exception) {
                    Log.e("ScanScreen", "Camera binding failed", e)
                }
            }, ContextCompat.getMainExecutor(context))
        }
    }
}