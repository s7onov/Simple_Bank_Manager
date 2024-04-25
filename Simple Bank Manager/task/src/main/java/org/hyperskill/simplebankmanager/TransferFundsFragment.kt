package org.hyperskill.simplebankmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import java.lang.Exception
import java.math.BigDecimal

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TransferFundsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TransferFundsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_transfer_funds, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.transferFundsButton).setOnClickListener {
            val accountEditText = view.findViewById<EditText>(R.id.transferFundsAccountEditText)
            val accountString = accountEditText.text.toString()
            val validAccount = accountString.matches("\\b[cs]a\\d{4}".toRegex())
            if (!validAccount) accountEditText.error = "Invalid account number"

            val amountEditText = view.findViewById<EditText>(R.id.transferFundsAmountEditText)
            val amountString = amountEditText.text.toString()
            var amount = BigDecimal.ZERO
            try {
                amount = BigDecimal(amountString).setScale(2)
            } catch (e: Exception) {

            }
            val validAmount = amount > BigDecimal.ZERO
            if (!validAmount) amountEditText.error = "Invalid amount"

            if (validAccount && validAmount) {
                if (amount > Singleton.getInstance().balance)
                    Toast.makeText(this.context, "Not enough funds to transfer \$$amount", Toast.LENGTH_SHORT).show()
                else {
                    Toast.makeText(this.context, "Transferred \$$amount to account $accountString", Toast.LENGTH_SHORT).show()
                    Singleton.getInstance().balance -= amount
                    findNavController().navigateUp()
                }
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
         * @return A new instance of fragment TransferFundsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TransferFundsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}