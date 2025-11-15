/* REGISTER PAGE JAVASCRIPT */
/* ======================== */

/* ------- CHECK USERNAME REALTIME ------- */
document.addEventListener("DOMContentLoaded", function () {
    const usernameInput = document.getElementById("register-username");
    const usernameMsg = document.getElementById("username-feedback");
    const form = usernameInput.closest("form");

    let usernameTaken = false;

    usernameInput.addEventListener("input", function () {
        const username = usernameInput.value.trim();

        if (username.length === 0) {
            usernameMsg.textContent = "";
            return;
        }

        fetch(`${CONTEXT}/check?username=${encodeURIComponent(username)}`)
                .then(res => res.json())
                .then(data => {
                    usernameTaken = data.exists;

                    if (usernameTaken) {
                        usernameMsg.textContent = "Tên này đã được sử dụng!";
                        usernameMsg.style.color = "red";
                    } else {
                        usernameMsg.textContent = "✓ Tên hợp lệ";
                        usernameMsg.style.color = "green";
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

