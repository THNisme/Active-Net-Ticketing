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
  // imageUploadURL: '/upload-image',   // servlet xử lý upload ảnh - khi 
  // videoUploadURL: '/upload-video',   // servlet xử lý upload video
  imageUploadParam: 'file',          // tên param input (phải trùng servlet)
  videoUploadParam: 'file',
  imageAllowedTypes: ['jpeg', 'jpg', 'png', 'gif'],
  videoAllowedTypes: ['mp4', 'webm', 'ogg'],

  imageUpload: false,
  imagePaste: true,
  imageDefaultWidth: 300,
  imageDefaultDisplay: 'block'
});


//Muốn upload ảnh thật qua servlet ===> Xóa imageUpload: false, giữ lại imageUploadURL và viết servlet
