package com.akhutornoy.customdialogfragment.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.akhutornoy.customdialogfragment.R
import com.akhutornoy.customdialogfragment.ui.dialog.BaseDialog.Companion.DialogType
import com.akhutornoy.customdialogfragment.ui.dialog.DialogInFragment
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        bt_dialog_in_fragment.setOnClickListener { showDialog() }
    }

    private fun showDialog() {
        DialogInFragment.showDialog(
            targetFragment = this,
            type = DialogType.BUTTONS_YES_NO,
            title = "Dialog Title",
            message = "Dialog Message",
            titleButtonYes = "Yes",
            titleButtonNo = "No"
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == DialogInFragment.REQUEST_CODE) {
            handleDialogResultCode(resultCode)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun handleDialogResultCode(resultCode: Int) {
        when(resultCode) {
            DialogInFragment.RESPONSE_CODE_BUTTON_YES_CLICKED -> showToast("Button Yes clicked")
            DialogInFragment.RESPONSE_CODE_BUTTON_NO_CLICKED -> showToast("Button No clicked")
            else -> throw IllegalArgumentException("Result code $resultCode is NOT recognized")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = MainFragment()
    }

}
