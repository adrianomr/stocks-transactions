package br.com.adrianorodrigues.stockstransactions.external.queue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserTransactionsDto implements Serializable {

    private Long id;
    private List<StockTransactionDto> transactions;

}
