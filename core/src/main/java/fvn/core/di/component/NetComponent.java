package fvn.core.di.component;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import fvn.core.di.module.AppModule;
import fvn.core.di.module.NetModule;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    // downstream components need these exposed
    Retrofit retrofit();
    OkHttpClient okHttpClient();
    SharedPreferences sharedPreferences();
}