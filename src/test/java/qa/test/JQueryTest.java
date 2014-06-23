package qa.test;

import java.io.IOException;
import java.net.URL;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Reporter;
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
	private final String JGROWL_STYLE = "jquery.jgrowl.css";

    @Test
	public void runTest() {
		WebDriver driver = new FirefoxDriver();
		driver.get("data:text/html,<html><head></head><body><h2>This is a <i>test</i></h2></body></html>");

        JavascriptExecutor js = (JavascriptExecutor) driver;
        URL jqueryUrl = Resources.getResource( JQUERY_SCRIPT );
        URL jgrowlUrl = Resources.getResource( JGROWL_SCRIPT );
        URL styleUrl = Resources.getResource( JGROWL_STYLE );
        String jqueryLoc = null;
        String jgrowlLoc = null;
        String styleLoc = null;
		try {
			jqueryLoc = Resources.toString( jqueryUrl, Charsets.UTF_8 );
			jgrowlLoc = Resources.toString( jgrowlUrl, Charsets.UTF_8 );
			styleLoc = Resources.toString( styleUrl , Charsets.UTF_8 );
		} catch ( IOException ioe ) {
			ioe.printStackTrace();
		}
		Reporter.log("Executing '" + JQUERY_SCRIPT + "' to load within WebDriver instance...", true );
        js.executeScript( jqueryLoc );
		//js.executeScript( "if (!window.jQuery) { var jquery = document.createElement('script'); jquery.type = 'text/javascript'; jquery.src = '" + jqueryLoc + "'; document.getElementsByTagName('head')[0].appendChild(jquery);}" );
        //js.executeScript( "if (!window.jQuery) { var jquery = document.createElement('script'); jquery.type = 'text/javascript'; jquery.src = 'https://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js'; document.getElementsByTagName('head')[0].appendChild(jquery); }" );
		
        Reporter.log("Done loading jQuery.", true );
        js.executeScript( jgrowlLoc );
        //js.executeScript( "$.getScript( '" + JGROWL_SCRIPT + "' )");
        //js.executeScript( "$.getScript('http://the-internet.herokuapp.com/js/vendor/jquery.growl.js')" );
        
        // try some basic javascript
        //Reporter.log("Executing a Javascript command to verify everything is ok...", true );
        //js.executeScript( "document.getElementById( 'textFieldTestInputControlID' ).scrollIntoView(); " );
        //Reporter.log("Done with Javascript command.", true );
        
        // type some text using Jquery to verify jquery works
        //Reporter.log("Executing a jquery click command to verify jQuery loaded...", true );
        //js.executeScript( "$(\"#textFieldTestInputControlID\").val(\"Test\");$(\"#textFieldTestProcessButtonID\").click();" );
        //Reporter.log("Done with click command.", true );
		
        // load jgrowl css using jquery
        js.executeScript( "$('head').append('<link rel=\"stylesheet\" href=\"" + JGROWL_STYLE + "\" type=\"text/css\" />');" );
        //js.executeScript( "$('head').append('<link rel=\"stylesheet\" href=\"http://the-internet.herokuapp.com/css/jquery.growl.css\" type=\"text/css\" />');" );
        
		// test jgrowl
		js.executeScript( "$.jGrowl(\"A message that will live a little longer.\", { life: 10000 }); " );
		js.executeScript( "$.growl({ title: 'GET', message: '/' });" );
		
        try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
		driver.quit();
	}

}
