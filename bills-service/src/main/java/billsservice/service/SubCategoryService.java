package billsservice.service;


import billsservice.dto.SubCategoryDto;
import billsservice.model.Subcategory;
import billsservice.repo.SubCategoryRepository;
import billsservice.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class SubCategoryService {
    @Autowired
    SubCategoryRepository subCategoryRepository;
    @Autowired
    Utils utils;
    private static final Logger LOGGER = LoggerFactory.getLogger(billsservice.service.SubCategoryService.class);
    private ObjectMapper mapper = new ObjectMapper();
    private ModelMapper modelMapper = new ModelMapper();

    public SubCategoryDto addSubcategory(SubCategoryDto subCategoryDto) throws JsonProcessingException {
        Subcategory subCategory = DtoToSubCategory(subCategoryDto);
        subCategory.setDeleted(false);
        subCategoryRepository.save(subCategory);
        LOGGER.info("Subcategory created {}", mapper.writeValueAsString(subCategory));
        subCategoryDto.setSubCategoryUuid(subCategory.getSubcategoryUuid());
        return subCategoryDto;
    }

    private Subcategory DtoToSubCategory(SubCategoryDto subCategoryDto) {
        return new Subcategory().builder()
                .categoryUuid(subCategoryDto.getCategory().getCategoryUuid())
                .subcategoryName(subCategoryDto.getSubCategoryName())
                .subcategoryUuid(utils.createUuid())
                .userUuid(subCategoryDto.getUserUuid())
                .type(subCategoryDto.getType())
                .description(subCategoryDto.getDecryption())
                .build();
    }

    public SubCategoryDto deleteSubcategory(String userUuid, String subcategoryUuid) throws JsonProcessingException {
        Optional<Subcategory> subcategoryOptional = subCategoryRepository.findByUserUuidAndSubcategoryUuidAndDeleted(
                userUuid, subcategoryUuid, false
        );
        Subcategory subcategory = subcategoryOptional.get();
        subcategory.setDeleted(true);
        SubCategoryDto subCategoryDto = SubcategoryToDto(subcategory);
        subCategoryRepository.save(subcategory);
        LOGGER.info("Subcategory deleted {}", mapper.writeValueAsString(subcategory));
        return subCategoryDto;
    }

    private SubCategoryDto SubcategoryToDto(Subcategory subcategory) {
        return modelMapper.map(subcategory, SubCategoryDto.class);
//    return new SubCategoryDto().builder()
//            .decryption(subcategory.getDescription())
//            .subCategoryName(subcategory.getSubcategoryName())
//            .subCategoryUuid(subcategory.getSubcategoryUuid())
//            .userUuid(subcategory.getUserUuid())
//            .type(subcategory.getType())
//            .build();
    }
}
