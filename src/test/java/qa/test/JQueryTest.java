package qa.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.Charsets;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import com.google.common.io.Resources;

/**
 * An example of loading jQuery dynamically using WebDriver.
 *  https://gist.github.com/djangofan/7677995
 */
public class JQueryTest {

	// its nice to keep JavaScript snippets in separate files.
	private final String JGROWL_SCRIPT = "http://cdnjs.cloudflare.com/ajax/libs/jquery-jgrowl/1.2.12/jquery.jgrowl.min.js";
	private final String JQUERY_SCRIPT = "http://code.jquery.com/jquery-1.11.1.min.js";
	private final String JGROWL_STYLE = "http://cdnjs.cloudflare.com/ajax/libs/jquery-jgrowl/1.2.12/jquery.jgrowl.min.css";

	@Test
	public void runTest() {
		WebDriver driver = new FirefoxDriver();
		driver.get("data:text/html,<html><head></head><body><h2>Test of <i>jGrowl</i> messaging.</h2></body></html>");

		JavascriptExecutor js = (JavascriptExecutor) driver;


		js.executeScript( "var jq = document.createElement('script'); jq.type = 'text/javascript'; jq.src = '" +
				JQUERY_SCRIPT + "'; document.getElementsByTagName('head')[0].appendChild(jq);" );

		js.executeScript( "$.getScript(\"" + JGROWL_SCRIPT + "\");" );

		js.executeScript( "var lnk = document.createElement('link'); lnk.rel = 'stylesheet'; lnk.href = '" +
			    JGROWL_STYLE + "'; lnk.type = 'text/css'; document.getElementsByTagName('head')[0].appendChild(lnk);" );


		// test jgrowl
		js.executeScript( "$.jGrowl('Hello world!');" );
		js.executeScript( "$.jGrowl('Stick this!', { sticky: true });" );
		js.executeScript( "$.jGrowl('A message with a header', { header: 'Important' });" );
		js.executeScript( "$.jGrowl('A message that will live a little longer.', { life: 10000 });" );
		//js.executeScript( "$.jGrowl('A message with a beforeOpen callback and a different opening animation.', " +
		//    "{ beforeClose: function(e,m) { alert('About to close this notification!'); }, animateOpen: {height: 'show' }});" );

		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		driver.quit();
	}

}
