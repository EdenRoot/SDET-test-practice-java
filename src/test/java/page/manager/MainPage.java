package page.manager;

import helpers.ParameterProvider;
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import page.BasePage;

import java.util.List;

public class MainPage extends BasePage {
    @FindBy(css = ".thumbnails .col-md-3, .thumbnails .col-sm-6")
    private List<WebElement> allHomeProducts;

    public MainPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void open() {
        webDriver.get(ParameterProvider.get("base.url").replace("\"", "").trim());
    }

    @Step("Поиск товара: {0}")
    public void searchFor(String text) {
        WebElement searchInput = webDriver.findElement(By.id("filter_keyword"));
        searchInput.clear();
        searchInput.sendKeys(text + Keys.ENTER);
    }

    public void clickProductOnHome(int index) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".thumbnails .col-md-3, .thumbnails .col-sm-6")));

        List<WebElement> products = webDriver.findElements(By.cssSelector(".thumbnails .col-md-3, .thumbnails .col-sm-6"));

        WebElement target = products.get(index);

        ((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", target);

        WebElement productLink = target.findElement(By.cssSelector("a.prdocutname"));
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", productLink);
    }

    public int getHomeProductsCount() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.prdocutname")));
        return webDriver.findElements(By.cssSelector("a.prdocutname")).size();
    }
}