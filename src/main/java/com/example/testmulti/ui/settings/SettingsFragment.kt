package com.example.testmulti.ui.settings

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.testmulti.ApiUtil
import com.example.testmulti.Global
import com.example.testmulti.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.editName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Global.newName = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing or add any logic you need
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing or add any logic you need
            }
        })

        binding.btnTrain.setOnClickListener(){
            ApiUtil.api("train", Global.ipAddress, null, null, Global.jwt)
        }

        binding.btnTrainPhoto.setOnClickListener(){
            ApiUtil.api("new/${Global.newName}", Global.ipAddress, null, null, Global.jwt)
        }



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}