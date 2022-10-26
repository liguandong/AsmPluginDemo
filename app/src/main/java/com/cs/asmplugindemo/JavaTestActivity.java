package com.cs.asmplugindemo;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cs.commonlib.CostAnnotation;
import com.cs.commonlib.JCostAnnotation;

public class JavaTestActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_test);
        View view = findViewById(R.id.btn1);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        View view2 = findViewById(R.id.btn2);
        view2.setOnClickListener(v -> {
            try {
                throw new RuntimeException("test exception");
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    @CostAnnotation
    @Override
    protected void onStart() {
        super.onStart();
    }

    @JCostAnnotation()
    @Override
    protected void onResume() {
        super.onResume();
    }
}
