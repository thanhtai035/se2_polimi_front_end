package com.se2.students_and_companies.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.recommendation.featureRecommendationModule
import com.google.android.material.color.DynamicColors
import com.google.firebase.FirebaseApp
import com.se2.account_profile.presentation.ui.ui.featureProfileModule
import com.se2.internship_detail.featureInternshipDetailModule
import com.se2.student_application.featureApplicationModule
import com.se2.student_dashboard.featureDashboardModule
import com.se2.students_and_companies.app.authenticationModule
import com.se2.students_and_companies.appModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext
import timber.log.Timber

// Set up modules
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initTimber()
        initDynamicColorScheme()
        FirebaseApp.initializeApp(this)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    }

    private fun initDynamicColorScheme() {
        DynamicColors.applyToActivitiesIfAvailable(this)
    }

    private fun initKoin() {
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@MainApplication)

            modules(appModule)
            modules(authenticationModule)
            modules(featureDashboardModule)
            modules(featureInternshipDetailModule)
            modules(featureProfileModule)
            modules(featureRecommendationModule)
            modules(featureApplicationModule)

        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
