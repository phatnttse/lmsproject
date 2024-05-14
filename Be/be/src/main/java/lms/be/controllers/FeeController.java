package lms.be.controllers;

import jakarta.validation.Valid;
import lms.be.dtos.FeeDTO;
import lms.be.models.Fee;
import lms.be.services.fee.IFeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/fees")
@RestController
@RequiredArgsConstructor
public class FeeController {

    private final IFeeService feeService;

    @PostMapping("/create")
    public ResponseEntity<?> createNewFee(@Valid @RequestBody FeeDTO feeDTO) {
        try {
            Fee newFee = feeService.createFee(feeDTO);
            return ResponseEntity.ok(newFee);
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFee(
            @PathVariable int id,
            @Valid @RequestBody FeeDTO feeDTO) {
        try {
            Fee newFee = feeService.updateFee(id, feeDTO);
            return ResponseEntity.ok(newFee);
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<?> getFeeById(@PathVariable int id){
        try {
            Fee fee = feeService.getFeeById(id);
            return ResponseEntity.ok(fee);
        }catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/all")
    public ResponseEntity<?> getFees(){
        try {
            List<Fee> fees = feeService.getAllFees();
            return ResponseEntity.ok(fees);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/mark-completed/{id}")
    public ResponseEntity<?> deleteFee(@PathVariable int id){
        try {
            Fee fee = feeService.markCompleted(id);
            return ResponseEntity.ok(fee);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
