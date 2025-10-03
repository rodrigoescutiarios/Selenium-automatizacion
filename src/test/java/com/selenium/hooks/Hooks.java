package com.selenium.hooks;

import com.selenium.utils.DriverManager;
import com.selenium.utils.BasePage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("====================================");
        System.out.println("Iniciando escenario: " + scenario.getName());
        System.out.println("====================================");

        String browser = System.getProperty("browser", "chrome");
        DriverManager.setDriver(browser);
        BasePage.resetStepCounter();
        BasePage.startReport(scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        System.out.println("====================================");

        if (scenario.isFailed()) {
            System.out.println("Escenario FALLIDO: " + scenario.getName());

            if (DriverManager.getDriver() != null) {
                TakesScreenshot screenshot = (TakesScreenshot) DriverManager.getDriver();
                byte[] screenshotBytes = screenshot.getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshotBytes, "image/png", "Screenshot de falla");
            }
        } else {
            System.out.println("Escenario EXITOSO: " + scenario.getName());
        }

        System.out.println("====================================\n");

        BasePage.endReport();
        DriverManager.quitDriver();
    }
}