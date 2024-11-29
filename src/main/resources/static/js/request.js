predictButtonListener();

function predictButtonListener() {
    const predictButton = document.getElementById('predict-btn');
    if (predictButton) {
        predictButton.addEventListener('click', () => requestPredict());
    }
}

async function requestPredict() {
    const turnState = getTurnState();
    const searchDepth = getSearchDepth();
    const boardState = getBoardState();

    if (!turnState || !searchDepth || !boardState) {
        alert("Please fill in all the necessary fields.");
        return;
    }

    const requestData = {
        whosTurn: turnState,
        searchDepth: parseInt(searchDepth),
        board: boardState
    };

    try {
        const response = await fetch('/predict', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const responseData = await response.json();

        if (responseData) {
            alert(`Piece: ${responseData.piece}, Move: ${responseData.move}`);
        } else {
            alert('No response data received');
        }

    } catch (error) {
        const responseParagraph = document.getElementById('response');
        responseParagraph.textContent = `Error: ${error.message}`;
        console.error("Fetch error:", error);
    }
}

function getTurnState() {
    const selectedRadio = document.querySelector('input[name="whos-turn"]:checked');
    return selectedRadio ? selectedRadio.value : null;
}

function getSearchDepth() {
    const slider = document.getElementById('depthrange');
    return slider ? slider.value : null;
}

function getBoardState() {
    const boardState = {};
    const squares = document.querySelectorAll('.square');

    squares.forEach((square) => {
        const piece = square.getAttribute('data-piece');
        const id = square.id;
        boardState[id] = piece;
    });

    return boardState;
}
