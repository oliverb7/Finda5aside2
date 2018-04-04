package com.example.oliverbaird.finda5aside;

import android.app.Activity;
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

//    private static final int m_PAYPAL_REQUEST_CODE = 7171;
//
//    Button buttonPayNow;
//    TextView textResponse;
//    Intent m_service;
//    PayPalConfiguration m_configuration;
//    String amount = "";
//    public static final String PAYPAL_CLIENT_ID = "AZKFiPvkx92lr65JU-ofrg4OlIC0lhvG42SNMzldvkuagGY0tSRZmXCNY0fN8obcyGBM8AWxRLoVI7if";
//
//    @Override
//    protected void onDestroy(){
//        stopService(new Intent(this, PayPalService.class));
//        super.onDestroy();
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_pay_pal);
//
//        m_configuration = new PayPalConfiguration()
//                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) //sandbox for test
//                .clientId(PAYPAL_CLIENT_ID);
//
//        //start PayPal Service
//        m_service = new Intent (this, PayPalService.class);
//        m_service.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,m_configuration);
//        startService(m_service);
//    }
//
//    void pay(View view) {
//
//        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(10), "GBP",
//                "Pay for football", PayPalPayment.PAYMENT_INTENT_SALE);
//
//        Intent intent = new Intent(this, PaymentActivity.class);
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, m_configuration);
//        intent.putExtra(PaymentActivity.EXTRA_PAYMENT,payPalPayment);
//        startActivityForResult(intent, m_PAYPAL_REQUEST_CODE);
//    }
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data){
//
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == m_PAYPAL_REQUEST_CODE)
//        {
//            if(resultCode == Activity.RESULT_OK)
//            {
//                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
//
//                if(confirmation != null)
//                {
//                        String state = confirmation.getProofOfPayment().getState();
//
//                        if (state.equals("approved")) {//if the payment worked, the state equals approved
//                            Toast.makeText(this, "Payment Approved", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                    else
//                    startActivity(new Intent(this, GamesDetails.class));
//                    Toast.makeText(this, "Payment Declined", Toast.LENGTH_SHORT).show();
//                }
//                Toast.makeText(this, "Confirmation is null", Toast.LENGTH_SHORT).show();
//            }
//        }
    }





