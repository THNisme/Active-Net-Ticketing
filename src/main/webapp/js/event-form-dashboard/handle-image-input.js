const fileInput = document.getElementById('fileInput');
const preview = document.getElementById('preview');
const previewImg = document.getElementById('previewImg');
const status = document.getElementById('status');
const errorMsg = document.getElementById('errorMsg');
const meta = document.getElementById('meta');
const chooseBtn = document.getElementById('chooseBtn');
const clearBtn = document.getElementById('clearBtn');

const REQUIRED_WIDTH = 1280;
const REQUIRED_HEIGHT = 720;

const isUpdate = status.dataset.isUpdate === "true";

status.textContent = isUpdate
        ? "Nhấp để chọn ảnh mới (nếu muốn thay đổi)"
        : "* Vui lòng chọn ảnh";
        
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
    meta.style.display = 'block';
    status.textContent = file.name;
    clearBtn.style.display = 'inline-block';
}
function clearAll() {
    preview.style.display = 'none';
    previewImg.src = '';
    meta.style.display = 'none';
    status.textContent = "* Vui lòng chọn ảnh";
    clearBtn.style.display = 'none';
    clearError();
    fileInput.value = '';
}
function validateImageFile(file) {
    if (!file.type.startsWith('image/')) {
        showError('Tệp không phải ảnh.');
        return;
    }
    const img = new Image();
    img.onload = () => {
        if (img.naturalWidth === REQUIRED_WIDTH && img.naturalHeight === REQUIRED_HEIGHT) {
            setAccepted(file);
        } else {
            showError(`Kích thước sai: ${img.naturalWidth}×${img.naturalHeight}, cần ${REQUIRED_WIDTH}×${REQUIRED_HEIGHT}.`);
        }
        URL.revokeObjectURL(img.src);
    };
    img.onerror = () => showError('Không thể đọc ảnh.');
    img.src = URL.createObjectURL(file);
}

chooseBtn.addEventListener('click', () => fileInput.click());
fileInput.addEventListener('change', e => {
    const f = e.target.files && e.target.files[0];
    if (f)
        validateImageFile(f);
});
clearBtn.addEventListener('click', e => {
    e.preventDefault();
    clearAll();
});
