package lms.be.service;

import lms.be.dtos.DepartmentDTO;
import lms.be.exceptions.DataNotFoundException;
import lms.be.models.Department;
import lms.be.repositories.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class DepartmentService implements IDepartmentService{

    private final DepartmentRepository departmentRepository;

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) throws Exception {
        if(departmentRepository.existsById(departmentDTO.getDepartmentId())) {
            throw new Exception("Department id already exist");
        } else {
            Department department = mapDtoToEntity(departmentDTO);
            department = departmentRepository.save(department);
            return mapEntityToDto(department);
        }
    }

    @Override
    public DepartmentDTO getDepartmentById(String id) throws DataNotFoundException {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Department not found with id: " + id));
        return mapEntityToDto(department);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(this::mapEntityToDto).collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO updateDepartment(String id, DepartmentDTO departmentDTO) throws DataNotFoundException {
        if(!departmentRepository.existsById(id)) {
            throw new DataNotFoundException("Cannot find department with id: " + departmentDTO.getDepartmentId());
        } else {
            Department updatedDepartment = mapDtoToEntity(departmentDTO);
            return mapEntityToDto(departmentRepository.save(updatedDepartment));
        }
    }

    @Override
    public void deleteDepartment(String id) throws DataNotFoundException {
        if (!departmentRepository.existsById(id)) {
            throw new DataNotFoundException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
    }

    private DepartmentDTO mapEntityToDto(Department department) {
        return DepartmentDTO.builder()
                .departmentId(department.getDepartmentId())
                .departmentName(department.getDepartmentName())
                .build();
    }

    private Department mapDtoToEntity(DepartmentDTO departmentDTO) {
        return Department.builder()
                .departmentId(departmentDTO.getDepartmentId())
                .departmentName(departmentDTO.getDepartmentName())
                .build();
    }
}
