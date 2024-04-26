package org.hyperskill.simplebankmanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    private val defaultMap = mapOf(
        "EUR" to mapOf(
            "GBP" to 0.5,
            "USD" to 2.0
        ),
        "GBP" to mapOf(
            "EUR" to 2.0,
            "USD" to 4.0
        ),
        "USD" to mapOf(
            "EUR" to 0.5,
            "GBP" to 0.25
        )
    )
    val defaultBillInfoMap = mapOf(
        "ELEC" to Triple("Electricity", "ELEC", 45.0),
        "GAS" to Triple("Gas", "GAS", 20.0),
        "WTR" to Triple("Water", "WTR", 25.5)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Singleton.getInstance().balance = BigDecimal.valueOf(10000, 2)
        val balance = intent.extras?.getDouble("balance")
        if (balance != null) Singleton.getInstance().balance = BigDecimal.valueOf(balance)
        Singleton.getInstance().balance.setScale(2)

        val serializableMap = intent.extras?.getSerializable("exchangeMap")
        if (serializableMap != null) Singleton.getInstance().exchangeMap = serializableMap as Map<String, Map<String, Double>>
        else Singleton.getInstance().exchangeMap = defaultMap

        val billMap = intent.extras?.getSerializable("billInfo")
        if (billMap != null) Singleton.getInstance().billInfoMap = billMap as Map<String, Triple<String, String, Double>>
        else Singleton.getInstance().billInfoMap = defaultBillInfoMap
    }

}