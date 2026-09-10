# BT05 – Spring Boot CRUD Category với Thymeleaf

## Công nghệ

- Java 17, Spring Boot 4.0.0, Maven WAR
- Spring MVC, Spring Data JPA, Validation
- SQL Server – database `ClothingShop`
- Thymeleaf và Thymeleaf Layout Dialect
- Bootstrap 5
- JSP/JSTL và SiteMesh vẫn được giữ cho các chức năng cũ

## Chức năng Category

- Xem danh sách danh mục
- Thêm, cập nhật, xóa danh mục
- Upload ảnh danh mục
- Tìm kiếm gần đúng, không phân biệt chữ hoa/thường
- Phân trang 5, 10 hoặc 20 dòng
- Giữ từ khóa tìm kiếm khi chuyển trang
- Validation tên danh mục và CSRF token cho các thao tác ghi

Truy cập:

```text
http://localhost:8081/admin/category
```

## Cấu trúc Thymeleaf

```text
src/main/resources/
├── static/css/thymeleaf-category.css
└── templates/thymeleaf/
    ├── layouts/main.html
    └── category/
        ├── list.html
        └── form.html
```

`main.html` là layout chung, gồm:

- Header: logo, menu và ảnh cá nhân
- Content: được thay bằng `layout:fragment="content"`
- Footer: họ tên, MSSV và lớp học phần

Các trang Category sử dụng:

```html
layout:decorate="~{thymeleaf/layouts/main}"
```

## Thay thông tin sinh viên

Mở `src/main/resources/application.properties` và sửa:

```properties
app.student.name=Họ và tên sinh viên
app.student.id=MSSV
app.student.class-name=Lớp học phần
```

Ảnh ở header ưu tiên avatar của tài khoản đang đăng nhập. Khi chưa đăng nhập, hệ thống dùng:

```text
src/main/webapp/uploads/avatar/default-avatar.jpg
```

Có thể thay file này bằng ảnh cá nhân nhưng nên giữ nguyên tên file.

## Cấu hình SQL Server

Chạy file `DB_BT05.sql`, sau đó cấu hình biến môi trường trên PowerShell:

```powershell
$env:DB_PASSWORD="MAT_KHAU_SQL_SERVER"
```

Nếu SQL Server không chạy ở cổng 1434, sửa `DB_URL` hoặc giá trị mặc định trong
`src/main/resources/application.properties`.

## Chạy dự án

Tại thư mục chứa `pom.xml`:

```powershell
mvn clean package
java -jar .\target\BTWeb_JPA-1.0-SNAPSHOT.war
```

Sau khi ứng dụng khởi động, mở:

```text
http://localhost:8081/admin/category
```

## Lưu ý kiến trúc

Thymeleaf chỉ xử lý các view có tiền tố `thymeleaf/*`. Các chức năng cũ vẫn dùng JSP,
nhờ đó phần Category được chuyển đổi mà không phá vỡ Login, Register, Product và Profile.
Các URL Category được loại khỏi SiteMesh vì layout đã do Thymeleaf Layout Dialect quản lý.
