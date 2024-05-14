package lms.be.services.fee;

import lms.be.dtos.FeeDTO;
import lms.be.models.Fee;
import lms.be.models.Transaction;
import lms.be.repositories.FeeRepository;
import lms.be.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeeService implements IFeeService {

    private final FeeRepository feeRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Fee createFee(FeeDTO feeDTO) {
        Fee fee = Fee.builder()
                .name(feeDTO.getName())
                .description(feeDTO.getDescription())
                .startDate(feeDTO.getStartDate())
                .endDate(feeDTO.getEndDate())
                .amount(feeDTO.getAmount())
                .build();
        return feeRepository.save(fee);
    }

    @Override
    public Fee getFeeById(int id) {
        return feeRepository.findById(id).orElseThrow(() -> new RuntimeException("Fee doesn't exists"));
    }

    @Override
    public List<Fee> getAllFees() {
        return feeRepository.findAll();
    }

    @Override
    @Transactional
    public Fee updateFee(int id, FeeDTO feeDTO) {
        Fee existingFee = getFeeById(id);

        existingFee.setName(feeDTO.getName());
        existingFee.setDescription(feeDTO.getDescription());
        existingFee.setStartDate(feeDTO.getStartDate());
        existingFee.setEndDate(feeDTO.getEndDate());
        existingFee.setAmount(feeDTO.getAmount());

        return feeRepository.save(existingFee);
    }

    @Override
    @Transactional
    public Fee markCompleted(int id) throws Exception {

        Fee existingFee = feeRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        existingFee.setCompleted(true);
        return feeRepository.save(existingFee);

        }

    }



