package billsservice.service;

import billsservice.dto.OperationDto;
import billsservice.dto.UpdateOperationDto;
import billsservice.enums.CategoryType;
import billsservice.model.Operation;
import billsservice.repo.OperationRepository;
import billsservice.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.sql.Date.valueOf;

@Service
public class OperationService {
    @Autowired
    OperationRepository operationRepository;
    @Autowired
    Utils utils;

    private static final Logger LOGGER = LoggerFactory.getLogger(billsservice.service.OperationService.class);
    private ObjectMapper mapper = new ObjectMapper();

    public OperationDto saveOperation(OperationDto operationDto) throws JsonProcessingException {
        Operation operation = DtoToOperation(operationDto);
        operation.setDeleted(false);
        operationRepository.save(operation);
        LOGGER.info("operation created {} ", mapper.writeValueAsString(operation));
        operationDto.setOperationUuid(operation.getOperationUuid());
        return operationDto;
    }

    public Map<LocalDate, List<OperationDto>> getAllOperations(String userUuid) {
        Optional<List<Operation>> operationsOptional = operationRepository.findAllByUserUuidAndDeleted(userUuid, false);
        if(!operationsOptional.isPresent()){
            return null;
        }
        List<Operation> operations = operationsOptional.get();
        List<OperationDto> operationDto = operations.stream().map(this::operationToDto).collect(Collectors.toList());
        Map<LocalDate, List<OperationDto>> operationsMap = operationDto.stream()
                .collect(Collectors.groupingBy(o -> o.getCreateDate(), Collectors.toList()));
        LOGGER.info("List of operation by user with id {}  ", userUuid);
        return operationsMap;
    }

    public OperationDto updateOperation(String operationUuid, String userUuid, CategoryType type, UpdateOperationDto updateOperationDto) throws JsonProcessingException {
        Optional<Operation>operationOptional = operationRepository.findByUserUuidAndOperationUuidAndTypeAndDeleted(
                userUuid, operationUuid, type, false);
        Operation operation = operationOptional.get();
        if(updateOperationDto.getBillUuid() != null){
            operation.setBillUuid(updateOperationDto.getBillUuid());
        }
        if(updateOperationDto.getSum() != null){
            operation.setSum(updateOperationDto.getSum());
        }
        if(updateOperationDto.getDescription() != null){
            operation.setDescription(updateOperationDto.getDescription());
        }
        if(updateOperationDto.getCreateDate() != null){
            operation.setCreateDate(valueOf(updateOperationDto.getCreateDate()));
        }
        if(updateOperationDto.getCurrency() != null){
            operation.setCurrency(updateOperationDto.getCurrency());
        }
        operationRepository.save(operation);
        LOGGER.info("Operation updated {}", mapper.writeValueAsString(operation));
        OperationDto operationDto = operationToDto(operation);
        return operationDto;
    }

    public OperationDto deleteOperation(String operationUuid, String userUuid, CategoryType type) throws JsonProcessingException {
        Optional<Operation>operationOptional = operationRepository.findByUserUuidAndOperationUuidAndTypeAndDeleted(
                userUuid, operationUuid, type, false);
        Operation operation = operationOptional.get();
        operation.setDeleted(true);
        operationRepository.save(operation);
        LOGGER.info("Operation deleted created {} ", mapper.writeValueAsString(operation));
        OperationDto operationDto = operationToDto(operation);
        return operationDto;
    }

    private OperationDto operationToDto(Operation operation) {
        return new OperationDto().builder()
                .billUuid(operation.getBillUuid())
                .categoryUuid(operation.getCategory_uuid())
                .createDate(Instant.ofEpochMilli(operation.getCreateDate().getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate())
                .currency(operation.getCurrency())
                .description(operation.getDescription())
                .operationUuid(operation.getOperationUuid())
                .subcategoryUuid(operation.getSubcategory_uuid())
                .sum(operation.getSum())
                .type(operation.getType())
                .userUuid(operation.getUserUuid())
                .build();
    }


    private Operation DtoToOperation(OperationDto operationDto) {
        return new Operation().builder()
                .operationUuid(utils.createUuid())
                .userUuid(operationDto.getUserUuid())
                .billUuid(operationDto.getBillUuid())
                .category_uuid(operationDto.getCategoryUuid())
                .subcategory_uuid(operationDto.getSubcategoryUuid())
                .sum(operationDto.getSum())
                .type(operationDto.getType())
                .description(operationDto.getDescription())
                .currency(operationDto.getCurrency())
                .build();
    }
}
