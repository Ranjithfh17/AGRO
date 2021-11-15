package `in`.global.agro.depinjection

import `in`.global.agro.api.AgroApi
import `in`.global.agro.utils.Constants.BASE_URl
import `in`.global.agro.utils.Constants.URl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URl)
            .addConverterFactory(MoshiConverterFactory.create().asLenient())
            .build()
    }



    @Singleton
    @Provides
    fun provideAgroApi(retrofit: Retrofit): AgroApi =
        retrofit.create(AgroApi::class.java)




}