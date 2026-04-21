package tests;

import org.junit.jupiter.api.*;
import page.manager.CartPage;
import page.manager.MainPage;
import page.manager.ProductPage;
import page.manager.SearchResultPage;

import java.util.Random;

public class SearchAndCartTest extends BaseTest {

    @Test
    @DisplayName("Кейс №2: Поисковая выдача, корзина и удвоение цены")
    void testSearchAndCartLogic() {
        MainPage mainPage = new MainPage(driver);
        SearchResultPage searchPage = new SearchResultPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);
        Random random = new Random();

        mainPage.open();
        mainPage.searchFor("shirt");
        searchPage.selectSort("Name A - Z");

        searchPage.clickResultByIndex(1);
        productPage.addToCart(random.nextInt(3) + 1);

        mainPage.searchFor("shirt");
        searchPage.selectSort("Name A - Z");

        searchPage.clickResultByIndex(2);
        productPage.addToCart(random.nextInt(3) + 1);

        double totalBefore = cartPage.getTotalPrice();
        cartPage.doubleCheapestItemQuantity();
        double totalAfter = cartPage.getTotalPrice();

        Assertions.assertTrue(totalAfter > totalBefore, "Итоговая сумма не увеличилась после удвоения товара");
    }
}
