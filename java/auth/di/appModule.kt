package auth.di

import auth.remote.service.HttpService
import auth.remote.service.impl.HttpServiceImpl
import auth.ui.screens.login.LoginViewModel
import auth.ui.screens.register.RegistrationViewModel
import com.example.fatcatproject.account.AccountViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module


val networkModule = module {
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }
        }
    }

    single { HttpServiceImpl(get(), get()) } bind HttpService::class
}

val viewModelModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegistrationViewModel(get()) }
    viewModel { AccountViewModel(get()) }
//    viewModel { PublicationViewModel(get(), get()) }
}

//val repositoryModule = module {
//    single { PublicationRepository(get(), get()) }
//}
//
//val databaseModule = module {
//    single {
//        Room.databaseBuilder(get(), AppDatabase::class.java, "KFD.db")
//            .allowMainThreadQueries()
//            .build()
//    }
//}


val appModule = module {

} + networkModule + viewModelModule
