let tableBody = $("#feedback-request-table tbody");
let theModal = $("#feedback-request-modal");
let recipient = $("#sender-name");
let emailField = $("#sender-email");
let message = $("#sender-message");
let replyMessage = $("#reply-message-text");
let replySubject = $("#reply-message-subject");
let replyId = $("#reply-id");
let alertContainer = $("#alert-container");
let interestedBookContainer = $("#interested-book");
let interestedBookImage = $("#interested-image");
let interestedBookTitle = $("#interested-title");

$(document).ready(function () {
    getFeedbackRequestTable().then(r => {
    });
});

async function getFeedbackRequestTable() {
    await fetch("/feedback-request")
        .then(status)
        .then(json)
        .then((data) => {
            tableBody.empty();
            for (let i = 0; i < data.length; i++) {
                let id = data[i].id;
                let senderName = data[i].senderName;
                let senderEmail = data[i].senderEmail;
                let content = data[i].content;
                let replied = data[i].replied ? '<input type="checkbox" disabled checked>'
                    : `<button type="submit"
                       class="btn btn-info"
                        id="replyBtn"
                        onclick="reply(${id}, '${senderName}', '${senderEmail}', '${content}')">
                         Reply
                        </button>`;
                let tr = $("<tr/>");
                tr.append(`
                            <td>${id}</td>
                            <td>${senderName}</td>
                            <td>${senderEmail}</td>
                            <td>${content}</td>
                            <td>${replied}</td>
                           `);
                tableBody.append(tr);
            }
        })
}

async function reply(id, senderName, email, content) {
    replySubject.val('');
    replyMessage.val('');
    replyId.val(id);
    recipient.val(senderName);
    emailField.val(email);
    message.val(content);
    await showInterestedBook(content);
    theModal.modal('show');
}

$(document).on('click', '#submit-btn', async () => {
    let SimpleMailMessage = {
        subject: replySubject.val(),
        text: replyMessage.val(),
    };
    await fetch("/feedback-request/reply/" + replyId.val(), {
        method: 'POST',
        body: JSON.stringify(SimpleMailMessage),
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
    theModal.modal('hide');
    await getFeedbackRequestTable();
    await showAlert(`Message for ${emailField.val()} successfully sent`);
});

async function showInterestedBook(message) {
    let bookId;
    if (message.includes("/page/")) {
        bookId = message.substr(message.indexOf("/page/") + 6, message.length);
        await fetch("/page/id/" + bookId)
            .then(status)
            .then(json).then(book => {
                interestedBookTitle.html(`<span>${book.name['en']}</span>`);
                interestedBookTitle.attr('href', "/page/" + bookId);
                interestedBookImage.attr('src', `/images/book${book.id}/${book.coverImage}`);
                interestedBookContainer.attr('style', 'display: inline');
            });
    }
}

async function showAlert(message) {
    let alert = `<div class="alert alert-success alert-dismissible" role="alert">
                            <strong>Success!</strong> <span>${message}</span>
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
    alertContainer.append(alert);
}

