package qa.test;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * An example of loading jQuery dynamically using WebDriver.
 * Some notes here: https://gist.github.com/djangofan/7677995
 */
public class JQueryTest {
	
	private EventFiringWebDriver efwd;
	private String initialUrl;
	
	@BeforeTest
	public void setUp() {
		initialUrl = "data:text/html,<html><head></head><body><h2>Test of <i>jGrowl</i> messaging.</h2></body></html>";
		WebDriver wd = new FirefoxDriver();
		efwd = new EventFiringWebDriver( wd );
		efwd.navigate().to( initialUrl );
		EventWrapper ew = new EventWrapper( efwd );
		efwd.register( ew );
	}

	@Test
	public void runTest() {
		JavascriptExecutor js = (JavascriptExecutor)efwd;
		js.executeScript( "$.jGrowl('Hello world!');" );
		js.executeScript( "$.jGrowl('Stick this!', { sticky: true });" );
		js.executeScript( "$.jGrowl('A message with a header', { header: 'Important' });" );
		js.executeScript( "$.jGrowl('A message that will live a little longer.', { life: 10000 });" );
		//js.executeScript( "$.jGrowl('A message with a beforeOpen callback and a different opening animation.', " +
		//    "{ beforeClose: function(e,m) { alert('About to close this notification!'); }, animateOpen: {height: 'show' }});" );		
	}
	
	@AfterTest
	public void cleanUp() {		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		efwd.quit();
	}

}
