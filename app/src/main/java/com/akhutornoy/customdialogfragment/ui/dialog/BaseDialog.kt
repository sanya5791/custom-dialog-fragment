package com.akhutornoy.customdialogfragment.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.akhutornoy.customdialogfragment.R
import kotlinx.android.synthetic.main.view_custom_dialog.view.*
import java.lang.IllegalArgumentException

abstract class BaseDialog : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val view = inflater.inflate(R.layout.view_custom_dialog, container, false)
        initDialog(view)
        return view
    }

    private fun initDialog(view: View) {
        view.tv_title.setArgument(ARG_TITLE)
        view.tv_message.setArgument(ARG_MESSAGE)
        view.bt_yes.setArgument(ARG_BUTTON_YES_TITLE)
        view.bt_no.setArgument(ARG_BUTTON_NO_TITLE)

        initListeners(view)
    }

    private fun TextView.setArgument(argButtonTitle: String) {
        val title = arguments?.getString(argButtonTitle)

        if (title.isNullOrEmpty()) {
            isVisible = false
        } else {
            text = title
        }
    }

    private fun initListeners(view: View) {
        val type = getType()
        when (type) {
            DialogType.BUTTON_YES -> initButtonYesListener(view)
            DialogType.BUTTON_NO -> initButtonNoListener(view)
            DialogType.BUTTONS_YES_NO -> initButtonsYesNoListener(view)
        }
    }

    private fun initButtonsYesNoListener(view: View) {
        initButtonYesListener(view)
        initButtonNoListener(view)
    }

    protected abstract fun initButtonYesListener(view: View)

    protected abstract fun initButtonNoListener(view: View)

    private fun getType(): DialogType {
        val argTypeString = arguments?.getString(ARG_TYPE)
            ?: throw IllegalArgumentException("Argument $ARG_TYPE should be passed to fragment ${this::class.java.simpleName}")
        return DialogType.valueOf(argTypeString)
    }

    companion object {
        private const val TAG_DIALOG = "TAG_DIALOG"
        private const val ARG_TYPE = "ARG_TYPE"
        private const val ARG_TITLE = "ARG_TITLE"
        private const val ARG_MESSAGE = "ARG_MESSAGE"
        private const val ARG_BUTTON_YES_TITLE = "ARG_BUTTON_YES_TITLE"
        private const val ARG_BUTTON_NO_TITLE = "ARG_BUTTON_NO_TITLE"

        fun showDialog(
            fragmentManager: FragmentManager,
            baseDialog: BaseDialog,
            arguments: Bundle
        ) {

            baseDialog.arguments = arguments

            val ft: FragmentTransaction = fragmentManager.beginTransaction()
            removePreviousDialogIfNeed(fragmentManager, ft)
            baseDialog.show(ft, TAG_DIALOG)
        }

        fun getArguments(
            type: DialogType,
            title: String? = null,
            message: String? = null,
            titleButtonYes: String? = null,
            titleButtonNo: String? = null
        ): Bundle {
            return Bundle().apply {
                putString(ARG_TYPE, type.name)
                putString(ARG_TITLE, title)
                putString(ARG_MESSAGE, message)
                putString(ARG_BUTTON_YES_TITLE, titleButtonYes)
                putString(ARG_BUTTON_NO_TITLE, titleButtonNo)
            }
        }

        private fun removePreviousDialogIfNeed(
            fragmentManager: FragmentManager,
            ft: FragmentTransaction
        ) {
            val prev: Fragment? = fragmentManager.findFragmentByTag(TAG_DIALOG)
            if (prev != null) {
                ft.remove(prev)
            }
        }

        enum class DialogType { BUTTON_YES, BUTTON_NO, BUTTONS_YES_NO }
    }

}

