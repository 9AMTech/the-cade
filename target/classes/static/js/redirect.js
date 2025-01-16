const redirectMessage = document.querySelector('#redirect-message');
const messages = [
    "Redirecting to Home in 5 Seconds...",
    "Redirecting to Home in 4 Seconds...",
    "Redirecting to Home in 3 Seconds...",
    "Redirecting to Home in 2 Seconds...",
    "Redirecting to Home in 1 Seconds...",
    "Redirecting!"
];

function wait(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

async function showMessages() {
    for (let i = 0; i < 6; i++) {
        redirectMessage.innerText = messages[i];
        await wait(950);
    }
    window.location.href = '/mainscreen';
}

showMessages();
