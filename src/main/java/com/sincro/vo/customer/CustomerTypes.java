package com.sincro.vo.customer;

import java.util.Set;
import com.sincro.model.customer.CustomerType;

public class CustomerTypes {

  private Set<CustomerType> customerTypes;

  public CustomerTypes(Set<CustomerType> customerTypes) {
    this.customerTypes = customerTypes;
  }

  public Set<CustomerType> getCustomerTypes() {
    return customerTypes;
  }

  public void setCustomerTypes(Set<CustomerType> customerTypes) {
    this.customerTypes = customerTypes;
  }

  public void addCustomerType(CustomerType customerType) {
    customerTypes.add(customerType);
  }

}
