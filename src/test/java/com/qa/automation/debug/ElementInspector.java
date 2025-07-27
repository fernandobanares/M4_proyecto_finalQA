package com.qa.automation.debug;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.List;

public class ElementInspector {
    
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        
        try {
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.get("https://www.saucedemo.com/");
            
            // Login
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();
            
            Thread.sleep(3000);
            
            // Inspect title element
            System.out.println("=== INSPECTING TITLE ELEMENT ===");
            try {
                WebElement titleByClass = driver.findElement(By.className("title"));
                System.out.println("Found by className 'title': " + titleByClass.getText());
            } catch (Exception e) {
                System.out.println("NOT found by className 'title'");
            }
            
            try {
                WebElement titleByDataTest = driver.findElement(By.cssSelector("[data-test='title']"));
                System.out.println("Found by data-test='title': " + titleByDataTest.getText());
            } catch (Exception e) {
                System.out.println("NOT found by data-test='title'");
            }
            
            try {
                WebElement titleBySpan = driver.findElement(By.cssSelector("span.title"));
                System.out.println("Found by span.title: " + titleBySpan.getText());
            } catch (Exception e) {
                System.out.println("NOT found by span.title");
            }
            
            // Inspect inventory items
            System.out.println("\n=== INSPECTING INVENTORY ITEMS ===");
            try {
                List<WebElement> itemsByClass = driver.findElements(By.className("inventory_item"));
                System.out.println("Found " + itemsByClass.size() + " items by className 'inventory_item'");
            } catch (Exception e) {
                System.out.println("NOT found by className 'inventory_item'");
            }
            
            try {
                List<WebElement> itemsByDataTest = driver.findElements(By.cssSelector("[data-test='inventory-item']"));
                System.out.println("Found " + itemsByDataTest.size() + " items by data-test='inventory-item'");
            } catch (Exception e) {
                System.out.println("NOT found by data-test='inventory-item'");
            }
            
            // Inspect menu button
            System.out.println("\n=== INSPECTING MENU BUTTON ===");
            try {
                WebElement menuBtn = driver.findElement(By.id("react-burger-menu-btn"));
                System.out.println("Found menu button by ID: " + menuBtn.isDisplayed());
            } catch (Exception e) {
                System.out.println("NOT found menu button by ID 'react-burger-menu-btn'");
            }
            
            // Print page source for debugging
            System.out.println("\n=== PAGE TITLE ===");
            System.out.println("Page title: " + driver.getTitle());
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}