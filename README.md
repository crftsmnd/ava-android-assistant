# AVA — Android Voice Assistant

AVA is a powerful Android AI assistant with voice control, multiple AI providers, accessibility services, and system automation. Built with Kotlin and Jetpack Compose.

## Features

- **Wake Word Detection**: Activate AVA with "Hey Ava"
- **Speech-to-Text**: Voice commands via Android speech recognition
- **Text-to-Speech**: Natural voice responses (Android TTS + Cartesia AI)
- **Multiple AI Backends**: OpenAI, Groq, Letta, local LLaMA, OpenCode Zen
- **SMS Management**: Read and auto-reply to incoming SMS
- **Messenger Integration**: WhatsApp, Facebook Messenger, Telegram
- **Call Management**: Make calls by name, announce callers
- **App Control**: Open, close, switch apps by voice
- **System Control**: WiFi, Bluetooth, brightness, volume, airplane mode
- **UI Automation**: Full accessibility service for screen interaction
- **Root Access**: Terminal access with LibSU (optional)
- **Floating Bubble**: Always-accessible assistant overlay
- **Memory System**: Persistent conversation history with Room database
- **Custom Commands**: Create your own voice-activated shortcuts

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose + Material 3
- **Architecture**: MVVM
- **Database**: Room
- **Preferences**: DataStore
- **Min SDK**: 26 (Android 8.0)
- **Target SDK**: 34 (Android 14)

## Building

```bash
git clone https://github.com/crftsmnd/ava-android-assistant.git
cd ava-android-assistant
./gradlew assembleDebug
```

Open in Android Studio (Hedgehog+) and sync Gradle, or build via command line.

## License

MIT
