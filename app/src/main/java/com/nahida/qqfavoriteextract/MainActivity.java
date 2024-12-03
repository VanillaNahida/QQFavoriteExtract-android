package com.nahida.qqfavoriteextract;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Environment;

public class MainActivity extends AppCompatActivity {

    // 定义权限请求码
    private static final int REQUEST_CODE_PERMISSIONS = 1001;

    // 定义SharedPreferences文件名和键名
    private static final String PREFS_NAME = "AppPrefs";
    private static final String KEY_FIRST_RUN = "isFirstRun";

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        TextView status = findViewById(R.id.status);

        // 获取SharedPreferences对象
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // 检查是否为首次运行
        boolean isFirstRun = prefs.getBoolean(KEY_FIRST_RUN, true);

        if (!Environment.isExternalStorageManager()){
            // 更新首次运行状态
            prefs.edit().putBoolean(KEY_FIRST_RUN, false).apply();
        }

        if (isFirstRun) {
            // 检查权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 创建AlertDialog.Builder对象
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("权限请求")
                        .setMessage("欢迎使用本软件\n本软件需要访问您的存储权限来提取并保存QQ的收藏表情包\n请点击下方“授予存储权限”来授权\n若取消授权，则软件将会退出\n（本窗口只会在未授权的情况下才会弹出）")
                        .setPositiveButton("授予存储权限", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 处理确定按钮点击事件
                                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 处理取消按钮点击事件
                                // 结束所有关联的 Activity，退出应用
                                finishAffinity();
                            }
                        });
                // 显示对话框
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                // 权限已被授予
                Toast.makeText(this, "权限已授予", Toast.LENGTH_SHORT).show();
                status.setText("权限已授予");
            }


            // prefs.edit().putBoolean(KEY_FIRST_RUN, false).apply();
        } else {
            // 不是首次运行，不显示对话框
            Toast.makeText(this, "欢迎回来", Toast.LENGTH_SHORT).show();
            status.setText("欢迎回来");
        }

        // 设置按钮点击事件
        button.setOnClickListener(v -> checkPermissions());
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void checkPermissions() {
        TextView status = findViewById(R.id.status);
        if (!Environment.isExternalStorageManager()){
            // 请求权限
            Toast.makeText(this, "未授权，正在拉起设置", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } else {
            // 权限已被授予，执行相关操作
            Toast.makeText(this, "权限已授权", Toast.LENGTH_SHORT).show();
            status.setText("权限已授权");
            // performStorageOperation();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        TextView status = findViewById(R.id.status);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!Environment.isExternalStorageManager()) {
            // 应用没有MANAGE_EXTERNAL_STORAGE权限
            status.setText("未获得MANAGE_EXTERNAL_STORAGE权限");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示")
                    .setMessage("未获得MANAGE_EXTERNAL_STORAGE权限\n是否前往设置授予？")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 处理确定按钮点击事件
                            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                            intent.setData(Uri.parse("package:" + getPackageName()));
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 处理取消按钮点击事件
                            Toast.makeText(MainActivity.this, "已取消授权", Toast.LENGTH_SHORT).show();
                            status.setText("已取消授权");
                        }
                    });
            // 显示对话框
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            // 应用拥有MANAGE_EXTERNAL_STORAGE权限
            status.setText("已获得MANAGE_EXTERNAL_STORAGE权限");
        }
    }

    private void performStorageOperation() {
        // 执行存储操作
        Toast.makeText(this, "存储权限已授予", Toast.LENGTH_SHORT).show();
    }

    private void Nya() {
        TextView status = findViewById(R.id.status);
        Toast.makeText(this, "喵~🐱", Toast.LENGTH_SHORT).show();
        status.setText("");
        // 获取当前 TextView 的内容
        String Text = status.getText().toString();;
        // 拼接“喵”
        String textString2 = Text + "喵";
        // 设置拼接后的内容到另一个 TextView
        status.setText(textString2);
    }

    private void shuai() {
        TextView status = findViewById(R.id.status);
        // 创建AlertDialog.Builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("问题")
                .setMessage("我帅吗？")
                .setPositiveButton("帅", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 处理确定按钮点击事件
                        Toast.makeText(MainActivity.this, "真不要脸！", Toast.LENGTH_SHORT).show();
                        status.setText("真不要脸！");
                    }
                })
                .setNegativeButton("不帅", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 处理取消按钮点击事件
                        Toast.makeText(MainActivity.this, "知道就好", Toast.LENGTH_SHORT).show();
                        status.setText("知道就好");
                    }
                });
        // 显示对话框
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void nahida() {
        TextView status = findViewById(R.id.status);
        // 创建AlertDialog.Builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("问题")
                .setMessage("纳西妲可爱吗？")
                .setPositiveButton("可爱", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 处理确定按钮点击事件
                        Toast.makeText(MainActivity.this, "纳西妲世界第一可爱！", Toast.LENGTH_SHORT).show();
                        status.setText("纳西妲世界第一可爱！");
                    }
                })
                .setNegativeButton("非常可爱", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 处理取消按钮点击事件
                        Toast.makeText(MainActivity.this, "包可爱的好吧", Toast.LENGTH_SHORT).show();
                        status.setText("包可爱的好吧");
                    }
                });
        // 显示对话框
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}