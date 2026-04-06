import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FirstTest extends BaseTest {

    @Test
    public void checkGoogleTitle() {
        driver.get("https://google.com");
        assertEquals("Google", driver.getTitle(), "Заголовок страницы не совпал");
    }
}