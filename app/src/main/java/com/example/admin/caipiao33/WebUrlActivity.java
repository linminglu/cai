package com.example.admin.caipiao33;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.caipiao33.application.MyApplication;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.FileManager;
import com.socks.library.KLog;

import java.io.File;

/**
 * 网页web
 * 从外部传入Constants.EXTRA_URL  访问的url
 * 从外部传入Constants.EXTRA_TITLE  该页面title
 * Created by lsd on 2016/3/14.
 */
public class WebUrlActivity extends BaseActivity
{

    private WebView webView;
    private ProgressBar mProgressbar;
    private Toolbar mToolbar;
    private String mUrl;
    private String mTitle;
    private View layoutError;
    private TextView tvTitle;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_url);
        setResult(RESULT_CANCELED);

        layoutError = findViewById(R.id.protocol_error_layout);
        findViewById(R.id.protocol_refresh).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                layoutError.setVisibility(View.GONE);
                webView.loadUrl(mUrl);
            }
        });
        //        mUrl = "https://test.doraemoney.com/newCube/SourceTestPage.html";
        mUrl = getIntent().getStringExtra(Constants.EXTRA_URL);
        mTitle = getIntent().getStringExtra(Constants.EXTRA_TITLE);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(mTitle);

        KLog.e(mUrl);
        webView = (WebView) findViewById(R.id.protocol_webView);
        webView.loadUrl(mUrl);
        WebSettings settings = webView.getSettings();
        settings.setAllowFileAccess(true);// 设置允许访问文件数据
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //        settings.setUserAgentString("Android_Lottery");
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        mProgressbar = (ProgressBar) findViewById(R.id.protocol_progressbar);
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                mProgressbar.setVisibility(View.VISIBLE);
                mUrl = url;
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                super.onReceivedError(view, errorCode, description, failingUrl);
                layoutError.setVisibility(View.VISIBLE);
            }
        });

        webView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100)
                {
                    mProgressbar.setVisibility(View.GONE);
                }
                else
                {
                    mProgressbar.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title)
            {
                super.onReceivedTitle(view, title);
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsg)
            {
                mUploadMessage = uploadMsg;
                showOptions();
            }

            // For Android > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)
            {
                mUploadMessage = uploadMsg;
                showOptions();
            }

            // For Android > 5.0支持多张上传
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams)
            {
                mUploadCallbackAboveL = filePathCallback;
                showOptions();
                return true;
            }

            // 配置权限（同样在WebChromeClient中实现）
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback)
            {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

        });
    }

    private Uri fileUri;
    public static final int TYPE_REQUEST_PERMISSION = 3;
    public static final int TYPE_REQUEST_PERMISSION1 = 4;
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_GALLERY = 2;
    /**
     * 图片选择回调
     */
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    public static final int SELECT_PIC_BY_TACK_PHOTO = 100;

    /**
     * 包含拍照和相册选择
     */
    public void showOptions()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setOnCancelListener(new ReOnCancelListener());
        alertDialog.setTitle("选择");
        alertDialog.setItems(R.array.options, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (which == 0)
                {
                    if (Build.VERSION.SDK_INT >= 23)
                    {
                        int check = ContextCompat.checkSelfPermission(WebUrlActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                        if (check == PackageManager.PERMISSION_GRANTED)
                        {
                            //调用相机
                            check = ContextCompat.checkSelfPermission(WebUrlActivity.this, Manifest.permission.CAMERA);
                            if (check == PackageManager.PERMISSION_GRANTED)
                            {
                                toCamera();
                            }
                            else
                            {
                                // 申请CAMERA权限
                                ActivityCompat.requestPermissions(WebUrlActivity.this, new String[]{android.Manifest.permission.CAMERA}, TYPE_REQUEST_PERMISSION);
                            }
                        }
                        else
                        {
                            // 申请WRITE_EXTERNAL_STORAGE权限
                            ActivityCompat.requestPermissions(WebUrlActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, TYPE_REQUEST_PERMISSION1);
                        }
                    }
                    else
                    {
                        toCamera();
                    }
                }
                else
                {
                    Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                    i.addCategory(Intent.CATEGORY_OPENABLE);
                    i.setType("image/*");
                    startActivityForResult(Intent.createChooser(i, "File Chooser"), TYPE_GALLERY);
                }
            }
        });
        alertDialog.show();
    }

    private Uri getOutputMediaFileUri()
    {
        File imgFile = FileManager.getImgFile(MyApplication.getInstance().getApplicationContext());
        if (Build.VERSION.SDK_INT >= 23)
        {
            //改变Uri  com.xykj.customview.fileprovider注意和xml中的一致
            Uri uri = FileProvider.getUriForFile(this, "com.example.admin.caipiao33.fileprovider", imgFile);
            return uri;
        }
        return Uri.fromFile(imgFile);
    }

    private class ReOnCancelListener implements DialogInterface.OnCancelListener
    {

        @Override
        public void onCancel(DialogInterface dialogInterface)
        {
            if (mUploadMessage != null)
            {
                mUploadMessage.onReceiveValue(null);
                mUploadMessage = null;
            }
            if (mUploadCallbackAboveL != null)
            {
                mUploadCallbackAboveL.onReceiveValue(null);
                mUploadCallbackAboveL = null;
            }
        }
    }

    // 请求拍照
    public void toCamera()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用android的相机
        // 创建一个文件保存图片
        fileUri = getOutputMediaFileUri();
        KLog.d("MainActivity", "fileUri=" + fileUri);
        //添加权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, TYPE_CAMERA);
    }

    /**
     * 回调到网页
     *
     * @param isCamera
     * @param uri
     */
    public void onActivityCallBack(boolean isCamera, Uri uri)
    {
        if (isCamera)
        {
            uri = fileUri;
        }

        if (mUploadCallbackAboveL != null)
        {
            Uri[] uris = new Uri[]{uri};
            mUploadCallbackAboveL.onReceiveValue(uris);
            mUploadCallbackAboveL = null;
        }
        else if (mUploadMessage != null)
        {
            mUploadMessage.onReceiveValue(uri);
            mUploadMessage = null;
        }
        else
        {
            Toast.makeText(WebUrlActivity.this, "无法获取数据", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK)
        {
            if (requestCode == TYPE_CAMERA)
            { // 相册选择
                onActivityCallBack(true, null);
            }
            else if (requestCode == TYPE_GALLERY)
            {// 相册选择
                if (data != null)
                {
                    Uri uri = data.getData();
                    if (uri != null)
                    {
                        onActivityCallBack(false, uri);
                    }
                    else
                    {
                        Toast.makeText(WebUrlActivity.this, "获取数据为空", Toast.LENGTH_LONG).show();
                    }
                }
            }

        }
    }

    // 权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == TYPE_REQUEST_PERMISSION1 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            //调用相机
            int check = ContextCompat.checkSelfPermission(WebUrlActivity.this, Manifest.permission.CAMERA);
            if (check == PackageManager.PERMISSION_GRANTED)
            {
                toCamera();
            }
            else
            {
                // 申请CAMERA权限
                ActivityCompat.requestPermissions(WebUrlActivity.this, new String[]{android.Manifest.permission.CAMERA}, TYPE_REQUEST_PERMISSION);
            }
        }
        else if (requestCode == TYPE_REQUEST_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            toCamera();// 到相机
        }
    }
}
