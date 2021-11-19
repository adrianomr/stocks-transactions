package br.com.adrianorodrigues.stockstransactions.external.queue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserTransactionsDto {

    private Long id;
    private List<StockTransactionDto> transactions;

}
