package com.example.dependecy_injection.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dependecy_injection.data.model.StatusModel
import com.example.dependecy_injection.databinding.ActivityMainBinding
import com.example.dependecy_injection.ui.adopter.FileAdapter
import com.example.dependecy_injection.ui.viewModel.MainViewModel
import com.example.dependecy_injection.utils.UIState
import com.example.dependecy_injection.utils.hideView
import com.example.dependecy_injection.utils.showView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel: MainViewModel by viewModels()
    private var adapter: FileAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initRecycle()
        setupObserver()


    }

    private fun initRecycle() {
        adapter = FileAdapter()
        with(binding) {
            recycle.hasFixedSize()
            recycle.layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setupObserver() {
        viewModel.fetchImages()
        lifecycleScope.launchWhenStarted {
            viewModel.imagesUiState.collect { result ->
                when (result) {
                    UIState.Loading -> {
                        binding.progress.showView()
                    }
                    is UIState.Success -> {
                        binding.progress.hideView()
                        displayList(result.statusList)
                    }
                }

            }
        }
    }

    private fun displayList(list: List<StatusModel>?) {
        list?.let { filteredList ->
            if (filteredList.isNotEmpty()) {
                binding.recycle.showView()
                binding.empty.hideView()
                adapter?.differ?.submitList(list)
                binding.recycle.adapter = adapter
            } else {
                binding.recycle.hideView()
                binding.empty.showView()
            }
        }

    }

    /*private fun queryVideosFolders() {
        val projection = arrayOf(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val collection = MediaStore.Images.Media.getContentUri(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                MediaStore.VOLUME_EXTERNAL_PRIMARY
            } else {
                "external"
            }
        )

        try {
            contentResolver.query(
                collection,
                projection,
                null,
                null,
                null
            )?.use { cursor ->
                while (cursor.moveToNext()) {
                    try {

                        val folder =
                            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))

                        Log.d("queryVideosFolders:", "queryVideosFolders: $folder")
                    } catch (e: IllegalArgumentException) {
                        continue
                    } catch (e: Exception) {
                        continue
                    }
                }
                cursor.close()
            }

        } catch (e: Exception) {
        }

    }

    private fun getImages() {
        val collection = MediaStore.Images.Media.getContentUri(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                MediaStore.VOLUME_EXTERNAL_PRIMARY
            } else {
                "external"
            }
        )

        val projection = arrayOf(
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
        )


        val query = contentResolver.query(
            collection,
            projection,
            null,
            null,
            null
        )
        query?.use { cursor ->

            while (cursor.moveToNext()) {

                val folder =
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME))

                Log.d("getImages", "getImages: $folder")
            }
        }
    }*/
}