package com.sample.nasaapodapp.view

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import java.text.SimpleDateFormat
import java.util.*


class CalenderFragment : DialogFragment(),
    DatePickerDialog.OnDateSetListener {

    private lateinit var calendar:Calendar


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Initialize a calendar instance
        calendar = Calendar.getInstance()

        // Get the system current date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Initialize a new date picker dialog and return it
        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            this,
            year,
            month,
            day
        )
        datePickerDialog.datePicker.setMaxDate(System.currentTimeMillis())
        return datePickerDialog
    }


    // When date set and press ok button in date picker dialog
    override fun onDateSet(
        view: DatePicker, year: Int, month: Int, day: Int) {

        val action = CalenderFragmentDirections.actionCalenderFragmentToHomeFragment(formatDate(year,month,day))
        findNavController().navigate(action)

    }

    // Custom method to format date
    private fun formatDate(year:Int, month:Int, day:Int):String{
        // Create a Date variable/object with user chosen date
        calendar.set(year, month, day, 0, 0, 0)
        val chosenDate = calendar.time
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
                println("Date : "+ sdf.format(chosenDate))
        return sdf.format(chosenDate)
    }
}

