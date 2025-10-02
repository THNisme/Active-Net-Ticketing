// // Xử lý Next
// document.querySelectorAll('.next-btn').forEach(btn => {
//   btn.addEventListener('click', () => {
//     const currentTab = btn.closest('.tab-pane');
//     const inputs = currentTab.querySelectorAll('input[required]');
//     let valid = true;
//     inputs.forEach(input => {
//       if (!input.value.trim()) {
//         input.classList.add('is-invalid');
//         valid = false;
//       } else {
//         input.classList.remove('is-invalid');
//       }
//     });
//     if (valid) {
//       const nextTabId = btn.getAttribute('data-next');
//       const nextTab = document.getElementById(nextTabId);
//       nextTab.disabled = false;
//       new bootstrap.Tab(nextTab).show();
//     }
//   });
// });

// // Xử lý Previous
// document.querySelectorAll('.prev-btn').forEach(btn => {
//   btn.addEventListener('click', () => {
//     const prevTabId = btn.getAttribute('data-prev');
//     const prevTab = document.getElementById(prevTabId);
//     new bootstrap.Tab(prevTab).show();
//   });
// });


// Chặn click vào tab nếu đang disabled
document.querySelectorAll('#stepTabs .nav-link').forEach(tab => {
  tab.addEventListener('click', e => {
    if (tab.classList.contains('disabled')) {
      e.preventDefault();
      e.stopPropagation();
    }
  });
});

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

    if (!valid) return; // chặn Next nếu chưa valid

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

  if (!valid) {
    alert('Vui lòng điền đầy đủ thông tin bắt buộc.');
    return;
  }

  alert('Form submitted!');
  // e.target.submit(); // mở lại nếu muốn submit thật
});

