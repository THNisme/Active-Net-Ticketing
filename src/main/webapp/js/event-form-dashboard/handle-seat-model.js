const tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]')
const tooltipList = [...tooltipTriggerList].map(el => new bootstrap.Tooltip(el))

function showControl() {
    const checkOn = document.getElementById('checkIdentityTicket');
    const initBlock = document.getElementById('init-seat-container');
    const rowInput = document.getElementById('rowsSeatInput');


    checkOn.addEventListener("change", () => {
        if (checkOn.checked) {
            // rowInput.removeAttribute('disabled');
            initBlock.classList.remove('disable');
        } else {
            // rowInput.setAttribute('disabled', true);
            initBlock.classList.add('disable');
        }
    });
}

function generateSeatInputs() {
    const seatContainer = document.getElementById('seatInputs');
    const seatRowsInput = document.getElementById('rowsSeatInput').value.trim().toUpperCase();
    const message = document.getElementById('message');
    message.textContent = '';
    seatContainer.innerHTML = '';
    document.getElementById('seatModelGridContainer').innerHTML = '';

    // Validate ký tự hợp lệ (chỉ A-Z và dấu -)
    if (!/^[A-Z\-]+$/.test(seatRowsInput)) {
        message.style.color = 'var(--error-color)';
        message.textContent = '⚠️ Dãy ghế chỉ được chứa chữ cái (A-Z) và dấu gạch ngang (-)!';
        return;
    }

    let seatRows = [];

    // Dạng A-D
    if (seatRowsInput.includes('-')) {
        const parts = seatRowsInput.split('-');
        if (parts.length !== 2 || parts[0].length !== 1 || parts[1].length !== 1) {
            message.style.color = 'var(--error-color)';
            message.textContent = '⚠️ Định dạng A-D không hợp lệ!';
            return;
        }
        const start = parts[0].charCodeAt(0);
        const end = parts[1].charCodeAt(0);
        if (start > end) {
            message.style.color = 'var(--error-color)';
            message.textContent = '⚠️ Dãy bắt đầu phải nhỏ hơn dãy kết thúc!';
            return;
        }
        for (let i = start; i <= end; i++) {
            seatRows.push(String.fromCharCode(i));
        }
    } else {
        // Dạng ABCD
        seatRows = seatRowsInput.split('');
    }

    // LAYOUT GRID 3 COlS
    seatContainer.innerHTML = '';

    for (let i = 0; i < seatRows.length; i += 3) {
        const rowDiv = document.createElement('div');
        rowDiv.classList.add('row', 'mb-3');

        for (let j = 0; j < 3; j++) {
            if (seatRows[i + j]) {
                const col = document.createElement('div');
                col.classList.add('col-md-4'); // 3 cột = 12/3 = 4
                col.innerHTML = `
        <div class="input-group seat-row-input-group mb-3">
          <span class="input-group-text">Số ghế dãy <span class="span-row-lable">${seatRows[i + j]}</span></span>
          <input type="number" id="seat-row_${seatRows[i + j]}" min="1" class="form-control" placeholder="Nhập số ghế của dãy ${seatRows[i + j]}" name="seatCount[${seatRows[i + j]}]" required>
        </div>
      `;
                rowDiv.appendChild(col);
            }
        }

        seatContainer.appendChild(rowDiv);
    }


}

function generateSeatGrid() {
    const totalTickets = parseInt(document.getElementById('ticketTypeTotal').value);
    const seatRowsInput = document.getElementById('rowsSeatInput').value.trim().toUpperCase();
    const message = document.getElementById('message');
    const grid = document.getElementById('seatModelGridContainer');
    grid.innerHTML = '';
    message.textContent = '';

    if (isNaN(totalTickets) || totalTickets <= 0) {
        message.style.color = 'var(--error-color)';
        message.textContent = '⚠️ Tổng vé phải là số lớn hơn 0!';
        return;
    }

    // Validate ký tự hợp lệ
    if (!/^[A-Z\-]+$/.test(seatRowsInput)) {

        message.style.color = 'var(--error-color)';
        message.textContent = '⚠️ Dãy ghế chỉ được chứa chữ cái (A-Z) và dấu gạch ngang (-)!';
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
        const count = parseInt(document.getElementById(`seat-row_${row}`).value);
        if (isNaN(count) || count <= 0) {
            message.style.color = 'var(--error-color)';
            message.textContent = `⚠️ Số ghế ở dãy ${row} phải lớn hơn 0!`;
            return;
        }
        rowData[row] = count;
        totalCount += count;
    }

    if (totalCount !== totalTickets) {
        message.style.color = 'var(--error-color)';
        message.textContent = `⚠️ Tổng số ghế (${totalCount}) không khớp với tổng vé (${totalTickets})!`;
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
            seat.style.backgroundColor = 'var(--pink)';
            seat.style.cursor = 'pointer';
            rowDiv.appendChild(seat);
        }
        grid.appendChild(rowDiv);
    }
}

showControl();