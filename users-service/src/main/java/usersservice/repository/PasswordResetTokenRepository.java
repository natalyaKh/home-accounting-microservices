package usersservice.repository;

import org.springframework.data.repository.CrudRepository;
import usersservice.models.entity.PasswordResetTokenEntity;


public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetTokenEntity, Long>{
	PasswordResetTokenEntity findByToken(String token);
}
