package lms.be.models;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "class")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Class {
    @Id
    private String classId;

    private String name;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    // getters and setters
}