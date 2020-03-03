package com.muklas.myalarmmanager

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment() : DialogFragment(), DatePickerDialog.OnDateSetListener, Parcelable {
    private var mListener: DialogDateListener? = null

    constructor(parcel: Parcel) : this() {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context != null) {
            mListener = context as DialogDateListener?
        }
    }
    override fun onDetach() {
        super.onDetach()
        if (mListener != null) {
            mListener = null
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val date = calendar.get(Calendar.DATE)
        return DatePickerDialog(activity as Context, this, year, month, date)
    }
    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        mListener?.onDialogDateSet(tag, year, month, dayOfMonth)
    }
    interface DialogDateListener {
        fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DatePickerFragment> {
        override fun createFromParcel(parcel: Parcel): DatePickerFragment {
            return DatePickerFragment(parcel)
        }

        override fun newArray(size: Int): Array<DatePickerFragment?> {
            return arrayOfNulls(size)
        }
    }

}