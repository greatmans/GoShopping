package com.sincro.model.offer.discount;

import java.math.BigDecimal;

public class Range {

  private BigDecimal lowerLimit;
  private BigDecimal upperLimit;

  public Range(BigDecimal lowerLimit, BigDecimal upperLimit) {
    if (lowerLimit.compareTo(BigDecimal.ZERO) < 0 || (upperLimit != null && upperLimit.compareTo(BigDecimal.ZERO) < 0))
      throw new IllegalArgumentException("Discount limits can't be negative");
    
    if(upperLimit != null && lowerLimit.compareTo(upperLimit) >= 0)
      throw new IllegalArgumentException("Lower limit can't be smaller than upperLimit");

    this.lowerLimit = lowerLimit;
    
    if(upperLimit != null) {
      this.upperLimit = upperLimit;
    }
    else {
      this.upperLimit = new BigDecimal(Double.MAX_VALUE);
    }
  }

  public BigDecimal getLowerLimit() {
    return lowerLimit;
  }

  public void setLowerLimit(BigDecimal lowerLimit) {
    this.lowerLimit = lowerLimit;
  }

  public BigDecimal getUpperLimit() {
    return upperLimit;
  }

  public void setUpperLimit(BigDecimal upperLimit) {
    this.upperLimit = upperLimit;
  }
}
