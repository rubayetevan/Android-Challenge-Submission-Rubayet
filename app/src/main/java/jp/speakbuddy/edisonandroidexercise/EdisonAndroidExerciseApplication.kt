package jp.speakbuddy.edisonandroidexercise

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.crossfade
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import javax.inject.Inject

@HiltAndroidApp
class EdisonAndroidExerciseApplication : Application(), SingletonImageLoader.Factory {

    @Inject
    lateinit var okHttpClient: OkHttpClient

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return ImageLoader.Builder(context)
            .crossfade(true)
            .components {
                add(
                    OkHttpNetworkFetcherFactory(
                        callFactory = {
                            okHttpClient
                        }
                    )
                )
            }
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, BuildConfig.IMAGE_MEMORY_CACHE_PERCENT)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve(BuildConfig.IMAGE_CACHE_DIRECTORY))
                    .maxSizePercent(BuildConfig.IMAGE_DISK_CACHE_PERCENT)
                    .build()
            }.build()
    }

}
