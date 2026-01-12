package com.ecommerce.service.discount;

import com.ecommerce.model.entity.User;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class BulkOrderDiscountStrategy implements DiscountStrategy {
    
    private static final BigDecimal BULK_THRESHOLD = new BigDecimal("500.00");
    private static final BigDecimal DISCOUNT_PERCENTAGE = new BigDecimal("0.05"); // 5%
    
    @Override
    public BigDecimal calculateDiscount(BigDecimal amount, User user) {
        if (amount.compareTo(BULK_THRESHOLD) > 0) {
            return amount.multiply(DISCOUNT_PERCENTAGE).setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }
    
    @Override
    public String getDiscountDescription() {
        return "Bulk Order: 5% discount for orders over $500";
    }
}
