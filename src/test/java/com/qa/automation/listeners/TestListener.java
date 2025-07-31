package com.qa.automation.listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ITestContext;
import com.qa.automation.utils.ScreenshotUtils;
import org.openqa.selenium.WebDriver;
import java.lang.reflect.Field;

public class TestListener implements ITestListener {
    
    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("\n=== INICIANDO TEST: " + result.getMethod().getMethodName() + " ===");
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("✓ TEST EXITOSO: " + result.getMethod().getMethodName());
        long duration = result.getEndMillis() - result.getStartMillis();
        System.out.println("Tiempo: " + duration + "ms");
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("✗ TEST FALLIDO: " + result.getMethod().getMethodName());
        System.out.println("Error: " + result.getThrowable().getMessage());
        
        try {
            Object testInstance = result.getInstance();
            Field driverField = testInstance.getClass().getSuperclass().getDeclaredField("driver");
            driverField.setAccessible(true);
            WebDriver driver = (WebDriver) driverField.get(testInstance);
            
            if (driver != null) {
                String testName = result.getMethod().getMethodName();
                ScreenshotUtils.captureScreenshot(driver, testName + "_FAILED");
            }
        } catch (Exception e) {
            System.out.println("No se pudo capturar screenshot: " + e.getMessage());
        }
    }
    
    @Override
    public void onFinish(ITestContext context) {
        System.out.println("\n=== RESUMEN DE EJECUCIÓN ===");
        System.out.println("Suite: " + context.getName());
        System.out.println("Total tests: " + context.getAllTestMethods().length);
        System.out.println("✓ Exitosos: " + context.getPassedTests().size());
        System.out.println("✗ Fallidos: " + context.getFailedTests().size());
        System.out.println("⚠ Omitidos: " + context.getSkippedTests().size());
        System.out.println("========================\n");
    }
}
