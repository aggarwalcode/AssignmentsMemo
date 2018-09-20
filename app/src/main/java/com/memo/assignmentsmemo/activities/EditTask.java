package com.memo.assignmentsmemo.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.memo.assignmentsmemo.R;
import com.memo.assignmentsmemo.modelclass.TaskAttributes;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class EditTask extends AppCompatActivity {

    String[] statusArray = new String[]{"Received","Confirmed","Assigned","Processed","Ready","Delivered","Rework"};
    String[] currencyArray = new String[]{
            "EUR", "AUD", "USD", "GBP", "NZD", "CAD"};
    private static String URL = null;

    EditText amountView,wordCountView,invoiceAmtView,remarks;
    TextView countryView, nameView,orderIdView,emailView,fBaseIdVIew,txn_id_view;
    TextView dateView;
    CheckBox checkBox;
    Button updateBut;
    Boolean allowSubmit=null,verify;
    Spinner currencySpnView,taskStatusView;
    Intent intent;
    String fBaseTaskId;
    Map<String,Object> taskMap = new HashMap<String,Object>();

    ArrayAdapter<String > currencySpnAdapter;
    ArrayAdapter<String > statusSpnAdapter;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference("tasks");
    private String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        emailView = findViewById(R.id.email);
        nameView = findViewById(R.id.name);
        orderIdView = findViewById(R.id.orderId);
        countryView = findViewById(R.id.country);
        currencySpnView =findViewById(R.id.currencySpinner);
        amountView = findViewById(R.id.amount);
        wordCountView = findViewById(R.id.wordCount);
        invoiceAmtView = findViewById(R.id.invoiceAmt);
        dateView = findViewById(R.id.date_created);
        fBaseIdVIew = findViewById(R.id.fBaseId);
        taskStatusView = findViewById(R.id.taskStatus);
        updateBut = findViewById(R.id.update_button);
        dateView = findViewById(R.id.date_created);
        checkBox = findViewById(R.id.checkbox);
        txn_id_view = findViewById(R.id.txn_id);
        remarks = findViewById(R.id.remarks_et);

        intent = getIntent();
        fBaseTaskId = intent.getStringExtra("FBASE_ID");

        statusSpnAdapter = new ArrayAdapter<String>(
                getApplication(),
                android.R.layout.simple_spinner_item,
                statusArray
        );
        taskStatusView.setAdapter(statusSpnAdapter);

        currencySpnAdapter = new ArrayAdapter<String>(
                getApplication(),
                android.R.layout.simple_spinner_item,
                currencyArray
        );
        currencySpnView.setAdapter(currencySpnAdapter);

        databaseReference.child(fBaseTaskId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TaskAttributes taskAttributes = dataSnapshot.getValue(TaskAttributes.class);
                setValues(taskAttributes);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        updateBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verify = addTask();
                if (verify){
                    updateTaskDetails();
                    Toast.makeText(getApplicationContext(), "Task Submited ",
                            Toast.LENGTH_LONG).show();
                    URL = "https://paypallistener.herokuapp.com/OrderDetails?id="+fBaseTaskId;
                    if (checkBox.isChecked()){
                        sendEmail();
                    }

                }
                else
                {Toast.makeText(getApplicationContext(), "Check Errors",
                        Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private Boolean addTask() {

        String name, country, currency,email,orderID,taskStatus,remarksVal;
        int amount=0, wordCount=0, invoiceAmt=0;
        SimpleDateFormat timestamp;
        TaskAttributes taskAttributes = new TaskAttributes();

        allowSubmit = true;

        taskStatus = taskStatusView.getSelectedItem().toString();

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

        remarksVal = remarks.getText().toString();

        timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        String sdf = timestamp.format(Calendar.getInstance().getTime());

        taskMap.put("taskStatus",taskStatus);
        taskMap.put("amount",amount);
        taskMap.put("invoiceAmt",invoiceAmt);
        taskMap.put("wordCount",wordCount);
        taskMap.put("lastUpdated",sdf);
        taskMap.put("remarks",remarksVal);

        if (allowSubmit){
            //databaseReference.child(fBaseTaskId).updateChildren(taskAttributes);
            databaseReference.child(fBaseTaskId).updateChildren(taskMap);
            return true;
        }
        else return false;
    }

    private void setValues(TaskAttributes taskAtb) {
        emailView.setText(taskAtb.getEmail());
        nameView.setText(taskAtb.getName());
        orderIdView.setText(taskAtb.getOrderID());
        countryView.setText(taskAtb.getCountry());
        txn_id_view.setText(taskAtb.getTxn_id());
        int currencySpinPos = currencySpnAdapter.getPosition(taskAtb.getCurrency());
        currencySpnView.setSelection(currencySpinPos);
        amountView.setText (String.valueOf(taskAtb.getAmount()));
        wordCountView.setText(String.valueOf(taskAtb.getWordCount()));
        invoiceAmtView.setText(String.valueOf(taskAtb.getInvoiceAmt()));
        dateView.setText(taskAtb.getTimestamp());
        int spinnerPosition = statusSpnAdapter.getPosition(taskAtb.getTaskStatus());
        taskStatusView.setSelection(spinnerPosition);
        remarks.setText(taskAtb.getRemarks());
    }

    private void sendEmail() {

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setType("message/rfc822");
        i.setData(Uri.parse("mailto:" + emailView.getText()));
        //i.putExtra(Intent.EXTRA_EMAIL  , new String[]{""+emailView.getText()});
        i.putExtra(Intent.EXTRA_SUBJECT, "Make My Assignments Order Confirmation");
        i.putExtra(Intent.EXTRA_TEXT   ,
                "Please find Your order details below.\n\n"+
                        "Name: "+nameView.getText()+"\n"+
                        "Order Id: "+fBaseTaskId+"\n"+
                        "Country: "+countryView.getText()+"\n"+
                        "Invoice Amount: "+invoiceAmtView.getText()+"\n"+
                        "Word Count: "+wordCountView.getText()+"\n"+
                        "Payment Link: "+URL+"\n"

        );



        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(EditTask.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateTaskDetails() {

    }

}
