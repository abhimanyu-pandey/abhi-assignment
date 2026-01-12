package com.ecommerce.service.discount;

import com.ecommerce.model.entity.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class NoDiscountStrategy implements DiscountStrategy {
    
    @Override
    public BigDecimal calculateDiscount(BigDecimal amount, User user) {
        return BigDecimal.ZERO;
    }
    
    @Override
    public String getDiscountDescription() {
        return "No discount applied";
    }
}
