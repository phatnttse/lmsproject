package lms.be.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDTO {
    @JsonProperty("transaction_id")
    private int transactionId;

    @JsonProperty("fee_id")
    private int feeId;

    @JsonProperty("student_id")
    private int studentId;

    private int amount;

    @JsonProperty("transaction_date")
    private Date transactionDate;


}
