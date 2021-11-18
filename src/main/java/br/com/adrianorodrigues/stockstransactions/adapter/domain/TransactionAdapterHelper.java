package br.com.adrianorodrigues.stockstransactions.adapter.domain;

import br.com.adrianorodrigues.stockstransactions.domain.Transaction;
import br.com.adrianorodrigues.stockstransactions.external.repository.dto.TransactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionAdapterHelper {

    TransactionAdapterHelper INSTANCE = Mappers.getMapper(TransactionAdapterHelper.class);

    Transaction convert(TransactionDto transactionDto);
}
