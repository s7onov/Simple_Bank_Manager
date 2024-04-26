package org.hyperskill.simplebankmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "username"

/**
 * A simple [Fragment] subclass.
 * Use the [UserMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserMenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.userMenuWelcomeTextView).text = "Welcome $username"

        val viewBalanceButton = view.findViewById<Button>(R.id.userMenuViewBalanceButton)
        viewBalanceButton.setOnClickListener {
            findNavController().navigate(R.id.action_userMenuFragment_to_viewBalanceFragment)
        }

        val transferFundsButton = view.findViewById<Button>(R.id.userMenuTransferFundsButton)
        transferFundsButton.setOnClickListener {
            findNavController().navigate(R.id.action_userMenuFragment_to_transferFundsFragment)
        }

        val exchangeCalculatorButton = view.findViewById<Button>(R.id.userMenuExchangeCalculatorButton)
        exchangeCalculatorButton.setOnClickListener {
            findNavController().navigate(R.id.action_userMenuFragment_to_calculateExchangeFragment)
        }

        val payBillsButton = view.findViewById<Button>(R.id.userMenuPayBillsButton)
        payBillsButton.setOnClickListener {
            findNavController().navigate(R.id.action_userMenuFragment_to_payBillsFragment)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserMenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserMenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}