package com.ecommerce.service.discount;

import com.ecommerce.model.entity.User;
import com.ecommerce.model.enums.UserRole;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class PremiumUserDiscountStrategy implements DiscountStrategy {
    
    private static final BigDecimal DISCOUNT_PERCENTAGE = new BigDecimal("0.10"); // 10%
    
    @Override
    public BigDecimal calculateDiscount(BigDecimal amount, User user) {
        if (user.getRole() == UserRole.PREMIUM_USER) {
            return amount.multiply(DISCOUNT_PERCENTAGE).setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }
    
    @Override
    public String getDiscountDescription() {
        return "Premium User: 10% discount";
    }
}
