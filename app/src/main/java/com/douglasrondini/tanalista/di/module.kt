package com.douglasrondini.tanalista.di

import androidx.room.Room
import com.douglasrondini.tanalista.data.local.AppDatabase
import com.douglasrondini.tanalista.data.repositoy.ItemRepositoryImpl
import com.douglasrondini.tanalista.domain.repository.ItemRepository
import com.douglasrondini.tanalista.domain.usecases.DeletItemUseCase
import com.douglasrondini.tanalista.domain.usecases.GetAllItensUseCase
import com.douglasrondini.tanalista.domain.usecases.GetItemByCategoryUseCase
import com.douglasrondini.tanalista.domain.usecases.InsertItemUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val databaseModule = module {
        single {
            Room.databaseBuilder(
                androidContext(),
                AppDatabase::class.java,
                "app_database"
            ).build()
        }
        single { get<AppDatabase>().itemDao() }
    }

val  repositoryModule = module {
    single<ItemRepository> { ItemRepositoryImpl(get()) }
}

val useCaseModule = module {
    factory { GetItemByCategoryUseCase(get()) }
    factory { InsertItemUseCase(get()) }
    factory { DeletItemUseCase(get()) }
    factory { GetAllItensUseCase(get()) }
}

val viewModelModule = module {

}

val appModules = listOf(
    databaseModule,
    repositoryModule,
    useCaseModule,
    viewModelModule
)
