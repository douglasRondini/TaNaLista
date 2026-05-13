package com.douglasrondini.tanalista.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.douglasrondini.tanalista.data.local.AppDatabase
import com.douglasrondini.tanalista.data.local.CategoryEntity
import com.douglasrondini.tanalista.data.repositoy.CategoryRepositoryImpl
import com.douglasrondini.tanalista.data.repositoy.ItemRepositoryImpl
import com.douglasrondini.tanalista.domain.repository.CategoryRepository
import com.douglasrondini.tanalista.domain.repository.ItemRepository
import com.douglasrondini.tanalista.domain.usecases.DeletItemUseCase
import com.douglasrondini.tanalista.domain.usecases.GetAllCategoriesUseCase
import com.douglasrondini.tanalista.domain.usecases.GetAllItensUseCase
import com.douglasrondini.tanalista.domain.usecases.GetItemByCategoryUseCase
import com.douglasrondini.tanalista.domain.usecases.InsertCategoryUseCase
import com.douglasrondini.tanalista.domain.usecases.InsertItemUseCase
import com.douglasrondini.tanalista.domain.usecases.UpdateItemUseCase
import com.douglasrondini.tanalista.ui.home.HomeViewModel
import com.douglasrondini.tanalista.ui.register.ProductRegistrationViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val databaseModule = module {
        single {
            Room.databaseBuilder(
                androidContext(),
                AppDatabase::class.java,
                "app_database"
            )
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)

                        CoroutineScope(Dispatchers.IO).launch {
                            val instance = Room.databaseBuilder(
                                androidContext(),
                                AppDatabase::class.java,
                                "app_database"
                            ).build()
                            instance.categoryDao().apply {
                                insertCategory(CategoryEntity(name = "Alimentos"))
                                insertCategory(CategoryEntity(name = "Limpeza"))
                                insertCategory(CategoryEntity(name = "Higiene"))
                            }
                        }
                    }
                })
                .build()
        }
        single { get<AppDatabase>().itemDao() }
        single { get<AppDatabase>().categoryDao() }
    }

val  repositoryModule = module {
    single<ItemRepository> { ItemRepositoryImpl(get()) }
    single<CategoryRepository> { CategoryRepositoryImpl(get()) }
}

val useCaseModule = module {
    // itens lista
    factory { GetItemByCategoryUseCase(get()) }
    factory { InsertItemUseCase(get()) }
    factory { DeletItemUseCase(get()) }
    factory { UpdateItemUseCase(get()) }
    factory { GetAllItensUseCase(get()) }
    // categorias
    factory { GetAllCategoriesUseCase(get()) }
    factory { InsertCategoryUseCase(get()) }
}

val viewModelModule = module {
    viewModel { ProductRegistrationViewModel(get(),get(), get()) }
    viewModel { HomeViewModel(get(),get(),get(),get(),get()) }
}

val appModules = listOf(
    databaseModule,
    repositoryModule,
    useCaseModule,
    viewModelModule
)
