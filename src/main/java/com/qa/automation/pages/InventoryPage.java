
package com.qa.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;

public class InventoryPage extends BasePage {
    
    @FindBy(css = "span.title")
    private WebElement pageTitle;
    
    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;
    
    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;
    
    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;
    
    @FindBy(css = "[data-test='shopping-cart-link']")
    private WebElement cartLink;
    
    public InventoryPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public boolean isInventoryPageDisplayed() {
        try {
            Thread.sleep(2000); // Espera adicional
            waitForElement(pageTitle);
            String titleText = pageTitle.getText();
            System.out.println("Título encontrado: '" + titleText + "'");
            return pageTitle.isDisplayed() && titleText.equals("Products");
        } catch (Exception e) {
            System.out.println("Error al verificar página de inventario: " + e.getMessage());
            return false;
        }
    }
    
    public String getPageTitle() {
        try {
            waitForElement(pageTitle);
            return pageTitle.getText();
        } catch (Exception e) {
            System.out.println("Error al obtener título: " + e.getMessage());
            return "";
        }
    }
    
    public int getInventoryItemsCount() {
        try {
            Thread.sleep(1000);
            return inventoryItems.size();
        } catch (Exception e) {
            System.out.println("Error al contar items: " + e.getMessage());
            return 0;
        }
    }
    
    public void logout() {
        try {
            System.out.println("=== INICIANDO LOGOUT ===");
            
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            
            // Click en el menú hamburguesa usando JavaScript
            WebElement menu = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("react-burger-menu-btn")));
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", menu);
            System.out.println("Menu hamburguesa clickeado con JavaScript");
            Thread.sleep(3000); // Más tiempo para que se abra el menú
            
            // Buscar el logout link con diferentes estrategias
            WebElement logoutElement = null;
            try {
                // Intentar con ID
                logoutElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
            } catch (Exception e1) {
                try {
                    // Intentar con texto
                    logoutElement = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Logout")));
                } catch (Exception e2) {
                    // Intentar con xpath más general
                    logoutElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Logout')]")));
                }
            }
            
            // Click en logout usando JavaScript también
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", logoutElement);
            System.out.println("Logout clickeado con JavaScript");
            Thread.sleep(4000); // Más tiempo para la redirección
            
            System.out.println("Logout completado, URL actual: " + driver.getCurrentUrl());
            
        } catch (Exception e) {
            System.out.println("Error durante logout: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Fallo en logout", e);
        }
    }

    public boolean isLoginPageDisplayed() {
        try {
            Thread.sleep(3000); // Más tiempo de espera
            String currentUrl = driver.getCurrentUrl();
            System.out.println("=== VERIFICANDO PÁGINA DE LOGIN ===");
            System.out.println("URL actual: " + currentUrl);
            
            // Verificar URL - debe contener saucedemo pero NO inventory
            boolean urlIsCorrect = currentUrl.contains("saucedemo.com") && 
                                  !currentUrl.contains("inventory");
            
            System.out.println("URL es correcta: " + urlIsCorrect);
            
            // Verificar elementos de login
            boolean hasLoginElements = false;
            try {
                hasLoginElements = !driver.findElements(By.id("user-name")).isEmpty() &&
                                  !driver.findElements(By.id("password")).isEmpty() &&
                                  !driver.findElements(By.id("login-button")).isEmpty();
                System.out.println("Tiene elementos de login: " + hasLoginElements);
            } catch (Exception e) {
                System.out.println("Error verificando elementos: " + e.getMessage());
            }
            
            boolean result = urlIsCorrect && hasLoginElements;
            System.out.println("Resultado final: " + result);
            
            return result;
            
        } catch (Exception e) {
            System.out.println("Error verificando página de login: " + e.getMessage());
            return false;
        }
    }
}
