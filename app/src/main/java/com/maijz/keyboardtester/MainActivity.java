package com.maijz.keyboardtester;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.graphics.*;
import javax.crypto.interfaces.*;
import android.webkit.*;
import java.io.*;
import android.content.*;
import android.support.v4.widget.*;
import java.util.*;

public class MainActivity extends Activity 
{

	public String[] default_keys = new String[]{
		"Esc", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "PrtSc", "Scroll Lock", "Pause", "Vol -", "Vol +",
		"`", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "=", "Backspace", "Ins", "Home", "PgUp", "Num Lock", "/", "*", "-",
		"\u21b9 Tab", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "[", "]", "\\", "Del", "End", "PgDn", "7", "8", "9", "+",
		"Caps Lock", "A", "S", "D", "F", "G", "H", "J", "K", "L", ";", "\"", "\u21b5 Enter", "4", "5", "6",
		"\u21e7 Shift", "Z", "X", "C", "V", "B", "N", "M", ",", ".", "/", "\u21e7 Shift", "\u2191", "1", "2", "3", "Enter",
		"Ctrl", "Win", "Alt", "Space", "Alt", "Win", "Menu", "Ctrl", "\u2190", "\u2193", "\u2192", "0", "." };

	// printscreen (44) no keydown
	// menu (93) no keyup
	
