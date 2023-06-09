package com.example.nanopost.data.module

import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.webkit.MimeTypeMap
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    private const val IMAGES_DIR_NAME = "images"

    @Provides
    fun provideContentResolver(
        @ApplicationContext context: Context,
    ): ContentResolver {
        return context.contentResolver
    }

    @Provides
    @Singleton
    fun provideMimeTypeMap(): MimeTypeMap {
        return MimeTypeMap.getSingleton()
    }

    @Provides
    @Singleton
    fun provideImagesDir(
        @ApplicationContext context: Context,
    ): File {
        return File(context.filesDir, IMAGES_DIR_NAME)
    }

    @Provides
    @Singleton
    fun provideSharedPref(
        @ApplicationContext appContext: Context
    ): SharedPreferences {
        return appContext.getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
    }
}