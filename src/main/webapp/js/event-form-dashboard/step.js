// Chặn click vào tab nếu đang disabled
document.querySelectorAll('#stepTabs .nav-link').forEach(tab => {
    tab.addEventListener('click', e => {
        if (tab.classList.contains('disabled')) {
            e.preventDefault();
            e.stopPropagation();
        }
    });
});

// Hàm check ngày bắt đầu - kết thúc
function validateDates() {
    const start = document.getElementById('eventDateStart');
    const end = document.getElementById('eventDateEnd');
    if (!start || !end)
        return true;

    if (!start.value || !end.value) {
        end.setCustomValidity('');
        return true;
    }

    const dStart = new Date(start.value);
    const dEnd = new Date(end.value);

    if (dEnd > dStart) {
        end.setCustomValidity('');
        return true;
    } else {
        end.setCustomValidity('Ngày kết thúc phải lớn hơn ngày bắt đầu');
        return false;
    }
}

// Hàm check riêng select & textarea
function validateCustomFields() {
    let valid = true;

    const eventCategory = document.getElementById('eventCategory');
    const eventPlace = document.getElementById('eventPlace');

    if (eventCategory) {
        if (eventCategory.value === '0') {
            eventCategory.setCustomValidity('Vui lòng chọn danh mục sự kiện');
            eventCategory.reportValidity();
            valid = false;
        } else {
            eventCategory.setCustomValidity('');
        }
    }

    if (eventPlace) {
        if (eventPlace.value === '0') {
            eventPlace.setCustomValidity('Vui lòng chọn địa điểm tổ chức');
            eventPlace.reportValidity();
            valid = false;
        } else {
            eventPlace.setCustomValidity('');
        }
    }

    return valid;
}

// Xử lý Next
document.querySelectorAll('.next-btn').forEach(btn => {
    btn.addEventListener('click', () => {
        const currentTabPane = btn.closest('.tab-pane');
        const inputs = currentTabPane.querySelectorAll('input[required], select[required], textarea[required]');
        let valid = true;

        inputs.forEach(input => {
            if (!input.value.trim()) {
                input.classList.add('is-invalid');
                valid = false;
            } else {
                input.classList.remove('is-invalid');
            }
        });

        // Check ngày bắt đầu - kết thúc
        if (!validateDates()) {
            document.getElementById('eventDateEnd').reportValidity();
            valid = false;
        }

        // Check select
        if (!validateCustomFields()) {
            valid = false;
        }

        if (!valid)
            return; // chặn Next nếu chưa valid

        // Nếu hợp lệ thì mở tab tiếp theo
        const nextTabId = btn.getAttribute('data-next');
        const nextTab = document.getElementById(nextTabId);

        nextTab.classList.remove('disabled');
        nextTab.removeAttribute('aria-disabled');

        new bootstrap.Tab(nextTab).show();
    });
});

// Xử lý Previous
document.querySelectorAll('.prev-btn').forEach(btn => {
    btn.addEventListener('click', () => {
        const prevTabId = btn.getAttribute('data-prev');
        const prevTab = document.getElementById(prevTabId);
        new bootstrap.Tab(prevTab).show();
    });
});

// Submit
document.getElementById('eventForm').addEventListener('submit', e => {
    e.preventDefault();

    const inputs = e.target.querySelectorAll('input[required], select[required], textarea[required]');
    let valid = true;

    inputs.forEach(input => {
        if (!input.value.trim()) {
            input.classList.add('is-invalid');
            valid = false;
        } else {
            input.classList.remove('is-invalid');
        }
    });

    // Check select
    if (!validateCustomFields()) {
        valid = false;
    }

    // Check ngày bắt đầu - kết thúc
    if (!validateDates()) {
        document.getElementById('eventDateEnd').reportValidity();
        valid = false;
    }

    if (!valid)
        return;

    // alert('Form submitted!');
    e.target.submit(); // mở lại nếu muốn submit thật
});
