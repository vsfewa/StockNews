package com.example.stocknews;

import android.app.Application;
import android.content.Context;
/*
获取全局context

 */
public class AppContext extends Application {

	private static Context instance;

	@Override
	public void onCreate() {
		super.onCreate();
		instance = getApplicationContext();
	}

	public static Context getContext()
	{
		return instance;
	}

}