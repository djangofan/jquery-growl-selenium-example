package qa.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * An example of loading jQuery dynamically using WebDriver.
 * Some notes here: https://gist.github.com/djangofan/7677995
 */
public class JQueryTest {
	
	private GrowlPage gp;
	
	@BeforeTest
	public void setUp() {		
		WebDriver wd = new FirefoxDriver();
		gp = new GrowlPage( wd ).get();		
	}

	@Test
	public void runTest() {
		gp.executeJavascript( "$.jGrowl('Short lived messsage.');" );
		gp.executeJavascript( "$.jGrowl('Stick this!', { sticky: true });" );
		gp.executeJavascript( "$.jGrowl('A message with a header', { header: 'Important' });" );
		gp.executeJavascript( "$.jGrowl('A message that will live a little longer.', { life: 10000 });" );		
		gp.clickFirstDiv();
	}
	
	@AfterTest
	public void cleanUp() {		
        gp.quitBrowser();
	}

}
