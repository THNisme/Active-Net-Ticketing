<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Double Slider Login / Registration Form</title>
        <link href="https://cdn.lineicons.com/4.0/lineicons.css" rel="stylesheet" />

        <!--Inter font - Google Fonts-->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap" rel="stylesheet">

        <link rel="stylesheet" href="css/authentication-page/login.css" />


    </head>
    <body>
        <div class="container" id="container">
            <!-- Register -->
            <div class="form-container register-container">
                <form action="register" method="post">
                    <h1>Đăng ký ngay.</h1>

                    <!-- Username -->
                    <div class="user-box">
                        <div class="input-with-icon">

                            <input type="text" name="username" id="register-username" required />
                            <label>Tên đăng nhập</label>
                            <svg xmlns="http://www.w3.org/2000/svg" class="icon" width="24" height="24" fill="none"
                                 stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2" />
                            <circle cx="12" cy="7" r="4" />
                            </svg>
                        </div> 
                        <span id="username-feedback" style="color:red"></span>
                        <c:if test="${not empty error}">
                            <span style="color:red"><c:out value="${error}"/></span>
                        </c:if>
                    </div>


                    <!-- Password -->
                    <div class="user-box">
                        <div class="input-with-icon">
                            <input type="password" name="password" id="register-password" required />
                            <label>Mật khẩu</label>
                            <span class="toggle-password" onclick="togglePassword('register-password', this)">
                                <svg class="eye-open" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none"
                                     stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M1 12s4-7 11-7 11 7 11 7-4 7-11 7S1 12 1 12z" />
                                <circle cx="12" cy="12" r="3" />
                                </svg>
                                <svg class="eye-closed" style="display:none;" xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path
                                    d="M17.94 17.94A10.47 10.47 0 0 1 12 19c-7 0-11-7-11-7a21.46 21.46 0 0 1 5.17-6.88M22 12s-1.1-2.1-3-4.27M2 2l20 20" />
                                </svg>
                            </span>
                        </div>
                    </div>

                    <!-- Re-password -->
                    <div class="user-box">
                        <div class="input-with-icon">
                            <input type="password" name="repassword" id="register-repassword" required />
                            <label>Xác nhận mật khẩu</label>
                            <span class="toggle-password" onclick="togglePassword('register-repassword', this)">
                                <svg class="eye-open" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none"
                                     stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path d="M1 12s4-7 11-7 11 7 11 7-4 7-11 7S1 12 1 12z" />
                                <circle cx="12" cy="12" r="3" />
                                </svg>
                                <svg class="eye-closed" style="display:none;" xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <path
                                    d="M17.94 17.94A10.47 10.47 0 0 1 12 19c-7 0-11-7-11-7a21.46 21.46 0 0 1 5.17-6.88M22 12s-1.1-2.1-3-4.27M2 2l20 20" />
                                </svg>
                            </span>
                        </div>
                    </div>

                    <button type="submit">Tạo</button>
                </form>
            </div>


            <!-- Login -->
            <div class="form-container login-container">
                <form action="login" method="post">
                    <h1>Đăng nhập ngay.</h1>
                    <div class="user-box">
                        <div class="input-with-icon">
                            <input type="text" name="username" required />
                            <label>Tên đăng nhập</label>
                            <svg xmlns="http://www.w3.org/2000/svg" class="icon" width="24" height="24" viewBox="0 0 24 24" fill="none"
                                 stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                                 class="lucide lucide-user-icon lucide-user">
                            <path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2" />
                            <circle cx="12" cy="7" r="4" />
                            </svg>
                        </div>
                    </div>
                    <div class="user-box">
                        <div class="input-with-icon" style="position: relative;">
                            <input type="password" name="password" id="login-password" required />
                            <label>Mật khẩu</label>
                            <!-- Nút con mắt -->
                            <span class="toggle-password" onclick="togglePassword('login-password', this)">
                                <!-- SVG hình con mắt mở -->
                                <svg id="eye-open" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none"
                                     stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                                     class="lucide lucide-eye">
                                <path d="M1 12s4-7 11-7 11 7 11 7-4 7-11 7S1 12 1 12z" />
                                <circle cx="12" cy="12" r="3" />
                                </svg>
                                <!-- SVG hình con mắt đóng (ẩn ban đầu) -->
                                <svg id="eye-closed" style="display:none;" xmlns="http://www.w3.org/2000/svg" width="24" height="24"
                                     fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                                     class="lucide lucide-eye-off">
                                <path
                                    d="M17.94 17.94A10.47 10.47 0 0 1 12 19c-7 0-11-7-11-7a21.46 21.46 0 0 1 5.17-6.88M22 12s-1.1-2.1-3-4.27M2 2l20 20" />
                                </svg>
                            </span>
                        </div>
                    </div>

                    <button>OK</button>
                    <c:if test="${not empty errorLogin}">
                        <span style="color:red; display:block; margin-top:10px;">
                            <c:out value="${errorLogin}" />
                        </span>
                    </c:if>
                </form>
            </div>

            <!-- Overlay Panels -->
            <div class="overlay-container">
                <div class="overlay">
                    <div class="overlay-panel overlay-left">
                        <h1 class="title">F-Active <br />Xin chào</h1>
                        <button class="ghost" id="login">Đăng nhập
                            <i class="lni lni-arrow-left login"></i>
                        </button>
                    </div>
                    <div class="overlay-panel overlay-right">
                        <h1 class="title">Active Net <br />ticketing</h1>

                        <button class="ghost" id="register">Đăng ký
                            <i class="lni lni-arrow-right register"></i>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <script src="js/authentication-page/index.js"></script>
        <script src="js/authentication-page/register.js"></script>
        <c:if test="${not empty errorLogin}">
            <script>
                                document.addEventListener("DOMContentLoaded", function () {
                                    const container = document.getElementById("container");
                                    container.classList.remove("right-panel-active"); // đảm bảo hiện LOGIN
                                });
            </script>
        </c:if>
    </body>

</html>