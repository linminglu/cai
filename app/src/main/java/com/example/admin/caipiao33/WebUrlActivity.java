package com.example.admin.caipiao33;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.admin.caipiao33.application.MyApplication;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.bean.GouCaiBean;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.FileManager;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.views.WebviewProgressBar;
import com.socks.library.KLog;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 网页web
 * 从外部传入Constants.EXTRA_URL  访问的url
 * 从外部传入Constants.EXTRA_TITLE  该页面title
 * 可选：
 * Constants.EXTRA_PLAY_NAME 该页面对应的玩法名称
 * Created by lsd on 2016/3/14.
 */
public class WebUrlActivity extends BaseActivity implements View.OnClickListener, Toolbar.OnMenuItemClickListener
{

    private WebView webView;
    private WebviewProgressBar mProgressbar;
    private Toolbar mToolbar;
    private String mUrl;
    private String mTitle;
    private View layoutError;
    private TextView tvTitle;
    private View errorRefreshView;
    // 标记是否页面已经出错了
    private boolean isReceivedError;
    private TextView tvName;
    private View layoutBottom;
    private boolean isShowMenu;
    private String mPlayName;
    public GouCaiBean mGouCaiBean;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_url);
        setResult(RESULT_CANCELED);

        layoutError = findViewById(R.id.protocol_error_layout);
        errorRefreshView = findViewById(R.id.protocol_refresh);
        errorRefreshView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                errorRefreshView.setEnabled(false);
                webView.loadUrl(mUrl);
            }
        });
        //        mUrl = "https://test.doraemoney.com/newCube/SourceTestPage.html";
        mUrl = getIntent().getStringExtra(Constants.EXTRA_URL);
        mTitle = getIntent().getStringExtra(Constants.EXTRA_TITLE);
        mPlayName = getIntent().getStringExtra(Constants.EXTRA_PLAY_NAME);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(mTitle);

        tvName = (TextView) findViewById(R.id.tv_name);
        layoutBottom = findViewById(R.id.layout_bottom);
        findViewById(R.id.tv_again).setOnClickListener(this);

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
        mProgressbar = new WebviewProgressBar((ProgressBar) findViewById(R.id.protocol_progressbar));
        webView.setWebViewClient(new WebViewClient()
        {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                mUrl = url;
                if (url.contains("platformapi/startApp"))
                {
                    startAlipayActivity(url);
                    // android  6.0 两种方式获取intent都可以跳转支付宝成功,7.1测试不成功
                }
                else if ((Build.VERSION.SDK_INT > Build.VERSION_CODES.M) && (url.contains("platformapi") && url
                        .contains("startApp")))
                {
                    startAlipayActivity(url);
                }
                else
                {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                super.onReceivedError(view, errorCode, description, failingUrl);
                layoutError.setVisibility(View.VISIBLE);
                isReceivedError = true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error)
            {
                handler.proceed();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                isReceivedError = false;
                mProgressbar.onProgressStart();
            }
        });

        webView.setWebChromeClient(new WebChromeClient()
        {

            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                // 页面已经销毁，进度后面达到
                if (null != mProgressbar)
                {
                    mProgressbar.onProgressChange(newProgress);
                }
                if (newProgress == 100)
                {
                    errorRefreshView.setEnabled(true);
                    if (!isReceivedError)
                    {
                        layoutError.setVisibility(View.GONE);
                    }
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

        if (getString(R.string.s_trend).equals(mTitle))
        {
            isShowMenu = true;
            // 右上角显示彩种，底部显示再来一注
            layoutBottom.setVisibility(View.VISIBLE);
            tvName.setText(mPlayName);
            mToolbar.setOnMenuItemClickListener(this);

            HttpUtil.requestFirst("hall", GouCaiBean.class, this, new MyResponseListener<GouCaiBean>()
            {

                @Override
                public void onSuccess(GouCaiBean result)
                {
                    if (result != null)
                    {
                        mGouCaiBean = result;
                        names = new ArrayList<>();
                        for (int i = 0; i < mGouCaiBean.getAll().size(); i++)
                        {
                            names.add(mGouCaiBean.getAll().get(i).getName());
                        }
                    }
                }

                @Override
                public void onFailed(int code, String msg)
                {
                    ToastUtil.show(msg);
                }

                @Override
                public void onFinish()
                {

                }
            }, null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if (isShowMenu)
        {
            getMenuInflater().inflate(R.menu.menu_web, menu);
        }
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_more: // 彩种
                showMoreDialog();
                break;
            default:
                break;
        }
        return true;
    }

    private ArrayList<String> names;

    private void showMoreDialog()
    {
        if (null == mGouCaiBean && null == names)
        {
            return;
        }

        new MaterialDialog.Builder(this).title("玩法选择")
                .items(names)
                .positiveText(R.string.dialog_ok)
                .itemsCallbackSingleChoice(-1, new MaterialDialog.ListCallbackSingleChoice()
                {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text)
                    {
                        if (which != -1)
                        {
                            webView.loadUrl(HttpUtil.mNewUrl + "/api/trend?gid=" + mGouCaiBean.getAll()
                                    .get(which)
                                    .getNum());
                            mPlayName = mGouCaiBean.getAll().get(which).getName();
                            tvName.setText(mPlayName);
                        }
                        return true;
                    }
                })
                .show();
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

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_again:
                showLoadingDialog();
                int i = mUrl.indexOf("gid=");
                String mGameId = mUrl.substring(i + 4, mUrl.length());
                HashMap<String, String> map = new HashMap<>();
                map.put("gid", mGameId);
                HttpUtil.requestFirst("buy", map, BuyRoomBean.class, WebUrlActivity.this, new MyResponseListener<BuyRoomBean>()
                {
                    @Override
                    public void onSuccess(BuyRoomBean result)
                    {
                        /**
                         * 两种情况
                         * room
                         *  -- 显示房间列表，再次选择一项进入page页面
                         * page
                         *  -- 立即购买页面
                         */

                        if (result.getPage().equals("room"))
                        {
                            Intent intent = new Intent(WebUrlActivity.this, BuyRoomActivity.class);
                            intent.putExtra(Constants.EXTRA_TITLE, mPlayName);
                            intent.putExtra(Constants.EXTRA_BUY_ROOM_BEAN, result);
                            startActivity(intent);
                        }
                        else
                        {
                            String roomId = result.getRoomId();
                            String playId = null;
                            String playId1 = null;
                            List<BuyRoomBean.PlayListBean> playList = result.getPlayList();
                            if (null != playList && playList.size() > 0)
                            {
                                BuyRoomBean.PlayListBean playListBean = playList.get(0);
                                playId = playListBean.getPlayId();
                                playId1 = playListBean.getPlayId1();
                            }
                            Intent intent = new Intent(WebUrlActivity.this, BuyActivity.class);
                            intent.putExtra(Constants.EXTRA_TITLE, mPlayName);
                            intent.putExtra(Constants.EXTRA_NUMBER, result.getNum());
                            intent.putExtra(Constants.EXTRA_ROOM_ID, roomId);
                            intent.putExtra(Constants.EXTRA_PLAY_ID, playId);
                            intent.putExtra(Constants.EXTRA_PLAY_ID1, playId1);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg)
                    {
                        ToastUtil.show(msg);
                    }

                    @Override
                    public void onFinish()
                    {
                        hideLoadingDialog();
                    }
                }, null);
                break;
            default:
                break;
        }
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

    @Override
    public void finish()
    {
        super.finish();
        if (mProgressbar != null)
        {
            mProgressbar.destroy();
            mProgressbar = null;
        }
        if (null != webView)
        {
            webView.setWebChromeClient(null);
            webView.setWebViewClient(null);
            webView = null;
        }
    }

    private void startAlipayActivity(String url)
    {
        Intent intent;
        try
        {
            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
            intent.addCategory(Intent.CATEGORY_BROWSABLE);
            intent.setComponent(null);
            startActivity(intent);
            finish();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
