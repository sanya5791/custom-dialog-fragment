package com.akhutornoy.customdialogfragment.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.akhutornoy.customdialogfragment.R
import com.akhutornoy.customdialogfragment.ui.dialog.BaseDialog.Companion.DialogType
import com.akhutornoy.customdialogfragment.ui.dialog.DialogInActivity
import com.akhutornoy.customdialogfragment.ui.dialog.OnDialogButtonYesClick
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity(), OnDialogButtonYesClick {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        initViews()
    }

    private fun initViews() {
        bt_dialog_in_activity.setOnClickListener { showDialog() }
        showDialog()
    }

    private fun showDialog() {
        DialogInActivity.showDialog(
            supportFragmentManager,
            type = DialogType.BUTTONS_YES_NO,
            title = "Custom Title",
            message = "Custom Message",
            titleButtonYes = "Custom Yes",
            titleButtonNo = "Custom No"
        )
    }

    override fun onDialogButtonYesClick() {
        Toast.makeText(this, "Pressed Yes", Toast.LENGTH_SHORT).show()
    }

}

