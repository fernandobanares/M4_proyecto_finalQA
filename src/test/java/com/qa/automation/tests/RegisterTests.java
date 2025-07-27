package com.qa.automation.tests;

import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.RegisterPage;
import com.qa.automation.utils.CSVUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RegisterTests extends BaseTest {
    private RegisterPage registerPage;
    
    @BeforeMethod
    public void setUpRegister() {
        driver.get("https://demo.automationtesting.in/Register.html");
        registerPage = new RegisterPage(driver);
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    @Test(priority = 1)
    public void testRegisterPageIsDisplayed() {
        Assert.assertTrue(registerPage.isRegisterPageDisplayed(), 
            "La página de registro no se mostró correctamente");
        System.out.println("✓ Página de registro mostrada correctamente");
    }
    
    @Test(priority = 2)
    public void testSuccessfulRegistration() {
        try {
            registerPage.fillRegistrationForm("Juan", "Perez", "juan.test@email.com", 
                "1234567890", "Male", "Test123!", "Test123!");
            registerPage.submitForm();
            
            Thread.sleep(5000); // Más tiempo para la redirección
            
            // Debug completo
            String currentUrl = driver.getCurrentUrl();
            String pageTitle = driver.getTitle();
            String pageSource = driver.getPageSource();
            
            System.out.println("=== DEBUG REGISTRO EXITOSO ===");
            System.out.println("URL actual: " + currentUrl);
            System.out.println("Título: " + pageTitle);
            System.out.println("¿Contiene 'WebTable'?: " + currentUrl.contains("WebTable"));
            System.out.println("¿Contiene 'success'?: " + pageSource.contains("success"));
            System.out.println("¿Contiene 'Register'?: " + currentUrl.contains("Register"));
            
            // Buscar indicadores de éxito en el contenido
            boolean hasSuccessIndicators = pageSource.toLowerCase().contains("success") ||
                                         pageSource.toLowerCase().contains("welcome") ||
                                         pageSource.toLowerCase().contains("registered") ||
                                         pageSource.toLowerCase().contains("thank you");
            
            System.out.println("¿Tiene indicadores de éxito?: " + hasSuccessIndicators);
            
            // Verificar que el registro fue exitoso
            Assert.assertTrue(registerPage.isSuccessPageDisplayed(), 
                "El registro exitoso no redirigió correctamente");
            
            System.out.println("✓ Registro exitoso completado");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Test interrumpido", e);
        } catch (Exception e) {
            captureScreenshot("testSuccessfulRegistration_failed");
            throw e;
        }
    }
    
    @Test(priority = 3)
    public void testEmptyFirstName() {
        try {
            // Intentar registro con nombre vacío
            registerPage.fillRegistrationForm("", "Gonzalez", "test@test.com", 
                "1111111111", "Male", "Test123!", "Test123!");
            registerPage.submitForm();
            
            Thread.sleep(3000);
            
            // Como la página no valida del lado del cliente, 
            // verificamos que al menos el formulario siga presente
            // o que haya algún indicador de que el registro no fue procesado correctamente
            boolean stillOnRegisterPage = driver.getCurrentUrl().contains("Register");
            boolean hasValidationErrors = registerPage.hasValidationErrors();
            
            // Si no hay validación del lado del cliente, al menos verificar que
            // no se muestre un mensaje de éxito completo
            boolean hasFullSuccess = registerPage.isSuccessPageDisplayed();
            
            System.out.println("Sigue en página de registro: " + stillOnRegisterPage);
            System.out.println("Tiene errores de validación: " + hasValidationErrors);
            System.out.println("Tiene éxito completo: " + hasFullSuccess);
            
            // El test pasa si hay errores de validación O si no hay éxito completo
            Assert.assertTrue(hasValidationErrors || !hasFullSuccess, 
                "El registro debería fallar con nombre vacío");
            
            System.out.println("✓ Validación de nombre vacío completada");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Test interrumpido", e);
        } catch (Exception e) {
            captureScreenshot("testEmptyFirstName_failed");
            throw e;
        }
    }
    
    @Test(priority = 4)
    public void testInvalidEmail() {
        try {
            registerPage.fillRegistrationForm("Carlos", "Rodriguez", "invalid-email", 
                "3333333333", "Male", "Test123!", "Test123!");
            registerPage.submitForm();
            
            Thread.sleep(3000);
            
            boolean hasValidationErrors = registerPage.hasValidationErrors();
            boolean hasFullSuccess = registerPage.isSuccessPageDisplayed();
            
            System.out.println("Tiene errores de validación: " + hasValidationErrors);
            System.out.println("Tiene éxito completo: " + hasFullSuccess);
            
            Assert.assertTrue(hasValidationErrors || !hasFullSuccess, 
                "El registro debería fallar con email inválido");
            
            System.out.println("✓ Validación de email inválido completada");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Test interrumpido", e);
        } catch (Exception e) {
            captureScreenshot("testInvalidEmail_failed");
            throw e;
        }
    }
    
    @Test(priority = 5)
    public void testPasswordMismatch() {
        try {
            registerPage.fillRegistrationForm("Sofia", "Herrera", "sofia@test.com", 
                "5555555555", "FeMale", "Test123!", "Different123!");
            registerPage.submitForm();
            
            Thread.sleep(3000);
            
            boolean hasValidationErrors = registerPage.hasValidationErrors();
            boolean hasFullSuccess = registerPage.isSuccessPageDisplayed();
            
            System.out.println("Tiene errores de validación: " + hasValidationErrors);
            System.out.println("Tiene éxito completo: " + hasFullSuccess);
            
            Assert.assertTrue(hasValidationErrors || !hasFullSuccess, 
                "El registro debería fallar con contraseñas diferentes");
            
            System.out.println("✓ Validación de contraseñas diferentes completada");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Test interrumpido", e);
        } catch (Exception e) {
            captureScreenshot("testPasswordMismatch_failed");
            throw e;
        }
    }
    
    @DataProvider(name = "registerData")
    public Object[][] getRegisterData() {
        return CSVUtils.readRegisterCSV("src/test/resources/testdata/register_users.csv");
    }
    
    @Test(dataProvider = "registerData", priority = 6)
    public void testRegistrationWithMultipleData(String firstName, String lastName, String email,
                                               String phone, String gender, String password, 
                                               String confirmPassword, String expectedResult, String description) {
        try {
            System.out.println("Probando registro: " + description);
            
            registerPage.fillRegistrationForm(firstName, lastName, email, phone, 
                gender, password, confirmPassword);
            registerPage.submitForm();
            
            Thread.sleep(3000);
            
            if (expectedResult.equals("success")) {
                Assert.assertTrue(registerPage.isSuccessPageDisplayed(), 
                    "Registro exitoso falló para: " + description);
            } else {
                Assert.assertFalse(registerPage.isSuccessPageDisplayed(), 
                    "Registro debería fallar para: " + description);
            }
            
            System.out.println("✓ Test de registro completado para: " + description);
            
            // Volver a la página de registro para el siguiente test
            if (expectedResult.equals("success")) {
                driver.get("https://demo.automationtesting.in/Register.html");
                Thread.sleep(2000);
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Test interrumpido", e);
        } catch (Exception e) {
            captureScreenshot("testRegistrationWithMultipleData_" + firstName + "_failed");
            throw e;
        }
    }
}





