package com.grishberg.testasynctaskloader.framework;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.grishberg.testasynctaskloader.data.containers.Result;
import com.grishberg.testasynctaskloader.data.model.MyDbHelper;

/**
 * Created by G on 28.05.15.
 */
public abstract class AsyncLoader extends AsyncTaskLoader
{
	public static final int	MY_CODE_ERROR	= -1;
	public static final String PARAM_ID		= "paramId";
	public static final String BASE_TAG		= "AsyncLoader";
	public static final int MSGCODE_MESSAGE	= 100;

	// интерфейс для коллбака
	public interface IUpdateProgress {
		public void onProgress(Result progressResult);
	}

	protected MyDbHelper		mDbHelper;
	protected IUpdateProgress 	mOnProgressListener;

	public AsyncLoader(Context context, Bundle args) {
		super(context);
	}

	@Override
	public Result loadInBackground()
	{
		mDbHelper = new MyDbHelper();
		Result result = null;
		try {
				result = inBackground();
		}
		catch (Exception e) {
			e.printStackTrace();
			Log.d(BASE_TAG, e.toString());
			result = new Result(MY_CODE_ERROR);
		}
		return result;
	}

	public void setListener(IUpdateProgress onProgressListener)
	{
		mOnProgressListener	= onProgressListener;
	}

	private final Handler mHandler = new Handler() {

		public void handleMessage(Message msg) {

			if(msg.what == AsyncLoader.MSGCODE_MESSAGE){
				Result progressResult = (Result)msg.obj;
				if(progressResult != null) {
					if(mOnProgressListener != null)
					{
						// если была подписка на событие прогресс, выполнить метод
						mOnProgressListener.onProgress( progressResult );
					}
					//onUpdateProgress(progressResult);
				}
			}
		}
	};

	// отправить промежуточный результат в UI поток через Handle
	protected void updateProgress(Result progressResult )
	{
		Message message = mHandler.obtainMessage();
		message.obj = progressResult;
		message.what = MSGCODE_MESSAGE;
		message.sendToTarget();
	}

	// выполняемые действия в фоне
	abstract protected Result inBackground();

	//abstract protected void onUpdateProgress( Result progressResult);
}
