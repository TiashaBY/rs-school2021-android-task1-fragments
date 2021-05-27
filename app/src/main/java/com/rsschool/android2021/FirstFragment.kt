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
    private var listener : ButtonClickedListener? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ButtonClickedListener
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
        previousResult?.text = "Previous result: ${result.toString()}"

        minTextEdit = view.findViewById(R.id.min_value)
        maxTextEdit = view.findViewById(R.id.max_value)

        generateButton?.setOnClickListener {
            val min by lazy { minTextEdit!!.text.toString().toInt()}
            val max by lazy { maxTextEdit!!.text.toString().toInt()}
            if (isTextEditEmpty(minTextEdit) || isTextEditEmpty(maxTextEdit)) {
                return@setOnClickListener
            }
            if (min > max) {
                showToastOnTop("Min value should be less than max value!")
                return@setOnClickListener
            }
            listener?.onGenerateButtonClicked(min, max)
        }
    }

    private fun isTextEditEmpty(editField: EditText?) : Boolean {
        return if (editField!!.text.toString() == "") {
            showToastOnTop("Fill in both fields!")
            true
        } else {
            false
        }
    }

    private fun showToastOnTop(message: String) {
        var toast = Toast.makeText(
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

    interface ButtonClickedListener {
        fun onGenerateButtonClicked(min: Int, max: Int)
    }
}