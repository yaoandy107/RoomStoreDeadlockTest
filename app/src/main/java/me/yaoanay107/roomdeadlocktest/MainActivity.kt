package me.yaoanay107.roomdeadlocktest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.yaoanay107.roomdeadlocktest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
