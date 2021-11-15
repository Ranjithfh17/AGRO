package `in`.global.agro.depinjection

import `in`.global.agro.room.AppDatabase
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideSalesDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, AppDatabase::class.java, "agro")
            .build()


    @Singleton
    @Provides
    fun provideProductDao(appDatabase: AppDatabase) = appDatabase.salesDao()


    @Singleton
    @Provides
    fun providePurchaseDao(appDatabase: AppDatabase) = appDatabase.purchaseDao()


    @Singleton
    @Provides
    fun provideReceiptDao(appDatabase: AppDatabase) = appDatabase.receiptDao()


    @Singleton
    @Provides
    fun provideProductsDao(appDatabase: AppDatabase) = appDatabase.productSDao()


    @Singleton
    @Provides
    fun provideCustomerBalanceDao(appDatabase: AppDatabase)= appDatabase.customerBalanceDao()


}