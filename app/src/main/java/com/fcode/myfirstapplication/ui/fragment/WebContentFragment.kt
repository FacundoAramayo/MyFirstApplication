package com.fcode.myfirstapplication.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.fcode.myfirstapplication.databinding.FragmentWebContentBinding

class WebContentFragment: Fragment() {

    private lateinit var viewBinding: FragmentWebContentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentWebContentBinding.inflate(inflater, container, false)

        setupWebView()

        webContentUrl.observe(viewLifecycleOwner, Observer {
            when {
                it.isNotEmpty() -> {
                    viewBinding.webView.loadUrl(it)
                }
            }
        })

        return viewBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        isAvailable = true
    }

    override fun onDetach() {
        super.onDetach()
        isAvailable = false
    }

    private fun setupWebView() {
        viewBinding.webView.apply {
            settings.javaScriptEnabled = false
            settings.builtInZoomControls = true
            webViewClient = WebViewClient()
        }
    }


    companion object {
        private var webContentUrl = MutableLiveData<String>()
        private var isAvailable = false

        fun setWebContent(url: String) {
            webContentUrl.postValue(url)
        }

        fun isAvailable(): Boolean {
            return isAvailable
        }
    }
}