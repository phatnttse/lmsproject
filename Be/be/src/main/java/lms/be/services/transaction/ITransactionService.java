package lms.be.services.transaction;

import lms.be.dtos.LookUpTransactionDTO;
import lms.be.dtos.TransactionDTO;
import lms.be.models.Transaction;
import lms.be.models.User;

import java.util.Date;
import java.util.List;

public interface ITransactionService {

    List<Transaction> getAllTransactions();

    List<Transaction> getTransactionsByUserId(int studentId);

    List<Transaction> getTransactionsByFeeId(int feeId);


    Transaction createTransaction(TransactionDTO transactionDTO) throws Exception;

    Transaction deleteTransaction(int id) throws Exception;

    int totalAmountCollected(int feeId) throws Exception;

    int calculateTotalFeeAmount(int feeId) throws Exception;

    int numberOfStudentsPaidFee(int feeId) throws Exception;

    int numberOfStudentsNotPaidFee(int feeId) throws Exception;

    List<Transaction> getTransactionsByDateRange(LookUpTransactionDTO lookUpTransactionDTO) throws Exception;


}
