package com.sincro.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.sincro.calculator.CartAmountCalculator;
import com.sincro.calculator.CartDiscountCalculator;
import com.sincro.model.customer.Customer;
import com.sincro.model.customer.CustomerType;
import com.sincro.model.offer.discount.CartDiscount;
import com.sincro.model.offer.discount.Discount;
import com.sincro.model.offer.discount.DiscountCategory;
import com.sincro.model.offer.discount.Range;
import com.sincro.model.product.Product;
import com.sincro.order.Order;
import com.sincro.vo.discount.DiscountCategoryVO;

class ShoppingCartTest {

  private final static String TYPE_REGULAR = "Regular";
  private final static String TYPE_PREMIUM = "Premium";

  Customer regularCustomer, premiumCustomer;
  CartAmountCalculator cartAmountcalculator;
  DiscountCategoryVO discountCategoryVO = new DiscountCategoryVO();

  @BeforeEach
  public void createDataset() {

    // GIVEN
    ///////////////////////////////////////

    // RGULAR CUSTOMER ////
    CustomerType regularType = new CustomerType(TYPE_REGULAR);
    regularCustomer = new Customer("2", "Jane", "US", regularType);

    // DISCOUNT SLABS FOR REGULAR TYPE
    Range r1 = new Range(new BigDecimal(0), new BigDecimal(5000));
    Range r2 = new Range(new BigDecimal(5000), new BigDecimal(10000));
    Range r3 = new Range(new BigDecimal(10000), null);

    Discount d1 = new CartDiscount(r1, 0);
    Discount d2 = new CartDiscount(r2, 10);
    Discount d3 = new CartDiscount(r3, 20);

    DiscountCategory regularDiscountCategory = new DiscountCategory();
    regularDiscountCategory.addDiscount(d1);
    regularDiscountCategory.addDiscount(d2);
    regularDiscountCategory.addDiscount(d3);

    discountCategoryVO.addDiscountCategory(regularType, regularDiscountCategory);

    //// PREMIUM CUSTOMER ////
    CustomerType premiumType = new CustomerType(TYPE_PREMIUM);
    premiumCustomer = new Customer("1", "John", "LDN", premiumType);

    // DISCOUNT SLABS FOR PREMIUM TYPE
    Range r4 = new Range(new BigDecimal(0), new BigDecimal(4000));
    Range r5 = new Range(new BigDecimal(4000), new BigDecimal(8000));
    Range r6 = new Range(new BigDecimal(8000), new BigDecimal(12000));
    Range r7 = new Range(new BigDecimal(12000), null);

    CartDiscount d4 = new CartDiscount(r4, 10);
    CartDiscount d5 = new CartDiscount(r5, 15);
    CartDiscount d6 = new CartDiscount(r6, 20);
    CartDiscount d7 = new CartDiscount(r7, 30);

    DiscountCategory premiumDiscountCategory = new DiscountCategory();
    premiumDiscountCategory.addDiscount(d4);
    premiumDiscountCategory.addDiscount(d5);
    premiumDiscountCategory.addDiscount(d6);
    premiumDiscountCategory.addDiscount(d7);

    discountCategoryVO.addDiscountCategory(premiumType, premiumDiscountCategory);

    CartDiscountCalculator discountCalculator = new CartDiscountCalculator(discountCategoryVO);
    cartAmountcalculator = new CartAmountCalculator(discountCalculator);
  }

  @Test
  void should_calculate_bill_correctly_for_regular_type_customer() {
    // WHEN
    Order order = new Order(regularCustomer.getCustomerId(), regularCustomer.getType(), cartAmountcalculator);
    order.addItem(new Product("1", "A", "Description of A", new BigDecimal(4000), true), 1);
    order.addItem(new Product("2", "B", "Description of B", new BigDecimal(5000), true), 1);
    order.addItem(new Product("3", "C", "Description of C", new BigDecimal(6000), true), 1);

    // THEN
    assertEquals(3, order.getItems().size());
    assertEquals(new BigDecimal(15000), order.getGrossAmount());
    assertEquals(new BigDecimal(13500), order.getNetAmount());
  }

  @Test
  void should_calculate_bill_correctly_for_premium_type_customer() {
    // WHEN
    Order order = new Order(premiumCustomer.getCustomerId(), premiumCustomer.getType(), cartAmountcalculator);
    order.addItem(new Product("1", "A", "Description of A", new BigDecimal(4000), true), 1);
    order.addItem(new Product("2", "B", "Description of B", new BigDecimal(5000), true), 1);
    order.addItem(new Product("3", "C", "Description of C", new BigDecimal(6000), true), 1);

    // THEN
    assertEquals(3, order.getItems().size());
    assertEquals(new BigDecimal(15000), order.getGrossAmount());
    assertEquals(new BigDecimal(12300), order.getNetAmount());
  }

