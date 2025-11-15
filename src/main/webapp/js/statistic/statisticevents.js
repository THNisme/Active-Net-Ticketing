document.addEventListener("DOMContentLoaded", function () {
    // ===== Vòng tròn phần trăm =====
    document.querySelectorAll('.progress-ring').forEach(ring => {
        const circle = ring.querySelector('.fg');
        const text = ring.querySelector('.percent-text');
        const radius = circle.r.baseVal.value;
        const circumference = 2 * Math.PI * radius;
        const targetPercent = parseFloat(ring.getAttribute('data-percent')) || 0;

        circle.style.strokeDasharray = `${circumference}`;
        circle.style.strokeDashoffset = circumference;

        function setProgress(percent) {
            const offset = circumference - (percent / 100) * circumference;
            circle.style.strokeDashoffset = offset;
            text.textContent = Math.round(percent) + '%';
        }

        let current = 0;
        function animate() {
            if (current <= targetPercent) {
                setProgress(current);
                current++;
                requestAnimationFrame(animate);
            } else {
                setProgress(targetPercent);
            }
        }
        animate();
    });

    // ===== Thanh tiến trình =====
    document.querySelectorAll('.progress-bar').forEach(bar => {
        const target = parseFloat(bar.getAttribute('data-target')) || 0;
        const percentText = bar.closest('td').querySelector('.percent');
        let width = 0;

        const animateBar = () => {
            if (width <= target) {
                bar.style.width = width + '%';
                percentText.textContent = Math.floor(width) + '%';
                width += 1;
                requestAnimationFrame(animateBar);
            } else {
                bar.style.width = target + '%';
                percentText.textContent = Math.floor(target) + '%';
            }
        };
        animateBar();
    });
});
