package lms.be.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subject")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subject {
    @Id
    private String subjectId;

    private String title;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
