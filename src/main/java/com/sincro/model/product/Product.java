package com.sincro.model.product;

import java.math.BigDecimal;

final public class Product {

  private String productID;
  private String name;
  private String description;
  private BigDecimal price;
  private boolean isAvailable;

  public Product(String productID, String name, String description, BigDecimal price, boolean isAvailable) {
    this.productID = productID;
    this.name = name;
    this.description = description;
    this.price = price;
    this.isAvailable = isAvailable;
  }

  public String getProductID() {
    return productID;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public boolean isAvailable() {
    return isAvailable;
  }

  public void setAvailable(boolean isAvailable) {
    this.isAvailable = isAvailable;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((description == null) ? 0 : description.hashCode());
    result = prime * result + (isAvailable ? 1231 : 1237);
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((price == null) ? 0 : price.hashCode());
    result = prime * result + ((productID == null) ? 0 : productID.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Product other = (Product) obj;
    if (description == null) {
      if (other.description != null)
        return false;
    } else if (!description.equals(other.description))
      return false;
    if (isAvailable != other.isAvailable)
      return false;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (price == null) {
      if (other.price != null)
        return false;
    } else if (!price.equals(other.price))
      return false;
    if (productID == null) {
      if (other.productID != null)
        return false;
    } else if (!productID.equals(other.productID))
      return false;
    return true;
  }

}