	String Keyboard_URL = "file:///android_asset/keyboard.html";
	String APP_URL = "file:///android_asset/app.html";
	String HOME_URL = APP_URL;	
	WebView mWebView;
	TextView tv;
	SwipeRefreshLayout swipeRefresh;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		tv = (TextView) findViewById(R.id.mainTextView);

		
		initWebView();
    }

	@Override
	public boolean dispatchKeyEvent(KeyEvent event)
	{
		if (event.getAction() == KeyEvent.ACTION_DOWN){
			onClickKeyDown(event);
			return true;
		}
		if (event.getAction() == KeyEvent.ACTION_UP)
		{
			onClickKey(event);
			return true;
		}
		return super.dispatchKeyEvent(event);
	};



	@Override
    public boolean onKeyUp(int keyCode, KeyEvent event)
	{

		String keyName = KeyMap.getKeyText(keyCode);
		tv.setText(String.valueOf(keyCode) + " - " + keyName);

		if (event.isMetaPressed())
		{
			tv.setBackgroundColor(Color.BLUE);
		}
		else
		{
			tv.setBackgroundColor(Color.GRAY);
		}

		if (event.isFunctionPressed())
		{
			tv.setBackgroundColor(Color.RED);
		}
		else
		{
			tv.setBackgroundColor(Color.GRAY);
		}

		return super.onKeyUp(keyCode, event);
    }
	
	private void initWebView(){
		WebView myWebView = (WebView) findViewById(R.id.webview);
		//myWebView.loadUrl("http://www.example.com");
		//加载本地assets目录下的网页
		myWebView.loadUrl(HOME_URL);
		//加载手机本地的html页面


		if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //Log.d(TAG, "No SDCARD");
		} else {
			// not work
			//myWebView.loadUrl("file:////storage/BADD-0D0A/KeyboardTester/keyboard.html");
		}

		WebSettings webSettings = myWebView.getSettings();
		//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
		webSettings.setJavaScriptEnabled(true);  
		// 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
		// 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可

		//设置自适应屏幕，两者合用
		webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小 
		webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
		//缩放操作
		webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
		webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
		webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

		//其他细节操作
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存 
		webSettings.setAllowFileAccess(true); //设置可以访问文件 
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口 
		webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
		webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
		//取消滚动条
        myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

		myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");

		setWebViewClient(myWebView);
		//MyWebViewClient myWebViewClient = new MyWebViewClient();
		//myWebView.setWebViewClient(myWebViewClient);

		mWebView = myWebView;


        //SwipeRefresh
        swipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipe_contain);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
				@Override
				public void onRefresh() {
					//重新加载刷新页面
					mWebView.loadUrl(mWebView.getUrl());
				}
			});
        //首次启动刷新页面
        swipeRefresh.post(new Runnable() {
				@Override
				public void run() {
					swipeRefresh.setRefreshing(true);
					mWebView.loadUrl(mWebView.getUrl());
				}
			});
        swipeRefresh.setColorSchemeResources(android.R.color.holo_blue_light,
											 android.R.color.holo_red_light,
											 android.R.color.holo_orange_light,
											 android.R.color.holo_green_light);
	}

	private void onClickKeyDown(KeyEvent keyEvent){
		int jsKeyCode = KeyMap.KeyCodeToKeyCode_JS(keyEvent.getKeyCode());
		jsOnKeyDown(jsKeyCode);	
		
	}
	
	private void onClickKey(KeyEvent keyEvent)
	{
		int keyCode = keyEvent.getKeyCode();
		TextView tv = (TextView) findViewById(R.id.mainTextView);

		String keyName = KeyMap.getKeyText(keyCode);
		tv.setText(String.valueOf(keyCode) + " - " + keyName);
		
		int jsKeyCode = KeyMap.KeyCodeToKeyCode_JS(keyEvent.getKeyCode());
		jsOnKeyUp(jsKeyCode);	
	}


	private void setWebViewClient(WebView webView)
	{
		//设置响应js 的Alert()函数
        //webView.setWebChromeClient(new WebChromeClient() {
	    webView.setWebViewClient(new WebViewClient() {
				@Override
				public void onPageStarted(WebView view, String url, Bitmap favicon)
				{
					super.onPageStarted(view, url, favicon);
					//Log.d("KeithXiaoY","开始加载");
				}

				@Override
				public void onPageFinished(WebView view, String url)
				{
					super.onPageFinished(view, url);
					//Log.d("KeithXiaoY","加载结束");
					
					tv.setText("onPageFinished");
					
					callJavaScript(mWebView, "activeAppMode");
					
					//callJavaScript(mWebView, "hello");
					/*
					mWebView.evaluateJavascript("javascript:hello()", new ValueCallback<String>() {
							@Override
							public void onReceiveValue(String value) {
								//此处为 js 返回的结果
							}
						});
						
					mWebView.post(new Runnable() {
							@Override
							public void run() {
								mWebView.loadUrl("javascript:hello()");
							}
						});
					*/
				}

				// 链接跳转都会走这个方法
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url)
				{
					//Log.d("KeithXiaoY","Url："+ url );
					view.loadUrl(url);// 强制在当前 WebView 中加载 url      
					return true;
				}

				@Override
				public WebResourceResponse shouldInterceptRequest(WebView webView, String url)
				{
					//Log.i("TAG***", "***"+url);
					//String jquery = "jquery_1.11.3.min.js";
					String mimeType =  null;
					
					HashMap<String, String> mimeMap = new HashMap<String, String>();
					mimeMap.put(".js", "text/javascript");
					mimeMap.put(".css", "text/css");
					mimeMap.put(".html",  "text/html");
					mimeMap.put(".txt", "text/plain");

					for (String key : mimeMap.keySet()){
						if (url.toLowerCase().endsWith(key)){
							mimeType = mimeMap.get(key);
						}
					}					
					
					if (mimeType != null){
						//Log.i("TAG***", "***"+url);
						try
						{
							//Log.i("TAG***", "加载本地jquery.js");
							//return new WebResourceResponse("application/x-javascript",
							//							   "utf-8", getAssets().open(jquery));
							return new  WebResourceResponse(mimeType,  "utf-8", getAssets().open(url));
						}
						catch (IOException e)
						{
							e.printStackTrace();
							//Log.i("TAG", "加载本地js错误："+e.toString());
						}
					}
					return super.shouldInterceptRequest(webView, url);
				}
			});
				
				
		webView.setWebChromeClient(new WebChromeClient() {
			
				@Override
				public void onProgressChanged(WebView view, int newProgress) {
					if(newProgress == 100){
						//隐藏进度条
						swipeRefresh.setRefreshing(false);
					}else if(!swipeRefresh.isRefreshing()){
						swipeRefresh.setRefreshing(true);
					}
				}
				
				//设置响应js 的Alert()函数
				@Override
				public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
					AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
					b.setTitle("Alert");
					b.setMessage(message);
					b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								result.confirm();
							}
						});
					b.setCancelable(false);
					b.create().show();
					return true;
				}
				//设置响应js 的Confirm()函数
				@Override
				public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
					AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
					b.setTitle("Confirm");
					b.setMessage(message);
					b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								result.confirm();
							}
						});
					b.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								result.cancel();
							}
						});
					b.create().show();
					return true;
				}
				/*
				//设置响应js 的Prompt()函数
				@Override
				public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
					final View v = View.inflate(MainActivity.this, R.layout.prompt_dialog, null);
					((TextView) v.findViewById(R.id.prompt_message_text)).setText(message);
					((EditText) v.findViewById(R.id.prompt_input_field)).setText(defaultValue);
					AlertDialog.Builder b = new AlertDialog.Builder(TestAlertActivity.this);
					b.setTitle("Prompt");
					b.setView(v);
					b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								String value = ((EditText) v.findViewById(R.id.prompt_input_field)).getText().toString();
								result.confirm(value);
							}
						});
					b.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								result.cancel();
							}
						});
					b.create().show();
					return true;
				}
				*/
				
			});

	}
	
	public void jsOnKeyDown(int keyCode){
		mWebView.loadUrl("javascript:jsOnKeyDown('" + String.valueOf(keyCode) + "');");
	}
	public void jsOnKeyUp(int keyCode){
		mWebView.loadUrl("javascript:jsOnKeyUp('" + String.valueOf(keyCode) + "');");
	}
	
	
	private void callJavaScript(WebView view, String methodName, Object...params){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("javascript:try{");
        stringBuilder.append(methodName);
        stringBuilder.append("(");
        String separator = "";
        for (Object param : params) {               
            stringBuilder.append(separator);
            separator = ",";
            if(param instanceof String){
                stringBuilder.append("'");
            }
			stringBuilder.append(param.toString().replace("'", "\\'"));
            if(param instanceof String){
                stringBuilder.append("'");
            }

        }
        stringBuilder.append(")}catch(error){console.error(error.message);}");
        final String call = stringBuilder.toString();
        //Log.i(TAG, "callJavaScript: call="+call);


        view.loadUrl(call);
    }
}
