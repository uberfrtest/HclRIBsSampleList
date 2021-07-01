package com.hcl.uberpoc.sample.extensions

import android.app.AlertDialog
import android.view.ViewGroup
import com.hcl.uberpoc.sample.R

fun ViewGroup.showErrorDialog(error: Throwable, onDismiss: () -> Unit) {
    AlertDialog.Builder(context)
        .setTitle(context.getString(R.string.error))
        .setMessage(error.message)
        .setCancelable(false)
        .setPositiveButton(context.getString(android.R.string.ok), null)
        .setOnDismissListener { onDismiss() }
        .show()
}