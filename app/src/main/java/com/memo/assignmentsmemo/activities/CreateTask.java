package com.memo.assignmentsmemo.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.memo.assignmentsmemo.R;
import com.memo.assignmentsmemo.modelclass.TaskAttributes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateTask extends AppCompatActivity {

    private static String URL = null;
    DatabaseReference databaseReference;
    Button submitBut;
    Boolean allowSubmit=null,verify;
    Spinner currencySpn;
    ArrayAdapter<String> countryAdapter;
    String[] currencyVal = new String[]{"Select Currency", "EUR", "AUD", "USD", "GBP", "NZD", "CAD"};
    String[] countries;
    CheckBox checkBox;
    private static String fBaseId;
    int unique_id= (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

    AutoCompleteTextView countryView;
    EditText nameView,orderIdView,emailView,amountView,wordCountView,invoiceAmtView,remarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        databaseReference = FirebaseDatabase.getInstance().getReference("tasks");
        fBaseId = String.valueOf(unique_id);

        countries = getResources().getStringArray(R.array.countries_array);

        nameView = findViewById(R.id.name);
        orderIdView = findViewById(R.id.orderId);
        countryView = (AutoCompleteTextView) findViewById(R.id.country);
        amountView = findViewById(R.id.amount);
        wordCountView = findViewById(R.id.wordCount);
        invoiceAmtView = findViewById(R.id.invoiceAmt);
        emailView = findViewById(R.id.email);
        currencySpn = findViewById(R.id.currencySpinner);
        remarks = findViewById(R.id.remarks);
        checkBox = findViewById(R.id.checkbox);

        orderIdView.setText(fBaseId);
        orderIdView.setKeyListener(null);

        countryAdapter = new ArrayAdapter<String>(CreateTask.this,
                android.R.layout.simple_list_item_1, countries);

        countryView.setAdapter(countryAdapter);


        ArrayAdapter<String> adapterCrn = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_dropdown_item, currencyVal
        );
        currencySpn.setAdapter(adapterCrn);

        submitBut = findViewById(R.id.submit_button);

        //adding an onclicklistener to button
        submitBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify = addTask();
                if (verify){
                    Toast.makeText(getApplicationContext(), "Task Submited ",
                            Toast.LENGTH_LONG).show();
                URL = "https://paypallistener.herokuapp.com/OrderDetails?id="+fBaseId;
                    if (checkBox.isChecked()){
                        sendEmail();
                    }
                    clearFields();
                    finish();
                }
                else
                {Toast.makeText(getApplicationContext(), "Check Errors",
                            Toast.LENGTH_LONG).show();
                }

            }

        });
    }

    private void sendEmail() {

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setType("message/rfc822");
        i.setData(Uri.parse("mailto:" + emailView.getText()));
        i.putExtra(Intent.EXTRA_SUBJECT, "Make My Assignments Order Confirmation");
        i.putExtra(Intent.EXTRA_TEXT   ,
                "Please find Your order details below.\n\n"+
                "Name: "+nameView.getText()+"\n"+
                "Order Id: "+fBaseId+"\n"+
                "Country: "+countryView.getText()+"\n"+
                "Invoice Amount: "+invoiceAmtView.getText()+"\n"+
                "Word Count: "+wordCountView.getText()+"\n"+
                "Payment Link: "+URL+"\n"

        );



        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(CreateTask.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean addTask() {

        String name, country, currency,email,orderID,taskStatus="Received";
        int amount=0, wordCount=0, invoiceAmt=0;
        SimpleDateFormat timestamp;
        TaskAttributes taskAttributes = new TaskAttributes();

        allowSubmit = true;

        name = nameView.getText().toString().trim();
        if (TextUtils.isEmpty(name)){

            allowSubmit = false;
            nameView.setError(getString(R.string.blank_field));
        }

        country = countryView.getText().toString().trim();
        if (TextUtils.isEmpty(country)){

            allowSubmit = false;
            countryView.setError(getString(R.string.blank_field));
        }


        currency = currencySpn.getSelectedItem().toString().trim();
        if (currencySpn.getSelectedItem().toString()=="Select Currency"){

            allowSubmit = false;
            ((TextView)currencySpn.getSelectedView()).setError("None Selected");
        }


        email = emailView.getText().toString().trim();

        if (!(Patterns.EMAIL_ADDRESS.matcher(email).matches()))
        {

            allowSubmit = false;
            emailView.setError("Enter Correct Email");
        }

        orderID = fBaseId;

        if( !amountView.getText().toString().equals("") && amountView.getText().toString().length() > 0 )
        {
            amount = Integer.parseInt(amountView.getText().toString());
        }
            else{

                allowSubmit = false;
                amountView.setError(getString(R.string.blank_field));
            }


        if( !wordCountView.getText().toString().equals("") && wordCountView.getText().toString().length() > 0 )
        {
            wordCount = Integer.parseInt(wordCountView.getText().toString());
        }
            else {

                allowSubmit = false;
                wordCountView.setError(getString(R.string.blank_field));
            }


        if( !invoiceAmtView.getText().toString().equals("") && invoiceAmtView.getText().toString().length() > 0 )
        {
            invoiceAmt = Integer.parseInt(invoiceAmtView.getText().toString());
        }
            else  {
                allowSubmit = false;
                invoiceAmtView.setError(getString(R.string.blank_field));
            }




        timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

        String sdf = timestamp.format(Calendar.getInstance().getTime());

        String remarkstext = remarks.getText().toString();

        try {
            taskAttributes = new TaskAttributes(
                    name, country, currency, email,
                    fBaseId, sdf,orderID,taskStatus,remarkstext,
                    amount,wordCount,invoiceAmt
            );
        }catch (Exception e){
            Exception ed =e;
            Toast.makeText(getApplicationContext(), ""+ed,
                    Toast.LENGTH_LONG).show();
        }


        if (allowSubmit == true){
            databaseReference.child(fBaseId).setValue(taskAttributes);
            return true;
        }
        else return false;
    }

    public void clearFields(){
        nameView.setText("");orderIdView.setText("");countryView.setText("");
        amountView.setText("");wordCountView.setText("");invoiceAmtView.setText("");
        emailView.setText("");
    }
}
