package app.skinlog

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import app.skinlog.shared.di.provideSharedModules
import org.koin.core.logger.Level

class SkinApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SkinApp)
            printLogger(Level.DEBUG)
            modules(provideSharedModules())
        }
    }
}