# 🚀 Framework de Automatización con Selenium

Framework de automatización de pruebas **E2E** desarrollado con **Selenium WebDriver**, **Java**, **Cucumber** y generación automática de reportes profesionales en **Word**.

---

## 📋 Requisitos Previos

- **Java JDK** (versión 11 o superior)
- **Maven** (versión 3.6 o superior)
- **IntelliJ IDEA** (recomendado) o cualquier IDE de Java
- **Google Chrome** (última versión)

---

## 🔧 Instalación

### 1. Clonar el repositorio
```bash
git clone https://github.com/rodrigoescutia/selenium-automation-framework.git
cd selenium-automation-framework
```

### 2. Instalar las dependencias
```bash
mvn clean install
```

### 3. Verificar la instalación
```bash
mvn clean test
```

---

## 🗂️ Estructura del Proyecto

```
selenium-automation-framework/
├── src/
│   ├── main/java/com/selenium/
│   │   ├── contants/
│   │   │   └── Urls.java              # URLs y rutas de la aplicación
│   │   ├── pages/
│   │   │   └── LoginPage.java         # Page Object del módulo de Login
│   │   └── utils/
│   │       ├── BasePage.java          # Clase base con funciones comunes
│   │       ├── DriverManager.java     # Gestión centralizada del WebDriver
│   │       └── WordReporter.java      # Generador de reportes en Word
│   └── test/
│       ├── java/com/selenium/
│       │   ├── hooks/
│       │   │   └── Hooks.java         # Configuración Before/After
│       │   ├── runners/
│       │   │   └── TestRunner.java    # Ejecutor de pruebas Cucumber
│       │   └── stepdefinitions/
│       │       └── LoginSteps.java    # Definición de pasos Gherkin
│       └── resources/
│           ├── features/
│           │   └── login.feature      # Casos de prueba en Gherkin
│           └── cucumber.properties    # Configuración de Cucumber
├── test-reports/                      # Reportes Word generados
├── screenshots/                       # Capturas de pantalla
├── target/
│   └── cucumber-reports/              # Reportes HTML de Cucumber
├── pom.xml                            # Dependencias Maven
└── README.md                          # Documentación
```

---

## 📝 Descripción de Archivos Principales

### `pom.xml`
Define dependencias y configuración:
- **Dependencias**: Selenium, Cucumber, Apache POI, WebDriverManager
- **Plugins**: Surefire (ejecución), Compiler (Java 11)
- **Propiedades**: versiones centralizadas

### `contants/Urls.java`
Centraliza todas las URLs de la aplicación:
```java
public class Urls {
    public static final String BASE_URL = "https://practica.testqacademy.com";
    public static final String STORE_PATH = "/store";
}
```

### `utils/BasePage.java`
Clase base con:
- Método `execute()` para encapsular pasos con screenshots
- Integración con `WebDriverWait`
- Evidencia automática en reportes

### `utils/DriverManager.java`
Gestión del **WebDriver**:
- Configuración automática con WebDriverManager
- Soporte para Chrome, Firefox, Edge
- Patrón **Singleton**

### `pages/LoginPage.java`
Page Object del login:
- Selectores
- Métodos de interacción
- Validaciones

### `utils/WordReporter.java`
Generador de reportes en Word:
- Documento con **identidad visual corporativa**
- Tablas detalladas con pasos
- Evidencias (screenshots)

---

## ▶️ Ejecución de Pruebas

### Ejecutar todas las pruebas
```bash
mvn clean test
```

### Ejecutar por tags
```bash
# Un tag específico
mvn clean test "-Dcucumber.filter.tags=@TC02"

# Múltiples tags (OR)
mvn clean test "-Dcucumber.filter.tags=@smoke or @TC01"

# Excluir tags
mvn clean test "-Dcucumber.filter.tags=not @TC05"

# Tests negativos
mvn clean test "-Dcucumber.filter.tags=@negative"
```

### Diferentes navegadores
```bash
# Chrome (por defecto)
mvn clean test

# Firefox
mvn clean test -Dbrowser=firefox

# Edge
mvn clean test -Dbrowser=edge
```

---

## 📊 Reportes

