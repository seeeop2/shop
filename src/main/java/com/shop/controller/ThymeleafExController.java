package com.shop.controller;

import com.shop.dto.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/thymeleaf")   //1차 주소 매핑
public class ThymeleafExController {
    @GetMapping(value = "/ex01")
    public String thymeleafExample01(Model model){
        model.addAttribute("data","타임리프 예제 입니다.");  //key,value 넣어주기
        return "thymeleafEx/thymeleafEx01";                //templages 폴더를 기준으로 뷰의 위치,이름 반환
    }
    @GetMapping(value = "/ex02")
    public String thymeleafExample02(Model model){

        ItemDto itemDto = new ItemDto();
        itemDto.setItemDetail("상품 상세 설명");
        itemDto.setItemNm("테스트 상품1");
        itemDto.setPrice(10000);
        itemDto.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDto",itemDto);  //key,value 넣어주기
        return "thymeleafEx/thymeleafEx02";                //templages 폴더를 기준으로 뷰의 위치,이름 반환
    }

    @GetMapping(value = "/ex03")
    public String thymeleafExample03(Model model){

        List<ItemDto> itemDtoList = new ArrayList<>();
        for(int i = 1 ; i <=10 ; i++ ) {
            ItemDto itemDto = new ItemDto();
            itemDto.setItemDetail("상품 상세 설명" + i);
            itemDto.setItemNm("테스트 상품" + i);
            itemDto.setPrice(1000* i);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }
        model.addAttribute("itemDtoList", itemDtoList);  //key,value 넣어주기
        return "thymeleafEx/thymeleafEx03";                //templages 폴더를 기준으로 뷰의 위치,이름 반환
    }
    @GetMapping(value = "/ex04")
    public String thymeleafExample04(Model model){

        List<ItemDto> itemDtoList = new ArrayList<>();

        for(int i = 1; i <= 10; i++ ){
            ItemDto itemDto = new ItemDto();
            itemDto.setItemDetail("상품 상세 설명" + i);
            itemDto.setItemNm("테스트 상품" + i);
            itemDto.setPrice(1000* i);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }
        model.addAttribute("itemDtoList",itemDtoList);
        return "thymeleafEx/thymeleafEx04";                //templages 폴더를 기준으로 뷰의 위치,이름 반환
    }

    @GetMapping(value = "/ex05")
    public String thymeleafExample05(){
        return "thymeleafEx/thymeleafEx05";                //templages 폴더를 기준으로 뷰의 위치,이름 반환
    }

    @GetMapping(value = "/ex06")
    public String thymeleafExample06(String param1,     //전달했던 매개변수와 같은 이름와 같은 이름의 String 변수 param1,param2를
                                     String param2,     //파라미터로 설정하면 자동으로 데이터가 바인딩
                                     Model model){
        model.addAttribute("param1",param1);
        model.addAttribute("param2",param2);
        return "thymeleafEx/thymeleafEx06";                //templages 폴더를 기준으로 뷰의 위치,이름 반환
    }

    @GetMapping(value = "/ex07")
    public String thymeleafExample07(){
        return "thymeleafEx/thymeleafEx07";                //templages 폴더를 기준으로 뷰의 위치,이름 반환
    }

}
