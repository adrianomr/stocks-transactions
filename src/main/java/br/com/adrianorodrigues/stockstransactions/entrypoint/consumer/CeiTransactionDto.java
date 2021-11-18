package br.com.adrianorodrigues.stockstransactions.entrypoint.consumer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CeiTransactionDto {
    @JsonProperty("operation_date")
    private String operationDate;
    private String action;
    @JsonProperty("market_type")
    private String marketType;
    @JsonProperty("raw_negotiation_code")
    private String rawNegotiationCode;
    @JsonProperty("asset_specification")
    private String assetSpecification;
    @JsonProperty("unit_amount")
    private int unitAmount;
    @JsonProperty("unit_price")
    private String unitPrice;
    @JsonProperty("total_price")
    private String totalPrice;
    @JsonProperty("quotation_factor")
    private int quotationFactor;
}

