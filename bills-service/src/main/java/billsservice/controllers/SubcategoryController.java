package billsservice.controllers;


import billsservice.dto.SubCategoryDto;
import billsservice.service.SubCategoryService;
import billsservice.service.ValidatorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/subcategory")
public class SubcategoryController {
    @Autowired
    SubCategoryService subCategoryService;
    @Autowired
    ValidatorService validatorService;

    @PostMapping()
    public SubCategoryDto addSubCategory (@Valid @RequestBody final SubCategoryDto subCategoryDto) throws JsonProcessingException {
         validatorService.checkCategoryAndSubCategoryForSaveSubCategory(subCategoryDto);
        if(subCategoryDto.getDecryption() == null){
            subCategoryDto.setDecryption("");
        }
        return subCategoryService.addSubcategory(subCategoryDto);
    }

    @DeleteMapping("/{uuid}/{userUuid}")
    public SubCategoryDto deleteSubCategory(@PathVariable String uuid, @PathVariable String userUuid) throws JsonProcessingException {
        validatorService.checkSubcategoryExists(userUuid, uuid);
        return subCategoryService.deleteSubcategory(userUuid, uuid);
    }
}