  @Test
  void should_calculate_bill_correctly_for_premium_type_customer_with_addedQuantity() {
    // WHEN
    Order order = new Order(premiumCustomer.getCustomerId(), premiumCustomer.getType(), cartAmountcalculator);
    order.addItem(new Product("1", "A", "Description of A", new BigDecimal(4000), true), 2);
    order.addItem(new Product("2", "B", "Description of B", new BigDecimal(5000), true), 1);

    // THEN
    assertEquals(2, order.getItems().size());
    assertEquals(new BigDecimal(13000), order.getGrossAmount());
    assertEquals(new BigDecimal(10900), order.getNetAmount());

    // WHEN
    // Increase quantity of product A
    Product p = new Product("1", "A", "Description of A", new BigDecimal(4000), true);
    order.increaseItemQuantity(p, 1);

    // THEN
    assertEquals(2, order.getItems().size());
    assertEquals(new BigDecimal(17000), order.getGrossAmount());
    assertEquals(new BigDecimal(13700), order.getNetAmount());
  }

  @Test
  void should_calculate_bill_correctly_for_premium_type_customer_with_removed_quantity() {
    // WHEN
    Order order = new Order(premiumCustomer.getCustomerId(), premiumCustomer.getType(), cartAmountcalculator);
    order.addItem(new Product("1", "A", "Description of A", new BigDecimal(4000), true), 1);
    order.addItem(new Product("2", "B", "Description of B", new BigDecimal(5000), true), 1);
    order.addItem(new Product("3", "C", "Description of C", new BigDecimal(6000), true), 1);

    // THEN
    assertEquals(3, order.getItems().size());
    assertEquals(new BigDecimal(15000), order.getGrossAmount());
    assertEquals(new BigDecimal(12300), order.getNetAmount());

    // WHEN
    // Decrease quantity of product A
    Product p = new Product("1", "A", "Description of A", new BigDecimal(4000), true);
    order.decreaseItemQuantity(p, 1);

    // THEN
    assertEquals(2, order.getItems().size());
    assertEquals(new BigDecimal(11000), order.getGrossAmount());
    assertEquals(new BigDecimal(9400), order.getNetAmount());
  }
  
  @Test
  void should_calculate_bill_correctly_for_premium_type_customer_with_product_removed() {
    // WHEN
    Order order = new Order(premiumCustomer.getCustomerId(), premiumCustomer.getType(), cartAmountcalculator);
    order.addItem(new Product("1", "A", "Description of A", new BigDecimal(4000), true), 2);
    order.addItem(new Product("2", "B", "Description of B", new BigDecimal(5000), true), 1);

    // THEN
    assertEquals(2, order.getItems().size());
    assertEquals(new BigDecimal(13000), order.getGrossAmount());
    assertEquals(new BigDecimal(10900), order.getNetAmount());

    // WHEN
    // Remove product A completely from order
    Product p = new Product("1", "A", "Description of A", new BigDecimal(4000), true);
    order.removeItem(p);

    // THEN
    assertEquals(1, order.getItems().size());
    assertEquals(new BigDecimal(5000), order.getGrossAmount());
    assertEquals(new BigDecimal(4450), order.getNetAmount());
  }  
  
  @Test
  void should_calculate_Gross_And_Net_Amount_To_Zero_when_cart_is_cleared() {
    // WHEN
    Order order = new Order(premiumCustomer.getCustomerId(), premiumCustomer.getType(), cartAmountcalculator);
    order.addItem(new Product("1", "A", "Description of A", new BigDecimal(4000), true), 2);
    order.addItem(new Product("2", "B", "Description of B", new BigDecimal(5000), true), 1);
    order.addItem(new Product("3", "C", "Description of C", new BigDecimal(6000), true), 1);

    // THEN
    assertEquals(3, order.getItems().size());
    assertEquals(new BigDecimal(19000), order.getGrossAmount());
    assertEquals(new BigDecimal(15100), order.getNetAmount());

    // WHEN
    // Clear the order completely
    order.clear();

    // THEN
    assertEquals(0, order.getItems().size());
    assertEquals(new BigDecimal(0), order.getGrossAmount());
    assertEquals(new BigDecimal(0), order.getNetAmount());
  }  
  
  
}
