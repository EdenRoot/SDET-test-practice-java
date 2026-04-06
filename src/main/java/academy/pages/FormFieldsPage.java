package academy.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent;

public class FormFieldsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public FormFieldsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "name-input")
    private WebElement nameInput;

    @FindBy(css = "input[type='password']")
    private WebElement passwordInput;

    @FindBy(xpath = "//*[@name='fav_drink']")
    private List<WebElement> drinkCheckBoxes;

    @FindBy(xpath = "//*[@name='fav_color']")
    private List<WebElement> colorRadioButtons;

    @FindBy(id = "automation")
    private WebElement automationSelect;

    @FindBy(css = "#feedbackForm > ul > li")
    private List<WebElement> automationTools;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "message")
    private WebElement messageArea;

    @FindBy(id = "submit-btn")
    private WebElement submitBtn;


    @Step("Field: Ввод имени")
    public FormFieldsPage enterName(String name) {
        wait.until(ExpectedConditions.visibilityOf(nameInput));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nameInput);
        nameInput.clear();
        nameInput.sendKeys(name);
        return this;
    }

    @Step("Field: Ввод пароля")
    public FormFieldsPage enterPassword(String pass) {
        passwordInput.clear();
        passwordInput.sendKeys(pass);
        return this;
    }

    @Step("CheckBox: Выбор напитков")
    public FormFieldsPage selectDrinks(String... drinks) {
        for (String drinkName : drinks) {
            WebElement checkbox = drinkCheckBoxes.stream()
                    .filter(el -> el.getAttribute("value").equalsIgnoreCase(drinkName))
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException("Напиток '" + drinkName + "' не найден!"));
            scrollTo(checkbox);
            wait.until(elementToBeClickable(checkbox));
            if (!checkbox.isSelected()) checkbox.click();
        }
        return this;
    }

    @Step("RadioButton: Выбор цвета")
    public FormFieldsPage selectColor(String color) {
        WebElement radio = colorRadioButtons.stream()
                .filter(el -> el.getAttribute("value").equalsIgnoreCase(color))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Цвет '" + color + "' не найден!"));
        scrollTo(radio);
        wait.until(elementToBeClickable(radio));
        radio.click();
        return this;
    }

    @Step("DropDown menu: Ответ на вопрос \"Do you like automation?\"")
    public FormFieldsPage selectAutomationOption(String answer) {
        Select select = new Select(automationSelect);
        List<String> options = select.getOptions().stream()
                .map(WebElement::getText)
                .toList();
        if (!options.contains(answer)) {
            throw new NoSuchElementException(
                    String.format("В DropDown menu ответ \"%s\" не найден в списке! Доступны: %s", answer, options)
            );
        }
        select.selectByVisibleText(answer);
        return this;
    }

    @Step("Field: Ввод почты")
    public FormFieldsPage enterEmail(String email) {
        emailInput.clear();
        emailInput.sendKeys(email);
        return this;
    }

    @Step("Element’s text: Получение списка названий инструментов автоматизации")
    public List<String> getAutomationToolsTexts() {
        return automationTools.stream()
                .map(WebElement::getText)
                .toList();
    }

    @Step("Field: Ввод сообщения в поле Message")
    public FormFieldsPage fillMessageWithToolsData(String message) {
        messageArea.clear();
        messageArea.sendKeys(message);
        return this;
    }

    @Step("Alert: Получение текста браузерного алерта и его закрытие")
    public String getAlertText() {
        wait.until(alertIsPresent());

        Alert alert = driver.switchTo().alert();
        String text = alert.getText();
        alert.accept();
        return text;
    }

    @Step("Button: Нажатие кнопки \"Submit\"")
    public FormFieldsPage clickSubmitButton() {
        scrollTo(submitBtn);
        wait.until(elementToBeClickable(submitBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitBtn);
        return this;
    }

    private void scrollTo(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
}