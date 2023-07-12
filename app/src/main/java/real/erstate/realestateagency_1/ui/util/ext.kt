package real.erstate.realestateagency_1.ui.util

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText

fun showToast(context: Context, message: Any) {
    Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT).show()
}

fun ImageView.loadImage(image: String){
    Glide.with(this).load(image).into(this)
}

@SuppressLint("ClickableViewAccessibility")
fun TextInputEditText.setupRecyclerViewOnFocus(recyclerView: RecyclerView) {
    this.setOnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            recyclerView.visibility = View.VISIBLE
        }
        false
    }

    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.isNullOrEmpty()) {
                recyclerView.visibility = View.GONE
            }
            if (s != null) {
                if (s.isEmpty()){
                    recyclerView.visibility = View.VISIBLE
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    })
}
