package billsservice.repo;


import billsservice.enums.CategoryType;
import billsservice.model.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<Subcategory, Long> {

    Optional<Subcategory> findByUserUuidAndCategoryUuidAndSubcategoryNameAndDeletedAndType(String userUuid,
                                                                                           String categoryUuid,
                                                                                           String subcategoryName,
                                                                                           boolean b,
                                                                                           CategoryType type);

    Optional<Subcategory> findByUserUuidAndSubcategoryUuidAndDeleted(String userUuid, String subcategoryUuid, boolean b);

    Optional<Subcategory> findByUserUuidAndSubcategoryUuidAndCategoryUuidAndDeleted(String userUuid, String subcategoryUuid, String categoryUuid, boolean b);
}

