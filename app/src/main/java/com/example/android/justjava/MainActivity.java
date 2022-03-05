package com.example.android.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
// Number of coffees
    int num_c = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
// Increases number of coffees
    public void increment(View view) {
        num_c += 1;
        if (num_c >= 50)
            num_c = 50;

        displayQuantityNumber(num_c);
    }
    //Decreases number of coffees
    public void decrement(View view) {
        num_c -= 1;
        if (num_c <= 0)
            num_c = 0;

        displayQuantityNumber(num_c);
    }

// Order Button

    @SuppressLint("QueryPermissionsNeeded")
    public void submitOrder(View view) {
        // Gets the email of the people
        EditText Email = (EditText) findViewById(R.id.email);
        String email = Email.getText().toString();
        String[] emailList = email.split(",");
        // Gets the name of people
        EditText Name = (EditText) findViewById(R.id.name);
        String name = Name.getText().toString();
        //Checkbox for WhippedCream and  checks whether we have added it or not
        CheckBox whippedCream = (CheckBox) findViewById(R.id.cream_checkbox);
        boolean hasCream = whippedCream.isChecked();
        //Checkbox for chocolate and checks whether we have added it or not
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChoco = chocolate.isChecked();
        //Price
        int price = calculatePrice(hasCream, hasChoco);
        //Order message contains name , price, quantity etc.
        String orderMessage = createOrderSummary(name, price, hasCream, hasChoco);
        //Email intent
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for: " + name);
        intent.putExtra(Intent.EXTRA_TEXT, orderMessage);
        intent.putExtra(Intent.EXTRA_EMAIL, emailList);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        }


// Method for the Order summary and message
    public String createOrderSummary(String value, int price, boolean hasCream,boolean hasChoco) {
        String priceMessage = "Name:" + value;
        priceMessage = priceMessage + "\nAdd whipped cream? " + hasCream;
        priceMessage = priceMessage + "\nAdd chocolate? " + hasChoco;
        priceMessage = priceMessage + "\nQuantity: " + num_c;
        priceMessage = priceMessage + "\nTotal: $" + price;
        priceMessage = priceMessage + "\nThank You!";
        return priceMessage;
    }

// It calculates the total price
    public int calculatePrice(boolean addCream, boolean addChoco) {
        int basePrice = 5;

        if(addCream) {
            basePrice += 1;
        }
        if(addChoco) {
            basePrice += 2;
        }
        return basePrice * num_c;
    }

// Displays the quantity off coffees ordered
    private void displayQuantityNumber(int number) {
        TextView quantity = (TextView) findViewById(R.id.quantity_text_view);
        quantity.setText("" + number);

    }

    // This method links this activity to Login page

    public void Sign(View view) {
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
    }
}