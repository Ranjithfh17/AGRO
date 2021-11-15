package `in`.global.agro.ui.activity

import `in`.global.agro.databinding.ActivitySplashScreenBinding
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.FirebaseApp
import kotlinx.coroutines.delay

class SplashScreenAct : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)

        lifecycleScope.launchWhenStarted {
            delay(3000)
            startActivity(Intent(this@SplashScreenAct, LoginActivity::class.java))
            finish()
        }
    }



}