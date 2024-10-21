package com.example.share_pre;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String SHARED_PREFERENCE_NAME = "SettingDisplay";
    private CheckBox cbEnableStorage;
    private CheckBox cbEnableBatteryPerformance;
    private SeekBar sbStorageLevel;
    private SeekBar sbBatteryPerformanceLevel;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cbEnableStorage = (CheckBox) findViewById(R.id.cb_enable_storage);
        cbEnableBatteryPerformance = (CheckBox) findViewById(R.id.cb_enable_battery_performance);
        sbStorageLevel = (SeekBar) findViewById(R.id.sb_storage_level);
        sbBatteryPerformanceLevel = (SeekBar) findViewById(R.id.sb_battery_performance_level);
        btnSave = (Button) findViewById(R.id.btn_save);

        cbEnableStorage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    sbStorageLevel.setVisibility(View.VISIBLE);
                else
                    sbStorageLevel.setVisibility(View.INVISIBLE);
            }
        });

        cbEnableBatteryPerformance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    sbBatteryPerformanceLevel.setVisibility(View.VISIBLE);
                else
                    sbBatteryPerformanceLevel.setVisibility(View.INVISIBLE);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        boolean isStorageEnabled = sharedPreferences.getBoolean("storage_enabled", false);
        boolean isBatteryPerformanceEnabled = sharedPreferences.getBoolean("battery_performance_enabled", false);
        int storageLevel = sharedPreferences.getInt("storage_level", 50);
        int batteryPerformanceLevel = sharedPreferences.getInt("battery_performance_level", 50);

        cbEnableStorage.setChecked(isStorageEnabled);
        cbEnableBatteryPerformance.setChecked(isBatteryPerformanceEnabled);

        if (isStorageEnabled)
            sbStorageLevel.setProgress(storageLevel);
        else
            sbStorageLevel.setVisibility(View.INVISIBLE);

        if (isBatteryPerformanceEnabled)
            sbBatteryPerformanceLevel.setProgress(batteryPerformanceLevel);
        else
            sbBatteryPerformanceLevel.setVisibility(View.INVISIBLE);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putBoolean("storage_enabled", cbEnableStorage.isChecked());
                editor.putBoolean("battery_performance_enabled", cbEnableBatteryPerformance.isChecked());

                if (cbEnableStorage.isChecked())
                    editor.putInt("storage_level", sbStorageLevel.getProgress());
                else
                    editor.putInt("storage_level", 50);

                if (cbEnableBatteryPerformance.isChecked())
                    editor.putInt("battery_performance_level", sbBatteryPerformanceLevel.getProgress());
                else
                    editor.putInt("battery_performance_level", 50);

                editor.commit();
                Toast.makeText(MainActivity.this, "Đã lưu cài đặt thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

