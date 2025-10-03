package com.selenium.pages;

import com.selenium.utils.BasePage;
import com.selenium.contants.Urls;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {
    // Selectores - Inputs y botones
    private final By usernameInput = By.id("qa-username-input");
    private final By passwordInput = By.id("qa-password-input");
    private final By loginButton = By.id("qa-login-button");
    private final By rememberMeCheckbox = By.xpath("//span[@class='qa-checkmark']");
    private final By showPasswordButton = By.xpath("//button[normalize-space()='MOSTRAR' or normalize-space()='OCULTAR']");

    // Selectores - Mensajes de error
    private final By usernameError = By.id("qa-username-error");
    private final By passwordError = By.id("qa-password-error");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void goTo() {
        execute("Navegar a la página de login", () -> {
            driver.get(Urls.BASE_URL);
        });
    }

    public void fillUsername(String username) {
        execute("Ingresar usuario: " + username, () -> {
            type(usernameInput, username);
        });
    }

    public void fillPassword(String password) {
        execute("Ingresar contraseña", () -> {
            type(passwordInput, password);
        });
    }

    public void clickLoginButton() {
        execute("Hacer clic en botón login", () -> {
            click(loginButton);
        });
    }

    public void clickRememberMe() {
        execute("Hacer clic en checkbox Recordarme", () -> {
            click(rememberMeCheckbox);
        });
    }

    public void clickShowPassword() {
        execute("Hacer clic en botón Mostrar/Ocultar contraseña", () -> {
            click(showPasswordButton);
        });
    }

    public void clearUsername() {
        execute("Limpiar campo de usuario", () -> {
            driver.findElement(usernameInput).clear();
        });
    }

    public void clearPassword() {
        execute("Limpiar campo de contraseña", () -> {
            driver.findElement(passwordInput).clear();
        });
    }

    public void verifyUsernameErrorMessage() {
        execute("Validar mensaje de error de usuario requerido", () -> {
            String actualMessage = getText(usernameError);
            assert actualMessage.contains("El usuario es requerido") :
                    "Mensaje esperado no encontrado. Actual: " + actualMessage;
        });
    }

    public void verifyPasswordErrorMessage() {
        execute("Validar mensaje de error de contraseña requerida", () -> {
            String actualMessage = getText(passwordError);
            assert actualMessage.contains("La contraseña es requerida") :
                    "Mensaje esperado no encontrado. Actual: " + actualMessage;
        });
    }

    public void verifyPasswordIsVisible() {
        execute("Validar que la contraseña es visible", () -> {
            String passwordType = driver.findElement(passwordInput).getAttribute("type");
            assert "text".equals(passwordType) :
                    "La contraseña no es visible. Type actual: " + passwordType;
        });
    }

    public void verifyPasswordIsHidden() {
        execute("Validar que la contraseña está oculta", () -> {
            String passwordType = driver.findElement(passwordInput).getAttribute("type");
            assert "password".equals(passwordType) :
                    "La contraseña no está oculta. Type actual: " + passwordType;
        });
    }

    public void verifyRememberMeIsChecked() {
        execute("Validar que Recordarme está seleccionado", () -> {
            // Buscar el checkbox padre que contiene la clase 'checked'
            String checkboxClass = driver.findElement(By.xpath("//span[@class='qa-checkmark']/parent::label"))
                    .getAttribute("class");
            assert checkboxClass != null && checkboxClass.contains("checked") :
                    "El checkbox Recordarme no está seleccionado. Clase actual: " + checkboxClass;
        });
    }

    public void verifyRedirectToStore() {
        execute("Validar redirección a /store", () -> {
            wait.until(ExpectedConditions.urlContains(Urls.STORE_PATH));
            String currentUrl = driver.getCurrentUrl();
            assert currentUrl.contains(Urls.STORE_PATH) :
                    "No se redirigió a /store. URL actual: " + currentUrl;
        });
    }

    public void verifyLoginPageIsDisplayed() {
        execute("Validar que la página de login es visible", () -> {
            assert isDisplayed(loginButton) :
                    "La página de login no es visible";
        });
    }
}