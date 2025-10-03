# language: es
@login @regression
Característica: Login en Test QAcademy
  Como usuario de la plataforma
  Quiero poder iniciar sesión
  Para acceder a la tienda

  @smoke @happy_path @TC01
  Escenario: TC01 - Login exitoso con credenciales válidas
    Dado que navego a la página de login
    Cuando ingreso el usuario "admin"
    Y ingreso la contraseña "123456"
    Y hago clic en el botón de login
    Entonces debo ser redirigido a la página de la tienda

  @negative @TC02
  Escenario: TC02 - Validar mensaje de error cuando no se ingresa usuario
    Dado que navego a la página de login
    Cuando limpio el campo de usuario
    Y ingreso la contraseña "123456"
    Y hago clic en el botón de login
    Entonces debo ver el mensaje de error de usuario requerido

  @negative @TC03
  Escenario: TC03 - Validar mensaje de error cuando no se ingresa contraseña
    Dado que navego a la página de login
    Cuando ingreso el usuario "admin"
    Y limpio el campo de contraseña
    Y hago clic en el botón de login
    Entonces debo ver el mensaje de error de contraseña requerida

  @negative @TC04
  Escenario: TC04 - Validar mensaje de error cuando no se ingresan credenciales
    Dado que navego a la página de login
    Cuando limpio el campo de usuario
    Y limpio el campo de contraseña
    Y hago clic en el botón de login
    Entonces debo ver el mensaje de error de usuario requerido
    Y debo ver el mensaje de error de contraseña requerida

  @functional @TC05
  Escenario: TC05 - Validar funcionalidad de mostrar/ocultar contraseña
    Dado que navego a la página de login
    Cuando ingreso la contraseña "123456"
    Entonces la contraseña debe estar oculta
    Cuando hago clic en el botón mostrar contraseña
    Entonces la contraseña debe estar visible
    Cuando hago clic en el botón mostrar contraseña
    Entonces la contraseña debe estar oculta

  @functional @TC06
  Escenario: TC06 - Validar funcionalidad de checkbox Recordarme
    Dado que navego a la página de login
    Cuando hago clic en el checkbox Recordarme
    Entonces el checkbox Recordarme debe estar seleccionado
    Cuando ingreso el usuario "admin"
    Y ingreso la contraseña "123456"
    Y hago clic en el botón de login
    Entonces debo ser redirigido a la página de la tienda

  @negative @TC07
  Escenario: TC07 - Login fallido con usuario incorrecto
    Dado que navego a la página de login
    Cuando ingreso el usuario "usuarioIncorrecto"
    Y ingreso la contraseña "123456"
    Y hago clic en el botón de login
    Entonces debo permanecer en la página de login

  @negative @TC08
  Escenario: TC08 - Login fallido con contraseña incorrecta
    Dado que navego a la página de login
    Cuando ingreso el usuario "admin"
    Y ingreso la contraseña "contraseñaIncorrecta"
    Y hago clic en el botón de login
    Entonces debo permanecer en la página de login

  @negative @TC09
  Escenario: TC09 - Login fallido con credenciales incorrectas
    Dado que navego a la página de login
    Cuando ingreso el usuario "usuarioIncorrecto"
    Y ingreso la contraseña "contraseñaIncorrecta"
    Y hago clic en el botón de login
    Entonces debo permanecer en la página de login

  @functional @TC10
  Escenario: TC10 - Validar que los campos se limpian al recargar la página
    Dado que navego a la página de login
    Cuando ingreso el usuario "admin"
    Y ingreso la contraseña "123456"
    Y navego a la página de login
    Entonces debo permanecer en la página de login