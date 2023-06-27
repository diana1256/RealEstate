package real.erstate.realestateagency_1.data.di


import org.koin.dsl.module
import real.erstate.realestateagency_1.data.repository.Repository


val repoModules = module {
    single { Repository(get()) }
}