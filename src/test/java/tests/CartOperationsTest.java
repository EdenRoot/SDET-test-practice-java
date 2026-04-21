package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.manager.CartPage;
import page.manager.MainPage;
import page.manager.ProductPage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CartOperationsTest extends BaseTest {

    @Test
    @DisplayName("Кейс №3: 5 товаров из разных категорий главной страницы")
    void testRemoveEvenItems() {
        MainPage mainPage = new MainPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);
        Random random = new Random();

        List<Integer> usedIndices = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            mainPage.open();
            int count = mainPage.getHomeProductsCount();

            int randomProductIndex;
            do {
                randomProductIndex = random.nextInt(count);
            } while (usedIndices.contains(randomProductIndex));

            usedIndices.add(randomProductIndex);

            mainPage.clickProductOnHome(randomProductIndex);
            int randomQty = random.nextInt(5) + 1;
            productPage.addToCart(randomQty);
        }

        double before = cartPage.getTotalPrice();
        cartPage.removeEvenItems();

        Assertions.assertTrue(cartPage.getTotalPrice() < before, "Сумма не уменьшилась!");
    }
}