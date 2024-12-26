package fannana.fahreen.floresdejardin.global

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fannana.fahreen.floresdejardin.database.BillingDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    @Singleton
    fun provideDatabase(application: Application, callback: BillingDatabase.Callback)
            = Room.databaseBuilder(application, BillingDatabase::class.java, "order_database")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()



    @Provides
    fun providesOrderDao(billingDatabase: BillingDatabase) =
        billingDatabase.ProductCartDao()


    @ApplicationScope
    @Provides
    @Singleton
    fun providesApplicationScope() = CoroutineScope(SupervisorJob())

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope