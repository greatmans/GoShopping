package com.sincro.calculator;

import java.math.BigDecimal;
import java.util.Map;
import com.sincro.model.product.Product;
import com.sincro.order.Order;

public class CartAmountCalculator {

  private DiscountCalculator discountCalculator;

  public CartAmountCalculator(CartDiscountCalculator discountCalculator) {
    this.discountCalculator = discountCalculator;
  }

  public BigDecimal calculateGrossAmount(Map<Product, Integer> items) {
    BigDecimal grossAmount = BigDecimal.ZERO;

   for(Map.Entry<Product, Integer> entry : items.entrySet()) {
      BigDecimal productPrice = entry.getKey().getPrice();
      BigDecimal productTotal = productPrice.multiply(new BigDecimal(entry.getValue()));
      grossAmount = grossAmount.add(productTotal);
    }

    return grossAmount;
  }

  public BigDecimal calculateNetAmount(Order order) {
    BigDecimal applicableDiscount = discountCalculator.calculate(order);
    return order.getGrossAmount().subtract(applicableDiscount);
  }
}
