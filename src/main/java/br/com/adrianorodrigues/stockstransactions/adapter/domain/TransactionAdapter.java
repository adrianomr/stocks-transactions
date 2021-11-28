package br.com.adrianorodrigues.stockstransactions.adapter.domain;

import br.com.adrianorodrigues.stockstransactions.domain.Transaction;
import br.com.adrianorodrigues.stockstransactions.entrypoint.consumer.CeiTransactionDto;
import br.com.adrianorodrigues.stockstransactions.entrypoint.consumer.CeiUserTransactionsDto;
import br.com.adrianorodrigues.stockstransactions.enums.TransactionOrigin;
import br.com.adrianorodrigues.stockstransactions.enums.TransactionType;
import br.com.adrianorodrigues.stockstransactions.external.repository.dto.TransactionDto;
import br.com.adrianorodrigues.stockstransactions.util.ExcelUtil;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
@Slf4j
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

    public static List<Transaction> fromFile(MultipartFile file, Long userId) {
        List<Transaction> transactions = new ArrayList<>();
        ExcelUtil
                .getSheet(file).rowIterator().forEachRemaining(cells -> fromRow(cells, userId, transactions));
        return transactions;
    }

    public void fromRow(Row row, Long userId, List<Transaction> transactions) {
        try {
            String ticker = row.getCell(1).getStringCellValue().trim().toUpperCase();
            String transactionType = row.getCell(2).getStringCellValue().trim();
            int amount = (int) row.getCell(3).getNumericCellValue();
            Double price = row.getCell(4).getNumericCellValue();
            LocalDate date = LocalDate.ofInstant(row.getCell(0).getDateCellValue().toInstant(), ZoneId.systemDefault());

            if (ticker.endsWith("F"))
                ticker = ticker.substring(0, ticker.length() - 1);

            transactions.add(Transaction
                    .builder()
                    .ticker(ticker)
                    .amount(BigDecimal.valueOf(amount))
                    .price(BigDecimal.valueOf(price))
                    .date(date)
                    .type(TransactionType.valueOf(transactionType))
                    .origin(TransactionOrigin.FILE)
                    .userId(userId)
                    .build());
        }catch (Exception e){
            log.info("Unable to process line: {}", row);
        }
    }
}
