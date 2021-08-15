package frameworkSupportLibraries;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputFilter.Config;
import java.util.Properties;

public class ConfigDataUtils {

	public static String getproperty(String name) throws IOException {
		String value = "";
		Properties obj1 = new Properties();
		FileInputStream objfile = new FileInputStream(System.getProperty("user.dir")+"\\Config.properties");
		obj1.load(objfile);
		value = obj1.getProperty(name);
		return value;
	}

}
