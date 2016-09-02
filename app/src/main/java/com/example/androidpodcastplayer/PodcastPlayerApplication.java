package com.example.androidpodcastplayer;

import android.app.Application;

import com.example.androidpodcastplayer.player.manager.PlaylistManager;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.timber.StethoTree;

import timber.log.Timber;

public class PodcastPlayerApplication extends Application{

    private static PodcastPlayerApplication sApplication;
    private static PlaylistManager sPlaylistManager;


    @Override
    public void onCreate() {
        super.onCreate();

        sApplication = this;
        sPlaylistManager = new PlaylistManager();

        // initialize Stetho
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this)) // enable cli
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this)) // enable chrome dev tools
                .build());

        // enable Timber debugging in debug build
        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    // adding the line number to the tag
                    return super.createStackElementTag(element) + ":" + element.getLineNumber();
                }
            });
            // show logs in the Chrome browser console log via Stetho (works with Timber 3.0.1)
            Timber.plant(new StethoTree());
        }

        // enable Leak Canary
        // LeakCanary.install(this);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        sApplication = null;
        sPlaylistManager = null;
    }

    public static PlaylistManager getsPlaylistManager() {
        return sPlaylistManager;
    }

    public static PodcastPlayerApplication getsApplication() {
        return sApplication;
    }

}
