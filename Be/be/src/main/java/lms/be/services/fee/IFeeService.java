package lms.be.services.fee;

import lms.be.dtos.FeeDTO;
import lms.be.models.Fee;

import java.util.List;

public interface IFeeService {

    Fee createFee(FeeDTO feeDTO);

    Fee getFeeById(int id);

    List<Fee> getAllFees();

    Fee updateFee(int id, FeeDTO feeDTO);

    Fee markCompleted(int id) throws Exception;
}
