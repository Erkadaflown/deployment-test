
document.addEventListener("DOMContentLoaded", () => {
    const chessboard = document.getElementById("chessboard");
    setupSlider('depthrange', 'depth-display');
    createChessboard(chessboard);
    setupRadioEventListener('field-type');
    document.getElementById('reset-btn').addEventListener('click', resetBoard);
    document.getElementById('predict-btn').addEventListener('click', calculateBestMove);  
    initializeChessGrid()
});

function setupRadioEventListener(radioName) {
    const radioButtons = document.querySelectorAll(`input[name="${radioName}"]`);

    radioButtons.forEach((radio) => {
        radio.addEventListener('change', (event) => {
        if (event.target.checked) {
            if (event.target.value === 'assembled') {
            loadBoardState(initial_board)
            } else if (event.target.value === 'empty') {
            loadBoardState(empty_board)
            }
        }
        });
    });
}

function setupSlider(sliderId, displayId) {
    const slider = document.getElementById(sliderId);
    const display = document.getElementById(displayId);

    const updateDepthDisplay = () => {
        display.textContent = slider.value;
    };

    updateDepthDisplay();

    slider.addEventListener('input', updateDepthDisplay);
}

function createChessboard(container) {
    for (let i = 0; i < 64; i++) {
        const square = document.createElement("div");
        square.classList.add("square");

        const row = Math.floor(i / 8);
        const col = i % 8;
        square.setAttribute("piece", "empty")
        square.setAttribute("id", `${row + 1}-${col + 1}`);

        if ((row + col) % 2 === 0) {
            square.classList.add("light");
        } else {
            square.classList.add("dark");
        }

        container.appendChild(square);
    }
}

function loadBoardState(boardState) {
    Object.keys(boardState).forEach((key) => {
        const square = document.getElementById(key);

        const value = boardState[key];
        square.setAttribute("piece", value);
    });
}

function resetBoard() {

    const radios = document.getElementsByName('field-type');
    
    let selectedValue;
    for (const radio of radios) {
        if (radio.checked) {
            selectedValue = radio.value;
            break;
        }
    }

    if (selectedValue === 'assembled') {
        loadBoardState(initial_board)
    } else if (selectedValue === 'empty') {
        loadBoardState(empty_board)
    }
}

function calculateBestMove() {
    const squares = document.querySelectorAll('.square');
    const boardState = [];
    const radios = document.getElementsByName('whos-turn');
    const slider = document.getElementById('depthrange');
    let currentTurn = 'white';
    
    radios.forEach(radio => {
        if (radio.checked) {
            currentTurn = radio.value;
        }
    });
    
    const predictionDepth = slider.value;
    
    squares.forEach(square => {
        const id = square.id;
        const piece = square.getAttribute('piece');
        boardState.push({ id, piece });
    });

    console.log(boardState)

    const requestData = {
        boardState: boardState,
        currentTurn: currentTurn,
        predictionDepth: parseInt(predictionDepth)
    };

    fetch('/predict', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        if (data.error) {
            alert(`Error: ${data.error}`);
        } else {
            alert(`The best move is: ${data.bestMove}`);
        }
    })
    .catch(error => {
        console.error("Error fetching the best move:", error);
        alert("Error calculating the best move. Please try again.");
    });
}

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
        const selectedPiece = event.target.getAttribute('data-piece');
        if (selectedPiece && activeCell) {
            activeCell.setAttribute('piece', selectedPiece);
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
