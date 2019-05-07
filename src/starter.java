import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * \\ABOUT:
 * This app can test a list of sites for an PHP errors, browser JavaScript site errors and mobile versions ones. 
 * 
 * \\PARAMS
 * Command line arguments:
 *  - source (! must use):
 *  	[
 *  		file - path to a file;
 *  		url -  an url to get the sites list;
 *  	]:one of this params. The list should be separated with new line characters
 *  
 *  - url (! must use): web url or file path
 *  
 *  - tests (*):
 *  	[
 * 	 		phpm - test for PHP warnings and errors in mobile version;
 *  		phpd - test for PHP warnings and errors in desktop version;
 * 		 	php - test for PHP warnings and errors in desktop and mobile versions;
 *  		jsm - test for Google Chrome console warnings and errors in mobile version;
 *  		jsd - test for Google Chrome console warnings and errors in desktop version;
 *  		js - test for Google Chrome console warnings and errors in desktop and mobile versions;
 *			(*) all - can exclude from cmd. Without this argument all tests will execute;  
 *  	]:comma separated values
 *  
 *  - man (? info): prints this help
 *  
 *  - openhome (? info): opens github link with this application
 *  
 *  - iddqd: opens slashka.ru TODO: убрать потом после проверок =)
 * 
 * \\FIGHT
 *
 * @author SlaSh
 *
 */
public class starter {
	public static void main(String[] args) {
		System.out.print(args.length);
		testGoogleSearch();
		System.exit(0);
		ArrayList <String> myUrls = new ArrayList <String>();
		
		try {
			URL url = new URL("http://bzaim.tomnolane.ru/fortests");
			Scanner s = new Scanner(url.openStream());
			while (s.hasNext())
				myUrls.add(s.next().trim());
			s.close();
		} catch (IOException e) {
			System.out.println("Error receiving urls! -> " + e.getMessage());
			System.exit(0);
		}
		
		if (myUrls.isEmpty()) {
			System.out.println("No urls :(");
			System.exit(0);
		}
		
		for (int i = 0; i < myUrls.size(); i++)
			checkUrl(myUrls.get(i));
	}

	/**
	 * checking site by url for PHP errors
	 * @param s_url
	 */
	private static void checkUrl (String s_url) {
		try {
			URL url = new URL(s_url);
			URLConnection con = url.openConnection();
			InputStream in = con.getInputStream();
			String encoding = con.getContentEncoding();
			encoding = encoding == null ? "UTF-8" : encoding;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[8192];
			int len = 0;
			while ((len = in.read(buf)) != -1) baos.write(buf, 0, len);

			String body = new String(baos.toByteArray(), encoding);
			int j = body.indexOf("PHP");
			System.out.println("(" + String.valueOf(j) + ") " + s_url);
		} catch (Exception e) {
			System.out.println(e.getMessage() + " -> " + s_url);
		}
	}
	
	private static void testGoogleSearch() {
		// Optional, if not specified, WebDriver will search your path for chromedriver.
		System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
		WebDriver driver;// = new ChromeDriver();
		DesiredCapabilities caps = DesiredCapabilities.chrome();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable("browser", Level.ALL);
        caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
		driver = new ChromeDriver(caps);
		driver.get("https://zaimsoon.ru/");
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  // Let the user actually see something!
		LogEntries logs = driver.manage().logs().get("browser");
        for (LogEntry entry : logs) {
            System.out.println(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
            //do something useful with the data
        }
		
		//Thread.sleep(5000);  // Let the user actually see something!
		driver.quit();
	}
}
