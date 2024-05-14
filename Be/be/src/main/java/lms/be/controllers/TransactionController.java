package lms.be.controllers;

import jakarta.validation.Valid;
import lms.be.dtos.LookUpTransactionDTO;
import lms.be.dtos.TransactionDTO;
import lms.be.models.Transaction;
import lms.be.responses.TransactionResponse;
import lms.be.services.transaction.ITransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final ITransactionService transactionService;

    @GetMapping("/all")
    public ResponseEntity<List<TransactionResponse>> getAllTransactions() {
        try {
            List<Transaction> transactions = transactionService.getAllTransactions();

            return ResponseEntity.ok(TransactionResponse.fromTransaction(transactions));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getTransactionsByUser(@PathVariable int userId) {
        try {
            List<Transaction> transactions = transactionService.getTransactionsByUserId(userId);
            return ResponseEntity.ok(TransactionResponse.fromTransaction(transactions));

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTransaction(@Valid @RequestBody TransactionDTO transactionDTO) {
        try {
            Transaction transaction = transactionService.createTransaction(transactionDTO);
            return ResponseEntity.ok(transaction);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/fee/{feeId}")
    public ResponseEntity<?> getTransactionsByFee(@PathVariable int feeId) {
        try {
            List<Transaction> transactions = transactionService.getTransactionsByFeeId(feeId);
            return ResponseEntity.ok(TransactionResponse.fromTransaction(transactions));

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/amount-collected/{feeId}")
    public int totalAmountCollected(@PathVariable int feeId) throws Exception {
        try {
            int totalAmountCollected = transactionService.totalAmountCollected(feeId);
            return totalAmountCollected;
        }catch (Exception e){
            return 0;
        }
    }

    @GetMapping("/amount-toCollect/{feeId}")
    public int totalAmountToCollect(@PathVariable int feeId) throws Exception {
        try {
            int totalToAmountCollect = transactionService.calculateTotalFeeAmount(feeId);
            return totalToAmountCollect;
        }catch (Exception e){
            return 0;
        }
    }

    @GetMapping("/number-student-paidFee/{feeId}")
    public int numberOfStudentsPaidFee(@PathVariable int feeId) throws Exception {
        try {
            int numberOfStudentsPaidFee = transactionService.numberOfStudentsPaidFee(feeId);
            return numberOfStudentsPaidFee;
        }catch (Exception e){
            return 0;
        }
    }

    @GetMapping("/number-student-notPaidFee/{feeId}")
    public int numberOfStudentsNotPaidFee(@PathVariable int feeId) throws Exception {
        try {
            int numberOfStudentsNotPaidFee = transactionService.numberOfStudentsNotPaidFee(feeId);
            return numberOfStudentsNotPaidFee;
        }catch (Exception e){
            return 0;
        }
    }

    @PostMapping("/lookUpTransactions")
    public ResponseEntity<?> lookUpTransactions(@Valid @RequestBody LookUpTransactionDTO lookUpTransactionDTO) throws Exception {
        try {
            List<Transaction> transactions = transactionService.getTransactionsByDateRange(lookUpTransactionDTO);
            return ResponseEntity.ok(TransactionResponse.fromTransaction(transactions));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable int id) {
        try {
            Transaction transaction = transactionService.deleteTransaction(id);
            return ResponseEntity.ok(transaction);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
