@echo off
echo ===================================
echo  EJECUTANDO TESTS Y GENERANDO REPORTES
echo ===================================

echo Limpiando proyecto...
mvn clean

echo.
echo Ejecutando tests...
mvn test

echo.
echo Generando reportes HTML...
mvn surefire-report:report

echo.
echo ===================================
echo  REPORTES DISPONIBLES EN:
echo ===================================
echo - TestNG Report: target\surefire-reports\index.html
echo - Emailable Report: target\surefire-reports\emailable-report.html
echo - Maven Report: target\site\surefire-report.html
echo - Screenshots: src\test\resources\screenshots\
echo ===================================

pause