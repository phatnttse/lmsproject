package lms.be.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {

    @JsonProperty("fullname")
    private String name;

    private String address;

    private String phoneNumber;

    private String picture;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;


}
