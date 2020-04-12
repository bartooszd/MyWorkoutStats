package com.example.bart.MyWorkoutHistory;

import android.util.Log;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

import static android.content.ContentValues.TAG;

/**
 * Created by bart on 15.07.2018.
 * Wallet will be calculated each time app is open.
 * To speed up calculation wallet will remember date of last calculation and the balance.
 */

public class Wallet implements Serializable {
    double accountBalance;
    double previousDayBalance;
    LocalDate lastDateIncludedInBalance;
    LocalDate previousDateIncludedInBalance; //it is needed it balance is recalculated and lastDateIncluededInBalance is set to today
    Double exercisesRatio[];
    int numberOfExercises;


    public Wallet(int numberOfExercises) {
        exercisesRatio = new Double[numberOfExercises];
        this.numberOfExercises = numberOfExercises;
        Arrays.fill( exercisesRatio, 0.0);
    }

    public void setWalletValues(double balance, LocalDate dateOfLastWithdrawal) {
        accountBalance = balance;
        //    lastWithdrawal = dateOfLastWithdrawal;
    }

    public boolean withdraw(int withdrawalAmount) {
        boolean result = false;
        if (withdrawalAmount <= accountBalance) {
            accountBalance -= withdrawalAmount;
            result = true;
        }
        return result;
    }

    public void setExerciseRatio(int exerciseNumber, double ratio) {
        //ToDo  throw exception if exerciseNumber is out of range
        if (exerciseNumber < numberOfExercises) {
            exercisesRatio[exerciseNumber] =  ratio;
        }
    }

