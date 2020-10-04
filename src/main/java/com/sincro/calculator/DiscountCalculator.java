package com.sincro.calculator;

import java.math.BigDecimal;
import com.sincro.order.Order;

public interface DiscountCalculator {

  public BigDecimal calculate(Order order);
}
