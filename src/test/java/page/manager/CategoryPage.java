package page.manager;

import helpers.ParameterProvider;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import page.BasePage;

import java.util.List;

public class CategoryPage extends BasePage {
    @FindBy(id = "sort")
    private WebElement sortDropdown;

    @FindBy(css = ".fixed_wrapper .prdocutname")
    private List<WebElement> productNames;

    @FindBy(css = ".oneprice, .pricenew")
    private List<WebElement> productPrices;

    public CategoryPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Открыть категорию по ссылке: {0}")
    public void open(String path) {
        webDriver.get(ParameterProvider.get("base.url") + path);
    }

    @Step("Выбрать сортировку: {0}")
    public void selectSort(String visibleText) {
        new Select(sortDropdown).selectByVisibleText(visibleText);
        try { Thread.sleep(1500); } catch (InterruptedException e) {}
    }

    public List<Double> getProductPrices() {
        return productPrices.stream()
                .map(WebElement::getText)
                .map(text -> text.replaceAll("[^0-9.]", "").trim())
                .filter(text -> !text.isEmpty())
                .map(Double::parseDouble)
                .toList();
    }

    public List<String> getProductNames() {
        return productNames.stream().map(WebElement::getText).toList();
    }
}