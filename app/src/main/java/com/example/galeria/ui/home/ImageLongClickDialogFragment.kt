package com.example.galeria.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.example.galeria.models.randomImageModel.Image
import com.example.galeria.models.randomImageModel.Urls


class ImageLongClickDialogFragment(obj: Image, viewModel:HomeViewModel): DialogFragment() {
    private val items = arrayOf<CharSequence>("Add to favourite", "View Online", "Image info")
    private val myClass: Image = obj
    private val vm: HomeViewModel = viewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
                .setItems(items) { _, id ->
                    when (id) {
                        0 -> {
                            val obj = Urls(myClass.urls.full, myClass.urls.full,myClass.urls.raw,
                                myClass.urls.regular,myClass.urls.small,myClass.urls.small_s3)
                            lifecycleScope.launchWhenCreated{
                                vm.postImageToDatabase(obj)
                            }
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
        const val TAG = "HomeFragment_Image_LongClick"
    }
}