package in.global.agro.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;


public class OTP_Reciver extends BroadcastReceiver {

    private OTPReceiveListener otpListener;

    /**
     * @param otpListener
     */
    private static EditText otp1,otp2,otp3,otp4,otp5,otp6;
    private static Button send;

    public void setotp1(EditText editText)
    {
        OTP_Reciver.otp1 = editText;
    }
    public void setotp2(EditText editText)
    {
        OTP_Reciver.otp2 = editText;
    }
    public void setotp3(EditText editText)
    {
        OTP_Reciver.otp3 = editText;
    }
    public void setotp4(EditText editText)
    {
        OTP_Reciver.otp4 = editText;
    }
    public void setotp5(EditText editText)
    {
        OTP_Reciver.otp5 = editText;
    }
    public void setotp6(EditText editText)
    {
        OTP_Reciver.otp6 = editText;
    }
    public void fitbutton(Button button){
        OTP_Reciver.send = button;
    }
    public void setOTPListener(OTPReceiveListener otpListener) {
        this.otpListener = otpListener;
    }


    /**
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);
            switch (status.getStatusCode()) {
                case CommonStatusCodes.SUCCESS:

                    //This is the full message
                    String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);

                    /*<#> Your ExampleApp code is: 123ABC78
                    FA+9qCX9VSu*/

                    //Extract the OTP code and send to the listener
                    try {
                        try {
                            String otp = message.split("is ")[1];
                            String fullnum = otp.substring(0, 6);
                            String notp1 = fullnum.substring(0, 1);
                            String notp2 = fullnum.substring(1, 2);
                            String notp3 = fullnum.substring(2, 3);
                            String notp4 = fullnum.substring(3, 4);
                            String notp5 = fullnum.substring(4, 5);
                            String notp6 = fullnum.substring(5, 6);
                            if (fullnum.matches("[0-9]+"))  {
                                otp1.setText(notp1);
                                otp2.setText(notp2);
                                otp3.setText(notp3);
                                otp4.setText(notp4);
                                otp5.setText(notp5);
                                otp6.setText(notp6);
                                send.performClick();
                            }
                        }catch (NullPointerException e){e.printStackTrace();}
                    }catch (ArrayIndexOutOfBoundsException e){e.printStackTrace();}
                    if (otpListener != null) {
                        otpListener.onOTPReceived(message);
                    }
                    break;
                case CommonStatusCodes.TIMEOUT:
                    // Waiting for SMS timed out (5 minutes)
                    if (otpListener != null) {
                        otpListener.onOTPTimeOut();
                    }
                    break;

                case CommonStatusCodes.API_NOT_CONNECTED:

                    if (otpListener != null) {
                        otpListener.onOTPReceivedError("API NOT CONNECTED");
                    }

                    break;

                case CommonStatusCodes.NETWORK_ERROR:

                    if (otpListener != null) {
                        otpListener.onOTPReceivedError("NETWORK ERROR");
                    }

                    break;

                case CommonStatusCodes.ERROR:

                    if (otpListener != null) {
                        otpListener.onOTPReceivedError("SOME THING WENT WRONG");
                    }

                    break;

            }
        }
    }

    /**
     *
     */
    public interface OTPReceiveListener {

        void onOTPReceived(String otp);

       default void onOTPTimeOut(){

        }

        default  void onOTPReceivedError(String error){

        }
    }
}