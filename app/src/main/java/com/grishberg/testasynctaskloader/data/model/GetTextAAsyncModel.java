package com.grishberg.testasynctaskloader.data.model;

import android.content.Context;
import android.os.Bundle;

import com.grishberg.testasynctaskloader.data.containers.Result;
import com.grishberg.testasynctaskloader.framework.AsyncLoader;

/**
 * Created by G on 28.05.15.
 */
public class GetTextAAsyncModel extends AsyncLoader
{
	private int mId	= 0;

	public GetTextAAsyncModel(Context context, Bundle args)
	{
		super(context, args);

		//параметры передаваемые post запросом
		mId	= args.getInt(AsyncLoader.PARAM_ID, 0);
	}

	@Override
	protected Result inBackground()
	{
		Result result	= null;
		updateProgress(new Result("A data from cache 1"));
		result	= mDbHelper.getA(mId);

		updateProgress(new Result("A data from cache 2"));
		return result;
	}

}
