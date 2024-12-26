package fannana.fahreen.floresdejardin.global

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fannana.fahreen.floresdejardin.preferences.PreferenceImpl
import fannana.fahreen.floresdejardin.preferences.PreferenceStorage

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {

    @Binds
    abstract fun bindsPreferenceStorage(preferenceStorageImpl: PreferenceImpl): PreferenceStorage

}