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
            senderName.val(userData.firstName + " " + userData.lastName);
        });
}

$(document).on('click', '#send-feedback-request', () => {
    hiddenButton.click();
});

$('#feedback-form').submit(async () => {
    let senderMessage;
    if (senderMessageInput.val().includes("/page/")) {
        senderMessage = senderMessageInput.val();
    } else {
        senderMessage = senderMessageInput.val() + ' ' + window.location.href;
    }
    let FeedbackRequest = {
        senderName: senderNameInput.val(),
        senderEmail: senderEmailInput.val(),
        content: senderMessage
    };
    await fetch("/api/feedback-request", {
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