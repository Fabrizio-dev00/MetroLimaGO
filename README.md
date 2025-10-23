# 🚇 MetroLima GO

**MetroLima GO** es una aplicación móvil desarrollada con **Android Studio** y **Jetpack Compose**, creada para ofrecer a los ciudadanos y visitantes una forma rápida, visual e intuitiva de **planificar sus viajes en el Metro de Lima** y consultar información sobre las estaciones, líneas y rutas.

---

## 🎯 Descripción general

El proyecto busca facilitar la movilidad en Lima al permitir que los usuarios:
- Consulten las **estaciones** del metro (nombre, línea, distrito y horario).
- Planifiquen sus **rutas** indicando origen y destino.
- Visualicen el **recorrido estimado** con tiempo de viaje.
- Accedan a **información actualizada** desde fuentes externas (horarios, alertas, mantenimiento).
- Personalicen la aplicación con **modo claro/oscuro** y cambio de idioma.

---

## 🧩 Funcionalidades principales

| Módulo | Descripción |
|--------|--------------|
| 🏠 **Inicio** | Pantalla principal con acceso a Estaciones, Rutas y Configuración. |
| 🚉 **Estaciones** | Listado completo de estaciones almacenadas localmente con Room. |
| 📍 **Rutas** | Planificador de trayectos entre estaciones (simulado). |
| 🌐 **Datos externos** | Consumo de una API pública o JSON remoto con Retrofit. |
| ⚙️ **Configuración** | Modo oscuro/claro, idioma y créditos del equipo. |

---

## 💻 Tecnologías utilizadas
- **Kotlin** con **Jetpack Compose**
- **Room** (Base de datos local)
- **Retrofit** (Consumo de API)
- **Coroutines**
- **Navigation Compose**
- **Material Design 3**

---

## 🧠 Estructura de ramas
| Rama | Propósito |
|------|------------|
| `main` | Versión estable del proyecto |
| `develop` | Rama principal de desarrollo |
| `feature/ui-inicio` | Rama de trabajo para la pantalla de inicio |

---

## 🧑‍💻 Equipo de desarrollo
| Integrante | Rol |
|-------------|------|
| **Fabrizio Jiménez** | Configuración de GitHub, ramas y pantalla de inicio |
| **[Nombre 2]** | Diseño de interfaz (Figma) |
| **[Nombre 3]** | Implementación de componentes UI |

---

## 🎨 Diseño en Figma
🔗 **Enlace al prototipo:**  
[👉 Ver diseño en Figma](https://www.figma.com/make/BeQi70cZOOdd5epKvVHq8l/MetroLima-GO-App-Structure?node-id=0-1&t=jMqdgqVBHpHJubps-1)