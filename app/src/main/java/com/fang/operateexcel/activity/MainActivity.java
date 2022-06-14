package com.fang.operateexcel.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.fang.operateexcel.R;
import com.fang.operateexcel.databinding.ActivityMainBinding;
import com.my.common.base.BaseActivity;
import com.my.common.log4j.Log4jConfigure;
import com.my.common.utils.ToastUtils;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
	private static final int REQUEST_PERMISSION_CALL = 111;
	private boolean isAllPermissionsGranted = false;
	String[] permissions = new String[]{
			//写入sd卡
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			//读取sd卡
			Manifest.permission.READ_EXTERNAL_STORAGE
	};

	@Override
	protected void initPresenter() {
	}

	@Override
	protected void initData() {
	}

	@Override
	protected void initView() {
		startRequestPermission(permissions);
	}

	public void startRequestPermission(String[] permissions) {
		isAllPermissionsGranted = false;
		ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION_CALL);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
										   @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == REQUEST_PERMISSION_CALL && grantResults.length > 0 && grantResults[0] != 0) {
			ToastUtils.showText(getString(R.string.allow_app_permission));
			new Handler().postDelayed(() -> {
				System.exit(0);//权限被拒绝，退出程序
			}, 1000);
		} else {
			isAllPermissionsGranted = true;
//          在所有权限都申请成功后，才能跳转到下一步
//          还是用户手动点击处理好点
//            toNext();
			//操作excel
			startActivity(new Intent(this, OperateExcelActivity.class));
			finish();

			if (!Log4jConfigure.isConfigSuccess) {
				Log4jConfigure.configure();
			}
		}
	}
}