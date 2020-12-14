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
import org.springframework.retry.backoff.ThreadWaitSleeper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;


import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@RestController
@Slf4j
public class MultiPartFileDemo {

    public static ExecutorService executorService = Executors.newFixedThreadPool(10);

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


    @GetMapping("/test")
    public ResponseVo testString(@RequestParam("status") String status){

        String[] split = status.split(",");
        Set<Long> collect = Arrays.stream(split).map(Long::valueOf).collect(Collectors.toSet());
        System.out.println(collect);

        return ResponseVo.success(collect);
    }

    protected void threadTest(){
        System.out.println("enter thread test method, thread: "+Thread.currentThread().getName());
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("enter new thread, thread: "+Thread.currentThread().getName());
            }
        });
    }

    public static void main(String[] args) {
        System.out.println(2333);
        MultiPartFileDemo multiPartFileDemo = new MultiPartFileDemo();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("thus is outer thread, name: "+ Thread.currentThread().getName());
                multiPartFileDemo.threadTest();
            }
        });
    }



}
