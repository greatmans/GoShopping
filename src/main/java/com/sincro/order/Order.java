package com.sincro.order;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import com.sincro.calculator.CartAmountCalculator;
import com.sincro.model.customer.CustomerType;
import com.sincro.model.product.Product;
import com.sincro.payment.PaymentMode;

public class Order {

  protected Map<Product, Integer> items;

  protected String customerID;
  protected CustomerType customerType;
  protected BigDecimal grossAmount;
  protected BigDecimal netAmount;
  protected CartAmountCalculator amountCalculator;
  protected OrderStatus status;

  public Order(String customerID, CustomerType customerType, CartAmountCalculator calculator) {
    this.customerID = customerID;
    this.customerType = customerType;
    this.items = new HashMap<>();
    this.grossAmount = BigDecimal.ZERO;
    this.netAmount = BigDecimal.ZERO;
    this.amountCalculator = calculator;
    this.status = OrderStatus.IN_CART;
  }

  public Order(String customerID, CustomerType customerType, Map<Product, Integer> items,
      CartAmountCalculator calculator) {
    this.customerID = customerID;
    this.customerType = customerType;
    this.items = items;
    this.grossAmount = BigDecimal.ZERO;
    grossAmount.setScale(2);
    this.netAmount = BigDecimal.ZERO;
    netAmount.setScale(2);
    this.amountCalculator = calculator;
    this.status = OrderStatus.IN_CART;
  }

  public Map<Product, Integer> getItems() {
    return items;
  }

  public void setItems(Map<Product, Integer> items) {
    this.items = items;
    calculateGrossAmount();
    calculateNetAmount();
  }

  public BigDecimal getGrossAmount() {
    return grossAmount;
  }

  public BigDecimal getNetAmount() {
    return netAmount;
  }

  public CustomerType getCustomerType() {
    return customerType;
  }

  public boolean increaseItemQuantity(Product item, int quantity) {
    return addOrUpdateItem(item, quantity);
  }
  
  public boolean addItem(Product item, int quantity) {
    return Optional.<Product>ofNullable(item).map(i -> addOrUpdateItem(i, quantity)).orElse(false);
  }

  private boolean addOrUpdateItem(Product item, int quantity) {
    boolean updated = false;

    Integer itemQty;
    if ((itemQty = items.get(item)) != null) {
      itemQty += quantity;
      updated = items.put(item, itemQty) != null;
    } else {
      updated = items.put(item, quantity) == null;
    }

    if (updated) {
      calculateGrossAmount();
      calculateNetAmount();
    }
    return updated;
  }
  
  public boolean removeItem(Product item) {
    return removeItem(item, items.get(item));
   }
   
   public boolean decreaseItemQuantity(Product item, int quantity) {
     return removeItem(item, quantity);
   }
   
  private boolean removeItem(Product item, int quantity) {
    return Optional.<Product>ofNullable(item).map(i -> removeOrUpdateItem(i, quantity)).orElse(false);
  }

  private boolean removeOrUpdateItem(Product item, int quantity) {
    boolean updated = false;
    
    Integer itemQty;
    if ((itemQty = items.get(item)) != null) {
      if (quantity >= itemQty) {
        //remove item as all items need to be removed
        items.remove(item);
      }
      else {
        itemQty -= quantity;
        items.put(item, itemQty);
      }
      updated = true;
    }

    if (updated) {
      calculateGrossAmount();
      calculateNetAmount();
    }
    return updated;
  }

  private void calculateGrossAmount() {
    grossAmount = amountCalculator.calculateGrossAmount(items);
  }

  private void calculateNetAmount() {
    netAmount = amountCalculator.calculateNetAmount(this);
  }

  public void clear() {
    items.clear();
    grossAmount = BigDecimal.ZERO;
    netAmount = BigDecimal.ZERO;
  }

  public void placeOrder() {
    status = OrderStatus.PLACED;
    // perform other operations post order placement
  }

  public void pay(PaymentMode paymentMode) {
    // if payment successful
    // status = OrderStatus.PAID
  }

  public void cancel() {
    status = OrderStatus.CANCELLED;
  }

  public void discard() {
    status = OrderStatus.DISCARDED;
  }

}
