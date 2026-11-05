Proyecto Integrador - Programación I (2026)

Sistema de gestión de reparto urbano: CityLink
Entregable 1

👤 Autor

[Matias Figueroa] 

🔗 Repositorio Git

[Pegar aquí el enlace al repositorio público de GitHub]

🚀 Instrucciones para compilar y ejecutar

El proyecto es una aplicación de consola en Java estructurada en paquetes (app, modelo, excepciones).

Opción 1: Desde terminal (Línea de comandos)

Abrir la terminal y ubicarse en la carpeta src del proyecto:
cd ruta/al/proyecto/src

Compilar el proyecto completo:
javac app/Main.java

Ejecutar el sistema:
java app.Main

Opción 2: Desde un IDE (VS Code, IntelliJ IDEA, Eclipse)

Abrir la carpeta raíz del proyecto en el IDE.

Navegar hasta el archivo src/app/Main.java.

Ejecutar utilizando el botón "Run" o "Play" nativo del editor.

📊 Diagrama de Clases


(Nota: Asegurarse de subir el archivo de imagen exportado con el nombre diagrama.png en la raíz del repositorio, junto a este README)

⚙️ Vehículos de Referencia (Valores)

Tal como se indica en la Sección 2.3 del enunciado, decidí mantener los valores orientativos por defecto para la capacidad en kg, velocidad en km/h, costo por km y tiempo extra de las Bicicletas, Motos y Camionetas. No se realizaron ajustes sobre los valores base.

🧪 Pruebas de Escritorio Realizadas

Para garantizar el correcto manejo de errores y propagación de excepciones (Sección 3.4), probé los siguientes casos de uso sin que el sistema finalice abruptamente:

Caso Normal: Registro de pedido, búsqueda polimórfica de cliente y asignación exitosa con cálculo de costo final.

Caso Borde (Restricción física): Intento de asignación de un Pedido Frágil a un Repartidor con Bicicleta (Lanza y captura CapacidadExcedidaException).

Caso Borde (Transición de Estado): Intento de cambiar un pedido de estado PENDIENTE directamente a CANCELADO (Transición válida), y un intento de cambiar de EN_CAMINO a CANCELADO (Lanza y captura EstadoInvalidoException).

Caso Borde (Entrada de teclado): Ingreso de letras en los menús que esperan enteros (Lanza y captura NumberFormatException).

🤖 Declaración de uso de IA

Herramienta utilizada: Gemini (Google).

¿Para qué se utilizó?:

Generación de código base ("boilerplate") para constructores, métodos getters/setters e interfaces.

Asistencia técnica para configurar correctamente la estructura de paquetes requerida en VS Code y solucionar problemas de enlazado y compilación (ClassNotFoundException).

Redacción del formato estándar de comentarios Javadoc.

¿Cómo se verificó el resultado?:
Todo el código generado fue leído y analizado estrictamente por mí para asegurar que no se utilizaran herramientas fuera del alcance de la materia (como instanceof o estructuras dinámicas no permitidas en esta etapa). Lo validé ejecutando el menú de forma iterativa y comprobando que el polimorfismo y las reglas de recargo matemático funcionaran de acuerdo a las especificaciones del dominio.