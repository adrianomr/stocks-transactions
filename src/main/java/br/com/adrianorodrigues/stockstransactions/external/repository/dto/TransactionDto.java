package br.com.adrianorodrigues.stockstransactions.external.repository.dto;

import br.com.adrianorodrigues.stockstransactions.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transaction")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDto {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private String ticker;
    @Column(nullable = false)
    private BigDecimal amount;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @Column(nullable = false)
    private Integer sequence;
    @Column(nullable = false)
    private Long userId;

}
