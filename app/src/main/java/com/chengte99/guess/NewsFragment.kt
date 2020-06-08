package com.chengte99.guess

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class NewsFragment : Fragment() {
    companion object {
        val instacne: NewsFragment by lazy {
            NewsFragment()
        }

//        private var instance: NewsFragment? = null
//        fun getInstance(): NewsFragment {
//            if (instance == null) {
//                instance = NewsFragment()
//            }
//            return instance as NewsFragment
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }
}