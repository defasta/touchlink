package apps.eduraya.e_parking.di

import android.content.Context
import apps.eduraya.e_parking.data.network.auth.Api
import apps.eduraya.e_parking.data.network.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Singleton
//    @Provides
//    fun provideAuthApi(
//        remoteDataSource: RemoteDataSource,
//        @ApplicationContext context: Context
//    ): AuthApi {
//        return remoteDataSource.buildTokenApi(AuthApi::class.java, context)
//    }

    @Singleton
    @Provides
    fun provideAuthApi(
        remoteDataSource: RemoteDataSource,
        @ApplicationContext context: Context
    ): Api {
        return remoteDataSource.buildApi(Api::class.java, context)
    }
}