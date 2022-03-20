package com.kc;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kc.router.annotation.Destination;

/**
 * author       : coffer
 * date         : 2022/3/20
 * description  :
 */

@Destination(
        url = "router://page-Test",
        description = "测试页"
)
public class TestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
