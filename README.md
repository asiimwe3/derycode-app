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

## Store submission pack

- `DeryCode-1.0.0.aab` — Android App Bundle for Google Play (target SDK 36, bundle validated with bundletool)
- `store/LISTING.md` — name, short and full description, category, data safety answers, content rating answers
- `store/graphics/icon-512.png` and `store/graphics/feature-graphic-1024x500.png`
- `store/screenshots/` — four 1080x2590 phone screenshots with captions

Rebuild instructions are in `build.sh`.
