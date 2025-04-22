package com.example.finalwork;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalwork.adapter.ResultAdapter;
import com.example.finalwork.model.GetDiscernResultResponce;
import com.example.finalwork.model.GetTokenResponse;
import com.example.finalwork.network.ApiService;
import com.example.finalwork.network.NetCallBack;
import com.example.finalwork.network.ServiceGenerator;
import com.example.finalwork.util.Base64Util;
import com.example.finalwork.util.Constant;
import com.example.finalwork.util.FileUtil;
import com.example.finalwork.util.SPUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.tbruyelle.rxpermissions3.RxPermissions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class PictureActivity extends AppCompatActivity {
    private static final String TAG = "PictureActivity";
    private ApiService service;
    private String accessToken;
    private ImageView image;
    Button tp,al;
    private ProgressBar pb;//加载进度条
    private BottomSheetDialog bottomSheetDialog;//底部弹窗
    private View bottomView;
    //private RxPermissions rxPermissions;//动态权限申请
    //读写文件权限申请
    private static final String[] PERMISSIONS_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //相机权限
    private static final String[] PERMISSIONS_CAMERA ={Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //请求状态码
    private  static final int REQUEST_CAMERA=3,REQUEST_EXTERNAL_STORAGE=4;
    private AlertDialog dialog;//对话框
    private static final int CAMERA_CODE =1,ALBUM_CODE=2;//请求码
    private File outputImage;//存储拍照返回的照片
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        //实例化ApiService
        service= ServiceGenerator.createService(ApiService.class);
        //调用函数获取Token
        requestApiGetToken();
        //显示图片的地方
        image=findViewById(R.id.image);
        //进度条
        pb=findViewById(R.id.pb_loading);
        //实例化底部弹窗
        bottomSheetDialog=new BottomSheetDialog(this);
        //加载弹窗布局
        //bottomView=getLayoutInflater().inflate(R.layout.dialog_bottom,null);
        bottomView=getLayoutInflater().inflate(R.layout.dialog_result,null);
        //rxPermissions=new RxPermissions(this);
        al=findViewById(R.id.album);
        al.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IdentifyAlbumPictures(view);//调用函数实现图像识别
            }
        });
        tp=findViewById(R.id.takephoto);
        tp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IdentifyCameraPicture(view);//调用函数实现图像识别
            }
        });
    }
    private String getAccessToken() {//获取鉴权Token
        String token = SPUtils.getString(Constant.TOKEN, null, this);
        if (token == null) {//为空重新获取
            //访问API获取接口
            requestApiGetToken();
        } else {
            //则判断Token是否过期
            if (isTokenExpired()) {
                //过期
                requestApiGetToken();
            } else {
                accessToken = token;
            }
        }
        return accessToken;
    }
    private void requestApiGetToken(){//访问Api获取Token
        //api_id: 43843562
        String grantType = "client_credentials";//授权类型
        String apiKey = "XhrVCb6GOpDSWFmlROpLFOCP";//根据申请的api情况改变
        String apiSecret = "rYdWMBtDZylBFcwgFSdFNkDvRkQwvD1b";//根据申请的api情况改变
        service.getToken(grantType,apiKey,apiSecret)
                .enqueue(new NetCallBack<GetTokenResponse>() {
                    @Override
                    public void onSuccess(Call<GetTokenResponse> call, Response<GetTokenResponse> response) {
                        /*
                        //每次都得申请
                        if(response.body()!=null){
                            accessToken=response.body().getAccess_token();
                            Log.d(TAG,accessToken);
                        }
                        */

                        //鉴权Token
                        accessToken = response.body().getAccess_token();
                        //过期时间 秒
                        long expiresIn = response.body().getExpires_in();
                        //当前时间 秒
                        long currentTimeMillis = System.currentTimeMillis() / 1000;
                        //放入缓存,待用
                        SPUtils.putString(Constant.TOKEN, accessToken, PictureActivity.this);
                        SPUtils.putLong(Constant.GET_TOKEN_TIME, currentTimeMillis, PictureActivity.this);
                        SPUtils.putLong(Constant.TOKEN_VALID_PERIOD, expiresIn, PictureActivity.this);
                    }

                    @Override
                    public void onFailed(String errorStr) {
                        Log.e(TAG,"获取Token失败，失败原因："+errorStr);
                        accessToken=null;
                    }
                });
    }
    private boolean isTokenExpired() {//判断Token是否过期
        //获取Token的时间
        long getTokenTime = SPUtils.getLong(Constant.GET_TOKEN_TIME, 0, this);
        //获取Token的有效时间
        long effectiveTime = SPUtils.getLong(Constant.TOKEN_VALID_PERIOD, 0, this);
        //获取当前系统时间
        long currentTime = System.currentTimeMillis() / 1000;

        return (currentTime - getTokenTime) >= effectiveTime;
    }
    //弹窗消息
    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    private void showDiscernResult(List<GetDiscernResultResponce.ResultBean> result) {//显示识别结果
        /*
        bottomSheetDialog.setContentView(bottomView);
        //bottomSheetDialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundColor(Color.TRANSPARENT);
        RecyclerView rvResult = bottomView.findViewById(R.id.rv_result);
        DiscernResultAdapter adapter = new DiscernResultAdapter(R.layout.item_result, result);
        rvResult.setLayoutManager(new LinearLayoutManager(this));
        rvResult.setAdapter(adapter);
        //进度条隐藏加载
        pb.setVisibility(View.GONE);
        //显示弹窗
        bottomSheetDialog.show();
         */
        bottomSheetDialog.setContentView(bottomView);
        ListView lvResult=bottomView.findViewById(R.id.lv_result);
        ResultAdapter adapter=new ResultAdapter(this,R.layout.item_result,result);
        lvResult.setAdapter(adapter);
        //进度条隐藏加载
        pb.setVisibility(View.GONE);
        //显示弹窗
        bottomSheetDialog.show();
    }
    private void ImageDiscern(String token,String imageBase64,String imgUrl){//图像识别
        service.getDiscernResult(token,imageBase64,imgUrl).enqueue(new NetCallBack<GetDiscernResultResponce>() {
            @Override
            public void onSuccess(Call<GetDiscernResultResponce> call, Response<GetDiscernResultResponce> response) {
                if(response.body() == null){
                    showMsg("未获得相应的识别结果");
                    return;
                }
                List<GetDiscernResultResponce.ResultBean> result = response.body().getResult();
                if (result != null && result.size() > 0) {
                    //显示识别结果
                    showDiscernResult(result);
                } else {
                    pb.setVisibility(View.GONE);
                    showMsg("未获得相应的识别结果");
                }
            }

            @Override
            public void onFailed(String errorStr) {
                pb.setVisibility(View.GONE);
                Log.e(TAG, "图像识别失败，失败原因：" + errorStr);
            }
        });
    }
    //获取权限，识别相册图片
    public void IdentifyAlbumPictures(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//Android6.0以后，读写文献属于危险权限
            //动态申请读写权限
            /*
            rxPermissions.request(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(grant ->{
                        if(grant){
                            //获得权限
                            openAlbum();
                        }
                        else{
                            showMsg("未获取到权限");
                        }
                    });
             */
            if(ContextCompat.checkSelfPermission(PictureActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                if(dialog!=null){
                    dialog.dismiss();
                    dialog=null;
                }
                dialog=new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("请开启文件访问权限，否则无法正常使用应用！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.dismiss();
                                //申请权限
                                ActivityCompat.requestPermissions(PictureActivity.this,PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
                                //openAlbum();
                            }
                        }).create();
                dialog.show();
            }
            else {
                openAlbum();
            }
        } else {
            openAlbum();
        }
    }
    //打开相册
    private void openAlbum() {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, ALBUM_CODE);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }
    //获取权限，识别拍照图片
    public void IdentifyCameraPicture(View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//Android6.0以后，读写文献属于危险权限
            if(ContextCompat.checkSelfPermission(PictureActivity.this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
                if(dialog!=null){
                    dialog.dismiss();
                    dialog=null;
                }
                dialog=new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("请开启文件访问权限，否则无法正常使用应用！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialog.dismiss();
                                //申请权限
                                ActivityCompat.requestPermissions(PictureActivity.this,PERMISSIONS_CAMERA,REQUEST_CAMERA);
                                //openCamera();
                            }
                        }).create();
                dialog.show();
            }
            else{
                openCamera();
            }
        }
        else{
            openCamera();
        }
    }
    //打开相机
    private void openCamera(){
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("HH_mm_ss");//获取当前时间
        String filename = timeStampFormat.format(new Date());
        //创建File对象
        outputImage=new File(getExternalCacheDir(),"takePhoto"+filename+".jpg");
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(PictureActivity.this,
                    "com.example.finalwork.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        //打开相机
        try {
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, CAMERA_CODE);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }
    //权限消息提示
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 3://返回申请相机权限的结果
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openCamera();//请求码3，打开相机
                }
                else {
                    Toast.makeText(this,"您不同意给予权限。",Toast.LENGTH_SHORT).show();
                }
                break;
            case 4://返回申请读写内存权限的结果
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();//请求码3，打开相册
                }
                else {
                    Toast.makeText(this,"您不同意给予权限。",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    //重写onActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pb.setVisibility(View.VISIBLE);//显示加载条
        switch (requestCode){//判断是什么活动返回数据
            case CAMERA_CODE://相机返回
                if(resultCode==RESULT_OK){
                    //拍照返回
                    String imagePath = outputImage.getAbsolutePath();
                    //识别
                    localImageDiscern(imagePath);
                }
                break;
            case ALBUM_CODE://相册返回
                if(resultCode==RESULT_OK) {//判断活动是否返回成功
                    String[] filePathColumns={MediaStore.Images.Media.DATA};
                    final Uri imageUri= Objects.requireNonNull(data).getData();
                    Cursor cursor=getContentResolver().query(imageUri,filePathColumns,null,null,null);
                    cursor.moveToFirst();
                    int columnIndex=cursor.getColumnIndex(filePathColumns[0]);
                    String imagePath=cursor.getString(columnIndex);//获取图片路径
                    cursor.close();
                    localImageDiscern(imagePath);//识别本地图片
                }
                else {
                    showMsg("获取相册图片失败。");
                }
                break;
            default:
                break;
        }
    }
    //根据图片路径将图片转成字节，再转Base64
    private void localImageDiscern(String imagePath) {
        try {
            String token = getAccessToken();
            //通过图片路径显示图片在ImageView中
            Glide.with(this).load(imagePath).into(image);
            //按字节读取文件
            byte[] imgData = FileUtil.readFileByBytes(imagePath);
            //字节转Base64
            String imageBase64 = Base64Util.encode(imgData);
            //图像识别
            ImageDiscern(token, imageBase64, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}