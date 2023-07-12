package real.erstate.realestateagency_1.data.di
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import real.erstate.realestateagency_1.ui.fragment.about_us.AboutUsViewModel
import real.erstate.realestateagency_1.ui.fragment.about_us.raiting.ReitingViewModel
import real.erstate.realestateagency_1.ui.fragment.add.AddViewModel
import real.erstate.realestateagency_1.ui.fragment.all.AllViewModel
import real.erstate.realestateagency_1.ui.fragment.all.real.AllRealViewModel
import real.erstate.realestateagency_1.ui.fragment.all.real.photo.AllPhotoViewModel
import real.erstate.realestateagency_1.ui.fragment.dashboard.DashboardViewModel
import real.erstate.realestateagency_1.ui.fragment.dashboard.real.SearchRealViewModel
import real.erstate.realestateagency_1.ui.fragment.dashboard.real.photo.SearchphotoViewModel
import real.erstate.realestateagency_1.ui.fragment.filter.FilterViewModel
import real.erstate.realestateagency_1.ui.fragment.home.HomeViewModel
import real.erstate.realestateagency_1.ui.fragment.home.photo.PhotoViewModel
import real.erstate.realestateagency_1.ui.fragment.home.real_estate.RealEstateViewModel
import real.erstate.realestateagency_1.ui.fragment.login.LoginViewModel
import real.erstate.realestateagency_1.ui.fragment.notifications.NotificationsViewModel
import real.erstate.realestateagency_1.ui.fragment.notifications.real.FavRealViewModel
import real.erstate.realestateagency_1.ui.fragment.notifications.real.photo.FavPhotoViewModel
import real.erstate.realestateagency_1.ui.fragment.registration.RegistrationViewModel


val viewModules = module {
    viewModel { AllViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { AddViewModel(get()) }
    viewModel { DashboardViewModel(get()) }
    viewModel { PhotoViewModel(get()) }
     viewModel { LoginViewModel(get()) }
    viewModel { RegistrationViewModel(get()) }
    viewModel { AboutUsViewModel(get()) }
    viewModel { FilterViewModel(get()) }
    viewModel { RealEstateViewModel(get()) }
    viewModel { NotificationsViewModel(get()) }
    viewModel { ReitingViewModel(get()) }
    viewModel { AllRealViewModel(get()) }
    viewModel { AllPhotoViewModel(get()) }
    viewModel { SearchphotoViewModel(get()) }
    viewModel { SearchRealViewModel(get()) }
    viewModel { FavPhotoViewModel(get()) }
    viewModel { FavRealViewModel(get()) }
}
