package com.shop.controller;

import com.shop.constant.ItemSellStatus;
import com.shop.dto.ItemDto;
import com.shop.entity.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/thymeleaf")
public class ThymeleafExController {
    // localhost/thymeleaf/ex01 -> thymeleafEx01.html
    // ${data} -> Hello World 나오도록 출력

    @GetMapping("/ex01")
    public String thymeleafEx01(Model model){
        model.addAttribute("data", "Hello World");
        return "thymeleafEx/thymeleafEx01";
    }

    @GetMapping("/ex02")
    public String thymeleafEx02(Model model){
        ItemDto itemDto = new ItemDto();
        itemDto.setItemDetail("상품 상세 설명");
        itemDto.setItemNm("테스트 상품1");
        itemDto.setPrice(10000);
        itemDto.setRegTime(LocalDateTime.now());
        model.addAttribute("itemDto",itemDto);
        return "thymeleafEx/thymeleafEx02";
    }

    @GetMapping("/ex03")
    public String thymeleafEx03(Model model){
        List<ItemDto> itemDtoList = new ArrayList<>();
        for(int i = 1;i<=10;i++){
            ItemDto item = new ItemDto();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setSellStatCd("SELL");
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemDtoList.add(item);
        }

        model.addAttribute("itemDtoList", itemDtoList);

        return "thymeleafEx/thymeleafEx03";
    }

    @GetMapping("/ex04")
    public String thymeleafEx04(Model model){
        List<ItemDto> itemDtoList = new ArrayList<>();
        for(int i = 1;i<=10;i++){
            ItemDto item = new ItemDto();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설명" + i);
            item.setSellStatCd("SELL");
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            itemDtoList.add(item);
        }

        model.addAttribute("itemDtoList", itemDtoList);

        return "thymeleafEx/thymeleafEx04";
    }

    @GetMapping(value = "/ex05")
    public String thymeleafExample05(Model model){
        return "thymeleafEx/thymeleafEx05";
    }
    // /thymeleaf/ex06(param1 = '홍길동', param2 = '안녕하세요.')
    @GetMapping("/ex06")
    public String thymeleafExample06(String param1, String param2, Model model){
        model.addAttribute("param1",param1);
        model.addAttribute("param2",param2);
        return "thymeleafEx/thymeleafEx06";
    }

    @GetMapping("/ex07")
    public String thymeleafExample07(){
        return "thymeleafEx/thymeleafEx07";

    }
}
