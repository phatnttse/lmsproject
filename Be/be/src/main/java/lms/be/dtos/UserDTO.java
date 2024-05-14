package lms.be.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lms.be.enums.Role;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    @JsonProperty("user_id")
    private int id;

    private String email;

    private String password;

    @JsonProperty("fullname")
    private String name;

    @JsonProperty("phone_number")
    private String phone;

    @JsonProperty("google_id")
    private String googleId;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    private String address;

    private String picture;

    @JsonProperty("ethnicity_id")
    private int ethnicityId;

    private Role role;







}
