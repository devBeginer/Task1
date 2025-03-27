package my.pet.project.task1.di
import androidx.room.Room
import my.pet.project.task1.data.AppDatabase
import my.pet.project.task1.repository.ContactsRepository
import my.pet.project.task1.ui.viewModel.ContactViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "contactsDB")
            .build()
    }
    single {
        get<AppDatabase>().contactDao()
    }
}

val repositoryModule = module {
    single { ContactsRepository(get()) }
}


val viewModelModule = module {
    viewModel {
        ContactViewModel(get())
    }
}