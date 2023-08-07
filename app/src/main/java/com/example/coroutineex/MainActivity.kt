package com.example.coroutineex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.coroutineex.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonCoroutines.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                runDreamCode()
            }
        }

        binding.buttonBlocking.setOnClickListener {
            runBlocking {
                runDreamCode()
            }
        }

        binding.buttonClear.setOnClickListener {
            binding.textView.text = ""
        }

        initProgress()
    }

    suspend fun runDreamCode() {
        val time = measureTimeMillis {
            val one = doSomethingUsefulOne()
            val two = doSomethingUsefulTwo()

            println("The answer is ${one+two}")
        }
        println("Completed in $time ms")
    }

    suspend fun doSomethingUsefulOne() : Int {
        println("start > doSomethingUsefulOne 1")
        delay(2000L)
        println("end > doSomethingUsefulOne 1")
        return 13
    }

    suspend fun doSomethingUsefulTwo() : Int {
        println("start > doSomethingUsefulTwo 2")
        delay(2000L)
        println("end > doSomethingUsefulTwo 2")
        return 29
    }

    fun <T>println(msg: T) {
        val log = "$msg [${Thread.currentThread().name}]"
        runOnUiThread {
            binding.textView.append("\n$log")
        }
        Log.d("TEST", log)
    }

    fun initProgress() {
        binding.progressBar1.max = 100
        binding.progressBar2.max = 200
        binding.progressBar3.max = 300

        GlobalScope.launch {
            while (isActive) {
                delay(10)

                var progress = binding.progressBar1.progress+1
                if (progress > binding.progressBar1.max) {progress = 0}
                binding.progressBar1.progress = progress

                progress = binding.progressBar2.progress+1
                if (progress > binding.progressBar2.max) {progress = 0}
                binding.progressBar2.progress = progress

                progress = binding.progressBar3.progress+1
                if (progress > binding.progressBar3.max) {progress = 0}
                binding.progressBar3.progress = progress

            }
        }

    }



}