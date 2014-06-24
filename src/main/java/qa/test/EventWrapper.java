package qa.test;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

public class EventWrapper extends AbstractWebDriverEventListener {
	
	private String standardHeader = "Selenium Internal Event";
	private JavascriptExecutor js;
	private WebDriver drv;
	
    private final String JGROWL_SCRIPT = "http://cdnjs.cloudflare.com/ajax/libs/jquery-jgrowl/1.2.12/jquery.jgrowl.min.js";
    private final String JQUERY_SCRIPT = "http://code.jquery.com/jquery-1.11.1.min.js";
    private final String JGROWL_STYLE = "http://cdnjs.cloudflare.com/ajax/libs/jquery-jgrowl/1.2.12/jquery.jgrowl.min.css";
    
	public EventWrapper( WebDriver wd ) {
		this.drv = wd;
		if ( js == null ) enableGrowl( drv );
	}

	public void afterNavigateTo( String url, WebDriver wd ) {
		displayGrowl( "Navigated to URL '" + url + "'." );
	}
	
	public void afterClickOn( WebElement el, WebDriver wd ) {
		displayGrowl( "Clicked on element '" + el.getTagName() + "'." );
	}

	public void displayGrowl( String message ) {		
		js.executeScript( "$.jGrowl('" + message + "', { header: '" + standardHeader + "' });" );		
	}
	
	public void displayGrowl( String message, long life ) {
		js.executeScript( "$.jGrowl('" + message + "', { header: '" + standardHeader + "', life: " + life + " });" );		
	}

	private void enableGrowl( WebDriver wd ) {
		this.drv = wd;
		js = (JavascriptExecutor)drv;
		//TODO Add check for existing jQuery on page

		js.executeScript( "var jq = document.createElement('script'); jq.type = 'text/javascript'; jq.src = '" +
		JQUERY_SCRIPT + "'; document.getElementsByTagName('head')[0].appendChild(jq);" );

		js.executeScript( "$.getScript(\"" + JGROWL_SCRIPT + "\");" );

		js.executeScript( "var lnk = document.createElement('link'); lnk.rel = 'stylesheet'; lnk.href = '" +
		JGROWL_STYLE + "'; lnk.type = 'text/css'; document.getElementsByTagName('head')[0].appendChild(lnk);" );	
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
