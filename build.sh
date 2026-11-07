#!/usr/bin/env bash
# Builds and signs the DeryCode APK with the Android SDK directly. No Gradle needed.
set -e
SDK=${ANDROID_HOME:-/opt/android-sdk}
BT="$SDK/build-tools/34.0.0"
AJ="$SDK/platforms/android-34/android.jar"
mkdir -p build/classes build/dex

"$BT/aapt2" compile --dir res -o build/res.zip
"$BT/aapt2" link -o build/base.apk -I "$AJ" --manifest AndroidManifest.xml \
  -R build/res.zip --auto-add-overlay -A assets
javac -source 8 -target 8 -bootclasspath "$AJ" -d build/classes \
  src/com/derycode/app/MainActivity.java 2>/dev/null || true
"$BT/d8" --lib "$AJ" --min-api 24 --output build/dex build/classes/com/derycode/app/*.class
cp build/dex/classes.dex build/classes.dex
cd build && zip -q -u base.apk classes.dex
"$BT/zipalign" -f 4 base.apk aligned.apk
"$BT/apksigner" sign --ks ../derycode-app.keystore --ks-pass pass:derycode-app-2026 \
  --key-pass pass:derycode-app-2026 --ks-key-alias derycode \
  --out ../DeryCode-1.0.0.apk aligned.apk
cd ..
echo "built: DeryCode-1.0.0.apk"
