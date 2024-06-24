package com.example.myneighborhoodv2.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import com.example.myneighborhoodv2.util.Constants.INTRODUCTION_SP
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


//    @GlideModule
//    public class MyAppGlideModule extends AppGlideModule {
//
//        @Override
//        public void registerComponents(Context context, Glide glide, Registry registry) {
//            // Register FirebaseImageLoader to handle StorageReference
//            registry.append(StorageReference.class, InputStream.class,
//            new FirebaseImageLoader.Factory());
//        }
//    }
@Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestoreDatabase() = Firebase.firestore

    @Provides
    fun provideIntroductionSP(
        application : Application
    ) = application.getSharedPreferences(INTRODUCTION_SP, MODE_PRIVATE)

//    @Provides
//    @Singleton
//    fun provideFirebaseCommon(
//        firebaseAuth: FirebaseAuth,
//        firestore: FirebaseFirestore
//    ) = FirebaseCommon(firestore,firebaseAuth)

    @Provides
    @Singleton
    fun provideStorage() = FirebaseStorage.getInstance().reference

}