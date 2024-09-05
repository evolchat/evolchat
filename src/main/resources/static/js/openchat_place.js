$(function() {
    $('select').selectric();

    // Function to create a table with dots
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

    // Create and append a table to a specific container
    function createTableContainer(selector, rows, cols) {
        const tableWrapper = document.createElement('div');
        tableWrapper.classList.add('table-wrapper');

        const table = document.createElement('table');
        table.classList.add('gameTable');
        tableWrapper.appendChild(table);

        createTable(table, rows, cols);

        document.querySelector(selector).appendChild(tableWrapper);
    }

    // Create the left table (14x6)
    createTableContainer('.dot-left-section', 6, 14);

    // Create the top-right table (45x6)
    createTableContainer('.dot-top-right', 6, 45);

    // Create the bottom-right table (70x6)
    createTableContainer('.dot-bottom-right', 6, 70);

    // Select all tables with the class 'gameTable' and add circles if necessary
    const tables = document.querySelectorAll('.gameTable');

    // Add sample circles to the first table (left-section)
    const firstTable = tables[0];
    addCircle(firstTable, 0, 0, 'red');
    addCircle(firstTable, 1, 1, 'blue');
    addCircle(firstTable, 2, 2, 'red');

    // Add sample circles to the second table (top-right)
    const secondTable = tables[1];
    addCircle(secondTable, 0, 0, 'red');
    addCircle(secondTable, 1, 1, 'blue');

    // Add sample circles to the third table (bottom-right)
    const thirdTable = tables[2];
    addCircle(thirdTable, 0, 0, 'red');
    addCircle(thirdTable, 1, 1, 'blue');
});