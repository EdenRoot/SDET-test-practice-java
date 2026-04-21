package page.manager;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import page.BasePage;

import java.util.List;

public class ProductPage extends BasePage {
    @FindBy(id = "product_quantity")
    private WebElement quantityInput;

    @FindBy(className = "cart")
    private WebElement addToCartButton;

    public ProductPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Установить количество {0} и добавить в корзину")
    public void addToCart(int qty) {
        wait.until(ExpectedConditions.visibilityOf(quantityInput));

        List<WebElement> radioOptions = webDriver.findElements(By.cssSelector("input[type='radio']"));
        if (!radioOptions.isEmpty()) {
            ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", radioOptions.get(0));
        }

        quantityInput.clear();
        quantityInput.sendKeys(String.valueOf(qty));

        addToCartButton.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".cart-info")));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("cart_update")));
    }
}