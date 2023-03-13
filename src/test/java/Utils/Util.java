package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
public class Util {
    private static final String FILE_PROPERTY = "src/test/resources/properties/application.properties";
    private static Properties properties;

    static {
        properties = new Properties();
        try {
            FileInputStream inputStream = new FileInputStream(FILE_PROPERTY);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getConfig(String key) {
        return ((System.getProperty(key) == null) ? properties.getProperty(key) : System.getProperty(key));
    }
}