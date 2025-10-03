const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
const tooltipList = [...tooltipTriggerList].map(el => new bootstrap.Tooltip(el))

function showControl() {
  const checkOn = document.getElementById('checkIdentityTicket');
  const initBlock = document.getElementById('init-seat-container');
  const rowInput = document.getElementById('rowsSeatInput');
  const colInput = document.getElementById('colsSeatInput');

  checkOn.addEventListener("change", () => {
    if (checkOn.checked) {
      rowInput.removeAttribute('disabled');
      colInput.removeAttribute('disabled');
      initBlock.classList.remove('disable');
    } else {
      rowInput.setAttribute('disabled', true);
      colInput.setAttribute('disabled', true);
      initBlock.classList.add('disable');
    }
  });
}

function parseRows(input) {
  input = input.trim().toUpperCase();
  if (input.includes('-')) {
    const [start, end] = input.split('-');
    const startCode = start.charCodeAt(0);
    const endCode = end.charCodeAt(0);
    const rows = [];
    for (let c = startCode; c <= endCode; c++) {
      rows.push(String.fromCharCode(c));
    }
    return rows;
  } else {
    return input.split('');
  }
}

function generateSeats() {
  const rowsInput = document.getElementById('rowsSeatInput').value;
  const colsInput = parseInt(document.getElementById('colsSeatInput').value);
  const container = document.getElementById('seatModelContainer');
  container.innerHTML = '';

  if (!rowsInput || !colsInput) {
    alert("Vui lòng nhập đủ thông tin (Dãy ghế - Số ghế)");
    return;
  }

  const rows = parseRows(rowsInput);
  const grid = document.createElement('div');
  grid.className = 'seat-grid';

  rows.forEach(row => {
    const rowDiv = document.createElement('div');
    rowDiv.className = 'seat-row';
    for (let i = 1; i <= colsInput; i++) {
      const seat = document.createElement('div');
      seat.className = 'seat';
      seat.textContent = row + i;
      rowDiv.appendChild(seat);
    }
    grid.appendChild(rowDiv);
  });

  container.appendChild(grid);
}

showControl();
parseRows();
generateSeats();