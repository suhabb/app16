package com.example.app16;

import android.content.Context;

import com.example.app16.ui.main.findQuoteBean;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class FindQuoteBeanValidationTest {

    findQuoteBean findQuoteBean;
    Context context = mock(Context.class);

    @BeforeEach
    public void setUp(){
        findQuoteBean = new findQuoteBean(context);
    }

    @Test
    public void given_valid_date_return_true(){
        String dateFrom="2016-01-01";
        String dateTo="2017-01-01";
        boolean validateDate = findQuoteBean.validateDate(dateFrom, dateTo);
        Assertions.assertEquals(true,validateDate);
    }

    @Test
    public void given_invalid_date_return_false(){
        String dateFrom="2016-19-19";
        String dateTo="2017-34-99";
        boolean validateDate = findQuoteBean.validateDate(dateFrom, dateTo);
        Assertions.assertEquals(false,validateDate);
    }

    @Test
    public void given_date_test_validate_range_return_true(){
        String dateFrom="2016-01-01";
        String dateTo="2017-01-01";
        boolean validateDate = findQuoteBean.validateDateRange(dateFrom, dateTo);
        Assertions.assertEquals(true,validateDate);
    }

    @Test
    public void given_date_test_valid_date_range_return_false(){
        String dateFrom="2019-01-01";
        String dateTo="2016-01-01";
        boolean validateDate = findQuoteBean.validateDateRange(dateFrom, dateTo);
        Assertions.assertEquals(false,validateDate);
    }

    @Test
    public void given_date_test_valid_min_date_range_return_true(){
        String dateFrom="2017-01-01";
        String dateTo="2017-02-01";
        boolean validateDate = findQuoteBean.minDateRangeRequired(dateFrom, dateTo);
        Assertions.assertEquals(true,validateDate);
    }

    @Test
    public void given_date_test_valid_min_date_range_return_false(){
        String dateFrom="2017-01-01";
        String dateTo="2017-03-04";
        boolean validateDate = findQuoteBean.minDateRangeRequired(dateFrom, dateTo);
        Assertions.assertEquals(false,validateDate);
    }

}
