package com.example.bart.MyWorkoutHistory;

import org.joda.time.LocalDate;
import org.junit.Test;

import static org.junit.Assert.*;

public class WalletTest {

    WorkoutStats myWorkoutToTest = WorkoutStats.getInstance();

    @Test
    public void calculateDailyBonus() {
        myWorkoutToTest.walletInstance.lastDateIncluededInBalance = LocalDate.now();
        assertEquals(0.0, myWorkoutToTest.walletInstance.calculateDailyBonus(),0.0);

        myWorkoutToTest.walletInstance.lastDateIncluededInBalance = LocalDate.now().minusDays(1);
        assertEquals(1.0, myWorkoutToTest.walletInstance.calculateDailyBonus(),0.0);

        myWorkoutToTest.walletInstance.lastDateIncluededInBalance = LocalDate.now().minusDays(2);
        assertEquals(0.0, myWorkoutToTest.walletInstance.calculateDailyBonus(),0.0);

        myWorkoutToTest.walletInstance.lastDateIncluededInBalance = LocalDate.now().minusDays(3);
        assertEquals(0.0, myWorkoutToTest.walletInstance.calculateDailyBonus(),0.0);

        myWorkoutToTest.walletInstance.lastDateIncluededInBalance = LocalDate.now().minusDays(4);
        assertEquals(-1.0, myWorkoutToTest.walletInstance.calculateDailyBonus(),0.0);

        myWorkoutToTest.walletInstance.lastDateIncluededInBalance = LocalDate.now().minusDays(5);
        assertEquals(-2.0, myWorkoutToTest.walletInstance.calculateDailyBonus(),0.0);

        myWorkoutToTest.walletInstance.lastDateIncluededInBalance = LocalDate.now().minusDays(6);
        assertEquals(-3.0, myWorkoutToTest.walletInstance.calculateDailyBonus(),0.0);

        myWorkoutToTest.walletInstance.lastDateIncluededInBalance = LocalDate.now().minusDays(36);
        assertEquals(-33.0, myWorkoutToTest.walletInstance.calculateDailyBonus(),0.0);

        myWorkoutToTest.walletInstance.lastDateIncluededInBalance = LocalDate.now().minusDays(436);
        assertEquals(-433.0, myWorkoutToTest.walletInstance.calculateDailyBonus(),0.0);
    }
}