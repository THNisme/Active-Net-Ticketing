document.addEventListener("DOMContentLoaded", function () {

    /* ===== PROGRESS RING ===== */
    document.querySelectorAll('.progress-ring').forEach(ring => {
        const fg = ring.querySelector('.fg');
        const text = ring.querySelector('.percent-text');
        const radius = fg.r.baseVal.value;
        const circumference = 2 * Math.PI * radius;

        fg.style.strokeDasharray = `${circumference}`;
        fg.style.strokeDashoffset = circumference;

        let target = parseFloat(ring.getAttribute('data-percent')) || 0;
        let current = 0;

        function animateRing() {
            if (current <= target) {
                fg.style.strokeDashoffset =
                    circumference - (current / 100) * circumference;
                text.innerHTML = current + "%";
                current++;
                requestAnimationFrame(animateRing);
            }
        }
        animateRing();
    });


    /* ===== PROGRESS BARS ===== */
    document.querySelectorAll('.progress-bar').forEach(bar => {
        const target = parseFloat(bar.dataset.target) || 0;
        const label = bar.closest('td').querySelector('.percent');
        let width = 0;

        function animateBar() {
            if (width <= target) {
                bar.style.width = width + "%";
                label.textContent = width + "%";
                width++;
                requestAnimationFrame(animateBar);
            }
        }
        animateBar();
    });


    /* ===== DROPDOWN ===== */
    const btn = document.getElementById("toggleEventList");
    const dropdown = document.getElementById("eventDropdown");
    const box = btn.parentElement;

    box.addEventListener("mouseenter", () => dropdown.classList.add("show"));
    box.addEventListener("mouseleave", () => dropdown.classList.remove("show"));
});
