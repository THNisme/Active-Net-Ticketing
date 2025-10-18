const freeCheck = document.getElementById('checkFreeTicket');
const priceInput = document.getElementById('ticketTypePrice');

freeCheck.addEventListener("change", () => {
  if (freeCheck.checked) {
    priceInput.value = 0;
    priceInput.readOnly = true;
  } else {
    priceInput.value = "";
    priceInput.readOnly = false;
  }
});
