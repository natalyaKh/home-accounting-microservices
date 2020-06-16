package billsservice.controllers;

import billsservice.dto.CategoryDto;
import billsservice.service.CategoryService;
import billsservice.service.ValidatorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ValidatorService validatorService;

    @PostMapping()
    public @ResponseBody
    CategoryDto addCategory(@RequestBody final CategoryDto categoryDto) throws JsonProcessingException {
        validatorService.checkUniqOfCategory(categoryDto.getUserUuid(),
                categoryDto.getCategoryName(), categoryDto.getType());
        if(categoryDto.getDecryption().isEmpty()){
            categoryDto.setDecryption("");
        }
        return categoryService.saveCategory(categoryDto);
    }

    @DeleteMapping("/{uuid}/{userUuid}")
    public CategoryDto deleteUser(@PathVariable String uuid, @PathVariable String userUuid) throws JsonProcessingException {
        validatorService.checkCategoryExists(uuid, userUuid);
        return categoryService.deleteCategory(uuid, userUuid);
    }
}
