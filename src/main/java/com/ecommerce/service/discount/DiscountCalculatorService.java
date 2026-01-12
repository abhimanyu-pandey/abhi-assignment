package com.ecommerce.service.discount;

import com.ecommerce.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class DiscountCalculatorService {
    
    private final List<DiscountStrategy> discountStrategies;
    
    public DiscountCalculatorService(
            PremiumUserDiscountStrategy premiumUserStrategy,
            BulkOrderDiscountStrategy bulkOrderStrategy) {
        this.discountStrategies = List.of(premiumUserStrategy, bulkOrderStrategy);
    }
    
    public BigDecimal calculateTotalDiscount(BigDecimal amount, User user) {
        BigDecimal totalDiscount = BigDecimal.ZERO;
        
        for (DiscountStrategy strategy : discountStrategies) {
            BigDecimal discount = strategy.calculateDiscount(amount, user);
            if (discount.compareTo(BigDecimal.ZERO) > 0) {
                log.debug("Applied discount: {} - Amount: {}", 
                         strategy.getDiscountDescription(), discount);
                totalDiscount = totalDiscount.add(discount);
            }
        }
        
        log.info("Total discount calculated: {} for user: {}", totalDiscount, user.getUsername());
        return totalDiscount;
    }
}
