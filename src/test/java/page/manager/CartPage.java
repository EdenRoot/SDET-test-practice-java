package page.manager;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import page.BasePage;

import java.util.List;

public class CartPage extends BasePage {
    @FindBy(css = "table.table-striped.grid tr:not(:first-child)")
    private List<WebElement> cartRows;

    @FindBy(id = "cart_update")
    private WebElement updateButton;

    public CartPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Step("Удалить все четные товары через прямой переход по ссылке")
    public void removeEvenItems() {
        List<WebElement> buttons = webDriver.findElements(By.cssSelector("a[href*='remove']"));
        if (buttons.size() >= 4) {
            String removeUrl = buttons.get(3).getAttribute("href");
            webDriver.get(removeUrl);
        }

        buttons = webDriver.findElements(By.cssSelector("a[href*='remove']"));
        if (buttons.size() >= 2) {
            String removeUrl = buttons.get(1).getAttribute("href");
            webDriver.get(removeUrl);
        }
    }

    @Step("Удвоить количество самого дешевого товара")
    public void doubleCheapestItemQuantity() {
        List<WebElement> rows = webDriver.findElements(By.cssSelector("table.table-striped.grid tbody tr"));
        int cheapIdx = 0;
        double min = Double.MAX_VALUE;

        for (int i = 0; i < rows.size(); i++) {
            String priceText = rows.get(i).findElement(By.cssSelector("td:nth-child(4)")).getText()
                    .replaceAll("[^0-9.]", "").trim();
            double price = Double.parseDouble(priceText);

            if (price < min) {
                min = price;
                cheapIdx = i;
            }
        }

        WebElement qtyInp = rows.get(cheapIdx).findElement(By.cssSelector("input.form-control"));
        int currentQty = Integer.parseInt(qtyInp.getAttribute("value"));
        qtyInp.clear();
        qtyInp.sendKeys(String.valueOf(currentQty * 2));
        updateButton.click();
    }

    public double getTotalPrice() {
        By totalLocator = By.xpath("//span[contains(text(), 'Total:')]/ancestor::tr/td[last()]/span");
        wait.until(ExpectedConditions.visibilityOfElementLocated(totalLocator));

        WebElement totalElement = webDriver.findElement(totalLocator);
        String priceText = totalElement.getText().replace("$", "").replace(",", "").trim();

        return Double.parseDouble(priceText);
    }
}