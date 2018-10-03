package com.example.bart.MyWorkoutHistory;

import android.util.Log;

import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

import static android.content.ContentValues.TAG;

/**
 * Created by bart on 15.07.2018.
 * Credits are calculated from previous days so last date of summing it up has to be recorded
 */

public class Wallet implements Serializable {
    int accountBalance;
    LocalDate lastDateIncluededInBalance;
    ArrayList exercisesRatio;
    int numberOfExercises;
    LinkedList<Withdrowal> historyOfWithdrowals = new LinkedList<Withdrowal>();

    private class Withdrowal {
        public LocalDate withdrowalDate;
        public int amount;

        public Withdrowal() {
            withdrowalDate = LocalDate.now();
            amount =0;
        }

        public Withdrowal(LocalDate withdrowalDate, int amount ) {
            this.withdrowalDate = withdrowalDate;
            this.amount = amount;
        }
    }

    public Wallet(int numberOfExercises) {
        accountBalance = 0;
        exercisesRatio = new ArrayList(numberOfExercises);
        this.numberOfExercises = numberOfExercises;

        //set default ratios
        exercisesRatio.add(0, 0.01);
        Log.i(TAG, "MyLog.Wallet constructor — loading credits for 1st exercise: 0.01");
        exercisesRatio.add(1, 0.01);
        exercisesRatio.add(2, 0.01);
        exercisesRatio.add(3, 0.1);
        exercisesRatio.add(4, 0.1);
    }

    public void setWalletValues(int balance, LocalDate dateOfLastWithdrawal) {
        accountBalance = balance;
        lastWithdrawal = dateOfLastWithdrawal;
    }

    public void setExerciseRatio(int exerciseNumber, double ratio) {
        //ToDo  throw exception if exerciseNumber is out of range
        if(exerciseNumber < numberOfExercises ) {
            exercisesRatio.add(exerciseNumber, ratio);
        }
    }

    Double calculateWalletBalance(  LinkedList<DailyWorkout> listOfExercises) {
        Double result = 0.0;
        
        ListIterator<DailyWorkout> listIterator = listOfExercises.listIterator();
        while (listIterator.hasNext()) {
//            result = result + litr.next().toString();
        }

        return result;
    }

    public int getAcountBalance() {
        return accountBalance;
    }

    public LocalDate getLastWithdrawal() {
        return lastWithdrawal;
    }

    public double getCredit(int exerciseNumber) {
        double result =0;
        if (exerciseNumber < numberOfExercises) {
            result = (double) exercisesRatio.get(exerciseNumber);
        }
        return result;
    }

    public int withdrawFromWallet(int coinsToWithdraw)  {
        int result = -1;
        if(accountBalance >= coinsToWithdraw) {
            accountBalance = accountBalance - coinsToWithdraw;
            result = accountBalance;
        }
       return result;
    }

}
