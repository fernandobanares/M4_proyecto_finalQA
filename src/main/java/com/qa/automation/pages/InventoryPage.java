
package com.qa.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

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
            Thread.sleep(1000);
            click(menuButton);
            Thread.sleep(1000);
            click(logoutLink);
        } catch (Exception e) {
            System.out.println("Error en logout: " + e.getMessage());
        }
    }
}

