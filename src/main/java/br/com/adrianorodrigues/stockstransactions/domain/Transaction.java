package br.com.adrianorodrigues.stockstransactions.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Transaction {

    private Long id;
    private LocalDate date;
    private String ticker;
    private BigDecimal amount;
    private BigDecimal price;
    private Integer sequence;
    private Long userId;

    public String generateKey() {
        return ticker + date + price.setScale(2, RoundingMode.CEILING) + amount.setScale(2, RoundingMode.CEILING);
    }

    public boolean equalsIgnoringId(Transaction transaction) {
        return (generateKey() + sequence).equalsIgnoreCase(transaction.generateKey() + transaction.sequence);
    }
}