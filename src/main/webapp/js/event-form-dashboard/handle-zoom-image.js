let scale = 1;
let posX = 0, posY = 0;
let startX, startY;
let isDragging = false;

const img = document.getElementById("modalZoneMapImage");
const wrapper = document.getElementById("modalZoneMapImageWrapper");

img.addEventListener("dragstart", e => e.preventDefault());

function applyTransform() {
  img.style.transform = `translate(${posX}px, ${posY}px) scale(${scale})`;
}

function zoomIn() {
  scale += 0.1;
  applyTransform();
}

function zoomOut() {
  if (scale > 0.2) {
    scale -= 0.1;
    applyTransform();
  }
}

// Zoom bằng lăn chuột
wrapper.addEventListener("wheel", function (e) {
  e.preventDefault();
  if (e.deltaY < 0) zoomIn();
  else zoomOut();
});

// Drag để di chuyển ảnh
wrapper.addEventListener("mousedown", function (e) {
  isDragging = true;
  wrapper.style.cursor = "grabbing";
  startX = e.clientX - posX;
  startY = e.clientY - posY;
});

wrapper.addEventListener("mousemove", function (e) {
  if (!isDragging) return;
  posX = e.clientX - startX;
  posY = e.clientY - startY;
  applyTransform();
});

wrapper.addEventListener("mouseup", function () {
  isDragging = false;
  wrapper.style.cursor = "grab";
});

wrapper.addEventListener("mouseleave", function () {
  isDragging = false;
  wrapper.style.cursor = "grab";
});