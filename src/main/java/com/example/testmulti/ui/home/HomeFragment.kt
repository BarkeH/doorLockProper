package com.example.testmulti.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.testmulti.ApiUtil.api
import com.example.testmulti.ApiUtil.imageApi
import com.example.testmulti.Global
import com.example.testmulti.Global.Companion.ipAddress
import com.example.testmulti.databinding.FragmentHomeBinding
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.editIpAddress.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                ipAddress = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing or add any logic you need
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing or add any logic you need
            }
        })

        binding.btnLock.setOnClickListener(){
            api("lock", ipAddress, null, null, Global.jwt)
        }
        binding.btnUnlock.setOnClickListener() {
            api("unlock", ipAddress, null, null, Global.jwt)
        }

        binding.btnRefresh.setOnClickListener(){
            imageApi(Global.ipAddress,Global.jwt, binding.imgCam)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}