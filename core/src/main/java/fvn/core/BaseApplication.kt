package fvn.div2.core

import android.app.Application
import fvn.div2.core.di.component.NetComponent

/**
 * Created by nguyen.thanh.cong on 02/08/2017.
 */
abstract class BaseApplication : Application() {
    var netComponent: NetComponent? = null
    override fun onCreate() {
        super.onCreate()
        // specify the full namespace of the component
        // Dagger_xxxx (where xxxx = component name)
//        netComponent = DaggerNetComponent.builder()
//                .appModule(AppModule(this))
//                .netModule(NetModule("https://api.github.com"))
//                .build()
    }
}
