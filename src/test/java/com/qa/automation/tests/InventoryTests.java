package com.qa.automation.tests;

import org.testng.annotations.*;
import org.testng.Assert;
import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.LoginPage;
import com.qa.automation.pages.InventoryPage;

public class InventoryTests extends BaseTest {
    
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    
    @BeforeMethod
    public void loginAndNavigate() {
        loginPage = new LoginPage(driver);
        inventoryPage = loginPage.login("standard_user", "secret_sauce");
    }
    
    @Test(priority = 1)
    public void testInventoryPageElements() {
        try {
            Assert.assertTrue(inventoryPage.isInventoryPageDisplayed(), 
                "La página de inventario no se muestra correctamente");
            Assert.assertEquals(inventoryPage.getPageTitle(), "Products", 
                "El título de la página no es correcto");
            Assert.assertTrue(inventoryPage.getInventoryItemsCount() > 0, 
                "No se encontraron productos en el inventario");
            System.out.println("✓ Elementos de la página de inventario validados");
        } catch (Exception e) {
            captureScreenshot("testInventoryPageElements_failed");
            throw e;
        }
    }
    
    @Test(priority = 2)
    public void testLogoutFunctionality() {
        try {
            inventoryPage.logout();
            Assert.assertTrue(loginPage.isLoginPageDisplayed(), 
                "No se redirigió correctamente a la página de login");
            System.out.println("✓ Funcionalidad de logout validada");
        } catch (Exception e) {
            captureScreenshot("testLogoutFunctionality_failed");
            throw e;
        }
    }
    
    @Test(priority = 3)
    public void testInventoryItemsCount() {
        try {
            int itemsCount = inventoryPage.getInventoryItemsCount();
            Assert.assertEquals(itemsCount, 6, 
                "El número de productos no es el esperado");
            System.out.println("✓ Cantidad de productos validada: " + itemsCount);
        } catch (Exception e) {
            captureScreenshot("testInventoryItemsCount_failed");
            throw e;
        }
    }
}

