package com.newsapp.activities.main.setting

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.newsapp.R
import kotlinx.android.synthetic.main.fragment_setting.*
import java.util.prefs.Preferences

class SettingFragment : Fragment() {

    private lateinit var viewModel: SettingViewModel
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProviders.of(this).get(SettingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_setting, container, false)
        spinnerAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, viewModel.spinnerItems)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sharedPref = activity?.getSharedPreferences("setting", Context.MODE_PRIVATE)!!
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        frequencyNotification.adapter = spinnerAdapter
        val notification = sharedPref.getBoolean("notification", false)
        switchNotification.isChecked = notification

        switchNotification.setOnClickListener {
            saveNotification(switchNotification.isChecked)
        }

        val isAlways = sharedPref.getBoolean("always_screen", false)
        switchAlwaysScreen.isChecked = isAlways

        switchAlwaysScreen.setOnClickListener {
            saveAwaysScreen(switchAlwaysScreen.isChecked)
        }
    }


    private fun saveAwaysScreen(value: Boolean) {
        sharedPref.edit().putBoolean("always_screen", value).apply()
    }

    private fun saveNotification(value: Boolean) {
        sharedPref.edit().putBoolean("notification", value).apply()
    }
}