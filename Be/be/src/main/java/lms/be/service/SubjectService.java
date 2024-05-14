package lms.be.service;

import lms.be.dtos.SubjectDTO;
import lms.be.exceptions.DataNotFoundException;
import lms.be.models.Department;
import lms.be.models.Subject;
import lms.be.repositories.DepartmentRepository;
import lms.be.repositories.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectService implements ISubjectService{

    private SubjectRepository subjectRepository;
    private DepartmentRepository departmentRepository;
    @Override
    public SubjectDTO createSubject(SubjectDTO subjectDTO) throws DataNotFoundException {
            Subject subject = mapDtoToEntity(subjectDTO);
            return mapEntityToDto(subjectRepository.save(subject));
    }

    @Override
    public SubjectDTO getSubjectById(String id) throws DataNotFoundException {
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Subject not found with id: " + id));
        return mapEntityToDto(subject);
    }

    @Override
    public List<SubjectDTO> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAll();
        return subjects.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }

    @Override
    public SubjectDTO updateSubject(SubjectDTO subjectDTO) throws DataNotFoundException {
        if(!subjectRepository.existsById(subjectDTO.getSubjectId())){
            throw new DataNotFoundException("Cannot find subject with id: " + subjectDTO.getSubjectId());
        }else {
            Subject updatedSubject = mapDtoToEntity(subjectDTO);
            return mapEntityToDto(subjectRepository.save(updatedSubject));
        }
    }

    @Override
    public void deleteSubject(String id) throws DataNotFoundException {
        if (!subjectRepository.existsById(id)) {
            throw new DataNotFoundException("Cannot find subject with id: " + id);
        }
        subjectRepository.deleteById(id);
    }

    private SubjectDTO mapEntityToDto(Subject subject) {
        return SubjectDTO.builder()
                .departmentId(subject.getDepartment().getDepartmentId())
                .title(subject.getTitle())
                .subjectId(subject.getSubjectId())
                .build();
    }

    private Subject mapDtoToEntity(SubjectDTO subjectDTO) throws DataNotFoundException {
        Department department = departmentRepository.findByDepartmentId(subjectDTO.getSubjectId()).
                orElseThrow(() -> new DataNotFoundException
                        ("Cannot find department with id: " + subjectDTO.getDepartmentId()));
        return Subject.builder()
                .department(department)
                .subjectId(subjectDTO.getSubjectId())
                .title(subjectDTO.getTitle())
                .build();
    }
}
