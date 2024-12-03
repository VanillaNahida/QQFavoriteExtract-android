package com.nahida.qqfavoriteextract;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.content.SharedPreferences;


public class MainActivity extends AppCompatActivity {

    // 定义权限请求码
    private static final int REQUEST_CODE_PERMISSIONS = 1001;

    // 定义SharedPreferences文件名和键名
    private static final String PREFS_NAME = "AppPrefs";
    private static final String KEY_FIRST_RUN = "isFirstRun";

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

        if (isFirstRun) {
            // 检查权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                // 创建AlertDialog.Builder对象
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("权限请求")
                        .setMessage("欢迎使用本软件\n本软件需要访问您的存储权限来提取并保存QQ的收藏表情包\n请点击下方“授予存储权限”来授权\n若取消授权，则软件将会退出")
                        .setPositiveButton("授予存储权限", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 处理确定按钮点击事件
                                Toast.makeText(MainActivity.this, "你点击了确定按钮", Toast.LENGTH_SHORT).show();
                                status.setText("你点击了确定按钮");
                                checkPermissions();
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
                Toast.makeText(this, "权限已授权", Toast.LENGTH_SHORT).show();
                status.setText("权限已授权");
            }

            // 更新首次运行状态
            // prefs.edit().putBoolean(KEY_FIRST_RUN, false).apply();
        } else {
            // 不是首次运行，不显示对话框
            Toast.makeText(this, "欢迎回来", Toast.LENGTH_SHORT).show();
            status.setText("欢迎回来");
        }

        // 设置按钮点击事件
        button.setOnClickListener(v -> checkPermissions());
    }

    private void checkPermissions() {
        if (
//                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
//                // 针对高版本安卓的权限请求
//                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VIDEO) != PackageManager.PERMISSION_GRANTED ||
//                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED) != PackageManager.PERMISSION_GRANTED ||
//                //管理所有文件的请求
                ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 请求权限
            ActivityCompat.requestPermissions(this, new String[] {
                    // 授予管理所有文件
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            }, REQUEST_CODE_PERMISSIONS);
            Toast.makeText(this, "已拉起权限授予弹窗", Toast.LENGTH_SHORT).show();
        } else {
            // 权限已被授予，执行相关操作
            Toast.makeText(this, "权限已授权", Toast.LENGTH_SHORT).show();
            performStorageOperation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        TextView status = findViewById(R.id.status);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            // 创建一个布尔数组来存储每个权限的授权状态
            boolean allGranted = true;
            StringBuilder deniedPermissions = new StringBuilder();

            // 定义需要检查的权限列表
            String[] requiredPermissions = {
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
//                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    Manifest.permission.READ_MEDIA_IMAGES,
//                    Manifest.permission.READ_MEDIA_VIDEO,
//                    Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
            };

            // 遍历每个权限，检查其授权状态
            for (int i = 0; i < requiredPermissions.length; i++) {
                if (grantResults.length > i && grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    deniedPermissions.append(requiredPermissions[i]).append(", ");
                }
            }

            // 判断所有权限是否都被授予
            if (allGranted) {
                // 所有权限被授予，执行相关操作
                performStorageOperation();
            } else {
                // 有权限被拒绝，提示用户
                String deniedList = deniedPermissions.toString();
                if (deniedList.endsWith(", ")) {
                    deniedList = deniedList.substring(0, deniedList.length() - 2);
                }
                Toast.makeText(this, "以下权限被拒绝: " + deniedList, Toast.LENGTH_LONG).show();
                status.setText(deniedList);
            }
        }
    }

    private void performStorageOperation() {
        // 执行存储操作
        Toast.makeText(this, "存储权限已授予，执行操作", Toast.LENGTH_SHORT).show();
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