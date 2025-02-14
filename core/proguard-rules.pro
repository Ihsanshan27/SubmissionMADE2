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

# Keep domain models
-keep class com.dicoding.submissionmade1.core.domain.model.** { *; }

# Keep repository interfaces
-keep class com.dicoding.submissionmade1.core.domain.repository.** { *; }

# Keep use case classes
-keep class com.dicoding.submissionmade1.core.domain.usecase.** { *; }

# Keep Koin dependency injection classes
-keep class org.koin.** { *; }

# Keep Room database-related classes
-keep class androidx.room.** { *; }
-keep class * extends androidx.room.RoomDatabase
-keep class * extends androidx.room.Entity
-keep class * extends androidx.room.Dao

# Keep DataStore-related classes
-keep class androidx.datastore.** { *; }

# Keep all classes in the `core` module
-keep class com.dicoding.submissionmade1.core.** { *; }
