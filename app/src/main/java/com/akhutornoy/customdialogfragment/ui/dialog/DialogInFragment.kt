package com.akhutornoy.customdialogfragment.ui.dialog

import android.view.View
import androidx.fragment.app.Fragment
import com.akhutornoy.customdialogfragment.ui.dialog.BaseDialog.Companion.DialogType
import kotlinx.android.synthetic.main.view_custom_dialog.view.*

class DialogInFragment : BaseDialog() {

    override fun initButtonYesListener(view: View) {
        view.bt_yes.setOnClickListener { sendResponseCode(RESPONSE_CODE_BUTTON_YES_CLICKED); dismiss() }
    }

    override fun initButtonNoListener(view: View) {
        view.bt_no.setOnClickListener { sendResponseCode(RESPONSE_CODE_BUTTON_NO_CLICKED); dismiss() }
    }

    private fun sendResponseCode(responseCode: Int) {
        targetFragment?.onActivityResult(targetRequestCode, responseCode, null)
    }

    companion object {
        const val REQUEST_CODE = 101
        const val RESPONSE_CODE_BUTTON_YES_CLICKED = 1
        const val RESPONSE_CODE_BUTTON_NO_CLICKED = 2

        fun showDialog(
            targetFragment: Fragment,
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
            val dialogFragment = DialogInFragment()
            val fragmentManager = targetFragment.fragmentManager ?: throw IllegalArgumentException("Should not be null")

            BaseDialog.showDialog(
                fragmentManager = fragmentManager,
                baseDialog = dialogFragment,
                arguments = args)
        }
    }

}

