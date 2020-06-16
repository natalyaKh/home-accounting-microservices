package billsservice.service;

import billsservice.dto.*;
import billsservice.enums.CategoryType;
import billsservice.enums.ErrorMessages;
import billsservice.exceptions.BillServiceException;
import billsservice.model.Bill;
import billsservice.model.Operation;
import billsservice.repo.BillRepository;
import billsservice.repo.CategoryRepository;
import billsservice.repo.OperationRepository;
import billsservice.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BillService {
    @Autowired
    BillRepository billRepository;
    @Autowired
    OperationRepository operationRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    Utils utils;
    @Autowired
    ValidatorService validatorService;

    private static final Logger LOGGER = LoggerFactory.getLogger(billsservice.service.BillService.class);
    private ObjectMapper mapper = new ObjectMapper();

    public BillDto addBill(BillDto billDto) throws JsonProcessingException {

        Bill bill = DtoToBill(billDto);
        bill.setDeleted(false);
        billRepository.save(bill);
        LOGGER.info("Bill saves  {}", mapper.writeValueAsString(bill));
        billDto.setBillUuid(bill.getBillUuid());
        return billDto;
    }

    private Bill DtoToBill(BillDto billDto) {
        return new Bill().builder()
                .billName(billDto.getBillName())
                .billUuid(utils.createUuid())
                .startSum(billDto.getStartSum())
                .userUuid(billDto.getUserUuid())
                .billNumber(billDto.getBillNumber())
                .description(billDto.getDescription())
                .build();
    }

    public Integer createBillNumber(BillDto billDto) throws JsonProcessingException {
        Optional<List<Bill>> bill = billRepository.findAllByUserUuidAndDeleted(
                billDto.getUserUuid(), false);
        LOGGER.info("Bill getting number  {}", mapper.writeValueAsString(bill));
        return bill.get().size() + 1;
    }

    public List<BillAllDto> getAllBills(String userUuid) {
        Optional<List<Bill>> billOptional = billRepository.findAllByUserUuidAndDeleted(userUuid, false);
        if (!billOptional.isPresent()) {
            return null;
        }
        List<BillAllDto> billListDto = billToBillAllDto(billOptional.get());
        LOGGER.info("List of Biils for user {} ", userUuid);
        return billListDto;
    }

    public Map<LocalDate, List<BillAllDetailsDto>> getAllDetailsBills(String userUuid) {
        Optional<List<Bill>> billOptional = billRepository.findAllByUserUuidAndDeleted(userUuid, false);
        if (!billOptional.isPresent()) {
            return null;
        }
        Map<LocalDate, List<BillAllDetailsDto>> billListDto = bittToBillAllDetailsDto(billOptional.get());
        LOGGER.info("Detail list of Biils for user {}", userUuid);
        return billListDto;
    }

    private Map<LocalDate, List<BillAllDetailsDto>> bittToBillAllDetailsDto(List<Bill> bill) {
        List<BillAllDetailsDto> billsList = new ArrayList<>(bill.size());
        for (Bill b : bill) {
            Double sum = b.getStartSum();
            List<OperationsByDateDto> operationByDate = new ArrayList<>();
            Optional<List<Operation>> operations = operationRepository.findAllByUserUuidAndBillUuidAndDeleted(
                    b.getUserUuid(), b.getBillUuid(), false);
            if (operations.isPresent()) {
                Map<CategoryType, Double> sumMap = getCategoryTypeDoubleMap(operationByDate, operations);
                sum = sum - sumMap.get(CategoryType.OUTCOME) + sumMap.get(CategoryType.INCOME);
            }
            List<OperationsByDateDto> operationByDateList = new ArrayList<>();
            operationByDateList = operationToOperationByDate(operations.get());
            for (OperationsByDateDto o : operationByDateList) {
                BillAllDetailsDto oneOperation = BillAllDetailsDto.builder()
                        .billName(b.getBillName())
                        .billUuid(b.getBillUuid())
                        .sumByBill(sum)
                        .userUuid(b.getUserUuid())
                        .operationName(o.getCategoryName())
                        .operationSum(o.getSum())
                        .operationType(o.getType())
                        .createdDate(o.getCreateDate())
                        .build();
                billsList.add(oneOperation);
            }
        }

        Map<LocalDate, List<BillAllDetailsDto>> billsDetailList = billsList.stream().collect(
                Collectors.groupingBy(b -> b.getCreatedDate(), Collectors.toList()));
        return billsDetailList;
    }

    private List<OperationsByDateDto> operationToOperationByDate(List<Operation> operations) {
        List<OperationsByDateDto> listOperations = new ArrayList<>(operations.size());
        for (Operation o : operations) {
            String categoryName = categoryRepository.findCategoryNameByCategoryUuid(o.getCategory_uuid());
            OperationsByDateDto op = OperationsByDateDto.builder()
                    .Sum(o.getSum())
                    .categoryName(categoryName)
                    .type(o.getType())
                    .createDate(o.getCreateDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                    .build();
            listOperations.add(op);
        }
        return listOperations;
    }

    private List<BillAllDto> billToBillAllDto(List<Bill> bill) {
        List<BillAllDto> billsList = new ArrayList<>(bill.size());
        for (Bill b : bill) {
            Double sum = b.getStartSum();
            List<OperationsByDateDto> operationByDate = new ArrayList<>();
            Optional<List<Operation>> operations = operationRepository.findAllByUserUuidAndBillUuidAndDeleted(
                    b.getUserUuid(), b.getBillUuid(), false);
            if (operations.isPresent()) {
                Map<CategoryType, Double> sumMap = getCategoryTypeDoubleMap(operationByDate, operations);
                sum = sum - sumMap.get(CategoryType.OUTCOME) + sumMap.get(CategoryType.INCOME);
            }
            BillAllDto oneBill = BillAllDto.builder()
                    .billName(b.getBillName())
                    .billUuid(b.getBillUuid())
                    .sumByBill(sum)
                    .operations(operationByDate)
                    .userUuid(b.getUserUuid())
                    .build();
            billsList.add(oneBill);
        }
        return billsList;
    }

    private Map<CategoryType, Double> getCategoryTypeDoubleMap(List<OperationsByDateDto> operationByDate, Optional<List<Operation>> operations) {
        Map<CategoryType, Double> sumMap = operations.get().stream().collect(
                Collectors.groupingBy(Operation::getType, Collectors.summingDouble(Operation::getSum)));
        OperationsByDateDto incomeOperation = getOperationsByDateDto(sumMap, CategoryType.OUTCOME);
        OperationsByDateDto outcomeOperation = getOperationsByDateDto(sumMap, CategoryType.INCOME);
        checkSum(sumMap, CategoryType.OUTCOME);
        checkSum(sumMap, CategoryType.INCOME);
        operationByDate.add(incomeOperation);
        operationByDate.add(outcomeOperation);
        return sumMap;
    }

    private void checkSum(Map<CategoryType, Double> sumMap, CategoryType type) {
        if (sumMap.get(type) == null) {
            sumMap.put(type, 0.0);
        }
    }

    private OperationsByDateDto getOperationsByDateDto(Map<CategoryType, Double> sumMap, CategoryType type) {
        return OperationsByDateDto.builder()
                .Sum(sumMap.get(type))
                .type(type)
                .build();
    }

    public BillDto deleteBill(String userUuid, String billUuid) throws JsonProcessingException {
        Bill bill = billRepository.findByUserUuidAndBillUuidAndDeleted(userUuid, billUuid, false).get();
        bill.setDeleted(true);
        billRepository.save(bill);
        LOGGER.info("Bill deleted {}", mapper.writeValueAsString(bill));
        BillDto billDto = billToDto(bill);
        return billDto;
    }

    private BillDto billToDto(Bill bill) {
        return new BillDto().builder()
                .billName(bill.getBillName())
                .billNumber(bill.getBillNumber())
                .billUuid(bill.getBillUuid())
                .description(bill.getDescription())
                .startSum(bill.getStartSum())
                .userUuid(bill.getUserUuid())
                .build();
    }

    public BillDto updateBill(String userUuid, String billUuid, UpdateBillDto billDto) throws JsonProcessingException {
        Bill bill = billRepository.findByUserUuidAndBillUuidAndDeleted(userUuid, billUuid, false).get();
        if (billDto.getBillName() != null) {
            bill.setBillName(billDto.getBillName());
        }
        if (billDto.getDescription() != null) {
            bill.setDescription(billDto.getDescription());
        }
        if (billDto.getStartSum() != null) {
            bill.setStartSum(billDto.getStartSum());
        }
        billRepository.save(bill);
        if (billDto.getBillNumber() != null) {
            bill.setBillNumber(billDto.getBillNumber());
            List<BillNumbersDto> billsForChangeNumber = billRepository.findNumbers(bill.getBillNumber(), userUuid,
                    false);
            billsForChangeNumber.remove(billsForChangeNumber.size() - 1);
            billsForChangeNumber.stream().forEach(b -> {
                b.setBillNumber(b.getBillNumber() + 1);
            });
            List<Bill> billList = validatorService.billNumbersToBill(billsForChangeNumber);
            billList.add(bill);
            billRepository.saveAll(billList);
        }
        LOGGER.info("Bill updated {}", mapper.writeValueAsString(bill));
        BillDto billDtoNew = billToDto(bill);
        return billDtoNew;
    }
}
