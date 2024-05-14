package lms.be.models;
import jakarta.persistence.*;
import lms.be.enums.Conduct;
import lombok.*;

@Entity
@Table(name = "academic_results")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AcademicResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long academicResultId;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    @Enumerated(EnumType.STRING)
    private Conduct conduct;

    private Float averageFirstSemester;

    private Float averageSecondSemester;

    private Float finalGrade;

    // getters and setters
}