package ua.kpi.comsys.dop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderStatusValidationTest {

    private static final BigDecimal VALID_AMOUNT = new BigDecimal("10.00");
    private static final LocalDate VALID_DATE = LocalDate.of(2026, 11, 16);

    @Nested
    @DisplayName("Paid")
    class PaidValidation {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "   ", "\t", "\n"})
        @DisplayName("порожній або null paymentID -> IllegalArgumentException")
        void invalidPaymentId(String paymentId) {
            assertThrows(IllegalArgumentException.class, () -> new Paid(paymentId, VALID_AMOUNT));
        }

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"0", "0.00", "-1", "-0.01"})
        @DisplayName("amount null, 0 або від'ємний -> IllegalArgumentException")
        void invalidAmount(BigDecimal amount) {
            assertThrows(IllegalArgumentException.class, () -> new Paid("PAY-1", amount));
        }

        @Test
        @DisplayName("коректні дані -> об'єкт створюється")
        void validPaid() {
            Paid paid = assertDoesNotThrow(() -> new Paid("PAY-1", new BigDecimal("0.01")));
            assertEquals("PAY-1", paid.paymentID());
            assertEquals(new BigDecimal("0.01"), paid.amount());
        }
    }

    @Nested
    @DisplayName("Shipped")
    class ShippedValidation {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "\t"})
        @DisplayName("порожній або null trackingCode -> IllegalArgumentException")
        void invalidTrackingCode(String trackingCode) {
            assertThrows(IllegalArgumentException.class, () -> new Shipped(trackingCode, VALID_DATE));
        }

        @Test
        @DisplayName("dispatchDate == null -> IllegalArgumentException")
        void nullDispatchDate() {
            assertThrows(IllegalArgumentException.class, () -> new Shipped("UA123", null));
        }

        @Test
        @DisplayName("коректні дані -> об'єкт створюється")
        void validShipped() {
            Shipped shipped = assertDoesNotThrow(() -> new Shipped("UA123", VALID_DATE));
            assertEquals("UA123", shipped.trackingCode());
            assertEquals(VALID_DATE, shipped.dispatchDate());
        }
    }

    @Nested
    @DisplayName("Cancelled")
    class CancelledValidation {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {" ", "\t"})
        @DisplayName("порожня або null reason -> IllegalArgumentException")
        void invalidReason(String reason) {
            assertThrows(IllegalArgumentException.class, () -> new Cancelled(reason));
        }

        @Test
        @DisplayName("коректні дані -> об'єкт створюється")
        void validCancelled() {
            Cancelled cancelled = assertDoesNotThrow(() -> new Cancelled("Out of stock"));
            assertEquals("Out of stock", cancelled.reason());
        }
    }

    @Test
    @DisplayName("records порівнюються за значенням")
    void recordsHaveValueEquality() {
        assertEquals(new Pending(), new Pending());
        assertEquals(new Paid("PAY-1", VALID_AMOUNT), new Paid("PAY-1", VALID_AMOUNT));
    }
}