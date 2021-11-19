package br.com.adrianorodrigues.stockstransactions.external.queue.dto;

import br.com.adrianorodrigues.stockstransactions.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StockTransactionDto implements Serializable {

    private LocalDate date;
    private String ticker;
    private BigDecimal amount;
    private BigDecimal price;
    private TransactionType type;

}
