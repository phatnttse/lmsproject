package lms.be.services.transaction;

import lms.be.dtos.LookUpTransactionDTO;
import lms.be.dtos.TransactionDTO;
import lms.be.enums.Role;
import lms.be.exceptions.DataNotFoundException;
import lms.be.models.Fee;
import lms.be.models.Transaction;
import lms.be.models.User;
import lms.be.repositories.FeeRepository;
import lms.be.repositories.TransactionRepository;
import lms.be.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TransactionService implements ITransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final FeeRepository feeRepository;


    @Override
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions;
    }

    @Override
    public List<Transaction> getTransactionsByUserId(int studentId) {
        Optional<User> user = userRepository.findById(studentId);
        if (user.isPresent()){
            return transactionRepository.findByStudent(user.get());
        }
        return Collections.emptyList();
    }

    @Override
    public List<Transaction> getTransactionsByFeeId(int feeId) {
        Optional<Fee> fee = feeRepository.findById(feeId);
        if (fee.isPresent()){
            return transactionRepository.findByFee(fee.get());
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {
        Fee fee = feeRepository.findById(transactionDTO.getFeeId()).orElseThrow(
                () -> new DataNotFoundException("Cannot find fee with feeId: "+ transactionDTO.getFeeId()));

        User student = userRepository.findById(transactionDTO.getStudentId()).orElseThrow(
                () -> new DataNotFoundException("Cannot find student with userId: "+ transactionDTO.getStudentId())
        );

        Transaction transaction = Transaction.builder()
                .fee(fee)
                .student(student)
                .amount(fee.getAmount())
                .transactionDate(transactionDTO.getTransactionDate())
                .build();

        return transactionRepository.save(transaction); 
    }

    @Override
    @Transactional
    public Transaction deleteTransaction(int id) throws Exception{

        Optional<Transaction> optionalTransaction = transactionRepository.findById(id);
        if (optionalTransaction.isPresent()) {
            Transaction existingTransaction = optionalTransaction.get();

            if (checkEndDateCondition(existingTransaction)) {

                transactionRepository.delete(existingTransaction);
                return existingTransaction;
            } else {
                throw new IllegalStateException("Cannot delete transaction due to end date condition");
            }
        } else {
            throw new DataNotFoundException("Transaction with id " + id + " not found");
        }
    }

    @Override
    public int totalAmountCollected(int feeId) throws DataNotFoundException {

        Fee existingFee = feeRepository.findById(feeId).orElseThrow(() -> new DataNotFoundException("Cannot find fee"));

        List<Transaction> transactions = transactionRepository.findByFee(existingFee);

        int totalAmount = 0;

        for (Transaction transaction : transactions) {
            totalAmount += transaction.getAmount();
        }

        return totalAmount;
    }

    @Override
    public int calculateTotalFeeAmount(int feeId) throws Exception {
        Fee existingFee = feeRepository.findById(feeId).orElseThrow(() -> new DataNotFoundException("Cannot find fee"));

        // Lấy số lượng học sinh
        int studentCount = userRepository.countByRole(Role.Student.name());

        // Tính tổng số tiền cần thu
        int totalAmountToCollect = studentCount * existingFee.getAmount();

        return totalAmountToCollect;
    }
    @Override
    public int numberOfStudentsPaidFee(int feeId) throws DataNotFoundException {
        // Lấy danh sách tất cả học sinh
        List<User> students = userRepository.findByRole(Role.Student.name());

        // Lấy danh sách các giao dịch liên quan đến khoản phí cụ thể
        Fee existingFee = feeRepository.findById(feeId).orElseThrow(() -> new DataNotFoundException("Cannot find fee"));
        List<Transaction> transactions = transactionRepository.findByFee(existingFee);

        // Tạo một bản đồ để lưu trữ số lượng giao dịch cho mỗi học sinh
        Map<Integer, Integer> transactionsCountByStudentId = new HashMap<>();
        for (Transaction transaction : transactions) {
            int userId = transaction.getStudent().getUserId();
            transactionsCountByStudentId.put(userId, transactionsCountByStudentId.getOrDefault(userId, 0) + 1);
        }

        // Đếm số lượng học sinh đã đóng phí
        int numberOfStudentsPaid = 0;
        for (User student : students) {
            int userId = student.getUserId();
            if (transactionsCountByStudentId.containsKey(userId)) {
                numberOfStudentsPaid++;
            }
        }

        return numberOfStudentsPaid;
    }

    @Override
    public int numberOfStudentsNotPaidFee(int feeId) throws DataNotFoundException {
        int totalStudents = userRepository.countByRole(Role.Student.name());
        int studentsPaidFee = numberOfStudentsPaidFee(feeId);
        return totalStudents - studentsPaidFee;
    }

    @Override
    public List<Transaction> getTransactionsByDateRange(LookUpTransactionDTO lookUpTransactionDTO) throws Exception {
        Optional<User> existingUser = userRepository.findById(lookUpTransactionDTO.getUserId());
        if (existingUser.isPresent()) {
            return transactionRepository.findByTransactionDateBetweenAndStudentUserId(lookUpTransactionDTO.getStartDate(),
                    lookUpTransactionDTO.getEndDate(), lookUpTransactionDTO.getUserId());
        }else {
            throw  new DataNotFoundException("Cannot find user");
        }

    }


    private boolean checkEndDateCondition(Transaction transaction) {
        // Lấy ngày kết thúc của Fee liên kết với giao dịch
        Date endDate = transaction.getFee().getEndDate();
        // Lấy ngày hiện tại
        Date currentDate = new Date();
        // So sánh ngày kết thúc của Fee với ngày hiện tại
        return currentDate.after(endDate);
    }


}
