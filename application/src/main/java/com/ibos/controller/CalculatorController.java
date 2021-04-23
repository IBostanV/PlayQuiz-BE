package com.ibos.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/calc")
public class CalculatorController {
    @PostMapping
    public void calculate(@RequestBody Map<String, String> requestMap) {
        String expression = requestMap.get("expression").trim();
        System.out.println(expression);

        String[] allNumbers = expression.split("[(^+\\-*/)]");
        List<String> allOperators = Arrays.stream(expression.split("[(^\\d,)]"))
                .filter(op -> !op.isBlank())
                .collect(Collectors.toList());

        List<String> result = new ArrayList<>();
        for (int i = 0; i < allNumbers.length; i++) {
            result.add(allNumbers[i]);
            result.add(allOperators.get(i));
        }

        System.out.println(result);
    }
}
