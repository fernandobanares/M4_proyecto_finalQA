
package com.qa.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {
    
    @FindBy(id = "user-name")
    private WebElement usernameField;
    
    @FindBy(id = "password")
    private WebElement passwordField;
    
    @FindBy(id = "login-button")
    private WebElement loginButton;
    
    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;
    
    @FindBy(className = "login_logo")
    private WebElement loginLogo;
    
    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    public void enterUsername(String username) {
        sendKeys(usernameField, username);
    }
    
    public void enterPassword(String password) {
        sendKeys(passwordField, password);
    }
    
    public void clickLoginButton() {
        click(loginButton);
    }
    
    public String getErrorMessage() {
        return getText(errorMessage);
    }
    
    public boolean isLoginPageDisplayed() {
        try {
            Thread.sleep(2000);
            String currentUrl = driver.getCurrentUrl();
            System.out.println("URL actual: " + currentUrl);
            
            // Verificar URL
            boolean urlIsCorrect = currentUrl.contains("saucedemo.com") && 
                                  !currentUrl.contains("inventory");
            
            // Verificar elementos de login (usar selectores que sí existen)
            boolean hasLoginElements = false;
            try {
                hasLoginElements = !driver.findElements(By.id("user-name")).isEmpty() &&
                                  !driver.findElements(By.id("password")).isEmpty() &&
                                  !driver.findElements(By.id("login-button")).isEmpty();
            } catch (Exception e) {
                System.out.println("Error verificando elementos: " + e.getMessage());
            }
            
            boolean result = urlIsCorrect && hasLoginElements;
            System.out.println("Página de login detectada: " + result);
            
            return result;
            
        } catch (Exception e) {
            System.out.println("Error verificando página de login: " + e.getMessage());
            return false;
        }
    }
    
    public InventoryPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        return new InventoryPage(driver);
    }
}

