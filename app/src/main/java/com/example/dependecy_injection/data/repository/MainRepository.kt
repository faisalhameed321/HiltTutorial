package com.example.dependecy_injection.data.repository


import com.example.dependecy_injection.data.model.StatusModel
import com.example.dependecy_injection.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File


class MainRepository {


    suspend fun getImages(): Flow<MutableList<StatusModel>> = flow {

        val imagesList = File(Constants.appPath).walkTopDown().filter { it.isFile }.filter {
            it.extension == Constants.JPG
        }.map {
            StatusModel(it.name, it.absolutePath)
        }.toMutableList()

        emit(imagesList)


    }.flowOn(Dispatchers.IO)

    fun getImage1(): Flow<File> {
        return File(Constants.appPath).walkTopDown().filter { it.isFile }.filter {
            it.extension == Constants.JPG
        }.asFlow()


    }

    fun getVideos1(): Flow<File> {
        return File(Constants.appPath).walkTopDown().filter { it.isFile }.filter {
            it.extension == Constants.MP4
        }.asFlow()


    }

}