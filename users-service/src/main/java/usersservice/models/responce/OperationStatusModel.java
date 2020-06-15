package usersservice.models.responce;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OperationStatusModel {
	private String operationResult;
	private String operationName;
}
