package lms.be.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UserResponse {
    @JsonProperty("user_id")
    private int id;

    private String email;

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

    private String role;

    public enum Role {
        Admin,
        Student,
        Teacher
    }
    public static UserResponse fromUser(User user) {
        return UserResponse.builder()
                .id(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhoneNumber())
                .address(user.getAddress())
                .picture(user.getPicture())
                .dateOfBirth(user.getDateOfBirth())
                .googleId(user.getGoogleId())
                .ethnicityId(user.getEthnicityId())
                .role(user.getRole())
                .build();
    }
    public static List<UserResponse> fromUserList(List<User> users) {
        return users.stream()
                .map(user -> UserResponse.builder()
                .id(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhoneNumber())
                .address(user.getAddress())
                .picture(user.getPicture())
                .dateOfBirth(user.getDateOfBirth())
                .googleId(user.getGoogleId())
                .ethnicityId(user.getEthnicityId())
                .role(user.getRole())
                .build())
                .collect(Collectors.toList());
    }
}
