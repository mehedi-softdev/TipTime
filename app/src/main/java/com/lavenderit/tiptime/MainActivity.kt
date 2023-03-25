package com.lavenderit.tiptime

import android.content.Context
import android.icu.text.NumberFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewAnimationUtils
import android.view.inputmethod.InputMethodManager
import com.lavenderit.tiptime.databinding.ActivityMainBinding
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
   private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnCalculate.setOnClickListener { calculateTip() }
        binding.costOfServiceEt.setOnKeyListener{ view, keyCode, _ -> handleKeyEvent(view, keyCode)}
    }

    private fun calculateTip() {
        val cost = binding.costOfServiceEt.text.toString().toDoubleOrNull()
        if(cost == null || cost == 0.0) {
            displayTip(0.0)
            return
        }

        val percentage = when(binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15 // else 15 % is possible
        }
        var tip = percentage * cost
        if(binding.roundUpSwitch.isChecked) {
            tip = ceil(tip)
        }
        displayTip(tip)
    }

    private fun displayTip(tip : Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipAmount.text = getString(R.string.tip_amount, formattedTip)
    }

    private fun handleKeyEvent(view: View, keyCode: Int) : Boolean {
        if(keyCode == KeyEvent.KEYCODE_ENTER) {
            // hide the soft keyboard
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}