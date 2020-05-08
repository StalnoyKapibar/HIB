let userData;
let hiddenButton = $('#hidden-submit-btn-feedback');
let senderNameInput = $("#sender-name");
let senderEmailInput = $("#sender-email");
let senderMessageInput = $("#sender-message");
let feedbackModal = $("#feedback-modal");
let senderName = $('#sender-name');
let senderEmail = $('#sender-email');
const messageTemplate = "Hello, I m interested in the book ";

$(document).ready(getUserData());

function getUserData() {
    fetch("/api/current-user")
        .then(resp => resp.json())
        .then((resp) => {
            userData = resp;
            senderEmail.val(userData.email);
            if (userData.lastName === null) {
                senderName.val(userData.firstName);
            } else {
                senderName.val(userData.firstName + " " + userData.lastName);
            }
        });
}

$(document).on('click', '#send-feedback-request', () => {
    hiddenButton.click();
});

$('#feedback-form').submit(async () => {
    let location = window.location.href;
    let bookId = null;
    if (location.includes("/page/")) {
        let index = location.indexOf("/page/") + 6;
        bookId = '';
        while (location[index] !== ' ' && location[index] !== undefined && location[index] !== "?") {
            bookId += location[index];
            index++;
        }
    }
    let FeedbackRequest = {
        senderName: senderNameInput.val(),
        senderEmail: senderEmailInput.val(),
        content: senderMessageInput.val(),
    };
    await fetch("/api/feedback-request" + "?book_id=" + bookId, {
        method: 'POST',
        body: JSON.stringify(FeedbackRequest),
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
    feedbackModal.modal('hide');
});

$(document).on('click', '#ask-question', async () => {
    let location = window.location + '';
    senderMessageInput.val(messageTemplate + '' + location.substr(0, location.indexOf("?")));
    feedbackModal.modal('show');
});