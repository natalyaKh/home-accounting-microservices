package billsservice.controllers;


import billsservice.dto.OperationDto;
import billsservice.dto.UpdateOperationDto;
import billsservice.enums.CategoryType;
import billsservice.service.OperationService;
import billsservice.service.ValidatorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/operation")
public class OperationController {

    @Autowired
    OperationService operationService;
    @Autowired
    ValidatorService validatorService;

    @PostMapping()
    public OperationDto addOperation(@Valid @RequestBody final OperationDto operationDto, HttpServletRequest req) throws Exception {

        validatorService.checkElementsForCategory(operationDto, req);
        if(operationDto.getDescription() == null){
            operationDto.setDescription("");
        }
        if(operationDto.getSubcategoryUuid() == null){
            operationDto.setSubcategoryUuid("");
        }
        return operationService.saveOperation(operationDto);
    }

    @GetMapping("/{userUuid}")
    public Map<LocalDate, List<OperationDto>> getAllOperations(@PathVariable String userUuid){
        return operationService.getAllOperations(userUuid);
    }

    @DeleteMapping("/{operationUuid}/{userUuid}/{type}")
    public OperationDto deleteOperation(@PathVariable String operationUuid, @PathVariable String userUuid,
                                        @PathVariable CategoryType type) throws JsonProcessingException {
        validatorService.checkOperation(userUuid, operationUuid, type);
        return operationService.deleteOperation(operationUuid, userUuid, type);
    }

    @PutMapping("/{operationUuid}/{userUuid}/{type}")
    public OperationDto updateOperation(@PathVariable String operationUuid, @PathVariable String userUuid,
                                        @PathVariable CategoryType type,
                                        @Valid @RequestBody UpdateOperationDto updateOperationDto) throws JsonProcessingException {
        validatorService.checkOperation(userUuid, operationUuid, type);
        return operationService.updateOperation(operationUuid, userUuid, type, updateOperationDto);
    }
}
