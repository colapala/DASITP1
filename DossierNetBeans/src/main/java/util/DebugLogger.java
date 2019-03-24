
package util;

/**
 *
 * @author Chanèle Jourdan, Jorge Terreu, Corentin laharotte
 * FACULTATIF
 * Gestion des affichages de Debug dans la console
 */
public class DebugLogger {

    // Méthode pour avoir des messages de Log dans le bon ordre (pause)
    public static void pause(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            ex.hashCode();
        }
    }

    // Méthode pour avoir des messages de Log dans le bon ordre (log)
    public static void log(String message) {
        System.out.flush();
        pause(5);
        System.err.println("[DebugLogger] " + message);
        System.err.flush();
        pause(5);
    }

    // Méthode pour avoir des messages de Log dans le bon ordre (log avec exception)
    public static void log(String message, Exception ex) {
        System.out.flush();
        pause(5);
        System.err.println("[DebugLogger] " + message);
        System.err.println("[**EXCEPTION**] " + ex.getMessage());
        System.err.print("[**EXCEPTION**] >>> ");
        ex.printStackTrace(System.err);
        System.err.flush();
        pause(5);
    }
    
}