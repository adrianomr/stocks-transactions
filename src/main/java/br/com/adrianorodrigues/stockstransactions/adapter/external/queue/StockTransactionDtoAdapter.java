package br.com.adrianorodrigues.stockstransactions.adapter.external.queue;

import br.com.adrianorodrigues.stockstransactions.domain.Transaction;
import br.com.adrianorodrigues.stockstransactions.external.queue.dto.StockTransactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StockTransactionDtoAdapter {

    StockTransactionDtoAdapter INSTANCE = Mappers.getMapper(StockTransactionDtoAdapter.class);

    List<StockTransactionDto> convert(List<Transaction> transactions);
}
