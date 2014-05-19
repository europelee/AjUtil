package org.upnp.gstreamerutil;

import android.util.Log;


public class GstUtilNative
{
	private static final String  TAG = "GstUtilNative";
	private GstMsgListener mGstMsgObserver = null;
	private boolean        mIsInited	   = false;
	private native void nativeInit(); // Initialize native code, build pipeline,
										// etc

	private native void nativeFinalize(); // Destroy pipeline and shutdown
											// native code

	private native void nativePlay(); // Set pipeline to PLAYING

	private native void nativePause(); // Set pipeline to PAUSED

	private native void nativeInputData(byte [] data);
	
	private native void nativeSetRecvLen(int len);
	
	private static native boolean nativeClassInit(); // Initialize native class:
														// cache Method IDs for
														// callbacks
	
	private long native_custom_data; // Native code will use this to keep
										// private data

	/**
	 * 
	* @Title: setRecvLen 
	* @Description: set audio/video file len
	* @param @param len
	* @return void
	* @throws
	 */
	public void setRecvLen(int len)
	{
		nativeSetRecvLen(len);
	}
	
	/**
	 * 
	* @Title: setGstMsgListener 
	* @Description: for android app layer, such as activity can interact with gstreamer
	* @param @param observer
	* @return void
	* @throws
	 */
	public void setGstMsgListener(GstMsgListener observer)
	{
		mGstMsgObserver = observer;
	}
	
	/**
	 * 
	* @Title: inject2Pipe 
	* @Description: inject data into gstreamer pipeline from application
	* @param @param data
	* @return void
	* @throws
	 */
	public void inject2Pipe(byte []data)
	{
		if (true == mIsInited)
		nativeInputData(data);
	}
	
	public void play()
	{
		if (true == mIsInited)		
		nativePlay();
	}
	
	public void pause()
	{
		if (true == mIsInited)
		nativePause();
	}
	
	public void InitGstreamer()
	{

		nativeInit();
		mIsInited = true;
	}
	
	public void FinGstreamer()
	{
		nativeFinalize();
		mIsInited = false;
	}
	
    // Called from native code. This sets the content of the TextView from the UI thread.
    private void setMessage(final String message) {
    	if (null == mGstMsgObserver)
    	{
    		Log.e(TAG, "mGstMsgObserver is null");
    		return;
    	} 
    	mGstMsgObserver.RecvGstMsg(message);
    }
    
    // Called from native code. Native code calls this once it has created its pipeline and
    // the main loop is running, so it is ready to accept commands.
    private void onGStreamerInitialized () {
    	if (null == mGstMsgObserver)
    	{
    		Log.e(TAG, "mGstMsgObserver is null");
    		return;
    	}
    	
    	mGstMsgObserver.CheckGstreamerInited();
    }
    static {
        nativeClassInit();
    }
}
