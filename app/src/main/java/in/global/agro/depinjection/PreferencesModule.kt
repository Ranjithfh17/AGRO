package `in`.global.agro.depinjection

import `in`.global.agro.prefernces.MainPreference
import `in`.global.agro.prefernces.SettingsPreference
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {

    @Provides
    @Singleton
    fun provideSettingsPreferences(@ApplicationContext app:Context)=SettingsPreference(app)

    @Provides
    @Singleton
    fun provideMainPreferences(@ApplicationContext app: Context)=MainPreference(app)




}