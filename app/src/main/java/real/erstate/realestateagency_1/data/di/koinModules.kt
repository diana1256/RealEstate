package real.erstate.realestateagency_1.data.di

import real.erstate.realestateagency_1.data.local.networkModule
import real.erstate.realestateagency_1.data.repository.remoteDataSourceModule


val koinModules = listOf(
    repoModules,
    viewModules,
    networkModule,
    remoteDataSourceModule
)