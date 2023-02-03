package com.fcode.myfirstapplication.ui.widget

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.collection.arrayMapOf
import androidx.core.view.iterator
import androidx.fragment.app.DialogFragment
import com.fcode.myfirstapplication.R

class SortByDialog(private val context: Activity, private val listener: OnSortByListener): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val options = arrayOf(
            getString(R.string.relevance),
            getString(R.string.popularity),
            getString(R.string.newest)
        )

        val builder = AlertDialog.Builder(context, R.style.MultiChoiceAlertDialog)
        val view = context.layoutInflater.inflate(R.layout.sort_by_dialog_view, null, false)
        val radioGroup = view.findViewById<RadioGroup>(R.id.radio_group)

        val radioStyle = ContextThemeWrapper(radioGroup.context, R.style.MyRadioButton)

        for (option in options) {
            val radioButton = RadioButton(radioStyle)
            radioButton.text = option
            radioGroup.addView(radioButton)
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            for (child in radioGroup) {
                child as RadioButton
                if (child.id == checkedId) {
                    listener.onSortBySet(child.text.toString())
                    this.dismiss()
                }
            }
        }

        builder.setView(view)
        return builder.create()
    }


    companion object {
        const val TAG = "SortByDialog"
    }

    interface OnSortByListener {
        fun onSortBySet(selection: String)
    }
}