package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment


class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null
    private var minTextEdit: EditText? = null
    private var maxTextEdit: EditText? = null
    private var listener : FragmentCommunicationListener? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as FragmentCommunicationListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = getString(R.string.result_label, result.toString())

        minTextEdit = view.findViewById(R.id.min_value)
        maxTextEdit = view.findViewById(R.id.max_value)

        generateButton?.setOnClickListener {
            if (isTextEditEmpty(minTextEdit) || isTextEditEmpty(maxTextEdit)) {
                return@setOnClickListener
            }
            val min = minTextEdit!!.text.toString().toInt()
            val max = maxTextEdit!!.text.toString().toInt()
            if (min > max) {
                showToastOnTop(getString(R.string.value_error))
                return@setOnClickListener
            }
            listener?.onGenerateButtonClicked(min, max)
        }
    }

    private fun isTextEditEmpty(editField: EditText?) : Boolean {
        return if (editField!!.text.toString().isEmpty()) {
            showToastOnTop(getString(R.string.empty_field_error))
            true
        } else {
            false
        }
    }

    private fun showToastOnTop(message: String) {
        val toast = Toast.makeText(
            context, message, Toast.LENGTH_SHORT
        )
        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 100)
        toast.show()
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}