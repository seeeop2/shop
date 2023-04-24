package com.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/thymeleaf")   //1차 주소 매핑
public class ThymeleafExController {
    @RequestMapping(value = "/ex01")
    public String thymeleafExample01(Model model){
        model.addAttribute("data","타임리프 예제 입니다.");  //key,value 넣어주기
        return "thymeleafEx/thymeleafEx01";                //templages 폴더를 기준으로 뷰의 위치,이름 반환
    }
}
