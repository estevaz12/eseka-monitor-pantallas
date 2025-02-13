# Monitor Nautilus
 Interfaz gráfica que muestra por pantalla las eficiencias de las máquinas de tejer en tiempo real del sistema Nautilus obtenidos de una vista de una base de datos.
 Hecho con JavaFX. BD en MySQL.

 -Compilar y ejecutar con IDE (javafx-maven-plugin) -> "mvn compile clean javafx:run"
 
 -Compilar Fat JAR ejecutable (Shade plugin) -> "mvn clean package" 
 
 NOTE: you will need a main class that doesn't extend from Application.Programa para comparar Programadas de Seamless.

 Ejecutar-> java -jar monitor-1.0-SNAPSHOT.jar <"ARGUMENTOS">
 
 ARGUMENTOS:
 "SEGUNDOS" "SECTOR" "GRUPO1" "GRUPO..."
 
 EJ:
 "15" "HOMBRE" "301-322" "323-344" "345-366" "367-388" "389-408" "409-428" "429-457"
