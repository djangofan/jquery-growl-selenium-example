package qa.test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.LoadableComponent;

import static org.testng.Assert.*;

import org.testng.Reporter;

public class GrowlPage extends LoadableComponent<GrowlPage> {

	private EventFiringWebDriver efwd;
	private String initialUrl = "data:text/html,<html><head></head><body><div><h2>Test of <i>jGrowl</i> messaging.</h2></div></body></html>";
	
	private static final By divLocator = By.xpath( ".//div" );

	public GrowlPage( WebDriver wd ) {
		Reporter.log( "Creating page object.", true );
        this.efwd = new EventFiringWebDriver( wd );
        efwd.get( initialUrl );
        sleep( 1000 );
	}

	@Override
	protected void load() {		
		Reporter.log( "Load page.", true );
		EventWrapper ew = new EventWrapper( efwd );
		efwd.register( ew );
		sleep( 1000 );
	}

	@Override
	protected void isLoaded() throws Error {
		Reporter.log( "Is page loaded.", true );
		sleep( 1000 );
		Boolean loaded = (Boolean)executeJavascript( "return (typeof jQuery=='function');" ); 
		assertTrue( loaded, "It seems that jQuery is not loaded." );
	}
	
	public WebElement getElement( By locator ) {
		return efwd.findElement( locator );
	}
	
	public WebElement getFirstDiv() {
		return efwd.findElement( divLocator );
	}
	
	public void clickFirstDiv() {
		efwd.findElement( divLocator ).click();
	}
	
	public Object executeJavascript( String script ) {
		JavascriptExecutor js = (JavascriptExecutor)efwd;
		return js.executeScript( script );
	}

	public void quitBrowser() {
        sleep( 3000 );
		efwd.quit();		
	}
	
	public void sleep( long time ) {
		try {
			Thread.sleep( time );
		} catch ( InterruptedException e ) {
			e.printStackTrace();
		}
	}

}
