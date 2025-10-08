
document.addEventListener("DOMContentLoaded", function() {
  document.querySelectorAll(".circle").forEach(function(circle) {
    let value = parseInt(circle.textContent);
    if (value === 0) {
      circle.classList.add("black");
    } else {
      circle.classList.add("pink");
    }
  });
});

