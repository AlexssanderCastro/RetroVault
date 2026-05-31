# RetroVault

A retro game library app scaffold built with Kotlin and Jetpack Compose.

![RetroVault Banner](docs/images/banner.png)

---

# English

## Overview
RetroVault is a modern Android app scaffold for organizing a personal retro game library. This stage focuses on architecture, navigation, and UI foundations.

## Highlights
- 🎮 Clean Architecture (simplified) + MVVM
- 🎨 Jetpack Compose + Material 3 UI
- 🧭 Navigation Compose with multi-screen flow
- 🗄️ Room database scaffolding (not yet processing annotations)
- ⚙️ Repository pattern + StateFlow + Coroutines
- 📱 Responsive layouts for phones and tablets

## Screens (current)
- 🌟 Splash
- 🏠 Home
- 🔍 Details
- ➕ Add Game

## UI Components
- 🧱 TopBar
- 🧭 BottomNavigation
- 🔎 SearchBar
- 🃏 GameCard
- ⏳ LoadingComponent
- 📭 EmptyStateComponent

## Tech Stack
- 🧩 Kotlin
- 🎨 Jetpack Compose (Material 3)
- 🧭 Navigation Compose
- 🗄️ Room (scaffold only)
- ⚙️ Coroutines + StateFlow
- 🧠 ViewModel

## Project Structure
```
app/src/main/java/com/example/retrovault/
  data/
    local/
    repository/
  domain/
    model/
  presentation/
    screens/
    components/
    navigation/
    theme/
    viewmodel/
  utils/
```

## Run Locally
Requirements:
- ☕ JDK 17
- 🧰 Android Studio or compatible Gradle environment

Commands:
```powershell
./gradlew.bat test
```

```powershell
./gradlew.bat assembleDebug
```

Optional (install to device/emulator):
```powershell
./gradlew.bat installDebug
```

## Notes
- 📝 Room entities/DAO/Database are scaffolded, but annotation processing is disabled to avoid a Windows-specific SQLite temp file lock. Re-enable Room processing (KSP or KAPT) when your environment is ready.

## Images
- Place screenshots in `docs/images/` and update links below.

---

# Portuguese

## Visao Geral
RetroVault e um scaffold moderno de app Android para organizar uma biblioteca pessoal de jogos retro. Esta etapa foca em arquitetura, navegacao e base de UI.

## Destaques
- 🎮 Clean Architecture (simplificada) + MVVM
- 🎨 Jetpack Compose + Material 3
- 🧭 Navigation Compose com fluxo multi-telas
- 🗄️ Room preparado (sem processamento de anotacoes)
- ⚙️ Repository pattern + StateFlow + Coroutines
- 📱 Layout responsivo para celulares e tablets

## Screens (current)
- 🌟 Splash
- 🏠 Home
- 🔍 Detalhes
- ➕ Cadastro

## UI Components
- 🧱 TopBar
- 🧭 BottomNavigation
- 🔎 SearchBar
- 🃏 GameCard
- ⏳ LoadingComponent
- 📭 EmptyStateComponent

## Tech Stack
- 🧩 Kotlin
- 🎨 Jetpack Compose (Material 3)
- 🧭 Navigation Compose
- 🗄️ Room (apenas estrutura)
- ⚙️ Coroutines + StateFlow
- 🧠 ViewModel

## Como Rodar Localmente
Requisitos:
- ☕ JDK 17
- 🧰 Android Studio ou ambiente Gradle compatível

Comandos:
```powershell
./gradlew.bat test
```

```powershell
./gradlew.bat assembleDebug
```

Opcional (instalar no device/emulador):
```powershell
./gradlew.bat installDebug
```

## Observacoes
- 📝 As entidades/DAO/Database do Room estao criadas, mas o processamento de anotacoes esta desativado devido a um lock temporario do SQLite no Windows. Reative o processamento (KSP ou KAPT) quando o ambiente estiver pronto.
