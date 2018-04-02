package com.example.oliverbaird.finda5aside;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oliverbaird.finda5aside.config.Config;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;

public class PayPal extends AppCompatActivity {

    private static final int PAYPAL_REQUEST_CODE = 7171;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) //sandbox for test
            .clientId(Config.PAYPAL_CLIENT_ID);

    Button buttonPayNow;
    EditText editTextAmount;
    Intent m_service;

    String amount = "";

    @Override
    protected void onDestroy(){
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_pal);

        //Start PayPal service

        m_service = new Intent (this, PayPalService.class);
        m_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(m_service);

        buttonPayNow = findViewById(R.id.buttonPayNow);
        editTextAmount = findViewById(R.id.editTextAmount);

        buttonPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processPayment();
            }
        });

    }

    private void processPayment() {

        amount = editTextAmount.getText().toString();
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(amount)), "GBR",
                "Pay for football", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PayPal.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);

    }

    protected void onActivity(int requestCode, int resultCode, Intent data){
        if (requestCode == PAYPAL_REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation != null)
                {
                        String state = confirmation.getProofOfPayment().getState();

                        if (state.equals("approved"))//if the payment worked, the state equals approved
                            Toast.makeText(this, "Payment Approved", Toast.LENGTH_SHORT).show();

                    }
                    else
                    Toast.makeText(this, "Payment Declined", Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(this, "Confirmation is null", Toast.LENGTH_SHORT).show();
            }
        }
    }





