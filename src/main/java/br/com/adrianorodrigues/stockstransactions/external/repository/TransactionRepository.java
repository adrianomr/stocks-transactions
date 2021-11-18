package br.com.adrianorodrigues.stockstransactions.external.repository;

import br.com.adrianorodrigues.stockstransactions.external.repository.dto.TransactionDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionDto, Long> {

    List<TransactionDto> findAllByUserId(Long userId);
}
