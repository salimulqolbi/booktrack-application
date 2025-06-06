# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Gson (agar serialisasi/deserialisasi JSON tidak error)
-keep class com.google.gson.** { *; }
-keepattributes *Annotation*

# Koin (agar dependency injection tidak error)
-keep class org.koin.** { *; }
-dontwarn org.koin.**

# ViewModel (untuk Jetpack ViewModel)
-keep class androidx.lifecycle.ViewModel { *; }
-keepclassmembers class ** extends androidx.lifecycle.ViewModel {
    public <init>(...);
}

# Ktor (agar network client & serialization tidak hilang)
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**

# ML Kit (agar scanning tidak rusak)
-keep class com.google.mlkit.** { *; }

# Coil (agar image loader tetap berfungsi)
-keep class coil.** { *; }

# Optional: untuk debugging stacktrace yang lebih jelas
-keepattributes SourceFile,LineNumberTable

# Optional: simpan nama metode public agar refleksi tetap bisa digunakan
-keepclassmembers class * {
    public <init>(...);
}
