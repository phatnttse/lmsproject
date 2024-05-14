package lms.be.repositories;

import lms.be.dtos.LookUpTransactionDTO;
import lms.be.models.Fee;
import lms.be.models.Transaction;
import lms.be.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByFee(Fee fee);

    List<Transaction> findByStudent(User student);

    List<Transaction> findByTransactionDateBetweenAndStudentUserId(Date startDate, Date endDate, int userId);

}
