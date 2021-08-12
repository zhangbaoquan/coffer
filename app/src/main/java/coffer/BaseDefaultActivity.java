package coffer;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import coffer.util.APP;
import coffer.util.global.ContactWindowUtil;
import coffer.widget.GrayFrameLayout;

/**
 * @author：张宝全
 * @date：2020/5/12
 * @Description： 基础业务类
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
public abstract class BaseDefaultActivity extends AppCompatActivity {

    public static final String PARAM_TARGET_INTENT = "targetIntent";

    /**
     * 用于activity跳转的intent
     */
    public Intent mTargetIntent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrAcvitity();
        initView();
        initData();
        ContactWindowUtil contactWindowUtil = new ContactWindowUtil(this);
        contactWindowUtil.setDialogListener(new ContactWindowUtil.ContactWindowListener() {
            @Override
            public void onDataCallBack(String str) {
                Toast.makeText(BaseDefaultActivity.this, "啦啦", Toast.LENGTH_SHORT).show();
            }
        });
        contactWindowUtil.showContactView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        try {
//            // 方案一：设置黑白模式
//            if ("FrameLayout".equals(name)) {
//                int count = attrs.getAttributeCount();
//                for (int i = 0; i < count; i++) {
//                    String attributeName = attrs.getAttributeName(i);
//                    String attributeValue = attrs.getAttributeValue(i);
//                    if (attributeName.equals("id")) {
//                        int id = Integer.parseInt(attributeValue.substring(1));
//                        String idVal = getResources().getResourceName(id);
//                        if ("android:id/content".equals(idVal)) {
//                            GrayFrameLayout grayFrameLayout = new GrayFrameLayout(context, attrs);
////                            grayFrameLayout.setWindow(getWindow());
//                            return grayFrameLayout;
//                        }
//                    }
//                }
//            }
//            // 方案二，直接设置DecorView的背景色
//            Paint paint = new Paint();
//            ColorMatrix colorMatrix = new ColorMatrix();
//            colorMatrix.setSaturation(0);
//            paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
//            getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE, paint);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onCreateView(name, context, attrs);
    }

    public abstract void initView();

    public abstract void initData();

    @Override
    protected void onResume() {
        super.onResume();
        setCurrAcvitity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected void setCurrAcvitity() {
        if (getParent() == null) {
            APP.setCurrActivity(this);
        }
    }

    /**
     * 清除APP的当前activity
     */
    private void cleanCurrActivity() {
        // 只有当前activity是自己时，才能清除，因为在acivity切换过程中设置和清除方法可能交替调用多次
        if (APP.getCurrActivity() == this) {
            APP.setCurrActivity(null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        setCurrAcvitity();
        super.onActivityResult(requestCode, resultCode, data);
    }
}
