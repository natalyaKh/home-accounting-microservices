package billsservice.repo;

import billsservice.enums.CategoryType;
import billsservice.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface OperationRepository extends JpaRepository<Operation, Long> {

    Optional<List<Operation>> findAllByUserUuidAndDeleted(String userUuid, boolean b);

    Optional<Operation> findByUserUuidAndOperationUuidAndTypeAndDeleted(String userUuid, String operationUuid, CategoryType type, boolean b);


    Optional<List<Operation>> findAllByUserUuidAndBillUuidAndDeleted(String userUuid, String billUuid, boolean b);
}



