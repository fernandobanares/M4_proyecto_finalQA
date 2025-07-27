# Guía de Ejecución - Suite de Automatización

## 🚀 Comandos de Ejecución

### Ejecución Básica
```bash
# Ejecutar toda la suite
mvn test

# Ejecutar con limpieza previa
mvn clean test

# Ejecutar en modo silencioso
mvn test -q
```

### Ejecución por Clases de Test
```bash
# Tests de Registro
mvn test -Dtest=RegisterTests

# Tests de Login
mvn test -Dtest=LoginTests

# Tests de Inventario
mvn test -Dtest=InventoryTests
```

### Ejecución por Métodos Específicos
```bash
# Test específico de registro exitoso
mvn test -Dtest=RegisterTests#testSuccessfulRegistration

# Múltiples tests específicos
mvn test -Dtest=RegisterTests#testSuccessfulRegistration,testEmptyFirstName

# Tests de login con datos múltiples
mvn test -Dtest=LoginTests#testLoginWithMultipleData
```

### Ejecución Cross-Browser
```bash
# Solo Chrome
mvn test -Dbrowser=chrome

# Solo Firefox
mvn test -Dbrowser=firefox

# Usando TestNG XML (ambos navegadores)
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

### Ejecución con Reportes
```bash
# Generar reportes Surefire
mvn test surefire-report:report

# Ver reportes en navegador
open target/site/surefire-report.html
```

## 📊 Interpretación de Resultados

### Salida de Consola
```
[INFO] Tests run: 12, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### Códigos de Estado
- **Tests run**: Total de tests ejecutados
- **Failures**: Tests que fallaron por assertions
- **Errors**: Tests que fallaron por excepciones
- **Skipped**: Tests omitidos

### Ubicación de Reportes
```
target/
├── surefire-reports/           # Reportes TestNG
│   ├── index.html             # Reporte principal
│   ├── testng-results.xml     # Resultados XML
│   └── TEST-*.xml             # Resultados por clase
├── site/
│   └── surefire-report.html   # Reporte Maven
└── screenshots/               # Capturas de error
```

## 🐛 Solución de Problemas Comunes

### Error: "No tests were executed"
```bash
# Verificar que las clases de test estén en el classpath
mvn test-compile

# Verificar nomenclatura de tests (deben terminar en *Test.java)
ls src/test/java/**/*Test.java
```

### Error: "WebDriver executable not found"
```bash
# WebDriverManager debería manejar esto automáticamente
# Si persiste, verificar conexión a internet
mvn dependency:resolve
```

### Error: "Element not found"
```bash
# Aumentar timeouts en config.properties
implicit.wait=15
explicit.wait=30

# Verificar que la aplicación esté disponible
curl -I https://www.saucedemo.com/v1/
```

### Error: "Permission denied" en screenshots
```bash
# En Linux/Mac
chmod 755 src/test/resources/screenshots/

# En Windows, verificar permisos de escritura
```

## 📈 Métricas de Ejecución

### Tiempo Esperado por Test
- **RegisterTests**: ~2-3 minutos por test
- **LoginTests**: ~1-2 minutos por test  
- **InventoryTests**: ~1-2 minutos por test
- **Suite completa**: ~15-20 minutos

### Recursos del Sistema
- **RAM**: Mínimo 4GB recomendado
- **CPU**: Dual-core mínimo
- **Disco**: 500MB para reportes y capturas

## 🔄 Integración Continua

### GitHub Actions Example
```yaml
name: Selenium Tests
on: [push, pull_request]
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
    - uses: actions/checkout@v2
    - name: Set up JDK 11
      uses: actions/setup-java@v2
      with:
        java-version: '11'
    - name: Run tests
      run: mvn clean test
    - name: Generate report
      run: mvn surefire-report:report
```

### Jenkins Pipeline
```groovy
pipeline {
    agent any
    stages {
        stage('Test') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target/site',
                    reportFiles: 'surefire-report.html',
                    reportName: 'Test Report'
                ])
            }
        }
    }
}
```

## 📝 Logs y Debug

### Habilitar Logs Detallados
```bash
# Maven debug
mvn test -X

# TestNG verbose
mvn test -Dtestng.verbose=2

# Selenium debug
mvn test -Dselenium.debug=true
```

### Ubicación de Logs
```
target/
├── surefire-reports/
│   └── *.txt                  # Logs de ejecución
└── maven.log                  # Log de Maven
```