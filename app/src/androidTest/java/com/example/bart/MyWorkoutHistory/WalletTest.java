package com.example.bart.MyWorkoutHistory;

import org.joda.time.LocalDate;
import org.junit.Test;

import static org.junit.Assert.*;

public class WalletTest {

    WorkoutStats myWorkoutToTest = WorkoutStats.getInstance();

    @Test
    public void calculateDailyBonus() {

        assertEquals(0.0, myWorkoutToTest.walletInstance.calculateDailyBonus(LocalDate.now(), LocalDate.now()),0.0);

        assertEquals(1.0, myWorkoutToTest.walletInstance.calculateDailyBonus(LocalDate.now().minusDays(1), LocalDate.now()),0.0);

        assertEquals(0.0, myWorkoutToTest.walletInstance.calculateDailyBonus(LocalDate.now().minusDays(2), LocalDate.now()),0.0);

        assertEquals(0.0, myWorkoutToTest.walletInstance.calculateDailyBonus(LocalDate.now().minusDays(3), LocalDate.now()),0.0);

        assertEquals(-1.0, myWorkoutToTest.walletInstance.calculateDailyBonus(LocalDate.now().minusDays(4), LocalDate.now()),0.0);

        assertEquals(-2.0, myWorkoutToTest.walletInstance.calculateDailyBonus(LocalDate.now().minusDays(5), LocalDate.now()),0.0);

        assertEquals(-3.0, myWorkoutToTest.walletInstance.calculateDailyBonus(LocalDate.now().minusDays(6), LocalDate.now()),0.0);

        assertEquals(-33.0, myWorkoutToTest.walletInstance.calculateDailyBonus(LocalDate.now().minusDays(36), LocalDate.now()),0.0);

        assertEquals(-433.0, myWorkoutToTest.walletInstance.calculateDailyBonus(LocalDate.now().minusDays(436), LocalDate.now()),0.0);
    }
}