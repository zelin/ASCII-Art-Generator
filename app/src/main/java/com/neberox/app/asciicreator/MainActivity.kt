package com.neberox.app.asciicreator
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.neberox.app.asciicreator.databinding.ActivityMainBinding
import com.neberox.library.asciicreator.ASCIIConverter
import com.neberox.library.asciicreator.utilities.OnBitmapTaskListener
import com.neberox.library.asciicreator.utilities.OnStringTaskListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var converter: ASCIIConverter

    private val SEEK_BAR_STEP = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            fontSeekBar.max = 50
            fontSeekBar.progress = 8
            fontText.text = "Font : ${fontSeekBar.progress + SEEK_BAR_STEP}"
            converter = ASCIIConverter(this@MainActivity, (fontSeekBar.progress + SEEK_BAR_STEP).toFloat())
            converter.setFontSize(18f)
            converter.setReversedLuminance(false)
            converter.setGrayScale(false)

            convertBtn.setOnClickListener {
                handleConvertButtonClick()
            }

            switch1.setOnCheckedChangeListener { _, isChecked ->
                converter.setGrayScale(isChecked)
            }

            switch2.setOnCheckedChangeListener { _, isChecked ->
                converter.setReversedLuminance(isChecked)
            }

            fontSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    converter.setFontSize((progress + SEEK_BAR_STEP).toFloat())
                    fontText.text = "Font : ${seekBar.progress + SEEK_BAR_STEP}"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}

                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
        }
    }

    private fun handleConvertButtonClick() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.test_image)
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                converter.createASCIIImage(bitmap, object : OnBitmapTaskListener {
                    override fun onTaskCompleted(data: Bitmap?) {
                        // Switch to the main thread to update the UI
                        lifecycleScope.launch(Dispatchers.Main) {
                            binding.imageView.setImageBitmap(data)
                        }
                    }
                })

                Log.d("ASCII-GENERATOR", converter.createASCIIString(bitmap))

                // Return the ASCII Bitmap for additional processing, if needed
                converter.createASCIIString(bitmap, object : OnStringTaskListener {
                    override fun onTaskCompleted(data: String?) {
                        data?.let {
                            Log.d("ASCII-GENERATOR", it)
                        }
                    }
                })
            }
        }

        val map: MutableMap<String, Float> = HashMap()
        map[" "] = 1.0f
        map["`"] = 0.95f
        map[","] = 0.9f
        map["-"] = 0.8f
        map["+"] = 0.7f
        map["<"] = 0.65f
        map["o"] = 0.55f
        map["="] = 0.5f
        map["%"] = 0.3f
        map["@"] = 0.0f
    }
}