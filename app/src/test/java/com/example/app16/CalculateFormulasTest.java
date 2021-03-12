package com.example.app16;

import com.example.app16.ui.main.CalculateFormulas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.reverse;

public class CalculateFormulasTest {

    CalculateFormulas calculateFormulas;

    ArrayList[] timeFrameAndValues;

    List<Double> stockValues;
    static ArrayList<Double> stockValues2;


    @BeforeEach
    public <stockValues2> void setUp() {
        //ArrayList<String> timeFrames = Arrays.asList(14,28);
        //  timeFrames = (ArrayList<String>) timeFrameAndValues[0];
        stockValues = Arrays.asList(37.70,
                37.75,
                37.40,
                36.95,
                34.25,
                34.65,
                35.90,
                35.85,
                37.00,
                36.70,
                36.65,
                37.05,
                37.40,
                38.55,
                39.70,
                41.35,
                45.80,
                44.40,
                44.45,
                47.15,
                46.05,
                46.25,
                48.45,
                46.60,
                50.20,
                51.40,
                49.65,
                49.45);
        calculateFormulas = new CalculateFormulas();

        stockValues2 = new ArrayList<Double>() {{
            add(37.70);
            add(37.75);
            add(37.40);
            add(36.95);
            add(34.25);
            add(34.65);
            add(35.90);
            add(35.85);
            add(37.00);
            add(36.70);
            add(36.65);
            add(37.05);
            add(37.40);
            add(38.55);
            add(39.70);
            add(41.35);
            add(45.80);
            add(44.40);
            add(44.45);
            add(47.15);
            add(46.05);
            add(46.25);
            add(48.45);
            add(46.60);
            add(50.20);
            add(51.40);
            add(49.65);
            add(49.45);
        }};
        calculateFormulas = new CalculateFormulas();

    }


    @Test
    public void given_input_return_ema_list() {
        reverse(stockValues);
        List<Double> sumEMAvalues = calculateFormulas.sumEMAvalues(stockValues, 12);
        sumEMAvalues.stream().forEach(System.out::println);
    }

    @Test
    public void given_input_return_ema_list_2() {
        ArrayList tFrameAndValues[] = new ArrayList[2];
        for (int i = 0; i < 2; i++) {
            tFrameAndValues[i] = new ArrayList<>();
        }
        tFrameAndValues[1] = stockValues2;
        CalculateFormulas calculateFormulas = new CalculateFormulas(tFrameAndValues);
        List<Double> sumEMAvalues = calculateFormulas.sumEMAvalues2(12);
        sumEMAvalues.stream().forEach(System.out::println);
    }

    @Test
    public void given_input_return_ema_list_of_price(){

        LocalDate date =
                Instant.ofEpochMilli(1318386508000L).atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println("CalculateFormulasTest.test:"+date.toString());

    }
}

