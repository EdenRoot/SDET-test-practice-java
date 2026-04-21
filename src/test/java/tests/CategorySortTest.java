package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.manager.CategoryPage;

import java.util.List;

public class CategorySortTest extends BaseTest {

    @Test
    @DisplayName("Кейс №1: Проверка сортировки в категориях")
    void testCategorySorting() {
        CategoryPage categoryPage = new CategoryPage(driver);
        categoryPage.open("/index.php?rt=product/category&path=68");

        categoryPage.selectSort("Name A - Z");
        List<String> names = categoryPage.getProductNames();
        Assertions.assertEquals(names.stream().sorted().toList(), names, "Сортировка Name A-Z неверна");

        categoryPage.selectSort("Price Low > High");
        List<Double> prices = categoryPage.getProductPrices();
        Assertions.assertEquals(prices.stream().sorted().toList(), prices, "Сортировка Price Low > High неверна");
    }
}