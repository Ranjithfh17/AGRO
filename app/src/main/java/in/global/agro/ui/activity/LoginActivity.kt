package `in`.global.agro.ui.activity
import `in`.global.agro.databinding.ActivityLoginBinding
import `in`.global.agro.extensions.showToast
import `in`.global.agro.utils.AppSignatureHashHelper
import `in`.global.agro.utils.Resource
import `in`.global.agro.utils.hasInternetConnection
import `in`.global.agro.viewmodels.LoginViewModel
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.Credentials
import com.google.android.gms.auth.api.credentials.CredentialsOptions
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.installations.FirebaseInstallations
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect



@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private val TAG = "LoginActivity"

    private lateinit var binding: ActivityLoginBinding
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var intentSender: ActivityResultLauncher<IntentSenderRequest>
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var appSignatureHashHelper: AppSignatureHashHelper
    private var locationPermission = false
    private  var token: String="0"


    @InternalCoroutinesApi
    @ExperimentalUnsignedTypes
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)


        appSignatureHashHelper=AppSignatureHashHelper(this)


        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permission ->

                locationPermission = permission[android.Manifest.permission.ACCESS_FINE_LOCATION]
                    ?: locationPermission

                if (locationPermission) {
//                    startActivity(Intent(this,MainActivity::class.java))

//                    getOtp()
                } else {
                    applicationContext.showToast("enable location permission to login")
                }
            }


        //mobile no
        intentSender =
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val credential: Credential? = it?.data?.getParcelableExtra(Credential.EXTRA_KEY)
                    Log.i(TAG, "onCreate:${credential?.id} ")

                    credential?.apply {
                        binding.mobileNo.setText(credential.id.substring(3))
                    }

                }

            }



        generateToken()
        getMobileNumber()
        login()


        lifecycleScope.launchWhenStarted {
            viewModel.loginFlow.collect {
                when (it) {
                    is Resource.Success -> {
                        if (it.data?.error == true) {
                            applicationContext.showToast("Your Mobile is Not registered")
                            startActivity(Intent(this@LoginActivity, OTP::class.java))
//                            finish()
                        } else {
                            Log.i(TAG, "onCreate: ")
                            startActivity(Intent(this@LoginActivity, OTP::class.java))
//                            finish()
                        }
                    }

                    is Resource.Error -> {
                        applicationContext.showToast(it.message.toString())
                        Log.i(TAG, "onCreate: ${it.message}")
                        startActivity(Intent(this@LoginActivity, OTP::class.java))

                    }

                   is Resource.Loading ->{

                   }
                }

            }
        }

        changeAppLanguage()

    }


    @ExperimentalUnsignedTypes
    @RequiresApi(Build.VERSION_CODES.P)
    private fun login() {

        binding.getOtp.setOnClickListener {

            val mobileNo = binding.mobileNo.text.toString()

            if (mobileNo.isEmpty() || mobileNo.length < 10) {
                applicationContext.showToast("Enter Valid Mobile Number")
            } else {
//                startActivity(Intent(this,MainActivity::class.java))
//                finish()

                getOtp()

//                requestPermissions()

            }
        }

    }

    private fun requestPermissions() {
        permissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_CONTACTS,
            )
        )
    }


    //mobile no
    private fun getMobileNumber() {

        val hintRequest = HintRequest.Builder().setPhoneNumberIdentifierSupported(true).build()

        val option = CredentialsOptions.Builder().forceEnableSaveDialog().build()

        val credentialClient = Credentials.getClient(this, option)
        val intent = credentialClient.getHintPickerIntent(hintRequest)

        intentSender.launch(IntentSenderRequest.Builder(intent.intentSender).build())


    }

    @SuppressLint("HardwareIds")
    @ExperimentalUnsignedTypes
    @RequiresApi(Build.VERSION_CODES.P)
    private fun getOtp() {
        val deviceId = Settings.Secure.getString(this.contentResolver, Settings.Secure.ANDROID_ID)
        lifecycleScope.launchWhenStarted {

            viewModel.login(
                "3",
                binding.mobileNo.text.toString(),
                appSignatureHashHelper.getAppSignature()[0],
                token,
                deviceId
            )
        }

    }


    private fun changeAppLanguage() {


    }


    private fun generateToken() {

        try {
            if(hasInternetConnection(this)){
                FirebaseInstallations.getInstance().id.addOnCompleteListener { task: Task<String?> ->
                    if (task.isSuccessful) {
                        token = task.result.toString()

                        viewModel.saveToken(token)
                    }
                }.addOnFailureListener {
                    Log.i(TAG, "generateToken: ${it.message}")
                }
            }

        }catch (e:Exception){

        }

    }
}