package com.astabits.lockmydevice;

import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{
    public static final int RESULT_ENABLE = 11;
    protected DevicePolicyManager devicePolicyManager;
    protected ActivityManager activityManager;
    protected ComponentName compName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        compName = new ComponentName(this, MyAdmin.class);
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Required action to lock the screen");
        startActivityForResult(intent, RESULT_ENABLE);

        boolean active = devicePolicyManager.isAdminActive(compName);

        if (active) {
            devicePolicyManager.lockNow();
            finish();
            System.exit(0);
        } else {
            Toast.makeText(this, "You need to enable the Admin Device Features", Toast.LENGTH_SHORT).show();
        }
    }

}
