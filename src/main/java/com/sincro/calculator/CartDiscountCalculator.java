package com.sincro.calculator;

import java.math.BigDecimal;
import com.sincro.model.offer.discount.Discount;
import com.sincro.model.offer.discount.DiscountCategory;
import com.sincro.order.Order;
import com.sincro.vo.discount.DiscountCategoryVO;

public class CartDiscountCalculator implements DiscountCalculator {

  private DiscountCategoryVO discountCategories;

  public CartDiscountCalculator(DiscountCategoryVO discountCategories) {
    this.discountCategories = discountCategories;
  }

  @Override
  public BigDecimal calculate(Order order) {
    BigDecimal totalDiscount = BigDecimal.ZERO;
    DiscountCategory discountCategory = discountCategories.getDiscountCategoryBasedOnCustomerType(order.getCustomerType());

    if (discountCategory != null && !discountCategory.getDiscounts().isEmpty()) {
      for (Discount discount : discountCategory.getDiscounts()) {
        BigDecimal applicableDiscount = discount.apply(order.getGrossAmount());
        totalDiscount = totalDiscount.add(applicableDiscount);
      }
    }

    return totalDiscount;
  }
}
