let userData;
let hiddenButton = $('#hidden-submit-btn-feedback');
let senderNameInput = $("#sender-name");
let senderEmailInput = $("#sender-email");
let senderMessageInput = $("#sender-message");
let feedbackModal = $("#feedback-modal");
let senderName = $('#sender-name');
let senderEmail = $('#sender-email');
let bookId;
let interestedBookClose = $('#img-close');
let interestedBookImage = $("#ask-interested-image");
let bookCoverImage;
let messageTemplate = "Hello, I m interested in the book ";

$(document).ready(function () {
    getUserData();
    if (currentLang === '') {
        if (getCookieByName("lang")) {
            currentLang = getCookieByName("lang");
        } else {
            currentLang = 'en';
        }
    }
    getLanguage();
    setLocaleFields();

})

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
    bookCoverImage = null;
});

$(document).on('click', '.ask-question-loc', async () => {
    let location = window.location + '';
    bookId = null;
    if (location.includes("/page/")) {
        let index = location.indexOf("/page/") + 6;
        bookId = '';
        while (location[index] !== ' ' && location[index] !== undefined && location[index] !== "?") {
            bookId += location[index];
            index++;
        }
    }
    //senderMessageInput.val(messageTemplate + '' + location.substr(0, location.indexOf("?")));
    console.log(bookId);
    if (bookId !== null) {
        await fetch(`/api/book/${bookId}`)
            .then(json)
            .then((data) => {
                bookCoverImage = data.coverImage;
            })
        interestedBookImage.attr('src', `/images/book${bookId}/${bookCoverImage}`);
    } else {
        interestedBookImage.attr('style', 'display: none');
        interestedBookClose.attr('style', 'display: none');
    }
    feedbackModal.modal('show');
});

function deleteBookFromRequest() {
    bookId = null;
    interestedBookImage.attr('style', 'display: none');
    interestedBookClose.attr('style', 'display: none');
}