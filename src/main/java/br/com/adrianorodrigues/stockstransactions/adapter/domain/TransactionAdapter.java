package br.com.adrianorodrigues.stockstransactions.adapter.domain;

import br.com.adrianorodrigues.stockstransactions.domain.Transaction;
import br.com.adrianorodrigues.stockstransactions.entrypoint.consumer.CeiTransactionDto;
import br.com.adrianorodrigues.stockstransactions.entrypoint.consumer.CeiUserTransactionsDto;
import br.com.adrianorodrigues.stockstransactions.enums.TransactionOrigin;
import br.com.adrianorodrigues.stockstransactions.enums.TransactionType;
import br.com.adrianorodrigues.stockstransactions.external.repository.dto.TransactionDto;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class TransactionAdapter {

    public static List<Transaction> fromCeiUserTransactionDto(CeiUserTransactionsDto userTransactions) {
        return userTransactions
                .getTransactions()
                .stream()
                .map(transaction -> TransactionAdapter.convert(userTransactions.getId(), transaction))
                .collect(Collectors.toList());
    }

    public static Transaction convert(Long userId, CeiTransactionDto transaction) {
        return Transaction
                .builder()
                .amount(new BigDecimal(transaction.getUnitAmount()))
                .date(LocalDate.parse(transaction.getOperationDate()))
                .ticker(transaction.getRawNegotiationCode())
                .price(new BigDecimal(transaction.getUnitPrice()))
                .type(TransactionType.valueOf(transaction.getAction().toUpperCase()))
                .userId(userId)
                .origin(TransactionOrigin.CEI_B3)
                .build();
    }

    public static List<Transaction> fromTransactionDto(List<TransactionDto> transactions) {
        return transactions
                .stream()
                .map(TransactionAdapterHelper
                        .INSTANCE::convert)
                .collect(Collectors.toList());
    }
}
