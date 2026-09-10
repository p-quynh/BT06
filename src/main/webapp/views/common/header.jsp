<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!doctype html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><c:out value="${param.pageTitle}"/> | UTE Footwear</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/app.css">
</head>
<body>
<header class="site-header">
    <div class="topbar">MIỄN PHÍ GIAO HÀNG CHO ĐƠN TỪ 500.000 VNĐ</div>
    <div class="container">
        <div class="brand-row">
            <a class="brand" href="${pageContext.request.contextPath}/home">UTE <span>Footwear</span></a>
            <span class="text-muted d-none d-md-inline">Thời trang cho từng bước chân</span>
        </div>
        <nav class="main-nav" aria-label="Điều hướng chính">
            <a href="${pageContext.request.contextPath}/home">Trang chủ</a>
            <a href="${pageContext.request.contextPath}/product">Sản phẩm</a>
            <c:if test="${sessionScope.account.roleid == 1}">
                <a href="${pageContext.request.contextPath}/admin/product">Quản lý sản phẩm</a>
                <a href="${pageContext.request.contextPath}/admin/category">Danh mục</a>
            </c:if>
            <span class="account-nav">
                <c:choose>
                    <c:when test="${not empty sessionScope.account}">
                        <a href="${pageContext.request.contextPath}/profile">Tài khoản</a>
                        <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
                        <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
                    </c:otherwise>
                </c:choose>
            </span>
        </nav>
    </div>
</header>
<main class="site-main">
