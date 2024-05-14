package lms.be.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AllocationResponse {
    private Long allocationId;
    private String subjectId;
    private int teacherId;
    private String classId;
}
