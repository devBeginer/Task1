package my.pet.project.task1

import android.app.Application
import my.pet.project.task1.di.databaseModule
import my.pet.project.task1.di.repositoryModule
import my.pet.project.task1.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidContext(this@App)
            modules(viewModelModule, databaseModule, repositoryModule)
        }
    }
}