# DeryCode App

The official DeryCode Technologies Android app: the whole derycode.online site in an
installable app that keeps working when the internet drops.

- Package: `com.derycode.app`
- Size: ~70 KB (no bundled frameworks)
- Android 7.0+ (minSdk 24, targetSdk 34)
- Permissions: INTERNET, ACCESS_NETWORK_STATE, REQUEST_INSTALL_PACKAGES

## What it does

- Opens derycode.online full screen, no browser chrome
- WhatsApp, phone and email links open the right app
- Downloads (other DeryCode APKs, PDFs) go to your Downloads folder
- Shows a styled offline page instead of a browser error when data drops
- Back button walks through the site history

## Download

Grab the latest APK from the releases page. Android will warn that the app is
installed from outside Play Store — that is normal for direct downloads.

## Build

No Gradle required. Built with the Android SDK directly (aapt2, javac, d8, apksigner).
See `build.sh`.

---
Built by DeryCode Technologies, Kyenjojo, Uganda — https://derycode.online
