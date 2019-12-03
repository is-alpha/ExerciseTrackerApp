package com.example.exercisetrackerapp;

import com.example.exercisetrackerapp.ui.results.AverageCalories.DateValidator;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

public class DateValidatorRangeTest {

    private DateValidator dateValidator;

    @Before
    public void init() {
        dateValidator = new DateValidator();
    }

    @Test
    public void testDateWithinRange_1() {
        assertTrue(dateValidator.isThisDateWithin1MonthRange("30/11/2019", "dd/MM/yyyy"));
    }

    @Test
    public void testDateWithinRange_2() {
        assertFalse(dateValidator.isThisDateWithin1MonthRange("31/9/2019", "dd/MM/yyyy"));
    }

    @Test
    public void testDateWithinRange_3() {
        assertTrue(dateValidator.isThisDateWithin1MonthRange("20/11/2019", "dd/MM/yyyy"));
    }

    @Test
    public void testDateWithinRange_4() {
        assertFalse(dateValidator.isThisDateWithin1MonthRange("21/1/2019", "dd/MM/yyyy"));
    }
}
