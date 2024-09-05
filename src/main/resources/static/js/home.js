$(function() {
    $('select').selectric();

    function createTable(tableElement, rows, cols) {
        for (let i = 0; i < rows; i++) {
            const tr = document.createElement('tr');
            for (let j = 0; j < cols; j++) {
                const td = document.createElement('td');
                const dot = document.createElement('div');
                dot.classList.add('dot');
                td.appendChild(dot);
                tr.appendChild(td);
            }
            tableElement.appendChild(tr);
        }
    }

    function addCircle(tableElement, row, col, color) {
        const cell = tableElement.rows[row].cells[col];
        const circle = document.createElement('div');
        circle.classList.add('circle', color);
        cell.innerHTML = '';  // Clear the dot
        cell.appendChild(circle);
    }

    // Select all tables with the class 'gameTable'
    const tables = document.querySelectorAll('.gameTable');

    // Create tables and add circles
    tables.forEach((table, index) => {
        createTable(table, 6, 30);

        if (index === 0) addCircle(table, 0, 0, 'red');
        if (index === 0) addCircle(table, 0, 1, 'blue');
        if (index === 0) addCircle(table, 0, 2, 'red');
        if (index === 0) addCircle(table, 0, 3, 'blue');
        if (index === 0) addCircle(table, 0, 4, 'red');
        if (index === 0) addCircle(table, 0, 5, 'blue');
        if (index === 0) addCircle(table, 0, 6, 'red');
        if (index === 0) addCircle(table, 1, 6, 'red');
        if (index === 0) addCircle(table, 2, 6, 'red');
        if (index === 0) addCircle(table, 3, 6, 'red');
        if (index === 0) addCircle(table, 4, 6, 'red');
        if (index === 0) addCircle(table, 5, 6, 'red');
        if (index === 0) addCircle(table, 5, 7, 'red');
        if (index === 1) addCircle(table, 0, 0, 'red');
        if (index === 1) addCircle(table, 0, 1, 'blue');
        if (index === 1) addCircle(table, 0, 2, 'red');
        if (index === 1) addCircle(table, 0, 3, 'blue');
        if (index === 1) addCircle(table, 0, 4, 'red');
        if (index === 1) addCircle(table, 1, 4, 'red');
        if (index === 1) addCircle(table, 2, 4, 'red');
        if (index === 1) addCircle(table, 3, 4, 'red');
        if (index === 1) addCircle(table, 4, 4, 'red');
        if (index === 1) addCircle(table, 5, 4, 'red');
        if (index === 1) addCircle(table, 0, 5, 'blue');
        if (index === 1) addCircle(table, 0, 6, 'red');
        if (index === 1) addCircle(table, 0, 7, 'blue');
        if (index === 2) addCircle(table, 0, 0, 'red');
        if (index === 2) addCircle(table, 1, 0, 'red');
        if (index === 2) addCircle(table, 0, 1, 'blue');
        if (index === 2) addCircle(table, 1, 1, 'red');
        if (index === 2) addCircle(table, 0, 2, 'red');
        if (index === 2) addCircle(table, 1, 2, 'red');
        if (index === 2) addCircle(table, 0, 3, 'blue');
        if (index === 2) addCircle(table, 0, 4, 'red');
        if (index === 2) addCircle(table, 0, 5, 'blue');
        if (index === 2) addCircle(table, 0, 6, 'red');
        if (index === 2) addCircle(table, 1, 6, 'red');
        if (index === 2) addCircle(table, 2, 6, 'red');
        if (index === 2) addCircle(table, 3, 6, 'red');
        if (index === 2) addCircle(table, 4, 6, 'red');
        if (index === 3) addCircle(table, 0, 0, 'red');
        if (index === 3) addCircle(table, 0, 1, 'blue');
        if (index === 3) addCircle(table, 0, 2, 'red');
        if (index === 3) addCircle(table, 0, 3, 'blue');
        if (index === 3) addCircle(table, 0, 4, 'red');
        if (index === 3) addCircle(table, 0, 5, 'blue');
        if (index === 3) addCircle(table, 0, 6, 'red');
        if (index === 3) addCircle(table, 1, 6, 'red');
        if (index === 3) addCircle(table, 2, 6, 'red');
        if (index === 3) addCircle(table, 3, 6, 'red');
        if (index === 3) addCircle(table, 4, 6, 'red');
        if (index === 3) addCircle(table, 5, 6, 'red');
        if (index === 3) addCircle(table, 5, 7, 'red');
        if (index === 4) addCircle(table, 0, 0, 'red');
        if (index === 4) addCircle(table, 0, 1, 'blue');
        if (index === 4) addCircle(table, 0, 2, 'red');
        if (index === 4) addCircle(table, 0, 3, 'blue');
        if (index === 4) addCircle(table, 0, 4, 'red');
        if (index === 4) addCircle(table, 1, 4, 'red');
        if (index === 4) addCircle(table, 2, 4, 'red');
        if (index === 4) addCircle(table, 3, 4, 'red');
        if (index === 4) addCircle(table, 4, 4, 'red');
        if (index === 4) addCircle(table, 5, 4, 'red');
        if (index === 4) addCircle(table, 0, 5, 'blue');
        if (index === 4) addCircle(table, 0, 6, 'red');
        if (index === 4) addCircle(table, 0, 7, 'blue');
        if (index === 5) addCircle(table, 0, 0, 'red');
        if (index === 5) addCircle(table, 1, 0, 'red');
        if (index === 5) addCircle(table, 0, 1, 'blue');
        if (index === 5) addCircle(table, 1, 1, 'red');
        if (index === 5) addCircle(table, 0, 2, 'red');
        if (index === 5) addCircle(table, 1, 2, 'red');
        if (index === 5) addCircle(table, 0, 3, 'blue');
        if (index === 5) addCircle(table, 0, 4, 'red');
        if (index === 5) addCircle(table, 0, 5, 'blue');
        if (index === 5) addCircle(table, 0, 6, 'red');
        if (index === 5) addCircle(table, 1, 6, 'red');
        if (index === 5) addCircle(table, 2, 6, 'red');
        if (index === 5) addCircle(table, 3, 6, 'red');
        if (index === 5) addCircle(table, 4, 6, 'red');
        if (index === 6) addCircle(table, 0, 0, 'red');
        if (index === 6) addCircle(table, 0, 1, 'blue');
        if (index === 6) addCircle(table, 0, 2, 'red');
        if (index === 6) addCircle(table, 0, 3, 'blue');
        if (index === 6) addCircle(table, 0, 4, 'red');
        if (index === 6) addCircle(table, 0, 5, 'blue');
        if (index === 6) addCircle(table, 0, 6, 'red');
        if (index === 6) addCircle(table, 1, 6, 'red');
        if (index === 6) addCircle(table, 2, 6, 'red');
        if (index === 6) addCircle(table, 3, 6, 'red');
        if (index === 6) addCircle(table, 4, 6, 'red');
        if (index === 6) addCircle(table, 5, 6, 'red');
        if (index === 6) addCircle(table, 5, 7, 'red');
        if (index === 7) addCircle(table, 0, 0, 'red');
        if (index === 7) addCircle(table, 0, 1, 'blue');
        if (index === 7) addCircle(table, 0, 2, 'red');
        if (index === 7) addCircle(table, 0, 3, 'blue');
        if (index === 7) addCircle(table, 0, 4, 'red');
        if (index === 7) addCircle(table, 1, 4, 'red');
        if (index === 7) addCircle(table, 2, 4, 'red');
        if (index === 7) addCircle(table, 3, 4, 'red');
        if (index === 7) addCircle(table, 4, 4, 'red');
        if (index === 7) addCircle(table, 5, 4, 'red');
        if (index === 7) addCircle(table, 0, 5, 'blue');
        if (index === 7) addCircle(table, 0, 6, 'red');
        if (index === 7) addCircle(table, 0, 7, 'blue');
        if (index === 8) addCircle(table, 0, 0, 'red');
        if (index === 8) addCircle(table, 1, 0, 'red');
        if (index === 8) addCircle(table, 0, 1, 'blue');
        if (index === 8) addCircle(table, 1, 1, 'red');
        if (index === 8) addCircle(table, 0, 2, 'red');
        if (index === 8) addCircle(table, 1, 2, 'red');
        if (index === 8) addCircle(table, 0, 3, 'blue');
        if (index === 8) addCircle(table, 0, 4, 'red');
        if (index === 8) addCircle(table, 0, 5, 'blue');
        if (index === 8) addCircle(table, 0, 6, 'red');
        if (index === 8) addCircle(table, 1, 6, 'red');
        if (index === 8) addCircle(table, 2, 6, 'red');
        if (index === 8) addCircle(table, 3, 6, 'red');
        if (index === 8) addCircle(table, 4, 6, 'red');
    });
});