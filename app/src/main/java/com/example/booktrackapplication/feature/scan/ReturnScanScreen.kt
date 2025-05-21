package com.example.booktrackapplication.feature.scan

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.booktrackapplication.viewmodel.MainViewmodel
import org.koin.androidx.compose.koinViewModel
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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.booktrackapplication.utils.ConfirmBookDialog
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

@OptIn(ExperimentalGetImage::class)
@Composable
fun ReturnScanScreen(
    navController: NavController,
    onBarcodeScanned: (String) -> Unit,
    viewmodel: MainViewmodel = koinViewModel()
) {
    val scannedBook = viewmodel.scannedBook

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val isValidationStarted = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if(!isValidationStarted.value) {
            isValidationStarted.value = true
            viewmodel.validateRetuningDate()
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val errorMessage = viewmodel.errorMessageState.value

    LaunchedEffect(errorMessage) {
        errorMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewmodel.clearErrorMessage()
        }
    }

    val cameraPermission = Manifest.permission.CAMERA

    var hasPermission by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasPermission = granted
        if (!granted) {
            Toast.makeText(context, "Izin kamera ditolak", Toast.LENGTH_SHORT).show()
        }
    }
    LaunchedEffect(Unit) {
        hasPermission = ContextCompat.checkSelfPermission(
            context, cameraPermission
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            launcher.launch(cameraPermission)
        }
    }

    Log.d("UI_CHECK", "scannedBook: $scannedBook, errorMessage: $errorMessage")
    if(scannedBook != null && errorMessage.isNullOrEmpty()) {
        ConfirmBookDialog(
            book = scannedBook,
            onDismiss = { viewmodel.reset() },
            onConfirm = {
                viewmodel.addBook(scannedBook)
                viewmodel.reset()
                navController.navigate("return_list_book")
            }
        )

    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color.Black)
            ) {
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
        }
    )

}