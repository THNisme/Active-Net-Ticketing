function generateSeatInputs() {
  const seatContainer = document.getElementById('seatInputs');
  const seatRowsInput = document.getElementById('seatRows').value.trim().toUpperCase();
  const message = document.getElementById('message');
  message.textContent = '';
  seatContainer.innerHTML = '';
  document.getElementById('seatGrid').innerHTML = '';

  // Validate ký tự hợp lệ (chỉ A-Z và dấu -)
  if (!/^[A-Z\-]+$/.test(seatRowsInput)) {
    message.textContent = '❌ Dãy ghế chỉ được chứa chữ cái (A-Z) và dấu gạch ngang (-)!';
    return;
  }

  let seatRows = [];

  // Dạng A-D
  if (seatRowsInput.includes('-')) {
    const parts = seatRowsInput.split('-');
    if (parts.length !== 2 || parts[0].length !== 1 || parts[1].length !== 1) {
      message.textContent = '❌ Định dạng A-D không hợp lệ!';
      return;
    }
    const start = parts[0].charCodeAt(0);
    const end = parts[1].charCodeAt(0);
    if (start > end) {
      message.textContent = '❌ Dãy bắt đầu phải nhỏ hơn dãy kết thúc!';
      return;
    }
    for (let i = start; i <= end; i++) {
      seatRows.push(String.fromCharCode(i));
    }
  } else {
    // Dạng ABCD
    seatRows = seatRowsInput.split('');
  }

  // Sinh input nhập số ghế
  seatRows.forEach(row => {
    const div = document.createElement('div');
    div.innerHTML = `
      <label>Số ghế dãy ${row}: </label>
      <input type="number" id="row_${row}" min="1" placeholder="Nhập số ghế của dãy ${row}">
    `;
    seatContainer.appendChild(div);
  });
}

function generateSeatGrid() {
  const totalTickets = parseInt(document.getElementById('totalTickets').value);
  const seatRowsInput = document.getElementById('seatRows').value.trim().toUpperCase();
  const message = document.getElementById('message');
  const grid = document.getElementById('seatGrid');
  grid.innerHTML = '';
  message.textContent = '';

  if (isNaN(totalTickets) || totalTickets <= 0) {
    message.textContent = '❌ Tổng vé phải là số lớn hơn 0!';
    return;
  }

  // Validate ký tự hợp lệ
  if (!/^[A-Z\-]+$/.test(seatRowsInput)) {
    message.textContent = '❌ Dãy ghế chỉ được chứa chữ cái (A-Z) và dấu gạch ngang (-)!';
    return;
  }

  let seatRows = [];
  if (seatRowsInput.includes('-')) {
    const [start, end] = seatRowsInput.split('-');
    for (let i = start.charCodeAt(0); i <= end.charCodeAt(0); i++) {
      seatRows.push(String.fromCharCode(i));
    }
  } else {
    seatRows = seatRowsInput.split('');
  }

  // Lấy tổng ghế từng dãy
  let totalCount = 0;
  const rowData = {};
  for (const row of seatRows) {
    const count = parseInt(document.getElementById(`row_${row}`).value);
    if (isNaN(count) || count <= 0) {
      message.textContent = `❌ Số ghế ở dãy ${row} phải lớn hơn 0!`;
      return;
    }
    rowData[row] = count;
    totalCount += count;
  }

  if (totalCount !== totalTickets) {
    message.textContent = `❌ Tổng số ghế (${totalCount}) không khớp với tổng vé (${totalTickets})!`;
    return;
  }

  message.style.color = 'green';
  message.textContent = '✅ Dữ liệu hợp lệ! Mô hình ghế đã được tạo.';

  // Tạo mô hình ghế
  for (const row of seatRows) {
    const rowDiv = document.createElement('div');
    rowDiv.style.display = 'flex';
    rowDiv.style.marginBottom = '5px';

    for (let i = 1; i <= rowData[row]; i++) {
      const seat = document.createElement('div');
      seat.textContent = `${row}${i}`;
      seat.style.border = '1px solid #000';
      seat.style.padding = '6px';
      seat.style.margin = '2px';
      seat.style.width = '40px';
      seat.style.textAlign = 'center';
      seat.style.borderRadius = '4px';
      seat.style.backgroundColor = '#f0f0f0';
      seat.style.cursor = 'pointer';
      seat.addEventListener('click', () => {
        seat.style.backgroundColor =
          seat.style.backgroundColor === 'lightgreen' ? '#f0f0f0' : 'lightgreen';
      });
      rowDiv.appendChild(seat);
    }
    grid.appendChild(rowDiv);
  }
}