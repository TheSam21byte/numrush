# 🎮 NumRush – Adivina el Número (Android – Jetpack Compose)

NumRush es un juego simple pero entretenido donde el usuario debe adivinar un número secreto generado aleatoriamente por la aplicación.  
El juego ofrece pistas como **“más alto”** o **“más bajo”** y cuenta con un sistema de intentos.

El proyecto está desarrollado en **Kotlin**, utilizando **Jetpack Compose**, **Arquitectura Limpia**, **ViewModel**, y **Navigation Compose**.

---

# 📌 1. Objetivo del Proyecto

El objetivo principal es aprender y demostrar:

- Construcción de interfaces modernas con **Jetpack Compose**  
- Separación limpia entre **UI**, **lógica**, **navegación** y **estado**  
- Arquitectura mantenible utilizando **Domain**, **ViewModel** y **Compose Navigation**  
- Implementación de un juego funcional y minimalista  
- Uso correcto de recursos (íconos, temas, colores)  

NumRush sirve como base para continuar expandiendo características como:

- Sistema de puntuaciones  
- Modo difícil  
- Persistencia con Room o DataStore  
- Animaciones y UI avanzada  

---

# 📁 2. Estructura del Proyecto

La estructura sigue una organización modular sencilla pero profesional, usando principios de **Clean Architecture** a pequeña escala.

com.example.numrush/
│
├── domain/
│ └── GameLogic.kt
│
├── ui/
│ ├── MainScreen.kt
│ ├── GameScreen.kt
│ │
│ ├── navigation/
│ │ ├── NavGraph.kt
│ │ └── Routes.kt
│ │
│ ├── theme/
│ │ ├── Color.kt
│ │ ├── Theme.kt
│ │ └── Type.kt
│ │
│ └── viewmodel/
│ └── GameViewModel.kt
│
└── MainActivity.kt


---

## 📦 ¿Qué contiene cada package y para qué sirve?

---

## 📁 domain/
**Responsabilidad:** Contiene la lógica del juego, totalmente independiente de UI o Android.

- **GameLogic.kt**  
  - Genera el número aleatorio  
  - Maneja los intentos  
  - Evalúa si el número ingresado es mayor, menor o correcto  
  - 100% testeable  

---

## 📁 ui/
**Responsabilidad:** Todo lo relacionado con la interfaz de usuario.

- **MainScreen.kt** – Pantalla inicial  
- **GameScreen.kt** – Pantalla del juego  

---

### 📁 ui/navigation/
**Responsabilidad:** navegación entre pantallas.

- **NavGraph.kt** – Define el grafo de rutas  
- **Routes.kt** – Constantes para evitar errores de navegación  

---

### 📁 ui/theme/
**Responsabilidad:** Tema visual general de la app.

- **Color.kt** – Paleta de colores  
- **Theme.kt** – Configuración Material 3  
- **Type.kt** – Tipografías  

---

### 📁 ui/viewmodel/
**Responsabilidad:** Manejo de estado y conexión UI ↔ lógica.

- **GameViewModel.kt**  
  Expone mensaje, intentos, y controla el flujo del juego usando GameLogic  

---

## 📁 MainActivity.kt
Entry point de la app.  
Inicializa Navigation Compose y muestra el NavGraph.

---

# 🚀 Próximas mejoras (opcional)
- Tabla de puntuaciones  
- Modo difícil  
- Animaciones y transiciones  
- Local storage (Room o DataStore)  
- Sonidos y vibración al acertar  

---


