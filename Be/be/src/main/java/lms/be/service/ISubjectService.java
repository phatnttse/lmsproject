package lms.be.service;
import lms.be.dtos.SubjectDTO;
import lms.be.exceptions.DataNotFoundException;

import java.util.List;
public interface ISubjectService {
        SubjectDTO createSubject(SubjectDTO subjectDTO) throws DataNotFoundException;
        SubjectDTO getSubjectById(String id) throws DataNotFoundException;
        List<SubjectDTO> getAllSubjects();
        SubjectDTO updateSubject(SubjectDTO subjectDTO) throws DataNotFoundException;
        void deleteSubject(String id) throws DataNotFoundException;

}
