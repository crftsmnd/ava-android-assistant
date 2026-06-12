# AVA - Build Notes & Troubleshooting

## 🔍 Current Status

**GitHub Actions Build**: In Progress  
**Local Build**: Not tested (requires Android SDK)  
**Code Status**: Complete (41 Kotlin files)

---

## ⚠️ Known Build Issues

### Issue 1: GitHub Actions Build Configuration

**Status**: Being Fixed

**Problem**: 
- GitHub Actions workflow may fail on first run
- Android SDK setup requires proper configuration

**Solution Applied**:
- Updated workflow to use built-in Android SDK
- Added proper license acceptance
- Added stacktrace for better error logging
- Added build log upload on failure

**Updated Files**:
```
.github/workflows/android-build.yml
```

---

## 🛠️ Build Methods

### Method 1: GitHub Actions (Recommended for APK)

**Pros**:
- ✅ Automatic builds on push
- ✅ No local setup required
- ✅ APK downloadable from Artifacts
- ✅ Consistent build environment

**Cons**:
- ❌ May take 10-15 minutes
- ❌ Requires working GitHub Actions
- ❌ Internet required

**How to Use**:
1. Push code to GitHub
2. Go to Actions tab
3. Wait for build to complete
4. Download APK from Artifacts

---

### Method 2: Android Studio (Recommended for Development)

**Pros**:
- ✅ Fast incremental builds
- ✅ IDE support (debugging, etc.)
- ✅ Direct device deployment
- ✅ Best for testing

**Cons**:
- ❌ Requires Android Studio installation
- ❌ Large download (3+ GB)
- ❌ Local environment setup

**How to Use**:
```bash
1. Install Android Studio
2. git clone https://github.com/piashmsubd/Maya-ai-automation-android.git
3. Open in Android Studio
4. Wait for Gradle sync
5. Build → Build APK
6. Find APK in: app/build/outputs/apk/debug/
```

---

### Method 3: Command Line (Advanced)

**Pros**:
- ✅ Scriptable/automatable
- ✅ No IDE required
- ✅ CI/CD friendly

**Cons**:
- ❌ Requires Android SDK installation
- ❌ Manual dependency management
- ❌ Complex setup

**Requirements**:
- JDK 17
- Android SDK 34
- Build Tools 34.0.0
- Gradle 8.2

**How to Use**:
```bash
# 1. Install Android SDK
export ANDROID_HOME=/path/to/android-sdk
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools

# 2. Clone and build
git clone https://github.com/piashmsubd/Maya-ai-automation-android.git
cd Maya-ai-automation-android
chmod +x gradlew
./gradlew assembleDebug

# 3. Find APK
ls -la app/build/outputs/apk/debug/app-debug.apk
```

---

## 🐛 Common Build Errors & Solutions

### Error 1: "SDK location not found"

**Error Message**:
```
SDK location not found. Define location with sdk.dir in the local.properties file or with an ANDROID_HOME environment variable.
```

**Solution**:
```bash
# Create local.properties
echo "sdk.dir=/path/to/android-sdk" > local.properties

# OR set environment variable
export ANDROID_HOME=/path/to/android-sdk
```

---

### Error 2: "Unresolved reference"

**Common Issues**:
- Missing imports
- Typos in class names
- Incorrect package names

**Solution**:
- Check import statements
- Verify class exists
- Use Android Studio auto-import (Alt+Enter)

---

### Error 3: "Resource not found"

**Common Issues**:
- Missing drawable files
- Incorrect resource references
- String resources not defined

**Solution**:
- Check res/ folder for file
- Verify @drawable, @string references
- Run "Build → Clean Project"

---

### Error 4: "Duplicate class found"

**Solution**:
```kotlin
// In build.gradle.kts, add:
packaging {
    resources {
        excludes += "/META-INF/{AL2.0,LGPL2.1}"
        excludes += "META-INF/DEPENDENCIES"
    }
}
```

---

### Error 5: "Cannot inline bytecode"

**Error Message**:
```
Cannot inline bytecode built with JVM target 17 into bytecode that is being built with JVM target 11
```

**Solution**:
```kotlin
// Already configured in build.gradle.kts
kotlinOptions {
    jvmTarget = "17"
}
compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
```

---

## 📋 Build Checklist

Before building, ensure:

- [ ] Git repository cloned successfully
- [ ] Android Studio installed (if using IDE)
- [ ] JDK 17 installed
- [ ] Android SDK 34 installed
- [ ] Internet connection available (first build downloads dependencies)
- [ ] Sufficient disk space (5+ GB)
- [ ] All resource files present
- [ ] No syntax errors in code

---

## 🔧 Quick Fixes

### Fix 1: Clean Build
```bash
./gradlew clean
./gradlew assembleDebug
```

### Fix 2: Invalidate Caches (Android Studio)
```
File → Invalidate Caches → Invalidate and Restart
```

### Fix 3: Sync Gradle
```
File → Sync Project with Gradle Files
```

### Fix 4: Update Dependencies
```bash
./gradlew --refresh-dependencies
```

---

## 📊 Build Performance

**Expected Build Times**:
- First build: 10-15 minutes (downloads dependencies)
- Incremental builds: 1-3 minutes
- Clean builds: 5-10 minutes

**Build Artifact Size**:
- Debug APK: ~50-80 MB
- Release APK: ~30-50 MB (after ProGuard)

---

## 🚀 Deployment Options

### Option 1: Direct Install
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### Option 2: Transfer & Install
1. Copy APK to phone
2. Enable "Unknown Sources"
3. Tap APK to install

### Option 3: GitHub Release
- Automatic release on successful build
- Download from Releases page
- Versioned (v1.0.x)

---

## 📝 Build Logs

**Location**:
- Android Studio: Build → Build Output
- Command Line: Console output
- GitHub Actions: Actions tab → Workflow run → Logs
- Gradle: `~/.gradle/daemon/*/daemon-*.out.log`

**Viewing Detailed Logs**:
```bash
./gradlew assembleDebug --stacktrace --info
```

---

## 🔒 Security Notes

**API Keys**:
- NOT included in repository
- Configure in app after installation
- Stored encrypted in DataStore

**Signing**:
- Debug builds use debug keystore
- Release builds need signing key
- Configure in `app/build.gradle.kts`

---

## 📞 Support

**Build Issues**:
1. Check GitHub Actions logs
2. Review this document
3. Open GitHub issue with error logs
4. Check Android Studio error messages

**Links**:
- Repository: https://github.com/piashmsubd/Maya-ai-automation-android
- Issues: https://github.com/piashmsubd/Maya-ai-automation-android/issues
- Actions: https://github.com/piashmsubd/Maya-ai-automation-android/actions

---

## ✅ Verification

After successful build, verify:
- [ ] APK file exists
- [ ] APK size is reasonable (50-80 MB)
- [ ] APK installs on device
- [ ] App launches without crashes
- [ ] All permissions can be requested
- [ ] UI loads correctly

---

**Last Updated**: February 11, 2026  
**Build Version**: 1.0.0  
**Gradle Version**: 8.2  
**Android Target**: SDK 34
