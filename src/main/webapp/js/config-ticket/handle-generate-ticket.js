/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


const hasSeatCheckbox = document.getElementById('hasSeat');
    const seatConfigSection = document.getElementById('seatConfigSection');
    const seatRowsContainer = document.getElementById('seatRowsContainer');
    const submitBtn = document.getElementById('generateTicketBtn');
    const generateBtn = document.getElementById('generateSeatRows');
    const rowInput = document.getElementById('rowInput');
    const seatTotalCheck = document.getElementById('seatTotalCheck');
    const ticketQuantityInput = document.getElementById('ticketQuantity');

    function toggleSubmit(disabled) {
      submitBtn.disabled = disabled;
    }

    hasSeatCheckbox.addEventListener('change', () => {
      if (hasSeatCheckbox.checked) {
        seatConfigSection.style.display = 'block';
        toggleSubmit(true);
      } else {
        seatConfigSection.style.display = 'none';
        seatRowsContainer.innerHTML = '';
        seatTotalCheck.textContent = '';
        toggleSubmit(false);
      }
    });

    // chỉ cho nhập chữ cái và dấu gạch ngang
    rowInput.addEventListener('input', (e) => {
      let value = e.target.value.toUpperCase();
      value = value.replace(/[^A-Z\-]/g, '');
      e.target.value = value;
    });

    generateBtn.addEventListener('click', () => {
      seatRowsContainer.innerHTML = '';
      seatTotalCheck.textContent = '';
      const rawValue = rowInput.value.trim().toUpperCase();

      if (!rawValue) {
        alert('Vui lòng nhập hàng ghế trước!');
        return;
      }

      let rows = [];

      if (rawValue.includes('-')) {
        const [start, end] = rawValue.split('-');
        if (start.length !== 1 || end.length !== 1) {
          alert('Khoảng hàng ghế không hợp lệ! Ví dụ: A-D');
          return;
        }
        const startChar = start.charCodeAt(0);
        const endChar = end.charCodeAt(0);
        for (let i = startChar; i <= endChar; i++) {
          rows.push(String.fromCharCode(i));
        }
      } else {
        rows = rawValue.split('');
      }

      const grid = document.createElement('div');
      grid.className = 'row row-cols-4 g-4'; // 3 cột, có khoảng cách giữa các item

      rows.forEach(row => {
        const div = document.createElement('div');
        div.classList.add('col');
        div.innerHTML = `
      <div class="input-group">
        <span class="input-group-text fw-bold">${row}</span>
        <input type="hidden" name="rowLabels[]" value="${row}">
        <input type="number" class="form-control seat-count"
           name="seatCounts[]" placeholder="Số ghế hàng ${row}" min="1" required>
      </div>
    `;
        grid.appendChild(div);
      });

      seatRowsContainer.appendChild(grid);
      validateSeatInputs();
    });

    function validateSeatInputs() {
      const seatInputs = document.querySelectorAll('.seat-count');
      toggleSubmit(true);

      seatInputs.forEach(input => {
        input.addEventListener('input', checkTotalSeats);
      });

      checkTotalSeats(); // check ngay khi sinh
    }

    function checkTotalSeats() {
      const seatInputs = document.querySelectorAll('.seat-count');
      const totalSeats = Array.from(seatInputs)
        .map(inp => parseInt(inp.value) || 0)
        .reduce((a, b) => a + b, 0);

      const totalTickets = parseInt(ticketQuantityInput.value) || 0;

      const allFilled = Array.from(seatInputs).every(inp => inp.value && inp.value > 0);

      if (allFilled) {
        if (totalSeats === totalTickets) {
          seatTotalCheck.textContent = '';
          toggleSubmit(false);
        } else {
          seatTotalCheck.textContent = `❌ Tổng số ghế (${totalSeats}) phải bằng tổng số vé (${totalTickets})`;
          toggleSubmit(true);
        }
      } else {
        seatTotalCheck.textContent = '⚠️ Vui lòng nhập đủ số ghế cho từng hàng.';
        toggleSubmit(true);
      }
    }