package com.grishberg.testasynctaskloader.data.model;

import android.util.Log;

import com.grishberg.testasynctaskloader.data.containers.Result;

import java.util.concurrent.TimeUnit;

/**
 * Created by G on 28.05.15.
 */
public class MyDbHelper
{
	private static final String TAG = "LoaderTest.DbHelper";

	public MyDbHelper()
	{

	}

	public Result getA(int param)
	{
		int i = 0;
		for(i = param; i < 20+param; i++)
		{
			try
			{
				Log.d(TAG,"getA "+i);
				Thread.sleep(1000);

			} catch (Exception e)
			{
				Log.d(TAG,"getA "+e.toString());
			}
		}
		return new Result("A"+Integer.toString(i));
	}

	public Result getB(int param)
	{
		int i = 0;
		for(i = param; i < 20 + param; i++)
		{
			try
			{
				Thread.sleep(1000);

			} catch (Exception e)
			{
				Log.d(TAG,"getB "+e.toString());
			}
		}
		return new Result("B"+Integer.toString(i));
	}
}
