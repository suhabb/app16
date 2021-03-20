package com.example.app16;


import com.example.app16.ui.main.CalculateFormulas;
import com.example.app16.ui.main.Price;
import com.example.app16.ui.main.PriceBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalculateFormulasTest {


    List stockPriceList;

    List<Price> priceList;

    @BeforeEach
    public void setUp() {
        priceList = getStocksPriceFromFile("src/test/java/com/example/app16/json_data/test_stock.json",true);
        stockPriceList = priceList.stream().map(Price::getStockPrice).collect(Collectors.toList());

    }

    @Test
    public void given_input_return_ema_list() {
        ArrayList tFrameAndValues[] = new ArrayList[3];
        for (int i = 0; i < 3; i++) {
            tFrameAndValues[i] = new ArrayList<>();
        }
        tFrameAndValues[1] = (ArrayList) stockPriceList;
        CalculateFormulas calculateFormulas = new CalculateFormulas(tFrameAndValues, 20);
        List<Price> emaValues = calculateFormulas.getEMAValues(priceList, 20);
        List<Price> expectedPriceList =
                getStocksPriceFromFile("src/test/java/com/example/app16/json_data/expect_ema.json",
                        false);
        Assertions.assertArrayEquals(expectedPriceList.toArray(),emaValues.toArray());
    }

    @Test
    public void given_input_return_macd_list() {
        ArrayList tFrameAndValues[] = new ArrayList[3];
        for (int i = 0; i < 3; i++) {
            tFrameAndValues[i] = new ArrayList<>();
        }
        tFrameAndValues[1] = (ArrayList) stockPriceList;
        tFrameAndValues[2] = (ArrayList) priceList;
        CalculateFormulas calculateFormulas = new CalculateFormulas(tFrameAndValues, 20);
        List<Price> macdValues = calculateFormulas.getMACDValues();
        List<Price> expectedPriceList =
                getStocksPriceFromFile("src/test/java/com/example/app16/json_data/expected_macd.json",
                        false);
        Assertions.assertArrayEquals(expectedPriceList.toArray(),macdValues.toArray());
    }

    @Test
    public void given_input_return_macd_avg_list() {
        ArrayList tFrameAndValues[] = new ArrayList[3];
        for (int i = 0; i < 3; i++) {
            tFrameAndValues[i] = new ArrayList<>();
        }
        tFrameAndValues[1] = (ArrayList) stockPriceList;
        tFrameAndValues[2] = (ArrayList) priceList;
        CalculateFormulas calculateFormulas = new CalculateFormulas(tFrameAndValues, 20);
        List<Price> macdValues = calculateFormulas.getMACDValues();
        List<Price> macdAvgValues = calculateFormulas.getEMAValues(macdValues,9);
        List<Price> expectedPriceList =
                getStocksPriceFromFile("src/test/java/com/example/app16/json_data/expected_macd_avg.json",
                        false);
        Assertions.assertArrayEquals(expectedPriceList.toArray(),macdAvgValues.toArray());
    }


    public static List<Price> getStocksPriceFromFile(String path,boolean isEpoch) {
        List<Price> priceList = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(path);
            String jsonString = readFile(fis);

            JSONParser parser = new JSONParser();
            Object a = parser.parse(jsonString);
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node = (ObjectNode) mapper.readTree(jsonString);
            JsonNode arrayNode = node.get("data");

            arrayNode.forEach(s -> {
                LocalDate date ;
                if(isEpoch) {
                    int epoch = Integer.parseInt(s.get("epoch").asText());
                  date = Instant.ofEpochSecond(epoch).atZone(ZoneId.systemDefault()).toLocalDate();
                }else{
                    String value = s.get("dateOfStock").asText();
                    date = LocalDate.parse(value);
                }

                String value = s.get("stockPrice").asText();
                Price price = new PriceBuilder()
                        .setStockPrice(Double.valueOf(value))
                        .setDateOfStock(date).build();
                priceList.add(price);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return priceList;
    }

    public static String readFile(FileInputStream fis) {
        StringBuilder result = new StringBuilder();

        try (InputStreamReader inStrmRdr = new InputStreamReader(fis);) {

            if (fis != null) {

                BufferedReader buffRdr = new BufferedReader(inStrmRdr);
                String fileContent;

                while ((fileContent = buffRdr.readLine()) != null) {
                    result.append(fileContent);
                }
                fis.close();
            }
        } catch (Exception _e) {
            _e.printStackTrace();
        }
        return result.toString();
    }
}

