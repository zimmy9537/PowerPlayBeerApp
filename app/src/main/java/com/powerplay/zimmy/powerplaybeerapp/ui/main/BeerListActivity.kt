package com.powerplay.zimmy.powerplaybeerapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commitNow
import com.powerplay.zimmy.powerplaybeerapp.databinding.ActivityBeerListBinding
import com.powerplay.zimmy.powerplaybeerapp.ui.main.fragment.BeerListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BeerListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBeerListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeerListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.commitNow {
            replace(
                binding.container.id,
                BeerListFragment()
            )
        }

    }
}