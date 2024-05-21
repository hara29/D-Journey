package com.dicoding.cindy.storyapp.view.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText

class MyEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs) {
    private var isError: Boolean = false
    private val errorBorderColor: Int = Color.RED
    private val defaultBorderColor: Int = Color.BLACK

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        background = if (isError) {
            getDrawableWithBorder(errorBorderColor, 4)
        } else {
            getDrawableWithBorder(defaultBorderColor, 2)
        }
    }

    init {
        // clearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_close_24) as Drawable
        // setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
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


    private fun emailCheck(text: String){
        if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
            error = "Enter a valid email address"
            isError = true
        } else {
            isError = false
        }
        invalidate()
    }

    private fun passwordCheck(text: String){
        isError = if (text.length < 8) {
            setError("this field can't be less than 8 character", null)
            true
        } else {
            false
        }
        invalidate()
    }

    private fun getDrawableWithBorder(borderColor: Int, width: Int): Drawable {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.setStroke(width, borderColor)
        shape.setColor(Color.TRANSPARENT)
        return shape
    }

    companion object {
        const val EMAIL = 0x00000021
        const val PASSWORD = 0x00000081
    }
}



