package com.rgw3d.collectors;

import android.app.Application;
import android.content.Context;

public class CollectorsInventory extends Application {
	private static CollectorsInventory instance;
 
    public static CollectorsInventory getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
