const editor = new FroalaEditor("#eventDescription", {
    toolbarButtons: [
        'bold', 'italic', 'underline', 'strikeThrough', '|',
        'formatUL', 'formatOL', '|',
        'align', 'insertImage', 'insertLink', 'insertVideo', '|',
        'undo', 'redo', 'html'
    ],
    charCounterCount: true,
    placeholderText: 'Hãy viết mô tả cho sự kiện này...',
    quickInsertTags: ['h1', 'h2', 'h3', 'p'],

    // ================== CẬP NHẬT QUAN TRỌNG ==================
    // Kích hoạt URL để trỏ tới servlet xử lý upload ảnh của bạn.
    // Sử dụng EL để lấy đường dẫn gốc của ứng dụng một cách chính xác.
    imageUploadURL: '${pageContext.request.contextPath}/froala-upload',
    // =========================================================

    // videoUploadURL: '/upload-video', // Bạn có thể làm tương tự cho video nếu cần

    imageUploadParam: 'file', // Tên param input (phải trùng với request.getPart("file") trong servlet)
    videoUploadParam: 'file',
    imageAllowedTypes: ['jpeg', 'jpg', 'png', 'gif'],
    videoAllowedTypes: ['mp4', 'webm', 'ogg'],

    // Dòng "imageUpload: false" đã được xóa để BẬT tính năng upload
    imagePaste: true,
    imageDefaultWidth: 300,
    imageDefaultDisplay: 'block'
});