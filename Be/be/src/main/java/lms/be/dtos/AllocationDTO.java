package lms.be.dtos;

import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllocationDTO {
    private String subjectId;
    private int teacherId;
    private String classId;
}
