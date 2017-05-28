package com.example.administrator.yuekaob;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<DataBean.AppBean> app;
    private XListView mXlist;
    private List<String> mBean;
    private AlertDialog.Builder wifi_dialog;
    private AlertDialog.Builder gx_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mXlist = (XListView) findViewById(R.id.listview);
        initData();
        mXlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isConnect(app.get(i));
            }
        });
    }

    public void isConnect(final DataBean.AppBean data) {
        //网络判断 dialog
        wifi_dialog = new AlertDialog.Builder(this);
        wifi_dialog.setTitle("网络判断");
        wifi_dialog.setSingleChoiceItems(new String[]{"wifi", "手机流量"}, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        isGX(data);
                        break;
                    case 1:
                        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        startActivity(intent);
                        break;
                }
                dialog.dismiss();
            }
        });
        wifi_dialog.create();
        wifi_dialog.show();
    }

    public void isGX(final DataBean.AppBean data) {
        //wifi 网络判断 是否更新程序dialog
        gx_dialog = new AlertDialog.Builder(this);
        gx_dialog.setTitle("网络判断");
        gx_dialog.setMessage("现在检测到版本更新，是否更新？");
        gx_dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        gx_dialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //开始下载
                getData(MainActivity.this, data);
            }
        });
        gx_dialog.create();
        gx_dialog.show();
    }

    public void getData(final Context context, final DataBean.AppBean data) {
        String url = data.getUrl();
        RequestParams requestParams = new RequestParams(url);
        requestParams.setSaveFilePath(Environment.getExternalStorageDirectory() + "/myapp/");
        requestParams.setAutoRename(true);

        x.http().get(requestParams, new Callback.CommonCallback<File>() {
            @Override
            public void onSuccess(File result) {
//                Toast.makeText(ChannelActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.setDataAndType(Uri.fromFile(result),"application/vnd.android.package-archive");
//                startActivity(intent);


                //apk下载完成后，调用系统的安装方法
                Toast.makeText(context, "下载成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(result), "application/vnd.android.package-archive");
                context.startActivity(intent);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            //网络请求开始的时候回调


            public void onStarted() {
            }
            //下载的时候不断回调的方法

            public void onLoading(long total, long current, boolean isDownloading) {
                //当前进度和文件总大小
                Log.i("JAVA", "current：" + current + "，total：" + total);
            }

        });

    }

    private void initData() {
        String path = "http://mapp.qzone.qq.com/cgi-bin/mapp/mapp_subcatelist_qq?yyb_cateid=-10&categoryName=%E8%85%BE%E8%AE%AF%E8%BD%AF%E4%BB%B6&pageNo=1&pageSize=20&type=app&platform=touch&network_type=unknown&resolution=412x732";
        final RequestParams requestParams = new RequestParams(path);

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //   Log.i("=-------------------------------", "onSuccess: "+result);
                String str = result.substring(0, result.length() - 1);
                Gson gson = new Gson();
                DataBean dataBean = gson.fromJson(str, DataBean.class);
                app = dataBean.getApp();
                mBean = new ArrayList<String>();
                for (int i = 0; i <app.size() ; i++) {
                    String name = app.get(i).getName();
                    mBean.add(name);
                }
              mXlist.setAdapter(new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,mBean));
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
    }
