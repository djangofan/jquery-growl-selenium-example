package qa.test;

import java.io.IOException;
import java.net.URL;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

/**
 * An example of loading jQuery dynamically using WebDriver.
 *  https://gist.github.com/djangofan/7677995
 */
public class JQueryTest {

	// its nice to keep JavaScript snippets in separate files.
	private final String JQUERY_SCRIPT = "jquery-1.9.0.js";
	private final String JGROWL_SCRIPT = "jquery.jgrowl-1.2.13.js";

    @Test
	public void runTest() {
		WebDriver driver = new FirefoxDriver();
		driver.get("http://djangofan.github.io/html-test-site/site/w1.html");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        URL jqueryUrl = Resources.getResource( JQUERY_SCRIPT );
        URL jgrowlUrl = Resources.getResource( JGROWL_SCRIPT );
        String jqueryText = null;
        String jgrowlText = null;
		try {
			jqueryText = Resources.toString( jqueryUrl, Charsets.UTF_8 );
			jgrowlText = Resources.toString( jgrowlUrl, Charsets.UTF_8 );
		} catch ( IOException ioe ) {
			ioe.printStackTrace();
		}
		System.out.println("Executing '" + JQUERY_SCRIPT + "' to load within WebDriver instance...");
        js.executeScript( jqueryText );
        System.out.println("Done loading jQuery.");
        js.executeScript( jgrowlText );
        
        // try some basic javascript
        System.out.println("Executing a Javascript command to verify everything is ok...");
        js.executeScript( "document.getElementById( 'textFieldTestInputControlID' ).scrollIntoView(); " );
        System.out.println("Done with Javascript command.");
        
        // type some text using Jquery to verify jquery works
        System.out.println("Executing a jquery click command to verify jQuery loaded...");
        js.executeScript( "$(\"#textFieldTestInputControlID\").val(\"Test\");$(\"#textFieldTestProcessButtonID\").click();" );
        System.out.println("Done with click command.");
		
        // load jgrowl css using jquery
        //js.executeScript( "$(\"<link/>\", { rel: \"stylesheet\", type: \"text/css\", href: \"jquery.jgrowl.css\"}).appendTo(\"head\");" );
        
		// test jgrowl
		js.executeScript( "$.jGrowl(\"A message that will live a little longer.\", { life: 10000 }); " );
		
        try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
		driver.quit();
	}

}
