package com.akhutornoy.customdialogfragment.ui.dialog

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.FragmentManager
import com.akhutornoy.customdialogfragment.ui.dialog.BaseDialog.Companion.DialogType
import kotlinx.android.synthetic.main.view_custom_dialog.view.*

class DialogInActivity : BaseDialog() {

    override fun initButtonYesListener(view: View) {
        view.bt_yes.invokeOnClickSafely {
            (activity as OnDialogButtonYesClick).onDialogButtonYesClick()
        }

        dismiss()
    }

    override fun initButtonNoListener(view: View) {
        view.bt_no.invokeOnClickSafely {
            (activity as OnDialogButtonNoClick).onDialogButtonNoClick()
        }

        dismiss()
    }

    private fun Button.invokeOnClickSafely(block: () -> Unit) {
        setOnClickListener { invokeSafely(block) }
    }

    private fun invokeSafely(block: () -> Unit) {
        kotlin.runCatching {
            block.invoke()
        }.onFailure {
            Log.e("invokeSafely", "message: ${it.message}", it)
        }
    }

    companion object {
        /**
         * Calling Activity should implement one of relevant `OnDialogClick`
         * to handle on button clicked callbacks
         */
        fun showDialog(
            fragmentManager: FragmentManager,
            type: DialogType,
            title: String? = null,
            message: String? = null,
            titleButtonYes: String? = null,
            titleButtonNo: String? = null
        ) {

            val args = BaseDialog.getArguments(
                type = type,
                title = title,
                message = message,
                titleButtonYes = titleButtonYes,
                titleButtonNo = titleButtonNo
            )

            val dialogFragment = DialogInActivity()

            BaseDialog.showDialog(
                fragmentManager = fragmentManager,
                baseDialog = dialogFragment,
                arguments = args
            )
        }
    }

}

interface OnDialogClick

interface OnDialogButtonYesClick : OnDialogClick {
    fun onDialogButtonYesClick()
}

interface OnDialogButtonNoClick : OnDialogClick {
    fun onDialogButtonNoClick()
}

interface OnDialogButtonClick : OnDialogButtonYesClick, OnDialogButtonNoClick
