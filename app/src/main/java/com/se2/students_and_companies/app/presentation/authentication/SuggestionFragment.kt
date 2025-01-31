package com.se2.students_and_companies.app.presentation.authentication

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.Dimension
import androidx.annotation.Dimension.Companion.DP
import androidx.annotation.Dimension.Companion.SP
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.se2.students_and_companies.R

class SuggestionFragment: AutocompleteSupportFragment() {
    override fun onViewCreated(view: View, p1: Bundle?) {
        super.onViewCreated(view, p1)
        val editText = view.findViewById<EditText>(com.google.android.libraries.places.R.id.places_autocomplete_search_input)
        editText.setHint("City")
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19f)
        editText.setTextColor(editText.getContext().getColor(R.color.white))
        editText.setHintTextColor(editText.getContext().getColor(R.color.white))
        view.minimumHeight = Dp(150f).value.toInt()
        view.findViewById<ImageView>(com.google.android.libraries.places.R.id.places_autocomplete_search_button)?.visibility = View.GONE
        view.setBackgroundResource(R.drawable.edittext_bg)
    }
}