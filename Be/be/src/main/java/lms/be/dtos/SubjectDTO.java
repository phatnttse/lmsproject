package lms.be.dtos;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {
    private String subjectId;
    private String title;
    private String departmentId;
}