    public void calculateWalletBalance(LinkedList<DailyWorkout> listOfWorkouts) {
        double coinsFromPreviosDay = 0;
        double dailyBonus = 0;
        DailyWorkout tmpDW;
        Log.i(TAG, "MyLog.Wallet calculateWalletBalance — starting. List of workout history: "+ listOfWorkouts.size());
        // if previous day wasn't added to balance, add it now
        if (listOfWorkouts.size() > 0)
        {
            ListIterator<DailyWorkout> listIterator = listOfWorkouts.listIterator(listOfWorkouts.size());

            if(listIterator.hasPrevious())
           {
               Log.i(TAG, "MyLog.Wallet calculateWalletBalance — will add previous day");
              tmpDW = listIterator.previous();
               Log.i(TAG, "MyLog.Wallet calculateWalletBalance — previous date: " + tmpDW.currentDate.toString());
               if(tmpDW.currentDate.compareTo(LocalDate.now()) == 0) {
                   Log.i(TAG, "MyLog.Wallet calculateWalletBalance — Do nothing. If current date is the last one, it means previous days has already been calculated when application has been run earlier today.");
               }
                else if(lastDateIncludedInBalance == null || tmpDW.currentDate.compareTo(lastDateIncludedInBalance) > 0) {
                    //Log.i(TAG, "MyLog.Wallet calculateWalletBalance — last date added to balance: " + lastDateIncluededInBalance);
                    for (int i = 0; i < numberOfExercises; i++) {
                        Log.i(TAG, "MyLog.Wallet calculateWalletBalance — adding exercise: " + i + ", coins: "+ tmpDW.readReps(i) * (Double) exercisesRatio[i]);
                        coinsFromPreviosDay = coinsFromPreviosDay + tmpDW.readReps(i) * (Double) exercisesRatio[i];
                    }
                    dailyBonus = calculateDailyBonus(lastDateIncludedInBalance,tmpDW.currentDate);
                    previousDateIncludedInBalance = lastDateIncludedInBalance;
                    lastDateIncludedInBalance = tmpDW.currentDate;
                    Log.i(TAG, "MyLog.Wallet calculateWalletBalance — coins from previous day: " + lastDateIncludedInBalance);
                    Log.i(TAG, "MyLog.Wallet calculateWalletBalance() — added coins from previous day: " + lastDateIncludedInBalance);
                    MyFileLogger.AddLog("Adding coins from previous day: " + lastDateIncludedInBalance);
                    if (dailyBonus > 0) MyFileLogger.AddLog("Adding bonus for daily workout: " + dailyBonus);
                    if (dailyBonus < 0) MyFileLogger.AddLog("Adding penelty for longer break: " + dailyBonus);
                    accountBalance = accountBalance + coinsFromPreviosDay + dailyBonus;
                    if (accountBalance<0) accountBalance =0; //just in case penelties from daily will take everything
                   MyFileLogger.AddLog("Balance after that: " + accountBalance);
                }

            }
        }
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public int getAccountBalanceInteger() {
        return (int) accountBalance;
    }

    public LocalDate getLastBalanceUpdate() {
        return lastDateIncludedInBalance;
    }
 //   public LocalDate getLastWithdrawal() {
 //       return lastWithdrawal;
 //   }

    public double getCredit(int exerciseNumber) {
        double result =0;
        if (exerciseNumber < numberOfExercises && exercisesRatio[exerciseNumber] != null) {
            result = (double) exercisesRatio[exerciseNumber];
        }
        return result;
    }

    public void readBalanaceFromCSV(String csvLine) {
        String[] split = csvLine.split(", ");
        accountBalance = Double.valueOf(split[0]);
        lastDateIncludedInBalance = LocalDate.parse(split[1]);
    }

    public String saveBalanaceToCSV() {
        String result = accountBalance + ", " + lastDateIncludedInBalance;
        return result;
    }

    public String saveExerciseRatioToCSV()  {
        String result = "";
        for(int i=0; i<exercisesRatio.length; i++)  {
            result = result + exercisesRatio[i] + ", ";
        }
        Log.i(TAG, "MyLog.Wallet saveExerciseRatioToCSV — size of exerciseRatio:  " + exercisesRatio.length);
        return result;
    }

    public void loadExerciseRatioFromCSV(String csvLine)  {
        String[] split = csvLine.split(", ");

        for(int i=0; i<exercisesRatio.length; i++)  {
            exercisesRatio[i] = Double.valueOf(split[i]) ;
        }
    }

    public double calculateCoinsFromToday(LinkedList<DailyWorkout> listOfWorkouts) {
        double coinsForToday = 0.0;
        DailyWorkout tmpDW;
        Log.i(TAG, "MyLog.Wallet calculateCoinsFromToday");
        if (listOfWorkouts.size() > 0)
        {
            ListIterator<DailyWorkout> listIterator = listOfWorkouts.listIterator(listOfWorkouts.size());
            Log.i(TAG, "MyLog.Wallet calculateCoinsFromToday size of workouts bigger then 0");
            if(listIterator.hasPrevious())  {
                tmpDW=listIterator.previous();
                if(tmpDW.currentDate.compareTo(LocalDate.now()) == 0) {
                    for (int i = 0; i < numberOfExercises; i++) {
                        Log.i(TAG, "MyLog.Wallet calculateCoinsFromToday — adding exercise: " + i + ", coins: "+ tmpDW.readReps(i) * (Double) exercisesRatio[i]);
                        coinsForToday = coinsForToday + tmpDW.readReps(i) * (Double) exercisesRatio[i];
                    }
                }
            }
        }
        return coinsForToday;
    }

    public double calculateDailyBonus(LocalDate previousDate, LocalDate currentDate) {
        double dailyBonus = 2.0;
        double dailyPenalty = -1.0;
        int numberOfDaysToStartPenalty = 3;

        int numberOfDaysSinceLastTraining = Days.daysBetween(previousDate,currentDate).getDays();

        if(numberOfDaysSinceLastTraining ==1 ) return dailyBonus;
        if(numberOfDaysSinceLastTraining  > numberOfDaysToStartPenalty ) {
            int numberOfDaysToPunish = numberOfDaysSinceLastTraining - numberOfDaysToStartPenalty;
            return numberOfDaysToPunish*dailyPenalty;
        }
        return 0.0;
    }

    public double calculateDailyBonusForToday() {
        double bonusForToday = 0;
        if(previousDateIncludedInBalance != null)
            bonusForToday = calculateDailyBonus(previousDateIncludedInBalance, LocalDate.now());

        return bonusForToday;
    }
}