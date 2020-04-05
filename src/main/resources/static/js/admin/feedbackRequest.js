let tableBody = $("#feedback-request-table tbody");
let theModal = $("#feedback-request-modal");
let recipient = $("#sender-name");
let emailField = $("#sender-email");
let message = $("#sender-message");
let replyMessage = $("#reply-message-text");
let replySubject = $("#reply-message-subject");

$(document).ready(function () {
    getFeedbackRequestTable();
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
                let replied = data[i].replied ? "checked" : "";
                let tr = $("<tr/>");
                tr.append(`
                            <td>${id}</td>
                            <td>${senderName}</td>
                            <td>${senderEmail}</td>
                            <td>${content}</td>
                            <td>
                                <button type="submit"
                                class="btn btn-info"
                                id="replyBtn"
                                onclick="reply(${id}, '${senderName}', '${senderEmail}', '${content}')">
                                Reply
                                </button>
                            </td>
                            <td><input type="checkbox" disabled ${replied}></td>
                           `);
                tableBody.append(tr);
            }
        })
}

function reply(id, senderName, email, content) {
    recipient.val(senderName);
    emailField.val(email);
    message.val(content);
    theModal.modal("show");
}

$(document).on('click', '#submit-btn', async () => {
    let SimpleMailMessage = {
        to: emailField.val(),
        from: "hibthebestproject@yandex.ru",
        subject: replySubject.val(),
        text: replyMessage.val(),
    };
    await fetch("/feedback-request/reply", {
        method: 'POST',
        body: JSON.stringify(SimpleMailMessage),
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    });
});