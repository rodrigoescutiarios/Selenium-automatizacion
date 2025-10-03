package com.selenium.stepdefinitions;

import com.selenium.pages.LoginPage;
import com.selenium.utils.DriverManager;
import io.cucumber.java.es.*;
import org.openqa.selenium.WebDriver;

public class LoginSteps {
    private WebDriver driver;
    private LoginPage loginPage;

    public LoginSteps() {
        this.driver = DriverManager.getDriver();
        this.loginPage = new LoginPage(driver);
    }

    @Dado("que navego a la página de login")
    public void queNavegoALaPaginaDeLogin() {
        loginPage.goTo();
    }

    @Cuando("ingreso el usuario {string}")
    public void ingresoElUsuario(String usuario) {
        loginPage.fillUsername(usuario);
    }

    @Cuando("ingreso la contraseña {string}")
    public void ingresoLaContrasena(String password) {
        loginPage.fillPassword(password);
    }

    @Cuando("hago clic en el botón de login")
    public void hagoClicEnElBotonDeLogin() {
        loginPage.clickLoginButton();
    }

    @Cuando("limpio el campo de usuario")
    public void limpioElCampoDeUsuario() {
        loginPage.clearUsername();
    }

    @Cuando("limpio el campo de contraseña")
    public void limpioElCampoDeContrasena() {
        loginPage.clearPassword();
    }

    @Cuando("hago clic en el checkbox Recordarme")
    public void hagoClicEnElCheckboxRecordarme() {
        loginPage.clickRememberMe();
    }

    @Cuando("hago clic en el botón mostrar contraseña")
    public void hagoClicEnElBotonMostrarContrasena() {
        loginPage.clickShowPassword();
    }

    @Cuando("navego a la página de login")
    public void navegoALaPaginaDeLogin() {
        loginPage.goTo();
    }

    @Entonces("debo ser redirigido a la página de la tienda")
    public void deboSerRedirigidoALaPaginaDeLaTienda() {
        loginPage.verifyRedirectToStore();
    }

    @Entonces("debo ver el mensaje de error de usuario requerido")
    public void deboVerElMensajeDeErrorDeUsuarioRequerido() {
        loginPage.verifyUsernameErrorMessage();
    }

    @Entonces("debo ver el mensaje de error de contraseña requerida")
    public void deboVerElMensajeDeErrorDeContrasenaRequerida() {
        loginPage.verifyPasswordErrorMessage();
    }

    @Entonces("la contraseña debe estar oculta")
    public void laContrasenaDebeEstarOculta() {
        loginPage.verifyPasswordIsHidden();
    }

    @Entonces("la contraseña debe estar visible")
    public void laContrasenaDebeEstarVisible() {
        loginPage.verifyPasswordIsVisible();
    }

    @Entonces("el checkbox Recordarme debe estar seleccionado")
    public void elCheckboxRecordarmeDebeEstarSeleccionado() {
        loginPage.verifyRememberMeIsChecked();
    }

    @Entonces("debo permanecer en la página de login")
    public void deboPermancerEnLaPaginaDeLogin() {
        loginPage.verifyLoginPageIsDisplayed();
    }
}