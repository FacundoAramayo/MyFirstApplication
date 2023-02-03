package com.fcode.myfirstapplication.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fcode.myfirstapplication.databinding.ActivityDetailBinding
import com.fcode.myfirstapplication.ui.fragment.WebContentFragment

class DetailActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val url = intent.getStringExtra(URL_PARAM).orEmpty()

        WebContentFragment.setWebContent(url)
    }

    companion object {
        const val URL_PARAM = "URL_PARAM"
    }
}