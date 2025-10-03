package com.selenium.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static int stepCounter = 0;
    private static WordReporter reporter = WordReporter.getInstance();

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    protected void execute(String description, Runnable action) {
        stepCounter++;
        System.out.println("Paso " + stepCounter + ": " + description);
        boolean passed = true;
        String screenshotPath = null;

        try {
            action.run();
            screenshotPath = takeScreenshot(description);
        } catch (Exception e) {
            passed = false;
            System.err.println("Error en: " + description);
            screenshotPath = takeScreenshot("ERROR_" + description);
            throw e;
        } finally {
            // Agregar el paso al reporte
            reporter.addStep(description, screenshotPath, passed);
        }
    }

    protected String takeScreenshot(String stepName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File source = screenshot.getScreenshotAs(OutputType.FILE);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = "screenshots/" + stepCounter + "_" + stepName.replaceAll("[^a-zA-Z0-9]", "_") + "_" + timestamp + ".png";
            File destination = new File(fileName);

            // Crear directorio si no existe
            destination.getParentFile().mkdirs();

            FileUtils.copyFile(source, destination);
            System.out.println("Screenshot guardado: " + fileName);

            return fileName; // Retornar la ruta del archivo
        } catch (IOException e) {
            System.err.println("No se pudo guardar el screenshot: " + e.getMessage());
            return null;
        }
    }

    protected WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        waitForElement(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return waitForElement(locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static void resetStepCounter() {
        stepCounter = 0;
    }

    public static void startReport(String testName) {
        reporter.startTest(testName);
    }

    public static void endReport() {
        reporter.endTest();
    }
}