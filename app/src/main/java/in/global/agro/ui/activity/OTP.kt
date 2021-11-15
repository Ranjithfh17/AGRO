package `in`.global.agro.ui.activity

import `in`.global.agro.databinding.ActivityOtpBinding
import `in`.global.agro.extensions.showToast
import `in`.global.agro.utils.OTP_Reciver
import `in`.global.agro.utils.Resource
import `in`.global.agro.viewmodels.OTPViewModel
import android.content.ContentValues
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.phone.SmsRetriever
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect



@AndroidEntryPoint
class OTP : AppCompatActivity(), OTP_Reciver.OTPReceiveListener {



    private lateinit var binding: ActivityOtpBinding
    private lateinit var otpReceiver: OTP_Reciver
    private val viewModel by viewModels<OTPViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        validateField()
        startSMSListener()
        setOtpToReceiver()
        sendOTP()
        verifyOTP()


    }


    private fun validateField() {

        binding.apply {

            edt1.doOnTextChanged { _, _, _, _ ->
                if (edt1.text.toString().length == 1) {
                    edt2.requestFocus()
                }
            }
            edt2.doOnTextChanged { _, _, _, _ ->
                if (edt2.text.toString().length == 1) {
                    edt3.requestFocus()
                }
                if (edt2.text.toString().isEmpty()) {
                    edt1.requestFocus()
                }
            }
            edt3.doOnTextChanged { _, _, _, _ ->
                if (edt3.text.toString().length == 1) {
                    edt4.requestFocus()
                }
                if (edt3.text.toString().isEmpty()) {
                    edt2.requestFocus()
                }
            }

            edt4.doOnTextChanged { _, _, _, _ ->
                if (edt4.text.toString().length == 1) {
                    edt5.requestFocus()
                }
                if (edt4.text.toString().isEmpty()) {
                    edt3.requestFocus()
                }
            }

            edt5.doOnTextChanged { _, _, _, _ ->
                if (edt5.text.toString().length == 1) {
                    edt6.requestFocus()
                }
                if (edt5.text.toString().isEmpty()) {
                    edt4.requestFocus()
                }
            }

            edt6.doOnTextChanged { _, _, _, _ ->
                if (edt6.text.toString().isEmpty()) {
                    edt5.requestFocus()
                }
            }


        }

    }


    private fun setOtpToReceiver() {
        OTP_Reciver().setotp1(binding.edt1)
        OTP_Reciver().setotp2(binding.edt2)
        OTP_Reciver().setotp3(binding.edt3)
        OTP_Reciver().setotp4(binding.edt4)
        OTP_Reciver().setotp5(binding.edt5)
        OTP_Reciver().setotp6(binding.edt6)
        OTP_Reciver().fitbutton(binding.verify)
    }



    override fun onOTPReceived(otp: String?) {
        unregisterReceiver(otpReceiver)
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(otpReceiver)
    }


    private fun startSMSListener() {
        try {
            otpReceiver = OTP_Reciver()
            otpReceiver.setOTPListener(this)
            val intentFilter = IntentFilter()
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
            registerReceiver(otpReceiver, intentFilter)
            val client = SmsRetriever.getClient(this)
            val task = client.startSmsRetriever()
            task.addOnSuccessListener { /*NO-OP*/ }
            task.addOnFailureListener {/*NO-OP*/ }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun sendOTP() {
        binding.verify.setOnClickListener {

            val otp = binding.edt1.text.toString()
                .plus(binding.edt2.text.toString())
                .plus(binding.edt3.text.toString())
                .plus(binding.edt4.text.toString())
                .plus(binding.edt5.text.toString())
                .plus(binding.edt6.text.toString())


            if (otp.isEmpty()) {
                applicationContext.showToast("Enter valid OTP")
            } else {
//                lifecycleScope.launchWhenStarted {
//                    viewModel.getOTP(
//                        type = "2",
//                        otp = otp,
//
//                    )
//                }

                startActivity(Intent(this@OTP,MainActivity::class.java))

            }
        }
    }



    private fun verifyOTP() {
        lifecycleScope.launchWhenStarted {
            viewModel.otpFlow.collect {
                when(it){
                    is Resource.Success ->{

                        Log.i("TAG", "verifyOTP: ${it.data}")

                        if (it.data?.error == true){
                            applicationContext.showToast(it.data.error_msg)
                            startActivity(Intent(this@OTP,MainActivity::class.java))

                        }else{
                            startActivity(Intent(this@OTP,MainActivity::class.java))
                        }

                    }

                    is Resource.Error ->{
                        startActivity(Intent(this@OTP,MainActivity::class.java))

                        Log.i("TAG", "verifyOTP: ${it.message.toString()}")

                    }

                    is Resource.Loading ->{

                    }
                }
            }
        }
    }



}