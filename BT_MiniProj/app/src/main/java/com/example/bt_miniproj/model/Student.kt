package com.example.bt_miniproj.model

import java.io.Serializable

// Data Class quản lý hồ sơ sinh viên
data class Student(
    val id: String,         // Mã số sinh viên
    val name: String,       // Họ và tên
    val className: String,  // Lớp sinh hoạt
    val email: String,      // Địa chỉ email
    val gpa: Double         // Điểm trung bình tích lũy (0.0 - 4.0)
) : Serializable {

    // Thuộc tính tính toán (Computed Property) kiểm tra sinh viên xuất sắc
    val isHonorStudent: Boolean
        get() = gpa >= 3.6
}