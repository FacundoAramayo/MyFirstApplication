package com.fcode.myfirstapplication.ui.widget

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.graphics.Color
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.fcode.myfirstapplication.R

class DatePickerDialogCustom(private val listener: OnDateSetListener) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val dayOfMonth: Int = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(
            requireActivity(),
            R.style.SpinnerDatePickerStyle,
            listener,
            year, month, dayOfMonth
        ).also { dialog ->
            val now = System.currentTimeMillis() - 1000
            val twoMonthsAgo = Calendar.getInstance()
            twoMonthsAgo.add(Calendar.MONTH, -2)

            dialog.datePicker.spinnersShown = true
            dialog.datePicker.calendarViewShown = false
            dialog.datePicker.maxDate = now
            dialog.datePicker.minDate = twoMonthsAgo.timeInMillis

            dialog.setOnShowListener {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
            }
        }
    }

    companion object {
        const val TAG = "DatePickerDialogCustom"
    }
}