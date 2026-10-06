package com.example.bt_miniproj

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bt_miniproj.databinding.ActivityMainBinding
import com.example.bt_miniproj.model.Student
import com.example.bt_miniproj.utils.toAcademicRanking
import com.example.bt_miniproj.utils.toast
import com.example.bt_miniproj.utils.trimmedText

class MainActivity : AppCompatActivity() {

    // 1. Khai báo biến binding (Không dùng findViewById)
    private lateinit var binding: ActivityMainBinding

    // 2. Tạo dữ liệu mặc định với thông tin cá nhân
    private val defaultStudent = Student(
        id = "2415053122207",
        name = "Phạm Văn Đức",
        className = "126LTTD01",
        email = "2415053122207@sv.ute.udn.vn",
        gpa = 2.44
    )

    // Biến lưu trạng thái sinh viên hiện tại
    private var currentStudent = defaultStudent

    companion object {
        private const val KEY_STUDENT_DATA = "EXTRA_KEY_STUDENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 3. Khởi tạo ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 4. Khôi phục dữ liệu nếu vừa xoay màn hình
        if (savedInstanceState != null) {
            val saved = savedInstanceState.getSerializable(KEY_STUDENT_DATA) as? Student
            saved?.let { currentStudent = it }
        }

        // 5. Hiển thị dữ liệu lên giao diện
        bindStudentData(currentStudent)

        // 6. Bắt sự kiện click nút Cập Nhật
        binding.btnUpdateGpa.setOnClickListener {
            // Lấy dữ liệu an toàn nhờ Extension trimmedText() và toDoubleOrNull()
            val gpa = binding.edtGpaInput.trimmedText().toDoubleOrNull()

            // Validate dữ liệu
            if (gpa == null || gpa !in 0.0..4.0) {
                binding.edtGpaInput.error = "GPA phải từ 0.0 đến 4.0"
                toast("Điểm số không hợp lệ, vui lòng kiểm tra lại!")
                return@setOnClickListener
            }

            // Nếu hợp lệ, xóa lỗi và cập nhật đối tượng bất biến
            binding.edtGpaInput.error = null
            currentStudent = currentStudent.copy(gpa = gpa)

            // Render lại giao diện
            bindStudentData(currentStudent)
            toast("Đã cập nhật GPA thành công!")
        }

        // Bắt sự kiện click nút Reset
        binding.btnReset.setOnClickListener {
            currentStudent = defaultStudent
            binding.edtGpaInput.error = null
            bindStudentData(currentStudent)
            toast("Đã khôi phục dữ liệu gốc!")
        }
    }

    // Hàm gán toàn bộ thông tin từ model lên các Views
    private fun bindStudentData(student: Student) {
        with(binding) {
            tvStudentName.text = student.name
            tvStudentDetails.text = "MSSV: ${student.id} | Lớp HP: ${student.className}"
            tvStudentEmail.text = "Email: ${student.email}"
            tvGpaBadge.text = "${student.gpa} GPA - ${student.gpa.toAcademicRanking()}"
            edtGpaInput.setText(student.gpa.toString())
        }
    }
    // 7. Lưu trạng thái trước khi Activity bị hủy (ví dụ: khi xoay màn hình)
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_STUDENT_DATA, currentStudent)
    }
}