package br.com.adrianorodrigues.stockstransactions.entrypoint.rest;

import br.com.adrianorodrigues.stockstransactions.adapter.domain.TransactionAdapter;
import br.com.adrianorodrigues.stockstransactions.interceptor.token.TokenContext;
import br.com.adrianorodrigues.stockstransactions.usecase.UpdateTransactionsUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("transactions")
public class TransactionsController {

    @Autowired
    UpdateTransactionsUseCase updateTransactions;

    @Autowired
    TokenContext tokenContext;

    @PostMapping("/file")
    public void importTransactions(MultipartFile file) {
        Long userId = tokenContext.getTokenDto().getUserId();
        updateTransactions.execute(userId, TransactionAdapter
                .fromFile(file, userId));

    }

}