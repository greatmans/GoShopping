package com.sincro.model.offer.discount;

import java.math.BigDecimal;
import com.sincro.model.offer.Offer;

public class Discount implements Offer {

  protected int percentage;

  public Discount(int percentage) {
    this.percentage = percentage;
  }

  public int getPercentage() {
    return percentage;
  }

  public void setPercentage(int percentage) {
    this.percentage = percentage;
  }

  @Override
  public BigDecimal apply(BigDecimal amount) {
    return amount.divide(new BigDecimal(100)).multiply(new BigDecimal(percentage));
  }
}
