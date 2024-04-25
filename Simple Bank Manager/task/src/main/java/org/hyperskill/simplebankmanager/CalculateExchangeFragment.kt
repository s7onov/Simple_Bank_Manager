package org.hyperskill.simplebankmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import java.math.BigDecimal
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CalculateExchangeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CalculateExchangeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private val list = listOf("EUR", "GBP", "USD")
    private val symbolsMap = mapOf("EUR" to '€', "GBP" to '£', "USD" to '$')

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculate_exchange, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinnerFrom = view.findViewById<Spinner>(R.id.calculateExchangeFromSpinner)
        ArrayAdapter(
            view.context,
            android.R.layout.simple_spinner_item,
            list
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerFrom.adapter = adapter
        }

        spinnerTo = view.findViewById<Spinner>(R.id.calculateExchangeToSpinner)
        ArrayAdapter(
            view.context,
            android.R.layout.simple_spinner_item,
            list
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerTo.adapter = adapter
        }
        spinnerTo.setSelection(1)

        val itemListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (spinnerFrom.selectedItem.toString() == spinnerTo.selectedItem.toString()) {
                    Toast.makeText(this@CalculateExchangeFragment.context, "Cannot convert to same currency", Toast.LENGTH_SHORT).show()
                    spinnerTo.setSelection( (spinnerTo.selectedItemPosition + 1) % 3)
                }
            }
        }
        spinnerFrom.onItemSelectedListener = itemListener
        spinnerTo.onItemSelectedListener = itemListener

        val calcButton = view.findViewById<Button>(R.id.calculateExchangeButton)
        calcButton?.setOnClickListener {
            val text = view.findViewById<EditText>(R.id.calculateExchangeAmountEditText).text.toString()
            if (text.isBlank()) Toast.makeText(this@CalculateExchangeFragment.context, "Enter amount", Toast.LENGTH_SHORT).show()
            else {
                val from = spinnerFrom.selectedItem.toString() //EUR
                val to = spinnerTo.selectedItem.toString() //GBP
                val amountFrom = BigDecimal(text)
                val multiplier: Double = Singleton.getInstance().exchangeMap[from]?.get(to) ?: 1.0
                val amountTo = amountFrom * BigDecimal(multiplier)
                buildString {
                    append(symbolsMap[from])
                    append(String.format(Locale.US, "%.2f", amountFrom))
                    append(" = ")
                    append(symbolsMap[to])
                    append(String.format(Locale.US, "%.2f", amountTo))
                }.also { view.findViewById<TextView>(R.id.calculateExchangeDisplayTextView).text = it }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CalculateExchangeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CalculateExchangeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}