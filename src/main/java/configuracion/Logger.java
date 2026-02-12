/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package configuracion;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 *
 * @author User
 */
public class Logger {
    
    private static  final  String LOG_FILE = "log.txt";
    
    public static void info(String msg) {
        System.out.println("[INFO] " + timestamp() + " - " + msg);
    }
    
    public static void error(String msg) {
        System.out.println("[ERROR] "+ timestamp() + " - " + msg);
    }
    
    private static String timestamp()  {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                }
    
    private static void log(String level, String msg) {
        String logEntry = String.format("[%s] %s - %s%n", level, timestamp(), msg); //creamos el objeto logentry
        try(FileWriter fw = new FileWriter(LOG_FILE, true)) //creamos y abrimos el fichero
        {
            fw.write(logEntry); //escribimos una entrada en el fichero log.txt¡
            
        } catch (IOException e) {
            System.err.println("No se pudo escribir en el log" + e.getMessage());
        }
    }
}
