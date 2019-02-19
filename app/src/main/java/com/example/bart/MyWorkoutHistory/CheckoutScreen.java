package com.example.bart.MyWorkoutHistory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.bart.firstapp2.R;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CheckoutScreen extends Activity {

    WorkoutStats myWorkoutStats;
    int checkoutAmount =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_screen);
        myWorkoutStats = WorkoutStats.getInstance();

        // calculate and display coins account balance
        myWorkoutStats.walletInstance.calculateWalletBalance(myWorkoutStats.myList);
        TextView vText;
        vText = findViewById(R.id.walletBalance);
        vText.setText( String.valueOf( myWorkoutStats.walletInstance.getAccountBalanceInteger()));
    }

    public void onCheckoutButton(View arg0) {
        checkoutAmount = Integer.parseInt(((EditText)findViewById(R.id.checkoutAmount)).getText().toString());

        if (checkoutAmount <=  myWorkoutStats.walletInstance.getAccountBalanceInteger()) {
            // display confirmation dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirm before checkout !");
            builder.setMessage("You are about to checkout " + checkoutAmount + " from your account. Are you sure?");
            builder.setCancelable(false);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "You've done a checkout from your account", Toast.LENGTH_SHORT).show();
                    myWorkoutStats.walletInstance.withdraw(checkoutAmount);

                    TextView vText;
                    vText = findViewById(R.id.walletBalance);
                    vText.setText( String.valueOf( myWorkoutStats.walletInstance.getAccountBalanceInteger()));
                }

            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(), "No changes has been done to your account", Toast.LENGTH_SHORT).show();
                }
            });

            builder.show();
        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "It is too much! You didn't work hard enough",
                    Toast.LENGTH_SHORT);

            toast.show();

        }


    }

}
