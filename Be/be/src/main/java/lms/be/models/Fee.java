package lms.be.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "fees")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Fee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("fee_id")
    private Integer feeId;

    private String name;

    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    @JsonProperty("start_date")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    @JsonProperty("end_date")
    private Date endDate;

    private int amount;

    private boolean completed;
}
