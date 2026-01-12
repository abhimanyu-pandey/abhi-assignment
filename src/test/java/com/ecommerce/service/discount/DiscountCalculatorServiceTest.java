package com.ecommerce.service.discount;

import com.ecommerce.model.entity.User;
import com.ecommerce.model.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountCalculatorServiceTest {

    private DiscountCalculatorService discountCalculatorService;
    private PremiumUserDiscountStrategy premiumUserDiscountStrategy;
    private BulkOrderDiscountStrategy bulkOrderDiscountStrategy;

    @BeforeEach
    void setUp() {
        premiumUserDiscountStrategy = new PremiumUserDiscountStrategy();
        bulkOrderDiscountStrategy = new BulkOrderDiscountStrategy();
        discountCalculatorService = new DiscountCalculatorService(
                premiumUserDiscountStrategy,
                bulkOrderDiscountStrategy
        );
    }

    @Test
    void testNoDiscountForRegularUserWithSmallOrder() {
        User user = User.builder().role(UserRole.USER).build();
        BigDecimal amount = new BigDecimal("100.00");

        BigDecimal discount = discountCalculatorService.calculateTotalDiscount(amount, user);

        assertThat(discount).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    void testPremiumUserDiscountOnly() {
        User user = User.builder().role(UserRole.PREMIUM_USER).build();
        BigDecimal amount = new BigDecimal("100.00");

        BigDecimal discount = discountCalculatorService.calculateTotalDiscount(amount, user);

        assertThat(discount).isEqualByComparingTo(new BigDecimal("10.00")); // 10% of 100
    }

    @Test
    void testBulkOrderDiscountOnly() {
        User user = User.builder().role(UserRole.USER).build();
        BigDecimal amount = new BigDecimal("600.00");

        BigDecimal discount = discountCalculatorService.calculateTotalDiscount(amount, user);

        assertThat(discount).isEqualByComparingTo(new BigDecimal("30.00")); // 5% of 600
    }

    @Test
    void testCombinedDiscounts() {
        User user = User.builder().role(UserRole.PREMIUM_USER).build();
        BigDecimal amount = new BigDecimal("1000.00");

        BigDecimal discount = discountCalculatorService.calculateTotalDiscount(amount, user);

        // Premium user: 10% = 100
        // Bulk order: 5% = 50
        // Total = 150
        assertThat(discount).isEqualByComparingTo(new BigDecimal("150.00"));
    }

    @Test
    void testAdminUserNoDiscount() {
        User user = User.builder().role(UserRole.ADMIN).build();
        BigDecimal amount = new BigDecimal("600.00");

        BigDecimal discount = discountCalculatorService.calculateTotalDiscount(amount, user);

        assertThat(discount).isEqualByComparingTo(new BigDecimal("30.00")); // Only bulk discount
    }
}
