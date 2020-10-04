package com.sincro.model.offer;

import java.math.BigDecimal;

public interface Offer {
  
  public BigDecimal apply(BigDecimal amount);
}
