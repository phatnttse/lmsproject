package lms.be.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lms.be.models.Transaction;
import lms.be.models.User;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    @JsonProperty("transaction_id")
    private int transactionId;

    @JsonProperty("fee_id")
    private int feeId;

    @JsonProperty("fee_type")
    private String feeType;

    @JsonProperty("user_id")
    private int userId;

    private String fullname;

    private int amount;

    @JsonProperty("transaction_date")
    private Date transactionDate;

    public static List<TransactionResponse> fromTransaction(List<Transaction> transactions) {
        return transactions.stream()
                .map(transaction -> TransactionResponse.builder()
                        .transactionId(transaction.getTransactionId())
                        .feeId(transaction.getFee().getFeeId())
                        .feeType(transaction.getFee().getName())
                        .userId(transaction.getStudent().getUserId())
                        .fullname(transaction.getStudent().getName())
                        .amount(transaction.getAmount())
                        .transactionDate(transaction.getTransactionDate())
                        .build())
                .collect(Collectors.toList());
    }
}
