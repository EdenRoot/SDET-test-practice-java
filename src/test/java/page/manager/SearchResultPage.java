package page.manager;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import page.BasePage;

import java.util.List;

public class SearchResultPage extends BasePage {
    @FindBy(id = "sort")
    private WebElement sortDropdown;

    @FindBy(css = ".thumbnails .col-md-3")
    private List<WebElement> results;

    public SearchResultPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Сортировка результатов: {0}")
    public void selectSort(String text) {
        new Select(sortDropdown).selectByVisibleText(text);
    }

    @Step("Открыть товар по индексу {0}")
    public void clickResultByIndex(int index) {
        wait.until(ExpectedConditions.visibilityOfAllElements(results));
        results.get(index).findElement(By.cssSelector(".prdocutname")).click();
    }
}