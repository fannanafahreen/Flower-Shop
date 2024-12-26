package fannana.fahreen.floresdejardin.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import fannana.fahreen.floresdejardin.data.ProductCartDao
import fannana.fahreen.floresdejardin.global.ApplicationScope
import fannana.fahreen.floresdejardin.ui.commons.ProductCart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [ProductCart::class],  version = 2)
abstract class BillingDatabase: RoomDatabase() {

    abstract fun ProductCartDao(): ProductCartDao

    class Callback @Inject constructor(private val songDatabase: Provider<BillingDatabase> , @ApplicationScope private val applicationScope: CoroutineScope) : androidx.room.RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dao = songDatabase.get().ProductCartDao()
            applicationScope.launch {
                //
            }
        }
    }


}