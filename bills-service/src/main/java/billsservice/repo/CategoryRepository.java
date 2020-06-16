package billsservice.repo;


import billsservice.enums.CategoryType;
import billsservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByUserUuidAndCategoryUuid(String userUuid, String uuid);

    Optional<Category> findByUserUuidAndCategoryNameAndTypeAndDeleted(String userUuid, String categoryName,
                                                                      CategoryType type, boolean b);

    Optional<Category> findByUserUuidAndCategoryUuidAndDeleted(String userUuid, String uuid, boolean b);

    Optional<Category> findByUserUuidAndCategoryNameAndDeletedAndType(String userUuid, String categoryName, boolean b,
                                                                      CategoryType type);

    Optional<Category> findByUserUuidAndCategoryUuidAndDeletedAndType(String userUuid, String categoryUuid, boolean b, CategoryType type);

    @Query("SELECT o.categoryName From Category o where o.categoryUuid=:categoryUuid")
    String findCategoryNameByCategoryUuid(@Param("categoryUuid") String category_uuid);
}

