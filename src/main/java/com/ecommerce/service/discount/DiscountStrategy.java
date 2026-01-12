package com.ecommerce.service.discount;

import com.ecommerce.model.entity.User;

import java.math.BigDecimal;

public interface DiscountStrategy {
    
    BigDecimal calculateDiscount(BigDecimal amount, User user);
    
    String getDiscountDescription();
}
