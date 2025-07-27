# Suite de Automatización Funcional - Selenium WebDriver

## 📋 Descripción del Proyecto

Suite de automatización funcional desarrollada con Selenium WebDriver para validar los flujos críticos de registro de usuarios e inicio de sesión de una aplicación web. El proyecto implementa el patrón Page Object Model (POM) y soporta ejecución cross-browser.

## 🎯 Objetivos

- Validar formularios de registro (campos obligatorios, reglas de negocio, mensajes de error)
- Validar inicio de sesión (credenciales válidas/inválidas, bloqueos)
- Generar evidencias de ejecución (capturas, logs, reportes)
- Establecer base automatizada para pruebas de regresión

## 🏗️ Arquitectura del Proyecto

```
src/
├── main/java/com/qa/automation/
│   ├── config/
│   │   └── ConfigManager.java          # Gestión de configuraciones
│   ├── pages/
│   │   ├── LoginPage.java              # POM - Página de Login
│   │   ├── RegisterPage.java           # POM - Página de Registro
│   │   └── InventoryPage.java          # POM - Página de Inventario
│   └── utils/
│       ├── CSVUtils.java               # Utilidades para CSV
│       ├── ExcelUtils.java             # Utilidades para Excel
│       └── ScreenshotUtils.java        # Captura de pantallas
├── test/java/com/qa/automation/
│   ├── base/
│   │   └── BaseTest.java               # Clase base para tests
│   └── tests/
│       ├── RegisterTests.java          # Tests de registro
│       ├── LoginTests.java             # Tests de login
│       └── InventoryTests.java         # Tests de inventario
└── test/resources/
    ├── config.properties               # Configuraciones
    ├── testng.xml                      # Suite de TestNG
    ├── testdata/                       # Datos de prueba
    │   ├── login_data.csv
    │   ├── register_data.csv
    │   └── users.xlsx
    ├── reports/                        # Reportes generados
    └── screenshots/                    # Capturas de pantalla
```

## 🛠️ Tecnologías Utilizadas

- **Java 11** - Lenguaje de programación
- **Selenium WebDriver 4.15.0** - Automatización web
- **TestNG 7.8.0** - Framework de testing
- **WebDriverManager 5.6.2** - Gestión automática de drivers
- **ExtentReports 5.1.1** - Reportes HTML
- **Apache POI 5.2.4** - Manejo de archivos Excel
- **Apache Commons CSV 1.10.0** - Manejo de archivos CSV
- **Maven** - Gestión de dependencias

## 📦 Instalación y Configuración

### Prerrequisitos
- Java 11 o superior
- Maven 3.6 o superior
- Chrome y Firefox instalados

### Pasos de instalación

1. **Clonar el repositorio**
```bash
git clone <url-del-repositorio>
cd selenium-functional-suite
```

2. **Instalar dependencias**
```bash
mvn clean install
```

3. **Verificar configuración**
```bash
mvn test -Dtest=BaseTest
```

## 🚀 Ejecución de Pruebas

### Ejecutar toda la suite
```bash
mvn test
```

### Ejecutar tests específicos
```bash
# Tests de registro
mvn test -Dtest=RegisterTests

# Tests de login
mvn test -Dtest=LoginTests

# Tests de inventario
mvn test -Dtest=InventoryTests
```

### Ejecutar con navegador específico
```bash
# Solo Chrome
mvn test -Dbrowser=chrome

# Solo Firefox
mvn test -Dbrowser=firefox
```

### Ejecutar con TestNG XML
```bash
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

## 📊 Generación de Reportes

### Reporte Surefire (Maven)
```bash
mvn surefire-report:report
```
Los reportes se generan en: `target/site/surefire-report.html`

### Reportes TestNG
Los reportes TestNG se generan automáticamente en: `target/surefire-reports/`

### Capturas de pantalla
Las capturas se guardan automáticamente en: `src/test/resources/screenshots/`

## 🧪 Escenarios de Prueba

### Tests de Registro (RegisterTests)
1. **testRegisterPageIsDisplayed** - Verificar que la página se muestre correctamente
2. **testSuccessfulRegistration** - Registro exitoso con datos válidos
3. **testEmptyFirstName** - Validación con nombre vacío
4. **testInvalidEmail** - Validación con email inválido
5. **testPasswordMismatch** - Validación con contraseñas diferentes
6. **testRegistrationWithMultipleData** - Múltiples combinaciones de datos

### Tests de Login (LoginTests)
1. **testSuccessfulLogin** - Login exitoso
2. **testInvalidCredentials** - Credenciales inválidas
3. **testEmptyCredentials** - Campos vacíos
4. **testLoginWithMultipleData** - Múltiples combinaciones

### Tests de Inventario (InventoryTests)
1. **testInventoryPageElements** - Validar elementos de la página
2. **testInventoryItemsCount** - Contar productos disponibles
3. **testLogoutFunctionality** - Funcionalidad de logout

## 📁 Datos de Prueba

### Archivos CSV
- `login_data.csv` - Datos para tests de login
- `register_data.csv` - Datos para tests de registro

### Archivo Excel
- `users.xlsx` - Datos de usuarios para múltiples escenarios

### Formato de datos
```csv
username,password,expected_result,description
standard_user,secret_sauce,success,Usuario válido
invalid_user,wrong_password,failure,Credenciales inválidas
```

## 🔧 Configuración

### config.properties
```properties
# URLs de la aplicación
base.url=https://www.saucedemo.com/v1/
register.url=https://demo.automationtesting.in/Register.html

# Configuración de timeouts
implicit.wait=10
explicit.wait=20

# Configuración de navegadores
default.browser=chrome
headless.mode=false

# Rutas de reportes y capturas
reports.path=src/test/resources/reports/
screenshots.path=src/test/resources/screenshots/
```

## 🌐 Cross-Browser Testing

El proyecto soporta ejecución en múltiples navegadores:

### Configuración en testng.xml
```xml
<test name="ChromeTests">
    <parameter name="browser" value="chrome"/>
    <classes>...</classes>
</test>

<test name="FirefoxTests">
    <parameter name="browser" value="firefox"/>
    <classes>...</classes>
</test>
```

## 📈 Métricas del Proyecto

- ✅ **Escenarios automatizados**: 6+
- ✅ **Navegadores soportados**: Chrome, Firefox
- ✅ **Combinaciones de datos**: 10+
- ✅ **Capturas automáticas**: En cada error
- ✅ **Páginas POM**: 3 (Login, Register, Inventory)

## 🐛 Solución de Problemas

### Error: WebDriver no encontrado
```bash
# Verificar que WebDriverManager esté configurado
mvn dependency:tree | grep webdrivermanager
```

### Error: Tests fallan por timeouts
```bash
# Aumentar timeouts en config.properties
implicit.wait=15
explicit.wait=30
```

### Error: Capturas no se guardan
```bash
# Verificar permisos de escritura en directorio screenshots
chmod 755 src/test/resources/screenshots/
```

## 👥 Contribución

1. Fork el proyecto
2. Crear rama feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crear Pull Request

## 📝 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE.md](LICENSE.md) para detalles.

## 📞 Contacto

- **Proyecto**: Suite de Automatización Funcional
- **Versión**: 1.0.0
- **Autor**: Equipo QA Automation