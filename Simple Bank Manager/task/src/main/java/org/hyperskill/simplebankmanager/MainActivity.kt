package org.hyperskill.simplebankmanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Singleton.getInstance().balance = BigDecimal.valueOf(10000, 2)
        val balance = intent.extras?.getDouble("balance")
        if (balance != null) Singleton.getInstance().balance = BigDecimal.valueOf(balance)
        Singleton.getInstance().balance.setScale(2)
    }

}