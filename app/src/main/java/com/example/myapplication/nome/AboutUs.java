package com.example.myapplication.nome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class AboutUs extends AppCompatActivity {

    MyArray listAdapter;
    int image[] = {R.drawable.darius, R.drawable.yuumi, R.drawable.vaybu, R.drawable.amumu, R.drawable.zed};
    String name[] = {"Hồ Thái Sơn", "Đặng Văn Thống", "Lê Hữu Thi", "Hoàng Văn Thông", "Đoàn Trung Kiên"};
    String masv[] = {"21115053120141", "21115053120150", "2115053120147", "21115053120149", "2115053120122"};

    String listView[][] = {{"CaiDatActivity", "NguoiDungActivity", "DangSanPhamActivity", "HoTroActivity"},
                            {"thongke", "select", "detail", "detail_shop"},
                            {"GioHang", "LichSuMuaHang", "Voucher", "MaGiamGia"},
                            {"minigame", "thongbao", "thongkedoanhthu", "thongtingiaohang"},
                            {"MainActivity", "Signup", "activity_trangchu", "activity_quanlybaidang", "activity_thanhtoan"}};
    String listName[][] = {{"Cài Đặt", "NGười Dùng", "Đăng Sản Phẩm", "Hỗ Trợ Khách Hàng"},
                            {"Thống Kê", "Danh Mục Sản Phẩm", "Thông Tin Sản Phẩm", "Thông Tin Shop"},
                            {"Giỏ Hàng", "Lịch Sử Mua Hàng", "Ví Thanh Toán", "Mã Giảm Giá"},
                            {"Mini Game", "Thông Báo", "Thống Kê Doanh Thu", "Thông Tin Giao Hàng"},
                            {"Đăng Nhập", "Đăng Ký", "Trang Chủ", "Quản Lý Bài Đăng", "Thanh Toán"}};
    ArrayList<meber> data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        findViewById(R.id.trove).setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
        ListView viewgui = findViewById(R.id.listview);
        for (int i = 0; i < 5; i++){
            data.add(new meber(name[i], masv[i], image[i], listView[i], listName[i]));
        }
        viewgui.setClickable(true);
        listAdapter = new MyArray(AboutUs.this, data);
        viewgui.setAdapter(listAdapter);
        viewgui.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), DatailMember.class);
                meber PP = data.get(i);
                intent.putExtra("member", PP);
                startActivity(intent);
            }
        });
    }
}