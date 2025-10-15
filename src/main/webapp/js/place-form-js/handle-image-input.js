const fileInput = document.getElementById('fileInput');
const preview = document.getElementById('preview');
const previewImg = document.getElementById('previewImg');
const status = document.getElementById('status');
const errorMsg = document.getElementById('errorMsg');
const chooseBtn = document.getElementById('chooseBtn');
const clearBtn = document.getElementById('clearBtn');

function showError(msg) {
  errorMsg.style.display = 'block';
  errorMsg.textContent = msg;
  status.textContent = 'Lỗi';
}

function clearError() {
  errorMsg.style.display = 'none';
  errorMsg.textContent = '';
}

function setAccepted(file) {
  clearError();
  preview.style.display = 'flex';
  previewImg.src = URL.createObjectURL(file);
  status.textContent = file.name;
  clearBtn.style.display = 'inline-block';
}

function clearAll() {
  preview.style.display = 'none';
  previewImg.src = '';
  status.textContent = 'Chưa có ảnh';
  clearBtn.style.display = 'none';
  clearError();
  fileInput.value = '';
}

function validateImageFile(file) {
  if (!file.type.startsWith('image/')) {
    showError('Tệp không phải ảnh.');
    return;
  }

  // Không kiểm tra kích thước nữa
  setAccepted(file);
}

chooseBtn.addEventListener('click', () => fileInput.click());
fileInput.addEventListener('change', e => {
  const f = e.target.files && e.target.files[0];
  if (f) validateImageFile(f);
});
clearBtn.addEventListener('click', e => {
  e.preventDefault();
  clearAll();
});

clearAll();
