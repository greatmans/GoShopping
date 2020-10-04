package com.sincro.model.offer.discount;

import java.math.BigDecimal;

public class CartDiscount extends Discount {

  private Range discountRange;

  public CartDiscount(Range discountRange, int percentage) {
    super(percentage);
    this.discountRange = discountRange;
  }

  public Range getDiscountRange() {
    return discountRange;
  }

  public void setDiscountRange(Range discountRange) {
    this.discountRange = discountRange;
  }

  @Override
  public BigDecimal apply(BigDecimal amount) {
    BigDecimal lowerLimit = discountRange.getLowerLimit();
    BigDecimal upperLimit = discountRange.getUpperLimit();
    BigDecimal diff = BigDecimal.ZERO;

    if (amount.compareTo(lowerLimit) > 0 &&  amount.compareTo(upperLimit) <= 0) {
      diff = amount.subtract(lowerLimit);
    } else if (amount.compareTo(upperLimit) > 0) {
      diff = upperLimit.subtract(lowerLimit);
    }

    return super.apply(diff);
  }

}
