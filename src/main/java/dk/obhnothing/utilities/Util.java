package dk.obhnothing.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Purpose: Utility class to read properties from a file
 * Author: Thomas Hartmann
 */
public class Util {
    public static void main(String[] args) {
        System.out.println(getPropertyValue("db.name", "properties-from-pom.properties"));
    }
    public static String getPropertyValue(String propName, String ressourceName)  {
        // REMEMBER TO BUILD WITH MAVEN FIRST. Read the property file if not deployed (else read system vars instead)
        // Read from ressources/config.properties or from pom.xml depending on the ressourceName
        try (InputStream is = Util.class.getClassLoader().getResourceAsStream(ressourceName)) { //"config.properties" or "properties-from-pom.properties"
            Properties prop = new Properties();
            prop.load(is);
            return prop.getProperty(propName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
