package billsservice.controllers;

import billsservice.dto.BillAllDetailsDto;
import billsservice.dto.BillAllDto;
import billsservice.dto.BillDto;
import billsservice.dto.UpdateBillDto;
import billsservice.exceptions.AppExceptionsHandler;
import billsservice.service.BillService;
import billsservice.service.ValidatorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    ValidatorService validatorService;
    @Autowired
    BillService billService;


    @PostMapping()
    public BillDto addBill(@Valid @RequestBody final BillDto billDto) throws JsonProcessingException, AppExceptionsHandler {
        validatorService.checkBillUniq(billDto.getBillName(), billDto.getUserUuid());
        validatorService.checkBillNumber(billDto.getBillName(), billDto.getUserUuid(), billDto.getBillNumber());
        if(billDto.getDescription() == null){
            billDto.setDescription("");
        }
        if(billDto.getStartSum() == null){
            billDto.setStartSum(0.0);
        }
        if(billDto.getBillNumber() == null){
            billDto.setBillNumber(billService.createBillNumber(billDto));
        }
        return billService.addBill(billDto);
    }


    @GetMapping("/allBills/{userUuid}")
    public List<BillAllDto> getAllBills(@PathVariable String userUuid){

        return billService.getAllBills(userUuid);
    }

    @GetMapping("/allBills/details/{userUuid}")
    public Map<LocalDate,List<BillAllDetailsDto>> getAllDetailsBills(@PathVariable String userUuid){
        return billService.getAllDetailsBills(userUuid);
    }

    @DeleteMapping("/{userUuid}/{billUuid}")
    public BillDto deletedBill(@PathVariable String userUuid, @PathVariable String billUuid, @RequestHeader HttpHeaders headers) throws JsonProcessingException {
        headers.get("userUuid");
        validatorService.checkBillUniqByUuid(userUuid, billUuid);
        return billService.deleteBill(userUuid, billUuid);
    }

    @PutMapping("/{userUuid}/{billUuid}")
    public BillDto updateBill(@PathVariable String userUuid, @PathVariable String billUuid, @RequestBody
    final UpdateBillDto billDto ) throws JsonProcessingException {
        validatorService.checkBill(userUuid, billUuid);
        return billService.updateBill(userUuid, billUuid, billDto);
    }

}
