package real.erstate.realestateagency_1.ui.activity.main

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import real.erstate.realestateagency_1.R
import real.erstate.realestateagency_1.databinding.ActivityMainBinding
import real.erstate.realestateagency_1.ui.fragment.registration.OnRegistrationListener
import real.estate.realestateagency1.ui.util.Pref

class MainActivity : AppCompatActivity() , OnRegistrationListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


        val navView: BottomNavigationView = binding.navView


        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )

        if (!Pref(applicationContext).isBoardingShowed()){
            navController.navigate(R.id.loginFragment)
        } else{
            navController.navigateUp()
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment || destination.id == R.id.registrationFragment) {
                navView.visibility = View.GONE
            } else {
                navView.visibility = View.VISIBLE
            }
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onRegistrationStatusChanged(isAdmin: String) {
        binding.navView.menu.findItem(R.id.navigation_add)?.isVisible = (isAdmin == "admin")
    }
}