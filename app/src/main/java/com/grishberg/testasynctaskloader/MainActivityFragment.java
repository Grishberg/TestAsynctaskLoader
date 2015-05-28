package com.grishberg.testasynctaskloader;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.LoaderManager;
import android.widget.Button;

import com.grishberg.testasynctaskloader.data.containers.Result;
import com.grishberg.testasynctaskloader.data.model.GetTextAAsyncModel;
import com.grishberg.testasynctaskloader.data.model.GetTextBAsyncModel;
import com.grishberg.testasynctaskloader.framework.AsyncLoader;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks, AsyncLoader.IUpdateProgress
{

	private static final String TAG = "LoaderTest.Fragment";
	private static final int	TASK_ID_GET_TOPIC_LIST	= 1;
	private static final int	TASK_ID_GET_NEWS_BODY	= 2;

	private boolean	mFirstRun	= true;
	public MainActivityFragment()
	{
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View rootView	= inflater.inflate(R.layout.fragment_main, container, false);
		Button bt = (Button)rootView.findViewById(R.id.btButton);
		bt.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				runNext();
			}
		});

		Log.d(TAG, "on Create View" + ", activity=" + getActivity().toString());

		return rootView;
	}



	public void runNext()
	{
		Bundle bundle	= new Bundle();
		bundle.putInt(AsyncLoader.PARAM_ID, 100);
		Loader loader = getLoaderManager().restartLoader(TASK_ID_GET_TOPIC_LIST, bundle, this);
	}


	// сей костыль нужен из за того, что при повороте onFinishLoaded вызывается 2 раза после поворота,
	// если initLoader запускать в onCreateView или onActivityCreated
	@Override
	public void onResume()
	{
		super.onResume();
		if(mFirstRun)
		{
			mFirstRun = false;
			Bundle bundle	= new Bundle();
			bundle.putInt(AsyncLoader.PARAM_ID, 0);
			Loader loader	= getLoaderManager().initLoader(TASK_ID_GET_TOPIC_LIST, bundle, this);
			if(loader != null)
			{
				((AsyncLoader)loader).setListener(this);
			}

		}
	}
	//---- AsyncTaskCollback

	// событие во время обновления прогресса
	@Override
	public void onProgress(Result progressResult)
	{
		Log.d(TAG, "onProgress"+progressResult+", activity="+getActivity());
	}

	@Override
	public Loader onCreateLoader(int id, Bundle args)
	{
		Loader loader = null;
		switch (id)
		{
			case TASK_ID_GET_TOPIC_LIST:
				loader	=  new GetTextAAsyncModel(getActivity(),args);
				break;
			case TASK_ID_GET_NEWS_BODY:
				loader	=  new GetTextBAsyncModel(getActivity(),args);
				break;
		}
		if(loader != null)
		{
			((AsyncLoader)loader).setListener(this);
			loader.forceLoad();
		}
		return loader;
	}
	
	@Override
	public void onLoadFinished(Loader loader, Object data)
	{
		Result result	= (Result)data;
		Log.d(TAG, "on Load Finished " + result+" activity="+getActivity());
	}

	@Override
	public void onLoaderReset(Loader loader)
	{
		Log.d(TAG, "on Load Reset");
	}
}
