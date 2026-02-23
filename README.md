# 😂 Meme Creator App

A cross-platform meme creator app for **Android** and **iOS**, built with **Kotlin Multiplatform** and **Compose Multiplatform**.  
Add captions to images, then save or share your memes directly from your device.

---

## 📋 Table of Contents

- Features
- Tech Stack
- Getting Started
- Project Structure
- License

---

## 📸 Screenshots

> _Add your screenshots here_

---

## ✨ Features

- 🖊️ **Add text & captions** — Overlay custom text on any image to create your meme
- 💾 **Save meme** — Download the finished meme directly to your device gallery
- 📤 **Share meme** — Share your creation instantly via any app on your device

---

## 🛠️ Tech Stack

| Technology               | Description                              |
|--------------------------|------------------------------------------|
| Kotlin Multiplatform     | Shared business logic for Android & iOS  |
| Compose Multiplatform    | Shared UI for Android & iOS              |
| Swift                    | iOS app entry point                      |
| Gradle (Kotlin DSL)      | Build system                             |

---

## 🚀 Getting Started

### Prerequisites

- [Android Studio](https://developer.android.com/studio) (Hedgehog or newer recommended)
- [Xcode](https://developer.apple.com/xcode/) 15+ _(for iOS builds — macOS only)_
- JDK 17+
- Kotlin Multiplatform plugin installed in Android Studio

### Clone the Repository

```bash
git clone https://github.com/M0bileDev/MemeCreatorAppProject.git
cd MemeCreatorAppProject
```

---

### 🤖 Android

#### Run from Android Studio

Open the project in Android Studio, select the `composeApp` run configuration from the toolbar, and hit **Run**.

#### Run from Terminal

```bash
# macOS / Linux
./gradlew :composeApp:assembleDebug

# Windows
.\gradlew.bat :composeApp:assembleDebug
```

Install on a connected device or emulator:

```bash
./gradlew :composeApp:installDebug
```

---

### 🍎 iOS

> Requires macOS with Xcode installed.

Open the `/iosApp` directory in **Xcode**:

```bash
open iosApp/iosApp.xcodeproj
```

Select your target device or simulator and press **Run** (`⌘R`).

Alternatively, use the run configuration from the Android Studio toolbar if you have the Kotlin Multiplatform plugin configured.

---

## 📁 Project Structure

```
MemeCreatorAppProject/
├── composeApp/
│   └── src/
│       ├── commonMain/         # Shared UI and business logic (KMP + Compose)
│       ├── androidMain/        # Android-specific implementations
│       └── iosMain/            # iOS-specific implementations
├── iosApp/
│   └── iosApp/                 # iOS app entry point (Swift / SwiftUI)
├── gradle/                     # Gradle wrapper files
├── build.gradle.kts            # Root build configuration
├── settings.gradle.kts         # Project settings
└── gradle.properties           # Gradle properties
```

---

## 📄 License

This project is licensed under the [Apache 2.0 License](LICENSE).
