package com.sincro.model.offer.discount;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DiscountCategory {

  private List<Discount> discounts;

  public DiscountCategory() {
    this.discounts = new ArrayList<>();
  }
  
  public DiscountCategory(List<Discount> discounts) {
    this.discounts = discounts;
  }

  public List<Discount> getDiscounts() {
    return discounts;
  }

  public void setDiscounts(List<Discount> discounts) {
    this.discounts = discounts;
  }
  
  public void addDiscount(Discount discount) {
    Optional.<Discount>ofNullable(discount)
      .ifPresent(d -> discounts.add(d));
  }

}
