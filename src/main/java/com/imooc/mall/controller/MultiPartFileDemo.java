package com.imooc.mall.controller;

import com.imooc.mall.enums.ResponseEnum;
import com.imooc.mall.vo.ResponseVo;
import com.imooc.mall.vo.bindKsIdByCSVDto;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author LingChen <lingchen@kuaishou.com>
 * Created on 2020-07-07
 */
@RestController
@Slf4j
public class MultiPartFileDemo {

    @PostMapping("/file")
    public ResponseVo test(@RequestParam("file") MultipartFile file){
        try{
            Reader reader = new InputStreamReader(file.getInputStream());
            CSVReader csvReader = new CSVReaderBuilder(reader).build();
            HeaderColumnNameMappingStrategy<bindKsIdByCSVDto> strategy
                    = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(bindKsIdByCSVDto.class);

            CsvToBean<bindKsIdByCSVDto> csvToBean = new CsvToBeanBuilder<bindKsIdByCSVDto>(csvReader)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<Long> ksIDList = csvToBean.parse().stream().map(bindKsIdByCSVDto::getKsID).collect(Collectors.toList());
            log.info("bindKsIdByDoc parse csv result, ks IDs: "+ksIDList);
            StringBuilder str = new StringBuilder();
            for (Long aLong : ksIDList) {
                str.append(String.format("\"%d\",", aLong));
            }
            System.out.println(str.toString());
            return ResponseVo.success(str.toString());
        }
        catch (Exception e){
            log.info("bindKsIdByDoc parse csv error: "+e);
            return ResponseVo.error(ResponseEnum.EMAIL_EXIST,"错误");
        }

    }

}
