package br.com.adrianorodrigues.stockstransactions.entrypoint.consumer;

import lombok.Data;

import java.util.List;

@Data
public class CeiUserTransactionsDto {

    private Long id;
    private List<CeiTransactionDto> transactions;

}
