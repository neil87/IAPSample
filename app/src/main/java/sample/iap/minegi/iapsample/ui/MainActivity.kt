package sample.iap.minegi.iapsample.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import sample.iap.minegi.iapsample.R
import sample.iap.minegi.iapsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initializeBindingData()
    }

    private fun initializeBindingData() {
        binding.activity = this
        binding.textMyCoin.text = "0"
    }

    fun isBillingSupported() {
        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show()
    }
}
