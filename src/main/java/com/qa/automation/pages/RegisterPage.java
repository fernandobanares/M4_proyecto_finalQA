package com.qa.automation.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.ElementClickInterceptedException;
import java.time.Duration;

public class RegisterPage {
    private WebDriver driver;
    private WebDriverWait wait;
    
    @FindBy(xpath = "//input[@placeholder='First Name']")
    private WebElement firstNameField;
    
    @FindBy(xpath = "//input[@placeholder='Last Name']")
    private WebElement lastNameField;
    
    @FindBy(xpath = "//input[@type='email']")
    private WebElement emailField;
    
    @FindBy(xpath = "//input[@type='tel']")
    private WebElement phoneField;
    
    @FindBy(xpath = "//input[@value='Male']")
    private WebElement maleRadio;
    
    @FindBy(xpath = "//input[@value='FeMale']")
    private WebElement femaleRadio;
    
    @FindBy(id = "firstpassword")
    private WebElement passwordField;
    
    @FindBy(id = "secondpassword")
    private WebElement confirmPasswordField;
    
    @FindBy(id = "submitbtn")
    private WebElement submitButton;
    
    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }
    
    public boolean isRegisterPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(firstNameField));
            return firstNameField.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public void fillRegistrationForm(String firstName, String lastName, String email, 
                                   String phone, String gender, String password, String confirmPassword) {
        if (!firstName.isEmpty()) {
            wait.until(ExpectedConditions.visibilityOf(firstNameField));
            firstNameField.clear();
            firstNameField.sendKeys(firstName);
        }
        
        if (!lastName.isEmpty()) {
            lastNameField.clear();
            lastNameField.sendKeys(lastName);
        }
        
        if (!email.isEmpty()) {
            emailField.clear();
            emailField.sendKeys(email);
        }
        
        if (!phone.isEmpty()) {
            phoneField.clear();
            phoneField.sendKeys(phone);
        }
        
        if (gender.equals("Male")) {
            maleRadio.click();
        } else if (gender.equals("FeMale")) {
            femaleRadio.click();
        }
        
        if (!password.isEmpty()) {
            passwordField.clear();
            passwordField.sendKeys(password);
        }
        
        if (!confirmPassword.isEmpty()) {
            confirmPasswordField.clear();
            confirmPasswordField.sendKeys(confirmPassword);
        }
    }
    
    public void submitForm() {
        try {
            System.out.println("=== INICIANDO SUBMIT ===");
            
            // Scroll al botón para asegurar que esté visible
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
            Thread.sleep(1000);

            // Intentar click normal primero
            try {
                wait.until(ExpectedConditions.elementToBeClickable(submitButton));
                System.out.println("Haciendo click en submit button...");
                submitButton.click();
            } catch (ElementClickInterceptedException e) {
                System.out.println("Click interceptado, usando JavaScript...");
                // Si falla, usar JavaScript click
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
            }
            
            System.out.println("Submit completado");
        } catch (Exception e) {
            System.out.println("Error en submit, usando JavaScript como último recurso...");
            // Como último recurso, usar JavaScript click
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
        }
    }
    
    public boolean isSuccessPageDisplayed() {
        try {
            System.out.println("=== VERIFICANDO PÁGINA DE ÉXITO ===");
            Thread.sleep(5000);
            
            String currentUrl = driver.getCurrentUrl();
            String pageSource = driver.getPageSource().toLowerCase();
            
            System.out.println("URL después del submit: " + currentUrl);
            
            boolean urlChanged = !currentUrl.contains("Register.html");
            boolean hasSuccessContent = pageSource.contains("success") ||
                                      pageSource.contains("thank you") ||
                                      pageSource.contains("registered");
            
            boolean result = urlChanged || hasSuccessContent;
            System.out.println("Resultado final: " + result);
            
            return result;
            
        } catch (Exception e) {
            System.out.println("Error verificando página de éxito: " + e.getMessage());
            return false;
        }
    }
    
    public boolean hasValidationErrors() {
        try {
            Thread.sleep(2000);
            String currentUrl = driver.getCurrentUrl();
            String pageSource = driver.getPageSource().toLowerCase();
            
            System.out.println("Verificando errores - URL: " + currentUrl);
            
            // Si seguimos en la página de registro, es probable que haya errores
            boolean stillOnRegister = currentUrl.contains("Register.html");
            
            // Buscar mensajes de error específicos
            boolean hasErrorMessages = pageSource.contains("error") ||
                                     pageSource.contains("invalid") ||
                                     pageSource.contains("required") ||
                                     pageSource.contains("please") ||
                                     pageSource.contains("must");
            
            // Verificar validación HTML5
            boolean hasHTML5Validation = checkHTML5Validation();
            
            // Verificar si los campos tienen estilos de error
            boolean hasErrorStyles = pageSource.contains("border-color: red") ||
                                   pageSource.contains("is-invalid") ||
                                   pageSource.contains("error-border");
            
            System.out.println("Sigue en registro: " + stillOnRegister);
            System.out.println("Mensajes de error: " + hasErrorMessages);
            System.out.println("Validación HTML5: " + hasHTML5Validation);
            System.out.println("Estilos de error: " + hasErrorStyles);
            
            boolean hasErrors = hasErrorMessages || hasHTML5Validation || hasErrorStyles;
            
            // Si sigue en la página de registro Y no hay éxito claro, asumir error
            if (stillOnRegister && !hasSuccessContent()) {
                hasErrors = true;
            }
            
            return hasErrors;
            
        } catch (Exception e) {
            System.out.println("Error verificando errores de validación: " + e.getMessage());
            return false;
        }
    }

    private boolean hasSuccessContent() {
        try {
            String pageSource = driver.getPageSource().toLowerCase();
            return pageSource.contains("success") ||
                   pageSource.contains("thank you") ||
                   pageSource.contains("registered");
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkHTML5Validation() {
        try {
            // Verificar validación HTML5 en campos requeridos
            String firstNameValidation = (String) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].validationMessage;", firstNameField);
            String emailValidation = (String) ((JavascriptExecutor) driver)
                .executeScript("return arguments[0].validationMessage;", emailField);
            
            return !firstNameValidation.isEmpty() || !emailValidation.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}








