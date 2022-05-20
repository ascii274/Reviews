package com.ascii274.testing;

import com.ascii274.testing.model.Calculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorTest {

    @Test
    @DisplayName("1+1 =2")
    void addsTwoNumbers(){
        Calculator calculator = new Calculator();
        assertEquals(2,calculator.add(1,1), "1 + 1 should equal 2");
    }
}
