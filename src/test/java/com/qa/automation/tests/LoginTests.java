
package com.qa.automation.tests;

import org.testng.annotations.*;
import org.testng.Assert;
import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.LoginPage;
import com.qa.automation.pages.InventoryPage;
import com.qa.automation.utils.CSVUtils;
import com.qa.automation.config.ConfigManager;

public class LoginTests extends BaseTest {
    
    private LoginPage loginPage;
    
    @BeforeMethod
    public void initializePages() {
        loginPage = new LoginPage(driver);
        // Asegurar que estamos en la página de login
        if (!loginPage.isLoginPageDisplayed()) {
            driver.get(ConfigManager.getBaseUrl());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    @Test(priority = 1)
    public void testLoginPageIsDisplayed() {
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), 
            "La página de login no se muestra correctamente");
        System.out.println("✓ Página de login mostrada correctamente");
    }
    
    @Test(priority = 2)
    public void testSuccessfulLogin() {
        try {
            InventoryPage inventoryPage = loginPage.login("standard_user", "secret_sauce");
            Assert.assertTrue(inventoryPage.isInventoryPageDisplayed(), 
                "No se pudo acceder a la página de inventario");
            Assert.assertEquals(inventoryPage.getPageTitle(), "Products", 
                "El título de la página no es el esperado");
            System.out.println("✓ Login exitoso completado");
        } catch (Exception e) {
            captureScreenshot("testSuccessfulLogin_failed");
            throw e;
        }
    }
    
    @Test(priority = 3)
    public void testLockedUserLogin() {
        try {
            loginPage.login("locked_out_user", "secret_sauce");
            String errorMessage = loginPage.getErrorMessage();
            Assert.assertTrue(errorMessage.contains("locked out"), 
                "No se muestra el mensaje de usuario bloqueado");
            System.out.println("✓ Validación de usuario bloqueado correcta");
        } catch (Exception e) {
            captureScreenshot("testLockedUserLogin_failed");
            throw e;
        }
    }
    
    @Test(priority = 4)
    public void testInvalidCredentials() {
        try {
            loginPage.login("invalid_user", "wrong_password");
            String errorMessage = loginPage.getErrorMessage();
            Assert.assertTrue(errorMessage.contains("Username and password do not match"), 
                "No se muestra el mensaje de credenciales inválidas");
            System.out.println("✓ Validación de credenciales inválidas correcta");
        } catch (Exception e) {
            captureScreenshot("testInvalidCredentials_failed");
            throw e;
        }
    }
    
    @Test(priority = 5)
    public void testEmptyUsername() {
        try {
            loginPage.login("", "secret_sauce");
            String errorMessage = loginPage.getErrorMessage();
            Assert.assertTrue(errorMessage.contains("Username is required"), 
                "No se muestra el mensaje de usuario requerido");
            System.out.println("✓ Validación de usuario vacío correcta");
        } catch (Exception e) {
            captureScreenshot("testEmptyUsername_failed");
            throw e;
        }
    }
    
    @Test(priority = 6)
    public void testEmptyPassword() {
        try {
            loginPage.login("standard_user", "");
            String errorMessage = loginPage.getErrorMessage();
            Assert.assertTrue(errorMessage.contains("Password is required"), 
                "No se muestra el mensaje de contraseña requerida");
            System.out.println("✓ Validación de contraseña vacía correcta");
        } catch (Exception e) {
            captureScreenshot("testEmptyPassword_failed");
            throw e;
        }
    }
    
    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        return CSVUtils.readCSV("src/test/resources/testdata/users.csv");
    }
    
    @Test(dataProvider = "loginData", priority = 7)
    public void testLoginWithMultipleData(String username, String password, 
                                        String expectedResult, String description) {
        try {
            System.out.println("Probando: " + description);
            
            if (expectedResult.equals("success")) {
                InventoryPage inventoryPage = loginPage.login(username, password);
                Thread.sleep(2000); // Espera adicional
                Assert.assertTrue(inventoryPage.isInventoryPageDisplayed(), 
                    "Login exitoso falló para: " + description);
                
                // Hacer logout para el siguiente test
                inventoryPage.logout();
                Thread.sleep(2000);
                
                // Verificar que volvimos a login
                driver.get(ConfigManager.getBaseUrl());
                Thread.sleep(1000);
            } else {
                loginPage.login(username, password);
                String errorMessage = loginPage.getErrorMessage();
                Assert.assertFalse(errorMessage.isEmpty(), 
                    "Se esperaba un mensaje de error para: " + description);
            }
            
            System.out.println("✓ Test completado para: " + description);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Test interrumpido", e);
        } catch (Exception e) {
            captureScreenshot("testLoginWithMultipleData_" + username + "_failed");
            throw e;
        }
    }
    
    @AfterMethod
    public void cleanupAfterTest() {
        try {
            // Si estamos en la página de inventario, hacer logout
            if (driver.getCurrentUrl().contains("inventory")) {
                InventoryPage inventoryPage = new InventoryPage(driver);
                inventoryPage.logout();
                Thread.sleep(1000);
            }
            // Volver a la página de login
            driver.get(ConfigManager.getBaseUrl());
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Error en cleanup: " + e.getMessage());
        }
    }
}