### Reporte HTML de Cucumber
Se genera en:
```
target/cucumber-reports/cucumber.html
```
Abrir con:
```bash
# Windows
start target/cucumber-reports/cucumber.html

# Linux/Mac
open target/cucumber-reports/cucumber.html
```

### Reporte Word
Generado en `test-reports/` después de cada ejecución. Incluye:
- Información general del test
- Fecha y hora de ejecución
- Tabla de pasos
- Screenshots por cada paso
- Identidad visual corporativa

### Screenshots
Guardados en `screenshots/` con el formato:
```
[Número]_[Descripción]_[Timestamp].png
```

---

## 🎨 Identidad Visual

| Uso                   | Color HEX |
|------------------------|-----------|
| Headers y títulos      | `#1E3A8A` |
| Estados de éxito       | `#10B981` |
| Estados de fallo       | `#EF4444` |
| Bordes secundarios     | `#F3F4F6` |
| Texto principal        | `#111827` |

---

## 🧪 Casos de Prueba Disponibles

El framework incluye 10 casos para el módulo de Login:

| Tag        | Test ID | Descripción |
|------------|---------|-------------|
| @smoke     | TC01    | Login exitoso con credenciales válidas |
| @negative  | TC02    | Validar mensaje de error sin usuario |
| @negative  | TC03    | Validar mensaje de error sin contraseña |
| @negative  | TC04    | Validar mensajes de error sin credenciales |
| @functional| TC05    | Mostrar/ocultar contraseña |
| @functional| TC06    | Checkbox "Recordarme" |
| @negative  | TC07    | Usuario incorrecto |
| @negative  | TC08    | Contraseña incorrecta |
| @negative  | TC09    | Credenciales incorrectas |
| @functional| TC10    | Limpieza de campos al recargar |

---

## 🔍 Modo Depuración

- Ver navegador: comentar línea `--headless` en `DriverManager.java`
- Timeouts: cambiar `Duration.ofSeconds(30)` en `BasePage.java`

---

## 🛠️ Personalización

- **Agregar navegadores**: modificar `DriverManager.java`
- **Cambiar URL base**: editar `Urls.java`
- **Configurar esperas implícitas**:
  ```java
  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
  ```

---

## 📝 Agregar Nuevos Tests

1. Crear **Page Object** en `pages/`
2. Crear archivo `.feature` en `resources/features/`
3. Crear `Step Definitions` en `stepdefinitions/`

---

## 💡 Tips y Mejores Prácticas

- Nomenclatura: `TC01 - Descripción clara`
- Prioridad de selectores: `id > name > className > xpath`
- Un Page Object por módulo
- Selectores privados y finales

---

## 🚨 Solución de Problemas Comunes

- **ChromeDriver no encontrado** → `mvn clean install -U`
- **Element not clickable** → usar `wait.until()`
- **Screenshots no aparecen** → revisar uso de `execute()`
- **Tags no funcionan** → validar sintaxis de `-Dcucumber.filter.tags`

---

## 📦 Scripts Maven Útiles

```bash
# Limpiar y compilar
mvn clean compile

# Ejecutar tests
mvn test -DskipTests=false

# Compilar sin tests
mvn clean install -DskipTests

# Ejecutar con perfil
mvn clean test -P smoke

# Ver dependencias
mvn dependency:tree
```

---

## 🤝 Contribución

1. Crear rama:
```bash
git checkout -b feature/nueva-funcionalidad
```
2. Commit:
```bash
git commit -m 'feat: Agregar nueva funcionalidad'
```
3. Push:
```bash
git push origin feature/nueva-funcionalidad
```
4. Crear **Pull Request**

---

## 📧 Soporte

- **Email**: rodrigoingsis@gmail.com
- **Repositorio**: [selenium-automation-framework](https://github.com/rodrigoescutia/selenium-automation-framework)

---

## 📄 Licencia

Este proyecto es de uso interno de la empresa. **Todos los derechos reservados.**

---

<p align="center">
  <b>Desarrollado con ❤️ por Jose Rodrigo Escutia Rios</b>
</p>
<p align="center">
  <i>Framework Version: 1.0.0 | Última actualización: 2025</i>
</p>
