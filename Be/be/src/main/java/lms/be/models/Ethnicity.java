package lms.be.models;


import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ethnicities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ethnicity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ethnicityId;

    private String name;

    // getters and setters
}
