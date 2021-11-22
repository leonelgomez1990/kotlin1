package com.lfg.homemarket.clases;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.module.AppGlideModule;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;

import okhttp3.OkHttpClient;

/**
 * Glide module to register {@link com.firebase.ui.storage.images.FirebaseImageLoader}.
 * See: http://bumptech.github.io/glide/doc/generatedapi.html
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {

    @Override
    public void registerComponents(@NonNull Context context,
                                   @NonNull Glide glide,
                                   @NonNull Registry registry) {
        OkHttpClient client = (new OkHttpClient.Builder()).readTimeout(3L, TimeUnit.SECONDS).connectTimeout(3L, TimeUnit.SECONDS).build();
        OkHttpUrlLoader.Factory factory = new OkHttpUrlLoader.Factory((okhttp3.Call.Factory)client);
        glide.getRegistry().replace(GlideUrl.class, InputStream.class, (ModelLoaderFactory)factory);

        // Register FirebaseImageLoader to handle StorageReference
        registry.append(StorageReference.class, InputStream.class,
                new FirebaseImageLoader.Factory());
    }

}