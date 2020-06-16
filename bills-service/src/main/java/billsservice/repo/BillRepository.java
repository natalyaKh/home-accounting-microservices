package billsservice.repo;

import billsservice.dto.BillNumbersDto;
import billsservice.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {


    Optional<Bill> findByUserUuidAndBillName(String userUuid, String billName);

    @Query("SELECT new billsservice.dto.BillNumbersDto(b.billUuid, b.billNumber, b.id, b.userUuid, b.createDate," +
            "b.deleted, b.billName, b.startSum, b.description) FROM Bill b WHERE b.billNumber >=:billNumber and b.userUuid =:userId and b.deleted =:deleted ")
    List<BillNumbersDto> findNumbers(@Param("billNumber")Integer billNumber, @Param("userId") String userUuid,
                                     @Param("deleted") boolean deleted);

    Optional<Bill> findByUserUuidAndBillNumberAndDeleted(String userUuid, Integer billNumber, boolean b);

    Optional<Bill> findByUserUuidAndBillUuidAndDeleted(String userUuid, String billUuid, boolean b);

    Optional<List<Bill>> findAllByUserUuidAndDeleted(String userUuid, boolean b);


    Optional<Bill> findByUserUuidAndBillNameAndDeleted(String userUuid, String billName, boolean b);
}
