package com.dicoding.submissionmade1.core.di

import androidx.room.Room
import com.dicoding.submissionmade1.core.data.source.DataStoreRepository
import com.dicoding.submissionmade1.core.data.source.RemoteRepository
import com.dicoding.submissionmade1.core.data.source.RoomRepository
import com.dicoding.submissionmade1.core.data.source.local.DataStoreDataSource
import com.dicoding.submissionmade1.core.data.source.local.RoomDataSource
import com.dicoding.submissionmade1.core.data.source.local.datastore.SettingsPreferences
import com.dicoding.submissionmade1.core.data.source.local.datastore.dataStore
import com.dicoding.submissionmade1.core.data.source.local.room.EventDatabase
import com.dicoding.submissionmade1.core.data.source.remote.RemoteDataSource
import com.dicoding.submissionmade1.core.data.source.remote.network.ApiService
import com.dicoding.submissionmade1.core.domain.repository.IDataStoreRepository
import com.dicoding.submissionmade1.core.domain.repository.IRemoteRepository
import com.dicoding.submissionmade1.core.domain.repository.IRoomRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory { get<EventDatabase>().favoriteDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("dicoding".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            EventDatabase::class.java, "Event.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val settingsModule = module {
    single {
        SettingsPreferences(androidContext().dataStore)
    }
}

val networkModule = module {
    single {
        val hostname = "event-api.dicoding.dev"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/IP3deCdJNWm0Ae27av8Odv7gpd7Z1jL6dKVGnJDOpDM=")
            .add(hostname, "sha256/K7rZOrXHknnsEhUH8nLL4MZkejquUuIvOIr6tCa0rbo=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://event-api.dicoding.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { RoomDataSource(get()) }
    single { DataStoreDataSource(get()) }
    single { RemoteDataSource(get()) }

    single<IRemoteRepository> { RemoteRepository(get()) }
    single<IRoomRepository> { RoomRepository(get()) }
    single<IDataStoreRepository> { DataStoreRepository(get()) }
}
