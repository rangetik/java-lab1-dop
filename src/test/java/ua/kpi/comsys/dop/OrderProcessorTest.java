package ua.kpi.comsys.dop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderProcessorTest {

    private OrderProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new OrderProcessor();
    }

    @Test
    @DisplayName("null замість статусу -> IllegalArgumentException з точним повідомленням")
    void processNullThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> processor.process(null));
        assertEquals("Status cannot be null", ex.getMessage());
    }

    @Test
    @DisplayName("Pending -> шаблон PENDING")
    void processPending() {
        String expected = """
                Order Status: PENDING
                Details: Order is currently pending processing.
                """;
        assertEquals(expected, processor.process(new Pending()));
    }

    @Test
    @DisplayName("Paid -> шаблон PAID з ID платежу та сумою")
    void processPaid() {
        String expected = """
                Order Status: PAID
                Details: Payment ID: PAY-123, Amount: $150.50
                """;
        assertEquals(expected, processor.process(new Paid("PAY-123", new BigDecimal("150.50"))));
    }

    @Test
    @DisplayName("Shipped -> шаблон SHIPPED з трек-кодом та датою")
    void processShipped() {
        String expected = """
                Order Status: SHIPPED
                Details: Tracking Code: UA123456789, Dispatch Date: 2026-11-16
                """;
        Shipped shipped = new Shipped("UA123456789", LocalDate.of(2026, 11, 16));
        assertEquals(expected, processor.process(shipped));
    }

    @Test
    @DisplayName("Cancelled -> шаблон CANCELLED з причиною")
    void processCancelled() {
        String expected = """
                Order Status: CANCELLED
                Details: Reason: Customer request
                """;
        assertEquals(expected, processor.process(new Cancelled("Customer request")));
    }
}