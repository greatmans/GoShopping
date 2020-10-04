package com.sincro.vo.discount;

import java.util.HashMap;
import java.util.Map;
import com.sincro.model.customer.CustomerType;
import com.sincro.model.offer.discount.DiscountCategory;

public class DiscountCategoryVO {

  private Map<CustomerType, DiscountCategory> discountCategories;
  
  public DiscountCategoryVO() {
    this.discountCategories = new HashMap<>();
  }

  public DiscountCategoryVO(Map<CustomerType, DiscountCategory> discountCategories) {
    this.discountCategories = discountCategories;
  }

  public Map<CustomerType, DiscountCategory> getDiscountCategories() {
    return discountCategories;
  }

  public void setDiscountCategories(Map<CustomerType, DiscountCategory> discountCategories) {
    this.discountCategories = discountCategories;
  }

  public DiscountCategory getDiscountCategoryBasedOnCustomerType(CustomerType customerType) {
    return discountCategories.get(customerType);
  }

  public void addDiscountCategory(CustomerType customerType, DiscountCategory discountCategory) {
    discountCategories.put(customerType, discountCategory);
  }

}
