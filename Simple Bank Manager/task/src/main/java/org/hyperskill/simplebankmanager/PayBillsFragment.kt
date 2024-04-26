package org.hyperskill.simplebankmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.app.AlertDialog
import java.math.BigDecimal
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PayBillsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PayBillsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var dialog: AlertDialog

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
        return inflater.inflate(R.layout.fragment_pay_bills, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.payBillsShowBillInfoButton).setOnClickListener {
            val code = view.findViewById<EditText>(R.id.payBillsCodeInputEditText).text.toString()
            if (Singleton.getInstance().billInfoMap.containsKey(code)) {
                val bill = Singleton.getInstance().billInfoMap[code]!!
                dialog = AlertDialog.Builder(view.context)
                    .setTitle("Bill info")
                    .setMessage("Name: ${bill.first}\n" +
                            "BillCode: ${bill.second}\n" +
                            String.format(Locale.US, "Amount: $%.2f", bill.third)
                    )
                    .setPositiveButton("Confirm") { _, _ ->
                        val billAmount = BigDecimal(bill.third)
                        if (Singleton.getInstance().balance >= billAmount) {
                            Toast.makeText(view.context, "Payment for bill ${bill.first}, was successful", Toast.LENGTH_SHORT).show()
                            Singleton.getInstance().balance -= billAmount
                        } else {
                            dialog.hide()
                            showErrorAlertDialog(view, "Not enough funds")
                        }
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            } else showErrorAlertDialog(view, "Wrong code")
        }
    }

    private fun showErrorAlertDialog(view: View, message: String) {
        AlertDialog.Builder(view.context)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("Ok") { _, _ -> }
            .show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PayBillsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PayBillsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}