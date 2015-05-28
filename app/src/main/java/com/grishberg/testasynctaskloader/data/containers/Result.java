package com.grishberg.testasynctaskloader.data.containers;

/**
 * Created by G on 28.05.15.
 */
public class Result
{
	private String	mResultString;
	private int		mError;

	@Override
	public String toString()
	{
		return mResultString;
	}

	public Result(String result)
	{
		mResultString	= result;
		mError			= 0;
	}

	public Result(int error)
	{
		mError			= error;
	}

	public String getmResultString()
	{
		return mResultString;
	}

	public void setmResultString(String mResultString)
	{
		this.mResultString = mResultString;
	}

	public int getmError()
	{
		return mError;
	}

	public void setmError(int mError)
	{
		this.mError = mError;
	}
}
