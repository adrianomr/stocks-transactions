package br.com.adrianorodrigues.stockstransactions.adapter.external.repository;

import br.com.adrianorodrigues.stockstransactions.domain.Transaction;
import br.com.adrianorodrigues.stockstransactions.external.repository.dto.TransactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TransactionDtoAdapter {

    TransactionDtoAdapter INSTANCE = Mappers.getMapper(TransactionDtoAdapter.class);

    List<TransactionDto> convert(List<Transaction> transactions);
}
