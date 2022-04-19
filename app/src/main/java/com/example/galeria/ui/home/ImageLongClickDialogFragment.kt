package com.example.galeria.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.galeria.models.randomImageModel.Image


class ImageLongClickDialogFragment(obj: Image): DialogFragment() {
    private val items = arrayOf<CharSequence>("Add to favourite", "View Online", "Image info")
    private val myClass: Image = obj
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
                .setItems(items) { _, id ->
                    when (id) {
                        0 -> {
                            print("x == 1")
                        }
                        1 -> {
                            val browserIntent =
                                Intent(Intent.ACTION_VIEW, Uri.parse(myClass.links.html))
                            startActivity(browserIntent)
                        }
                        2 -> {
                            print("x == 2")
                        }
                    }
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
    companion object {
        const val TAG = "PurchaseConfirmationDialog"
    }
}