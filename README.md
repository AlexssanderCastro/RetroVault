# RetroVault

RetroVault is an Android app scaffold for organizing a retro game library. This project uses Kotlin, Jetpack Compose, Material 3, Navigation Compose, and a basic MVVM setup.

## Requirements

- JDK 17
- Android Studio or a compatible Gradle environment

## Quick Start

```powershell
./gradlew.bat test
```

```powershell
./gradlew.bat assembleDebug
```

## Notes

- Room entities/DAO/Database are scaffolded, but annotation processing is currently disabled due to a Windows-specific SQLite temp file lock. Re-enable Room processing (KSP or KAPT) when your environment is ready.

