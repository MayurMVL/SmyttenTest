package assignment;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Adding a product to the cart on amazon online shopping application.
 * 
 * @author mayur
 *
 */
public class TestClass {

	public static void main(String[] args) throws InterruptedException {
// To Open Chrome Browser.
		System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
		WebDriver driver = new ChromeDriver();

// To maximize the Chrome browser window.
		driver.manage().window().maximize();

// To implement implicitly wait statement.
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

// To navigate to the amazon online shopping website.
		driver.get("https://www.amazon.in/");

// To click on 'All' menu.
		driver.findElement(By.xpath("(//span[.='All'])[2]")).click();

// To click on 'Women's Fashion' option.		
		WebElement womensFashion = driver.findElement(By.xpath("//a[@data-menu-id='11']//div"));
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(womensFashion));
		driver.findElement(By.xpath("//a[@data-menu-id='11']//div")).click();

// To click on 'Top Brands' option.
		driver.findElement(By.linkText("Top Brands")).click();
		
// To select the third product on the products page, the following statement can be used.
//		driver.findElement(By.xpath("(//div//h2//a//span)[3]")).click();

// To select the product mentioned in the assignment.
		WebElement product = driver.findElement(By.linkText("Women's Off-White Poly Silk Kurta With Pant And Dupatta"));
		String text1 = product.getText();
		System.out.println("Product Name : " + text1);
		product.click();

// To navigate to the new window.
		Set<String> windowHandles = driver.getWindowHandles();
		for (String whs : windowHandles) {
			String pageTitle = driver.switchTo().window(whs).getTitle();
			if (pageTitle.contains("Buy Janasya")) {
// To select a size for the product.
				WebElement sizeDropdown = driver.findElement(By.id("native_dropdown_selected_size_name"));
				if (sizeDropdown.isDisplayed()) {
					Select sel = new Select(sizeDropdown);
					sizeDropdown.click();
					sel.selectByValue("3,B08XQ8MCKP");
					Thread.sleep(2000);
				} 
				WebElement wholePrice = driver.findElement(By.xpath("(//span[@class='a-price-whole'])[1]"));
				String productPrice = wholePrice.getText();
				System.out.println("Product Price before ading it to cart : " + productPrice);
				Thread.sleep(2000);

// To add the product to the cart.
				driver.findElement(By.id("add-to-cart-button")).click();

// To verify that the product is added to the cart.
				WebElement addedItemIcon = driver.findElement(By.xpath("(//i[@class='a-icon a-icon-alert'])[1]"));
				if (addedItemIcon.isDisplayed()) {
					System.out.println("Item added to the cart");
				}
				driver.findElement(By.id("nav-cart")).click();
				Thread.sleep(1000);
				String price = driver.findElement(By.id("sc-subtotal-amount-activecart")).getText();
				String cartPrice = price.substring(2);
				System.out.println("Product price after adding it to the cart : " + cartPrice);
				String[] finalPrice = cartPrice.split(".");
				if (productPrice.contains(finalPrice[0])) {
					System.out.println("product added to the cart");
				}
			}
		}

	}

}
