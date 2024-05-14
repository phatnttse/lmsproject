package lms.be.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "relatives")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Relative {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relativeId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String job;

    private Date dob;

    private String address;

    // getters and setters
}
