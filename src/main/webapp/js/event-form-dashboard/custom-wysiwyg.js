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
});

document.getElementById("show-content").addEventListener("click", () => {
  const content = editor.html.get();
  document.getElementById("eventDescription").value = content;
});