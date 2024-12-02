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

// 定义权限请求码
public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSIONS = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        TextView status = findViewById(R.id.status);
        button.setOnClickListener(v -> shuai());
        // 创建AlertDialog.Builder对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("欢迎")
                .setMessage("欢迎使用本应用！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
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
                        Toast.makeText(MainActivity.this, "你点击取消按钮", Toast.LENGTH_SHORT).show();
                        status.setText("你点了取消按钮");
                    }
                });
        // 显示对话框
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, REQUEST_CODE_PERMISSIONS);
        } else {
            performStorageOperation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                performStorageOperation();
            } else {
                Toast.makeText(this, "存储权限被拒绝，无法执行操作", Toast.LENGTH_SHORT).show();
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