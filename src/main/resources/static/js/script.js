document.addEventListener("DOMContentLoaded", () => {
    createChessboard();
    setupFieldListener();
    setupSliderListener();
    resetBoardState();
    initializeChessGrid();
});

/* ------------------------------------------------------------------------------------- */

function setupFieldListener() {
    const fieldButtons = document.querySelectorAll('input[name="field-type"]');
    
    fieldButtons.forEach((field) => {
        field.addEventListener('change', handleFieldChange);
    });
}

function handleFieldChange(event) {
    const selectedValue = event.target.value;
    if (selectedValue === 'assembled') {
        loadBoardState(initial_board);
    } else if (selectedValue === 'empty') {
        loadBoardState(empty_board);
    }
}

/* ------------------------------------------------------------------------------------- */

function setupSliderListener() {
    const slider = document.getElementById('depthrange');
    const numberDisplay = document.getElementById('depth-display');

    const updateDepthDisplay = () => {
        numberDisplay.textContent = slider.value;
    };

    updateDepthDisplay();

    slider.addEventListener('input', updateDepthDisplay);
}

/* ------------------------------------------------------------------------------------- */

function resetBoardState() {
    const resetButton = document.getElementById('reset-btn');
    
    resetButton.addEventListener('click', () => {
        const fieldState = getFieldState();
        
        if (fieldState === 'assembled') {
            loadBoardState(initial_board);
        } else if (fieldState === 'empty') {
            loadBoardState(empty_board);
        }
    });
}

function getFieldState() {
    const selectedRadio = document.querySelector('input[name="field-type"]:checked');
    return selectedRadio ? selectedRadio.value : null;
}

/* ------------------------------------------------------------------------------------- */

function createChessboard() {
    const chessboard = document.getElementById("chessboard");

    const columns = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'];

    for (let row = 8; row >= 1; row--) {
        for (let col = 0; col < 8; col++) {
            const squareId = `${columns[col]}${row}`;
            const square = document.createElement("div");
            square.className = `square ${(row + col) % 2 === 0 ? "light" : "dark"}`;
            square.id = squareId;
            square.dataset.piece = "empty";
            chessboard.appendChild(square);
        }
    }
}

/* ------------------------------------------------------------------------------------- */

function loadBoardState(boardState) {
    Object.entries(boardState).forEach(([key, value]) => {
        const square = document.getElementById(key);
        if (square) {
            square.dataset.piece = value;
        }
    });
}

/* ------------------------------------------------------------------------------------- */

function initializeChessGrid() {
    const gridCells = document.querySelectorAll('.square');
    const pieceMenu = document.getElementById('piece-menu');
    let activeCell = null;

    function showPieceMenu(cell, x, y) {
        pieceMenu.style.display = 'block';
        pieceMenu.style.left = `${x}px`;
        pieceMenu.style.top = `${y}px`;
        activeCell = cell;
    }

    function hidePieceMenu() {
        pieceMenu.style.display = 'none';
        activeCell = null;
    }

    function onGridCellClick(event) {
        const { clientX, clientY } = event;
        showPieceMenu(event.currentTarget, clientX, clientY);
    }

    function onPieceSelect(event) {
        const selectedPiece = event.target.dataset.piece;
        if (selectedPiece && activeCell) {
            activeCell.dataset.piece = selectedPiece;
            hidePieceMenu();
        }
    }

    function onDocumentClick(event) {
        if (
            !pieceMenu.contains(event.target) &&
            !Array.from(gridCells).includes(event.target)
        ) {
            hidePieceMenu();
        }
    }

    gridCells.forEach(cell => {
        cell.addEventListener('click', onGridCellClick);
    });

    pieceMenu.addEventListener('click', onPieceSelect);
    document.addEventListener('click', onDocumentClick);
}
