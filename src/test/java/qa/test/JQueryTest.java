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
	private final String JQUERY_SCRIPT = "jquery-1.11.0.min.js";
	private final String JGROWL_SCRIPT = "jquery.jgrowl.js";

    @Test
	public void runTest() {
		WebDriver driver = new FirefoxDriver();
		driver.get("about:home");

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
        js.executeScript( jqueryText );
        js.executeScript( jgrowlText );

        // loadCSS = function(href) { 
        // 	  var cssLink = $("<link>");
        // 	  $("head").append(cssLink); //IE hack: append before setting href 
        // 	  cssLink.attr({
        // 	    rel:  "stylesheet",
        // 	    type: "text/css",
        // 	    href: href
        // 	  }); 
        // 	};
        
        // type some text using Jquery to verify jquery works
        js.executeScript( "$(\"#searchText\").text('Test message here');" );
        
		
        // load jgrowl css using jquery
        js.executeScript( "$(\"<link/>\", { rel: \"stylesheet\", type: \"text/css\", href: \"jquery.jgrowl.css\"}).appendTo(\"head\");" );
        
        
		// test jgrowl
		js.executeScript( "$.jGrowl(\"Sticky notification with a header.\", { header: 'A Header', sticky: true });" );
		
        try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
		driver.quit();
	}

}
