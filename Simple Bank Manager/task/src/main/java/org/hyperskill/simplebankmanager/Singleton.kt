package org.hyperskill.simplebankmanager

import java.math.BigDecimal

class Singleton private constructor() {
    lateinit var balance: BigDecimal
    lateinit var exchangeMap: Map<String, Map<String, Double>>
    lateinit var billInfoMap: Map<String, Triple<String, String, Double>>
    companion object {

        @Volatile
        private var instance: Singleton? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: Singleton().also { instance = it }
        }
    }
}