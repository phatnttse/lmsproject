package lms.be.service;

import lms.be.dtos.AllocationDTO;
import lms.be.exceptions.DataNotFoundException;
import lms.be.models.Allocation;
import lms.be.models.Class;
import lms.be.models.Subject;
import lms.be.models.User;
import lms.be.repositories.AllocationRepository;
import lms.be.repositories.ClassRepository;
import lms.be.repositories.SubjectRepository;
import lms.be.repositories.UserRepository;
import lms.be.responses.AllocationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AllocationService implements IAllocationService{
    private AllocationRepository allocationRepository;
    private SubjectRepository subjectRepository;
    private ClassRepository classRepository;
    private UserRepository userRepository;

    @Override
    public AllocationResponse createAllocation(AllocationDTO allocationDTO) throws DataNotFoundException {
        Allocation allocation = mapDtoToEntity(allocationDTO);
        allocation = allocationRepository.save(allocation);
        return mapEntityToResponse(allocation);
    }

    @Override
    public AllocationResponse getAllocationById(Long id) throws DataNotFoundException {
        Allocation allocation = allocationRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Allocation not found with id: " + id));
        return mapEntityToResponse(allocation);
    }

    @Override
    public List<AllocationResponse> getAllAllocations() {
        List<Allocation> allocations = allocationRepository.findAll();
        return allocations.stream().map(this::mapEntityToResponse).collect(Collectors.toList());
    }

    @Override
    public AllocationResponse updateAllocation(Long id, AllocationDTO allocationDTO) throws DataNotFoundException {
        if(!allocationRepository.existsById(id)) {
            throw new DataNotFoundException("Cannot find allocation with id: " + id);
        } else {
            Allocation updatedAllocation = mapDtoToEntity(allocationDTO);
            updatedAllocation.setAllocationId(id);
            return mapEntityToResponse(allocationRepository.save(updatedAllocation));
        }
    }

    @Override
    public void deleteAllocation(Long id) throws DataNotFoundException {
        if (!allocationRepository.existsById(id)) {
            throw new DataNotFoundException("Cannot find allocation with id: " + id);
        }
        allocationRepository.deleteById(id);
    }

    private AllocationResponse mapEntityToResponse(Allocation allocation) {
        return AllocationResponse.builder()
                .allocationId(allocation.getAllocationId())
                .classId(allocation.getAClass().getClassId())
                .teacherId(allocation.getTeacher().getUserId())
                .subjectId(allocation.getSubject().getSubjectId())
                .build();
    }

    private Allocation mapDtoToEntity(AllocationDTO allocationDTO) throws DataNotFoundException {
        Subject subject = subjectRepository.findById(allocationDTO.getSubjectId())
                .orElseThrow(()-> new DataNotFoundException
                        ("Cannot find subject with id: " + allocationDTO.getSubjectId()));
        Class aclass = classRepository.findById(allocationDTO.getClassId())
                .orElseThrow(()-> new DataNotFoundException
                        ("Cannot find class with id: " + allocationDTO.getClassId()));
        User teacher = userRepository.findById(allocationDTO.getTeacherId())
                .orElseThrow(()-> new DataNotFoundException
                        ("Cannot find teacher with id: " + allocationDTO.getTeacherId()));
        return Allocation.builder()
                .subject(subject)
                .aClass(aclass)
                .teacher(teacher)
                .build();
    }

}
