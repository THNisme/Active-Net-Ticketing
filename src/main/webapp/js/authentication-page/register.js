/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


document.addEventListener("DOMContentLoaded", function () {
    const usernameInput = document.getElementById("register-username");
    const errorMsg = document.getElementById("username-feedback");
    const form = usernameInput.closest("form");
    let usernameTaken = false;

    usernameInput.addEventListener("input", function () {
        const username = usernameInput.value.trim();
        if (!username)
            return;

        fetch(`${CONTEXT}/check?username=${encodeURIComponent(username)}`)
                .then(res => res.json())
                .then(data => {
                    usernameTaken = data.exists;
                    if (usernameTaken) {
                        errorMsg.textContent = "Tên này đã được sử dụng!";
                    } else {
                        errorMsg.textContent = "";
                    }
                })
                .catch(err => console.error(err));
    });

    form.addEventListener("submit", function (e) {
        if (usernameTaken) {
            e.preventDefault();

        }
    });
});
