package lms.be.service;

import lms.be.dtos.AllocationDTO;
import lms.be.exceptions.DataNotFoundException;
import lms.be.responses.AllocationResponse;

import java.util.List;

public interface IAllocationService {
    AllocationResponse createAllocation(AllocationDTO allocationDTO) throws DataNotFoundException;
    AllocationResponse getAllocationById(Long id) throws DataNotFoundException;
    List<AllocationResponse> getAllAllocations();
    AllocationResponse updateAllocation(Long id, AllocationDTO allocationDTO) throws DataNotFoundException;
    void deleteAllocation(Long id) throws DataNotFoundException;
}
