# Tres en Raya para Android

Repositorio que contiene el código fuente y la .apk de el juego
"Tres en Raya" desarrollado en Android Studio siguiendo el tutorial
de "pildorasinformaticas". He hecho algunas modificaciones respecto
al diseño de la interfaz basándome en el Tres en Raya de Google.

## Funcionamiento

El juego está compuesto por una única actividad y layout. Al iniciar la
aplicación, nos encontramos con un menú de selección de opciones
en la parte de la izquierda, y la cuadrícula de juego en la derecha.
Hay que seleccionar una de las dificultades posibles, y pulsar la opción
de 1 o 2 jugadores para poder comenzar a jugar.

* **1 jugador**: Jugarás tu solo contra la I.A. Tiene 3 dificultades:

  * **Fácil**: la I.A. simplemente pulsará casillas al azar, sin ninguna
  intención de ganar.
  * **Normal**: la I.A. va a contrarrestar todos tus movimientos. Cuando tengas
  posibilidades de hacer tres en raya, la máquina marcará la casilla
  necesaria para bloquear esa posibilidad.
  * **Imposible**: es igual que la dificultad anterior, aunque tiene algunas
  situaciones programadas para que anule todas las posibilidades de ganarle.
  Es imposible ganar, aunque se puede llegar a un empate.

* **2 jugadores**: Jugarás contra otra persona en el mismo móvil.

<div align="center">

![menu](https://github.com/oscarcillo/TresEnRaya/blob/master/capturas/Screenshot_1544108130.png)

</div>

Una vez seleccionada una de las dos opciones de juego, el menú de la izquierda
se hará invisible lentamente, y después la cuadrícula de juego
se moverá con una transición al centro de la pantalla. Ahora ya se puede
comenzar a jugar, simplemente seleccionando una de las 9 casillas
de la cuadrícula.

<div align="center">

![partida](https://github.com/oscarcillo/TresEnRaya/blob/master/capturas/Screenshot_1544045168.png)

</div>

Justo después de terminar la partida, aparecerá un texto en la parte
superior de la pantalla indicando cual de los dos jugadores ha ganado,
o si ha habido un empate. También, una linea roja aparecerá y tachará la
combinación ganadora. Unos segundos después, se restablecerá la forma
de la interfaz que había inicialmente, y podremos volver a jugar otra
partida.

La aplicación está en dos idiomas: Español e Inglés.

## Código fuente

[Enlace a las clases Java](https://github.com/oscarcillo/TresEnRaya/tree/master/app/src/main/java/com/otr/tres_en_raya)

[Enlace al Layout principal](https://github.com/oscarcillo/TresEnRaya/tree/master/app/src/main/res/layout)

[Enlace a la carpeta res](https://github.com/oscarcillo/TresEnRaya/tree/master/app/src/main/res)

## Descarga de la apk

[Enlace de descarga de la .apk](https://raw.githubusercontent.com/oscarcillo/TresEnRaya/master/app/release/app-release.apk)