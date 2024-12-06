package com.nahida.qqfavoriteextract;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
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
import android.content.Intent;
import android.os.Environment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    // 定义权限请求码
    private static final int REQUEST_CODE = 1024;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        // 设置按钮点击事件
        button.setOnClickListener(v -> main());
        button.setOnLongClickListener(v -> {
            // 处理长按事件的逻辑
            nahida();
            return true; // 返回true表示事件已处理，不再继续传递
        });
    }

    private void main() {
        TextView status = findViewById(R.id.status);
        if (!requestPermission()) {
            // 申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        } else {
            status.setText("已有存储权限，尝试写入1.txt测试文本到Andro/data目录");
            write_text_to_data_directory();
        }
    }


    @SuppressLint("NewApi")
    private boolean requestPermission() {
        TextView status = findViewById(R.id.status);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            // 当前设备的Android版本在Android 6.0（API 23）到Android 10（API 29）之间
            // 先判断有没有权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // 有权限，返回True
                Toast.makeText(this, "已获得存储权限", Toast.LENGTH_SHORT).show();
                return true;
            } else {
                return false;
//                // 申请权限
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        } else {
            // 当前设备的Android版本是Android 11（API 30）及以上
            if (!Environment.isExternalStorageManager()){
                // 请求权限
                Toast.makeText(this, "未授权，正在拉起设置", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            } else {
                // 权限已被授予，执行相关操作
                Toast.makeText(this, "已授权所有文件管理权限", Toast.LENGTH_SHORT).show();
                status.setText("已授权所有文件管理权限");
                return true;
            }
        }
        return false;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "存储权限获取成功", Toast.LENGTH_SHORT).show();
                //在此填写文件操作代码
            } else {
                Toast.makeText(this, "存储权限获取失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    private void manage_storage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 检查MANAGE_EXTERNAL_STORAGE权限
            if (!Environment.isExternalStorageManager()) {
                // 请求MANAGE_EXTERNAL_STORAGE权限
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        }
    }

    //检查文件能否写入到data目录，如果不能，就拉起document-ui来授权QQ数据目录
    private void write_text_to_data_directory() {
        TextView status = findViewById(R.id.status);
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/1.txt";
        File file = new File(filePath);
        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8")) {
            osw.write("纳西妲世界第一可爱！\n\n香草味的纳西妲");
            Toast.makeText(this, "文件写入成功", Toast.LENGTH_SHORT).show();
            status.setText("写入成功！在/sdcard/Android/data/1.txt中");
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "文件写入失败", Toast.LENGTH_SHORT).show();
            status.setText("文件写入失败！");
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