<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!doctype html>
<html lang="vi">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>
        <sitemesh:write property="title"/>
    </title>


    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">


    <sitemesh:write property="head"/>

</head>


<body class="bg-light">


<nav class="navbar navbar-expand-lg bg-white border-bottom">


    <div class="container">


        <!-- Logo -->

        <a class="navbar-brand fw-bold"
           href="${pageContext.request.contextPath}/home">

            UTE SHOP

        </a>



        <!-- Menu -->

        <div class="navbar-nav me-auto">


            <a class="nav-link"
               href="${pageContext.request.contextPath}/home">

                Home

            </a>


            <a class="nav-link"
               href="${pageContext.request.contextPath}/product">

                Products

            </a>



            <c:if test="${sessionScope.account.roleid == 1}">

                <a class="nav-link"
                   href="${pageContext.request.contextPath}/admin/product">

                    Admin

                </a>

            </c:if>


        </div>




        <!-- User -->

        <div class="d-flex align-items-center">


            <c:choose>


                <c:when test="${not empty sessionScope.account}">


                    <!-- Avatar -->

                    <c:if test="${not empty sessionScope.account.avatar}">

                        <img
                                src="${pageContext.request.contextPath}/uploads/avatar/${sessionScope.account.avatar}"
                                alt="avatar"
                                class="rounded-circle me-2"
                                width="40"
                                height="40"
                                style="object-fit:cover;">

                    </c:if>



                    <!-- Profile link -->

                    <a class="nav-link me-3"
                       href="${pageContext.request.contextPath}/profile">

                            ${sessionScope.account.fullname}

                    </a>



                    <!-- Logout -->

                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/logout">

                        Logout

                    </a>


                </c:when>



                <c:otherwise>


                    <a class="nav-link me-3"
                       href="${pageContext.request.contextPath}/login">

                        Login

                    </a>


                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/register">

                        Register

                    </a>


                </c:otherwise>


            </c:choose>


        </div>


    </div>


</nav>



<main class="container py-4">


    <sitemesh:write property="body"/>


</main>



<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js">

</script>


</body>


</html>