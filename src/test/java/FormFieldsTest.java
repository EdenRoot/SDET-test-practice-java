import academy.pages.FormFieldsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static java.util.Comparator.comparingInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Epic("UI Тестирование (Java) \"practice-automation.com/form-fields/\"")
@Feature("Форма Form Fields")
public class FormFieldsTest extends BaseTest {

    @Test
    @Story("Позитивный сценарий отправки формы")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("Успешная отправка формы")
    @Description("Заполнение всех полей, расчет инструментов и проверка успешного алерта")
    void testSuccessfulFormSubmission() {
        FormFieldsPage formPage = new FormFieldsPage(driver);

        driver.get("https://practice-automation.com/form-fields/");

        List<String> tools = formPage.getAutomationToolsTexts();
        int countTools = tools.size();
        String longestTool = tools.stream()
                .max(comparingInt(String::length))
                .orElse("");

        String alertText = formPage
                .enterName("Kirill")
                .enterPassword("PasswordSafetyNoYesIDontKnow")
                .selectDrinks("Milk", "Coffee")
                .selectColor("Yellow")
                .selectAutomationOption("Yes")
                .enterEmail("name@example.com")
                .fillMessageWithToolsData(countTools + "\n" + longestTool)
                .clickSubmitButton()
                .getAlertText();

        assertEquals("Message received!", alertText, "Текст алерта не совпадает!");
    }

    @Test
    @Story("Негативный сценарий")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Отправка формы с пустым полем Name")
    @Description("Проверка того, что форма не отправляется, если обязательное поле Name не заполнено")
    void testNegativeEmptyName() {
        FormFieldsPage formPage = new FormFieldsPage(driver);

        driver.get("https://practice-automation.com/form-fields/");

        formPage.enterPassword("12345")
                .enterEmail("test@example.com")
                .clickSubmitButton();

        boolean isAlertPresent;
        try {
            new WebDriverWait(driver, Duration.ofSeconds(2))
                    .until(ExpectedConditions.alertIsPresent());
            isAlertPresent = true;
        } catch (Exception e) {
            isAlertPresent = false;
        }

        assertFalse(isAlertPresent, "Алерт появился, хотя обязательное поле Name пустое!");
    }
}