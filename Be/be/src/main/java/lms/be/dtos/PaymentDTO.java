package lms.be.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDTO {

    @JsonProperty("fee_id")
    private int feeId;

    @JsonProperty("user_id")
    private int userId;

    private int amount;
    private String bankCode;
    private String language;
}
