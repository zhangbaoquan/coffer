package coffer;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author：张宝全
 * @date：2020-03-18
 * @Description：
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public abstract class BaseActivity extends BaseDefaultActivity {

    /**
     * 权限请求码
     */
    private final int mRequestCode = 100;
    /**
     * 声明一个数组将所有需要申请的权限都放入
     */
    private String[] permissions = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 创建一个mPermissionList，逐个判断哪些权限未授权，将未授权的权限存储到mPermissionList中
     */
    List<String> mPermissionList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPermission();
    }

    /***************   权限申请    **************/

    private void initPermission() {
        mPermissionList.clear();//清空已经允许的没有通过的权限
        //逐个判断是否还有未通过的权限
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) !=
                    PackageManager.PERMISSION_GRANTED) {
                //添加还未授予的权限到mPermissionList中
                mPermissionList.add(permissions[i]);
            }
        }
        //申请权限
        if (mPermissionList.size() > 0) {
            //有权限没有通过，需要申请
            ActivityCompat.requestPermissions(this, permissions, mRequestCode);
        } else {
            //权限已经都通过了，可以将程序继续打开了
            Toast.makeText(BaseActivity.this, "申请成功", Toast.LENGTH_SHORT).show();
        }
        // 暂时注释掉去设置界面授予权限
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (!Settings.canDrawOverlays(this)) {
//                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivityForResult(intent, 1);
//            } else {
//                //TODO do something you need
//            }
//        }
    }

    /**
     * 6.不再提示权限时的展示对话框
     */
    AlertDialog mPermissionDialog;
    String mPackName = "coffer.androidjatpack";

    private void showPermissionDialog() {
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(this)
                    .setMessage("已禁用权限，请手动授予")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelPermissionDialog();

                            Uri packageURI = Uri.parse("package:" + mPackName);
                            Intent intent = new Intent(Settings.
                                    ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //关闭页面或者做其他操作
                            cancelPermissionDialog();
                            BaseActivity.this.finish();
                        }
                    })
                    .create();
        }
        mPermissionDialog.show();
    }

    private void cancelPermissionDialog() {
        mPermissionDialog.cancel();
    }

    /**
     * 5.请求权限后回调的方法
     *
     * @param requestCode  是我们自己定义的权限请求码
     * @param permissions  是我们请求的权限名称数组
     * @param grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限
     *                     名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean hasPermissionDismiss = false;
        //有权限没有通过
        if (mRequestCode == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;
                    break;
                }
            }
        }
        if (hasPermissionDismiss) {
            //如果有没有被允许的权限
            showPermissionDialog();
        } else {
            //权限已经都通过了，可以将程序继续打开了
            Toast.makeText(BaseActivity.this, "申请成功", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 新增一个Window，注意添加时机，必须要等activity的生命周期函数全部执行完毕之后，需要依附的View加载完成了才可以。
     */
    private void addWindow(){
//        Button btn_click= new Button(this);
//        btn_click .setText("浮窗");
//        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
//        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        mParams.format = PixelFormat.TRANSLUCENT;
//        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//        //Flag参数表示window的属性，通过这些选项控制Window的显示特性：
//        //1.FLAG_NOT_FOCUSABLE:表示窗口不需要获取焦点，也不需要接收各种事件，这属性会同时启动FLAG_NOT_TOUCH_MODAL，最终事件会传递给下层的具体焦点的window
//        //2.FLAG_NOT_TOUCH_MODAL:系统会将当前window区域以外的单击事件传递给底层的Window，当前的Window区域以内的单机事件自己处理，这个标记很重要，一般来说都需要开启，否则其他windows无法接受到点击事件。
//        mParams.gravity = Gravity.CENTER;
//        mParams.token = getWindow().getDecorView().getWindowToken();
//        // 仅在当前Activity上显示
//        mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
//        //emm..这个是Type参数。
//        mParams.x = 0;
//        mParams.y = 0;
//        wm.addView(btn_click, mParams);

    }
}
