
document.addEventListener("DOMContentLoaded", function () {
    new FroalaEditor("#eventDescription", {
        toolbarButtons: [
            'bold', 'italic', 'underline', 'strikeThrough', '|',
            'formatUL', 'formatOL', 'align', '|',
            'fontSize', 'insertLink', '|',
            'undo', 'redo', 'html'
        ],
        charCounterCount: true,
        placeholderText: 'Hãy viết mô tả cho sự kiện này...',
        quickInsertTags: ['h1', 'h2', 'h3', 'p'],
        // ================== CẬP NHẬT QUAN TRỌNG ==================
        // Kích hoạt URL để trỏ tới servlet xử lý upload ảnh của bạn.
        // Sử dụng EL để lấy đường dẫn gốc của ứng dụng một cách chính xác.
        imageUploadURL: '/Active_Net_Ticketing/froala-upload',
        // =========================================================

        // videoUploadURL: '/upload-video', // Bạn có thể làm tương tự cho video nếu cần

        imageUploadParam: 'file', // Tên param input (phải trùng với request.getPart("file") trong servlet)
        videoUploadParam: 'file',
        imageAllowedTypes: ['jpeg', 'jpg', 'png', 'gif'],
        videoAllowedTypes: ['mp4', 'webm', 'ogg'],
        // Dòng "imageUpload: false" đã được xóa để BẬT tính năng upload
        imagePaste: true,
        imageDefaultWidth: 300,
        imageDefaultDisplay: 'block',
        imageUpload: true, // Cho phép upload ảnh khi kéo thả
        quickInsertEnabled: true, // Bật tính năng Quick Insert
        imageInsertButtons: ['imageUpload', 'imageByURL'], // Cho phép chọn upload hoặc URL

        imageUploadMethod: 'POST', // <== BẮT BUỘC ĐỂ ENABLE input click
        fileUpload: true, // <== Quan trọng để Froala tạo input file
        fileUploadURL: '/Active_Net_Ticketing/froala-upload', // có thể trỏ chung với ảnh
        fileUploadMethod: 'POST'
    });
});
