package com.example.testmulti.ui.users

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
import com.example.testmulti.databinding.FragmentUsersBinding

class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val usersViewModel =
            ViewModelProvider(this).get(UsersViewModel::class.java)

        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.editUsername.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Global.username = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing or add any logic you need
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing or add any logic you need
            }
        })

        binding.editPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                Global.password = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing or add any logic you need
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing or add any logic you need
            }
        })

        binding.btnSubmit.setOnClickListener(){
            ApiUtil.api("login", Global.ipAddress, Global.username, Global.password, null)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}