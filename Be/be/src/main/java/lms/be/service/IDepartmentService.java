package lms.be.service;

import lms.be.dtos.DepartmentDTO;
import lms.be.exceptions.DataNotFoundException;

import java.util.List;

public interface IDepartmentService {
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO) throws Exception;
    DepartmentDTO getDepartmentById(String id) throws DataNotFoundException;
    List<DepartmentDTO> getAllDepartments();
    DepartmentDTO updateDepartment(String id, DepartmentDTO departmentDTO) throws DataNotFoundException;
    void deleteDepartment(String id) throws DataNotFoundException;
}
