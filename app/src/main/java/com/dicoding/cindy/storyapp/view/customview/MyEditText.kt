package com.dicoding.cindy.storyapp.view.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import com.dicoding.cindy.storyapp.R
import com.google.android.material.textfield.TextInputLayout

class MyEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {
    private var isError: Boolean = false
    private var originalBorderColor: Int = 0

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val text = s.toString()

                when (inputType) {
                    EMAIL -> {
                        emailCheck(text)
                    }

                    PASSWORD -> {
                        passwordCheck(text)
                    }
                }
            }
            override fun afterTextChanged(s: Editable) {}
        })
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val parentLayout = parent.parent as? TextInputLayout
        parentLayout?.let {
            originalBorderColor = it.boxStrokeColor
        }
    }


    private fun emailCheck(text: String){
        if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            error = "Enter a valid email address"
            isError = true
        } else {
            isError = false
        }
        updateBorderColor()
    }

    private fun passwordCheck(text: String){
        isError = if (text.length < 8) {
            setError(context.getString(R.string.warning_minimum_character), null)
            true
        } else {
            false
        }
        updateBorderColor()
    }

    private fun updateBorderColor() {
        val parentLayout = parent.parent as? TextInputLayout
        parentLayout?.let {
            if (isError) it.boxStrokeColor = context.getColor(R.color.md_theme_error)
            else  it.boxStrokeColor = originalBorderColor
        }
    }

    companion object {
        const val EMAIL = 0x00000021
        const val PASSWORD = 0x00000081
    }
}



