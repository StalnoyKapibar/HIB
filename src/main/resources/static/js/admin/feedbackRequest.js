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
let toggleReplied = $("#toggle-replied");
const localStorageToggleKey = "request-toggle";

$(document).ready(function () {
    if (localStorage.getItem(localStorageToggleKey) === "true") {
        toggleReplied.bootstrapToggle('on');
    } else {
        getFeedbackRequestTable(false).then(r => {
        });
    }
});

function markAsRead(id, replied) {
    let message = "Mark this message as ";
    message += replied ? "read?" : "unread?";
    if (confirm(message)) {
        fetch("/api/admin/feedback-request/" + id + "/" + replied, {
            method: 'POST'
        });
    }
    getFeedbackRequestTable(replied).then(r => {
    });
}

async function getFeedbackRequestTable(replied) {
    await fetch("/api/admin/feedback-request?replied=" + replied)
        .then(json)
        .then((data) => {
            tableBody.empty();
            for (let i = 0; i < data.length; i++) {
                let id = data[i].id;
                let senderName = data[i].senderName;
                let senderEmail = data[i].senderEmail;
                let content = data[i].content;
                let replied = data[i].replied ? '<input type="checkbox" disabled checked>'
                    : `<button type="button"
                       class="btn btn-info btn-reply"
                        id="replyBtn"
                        data-id="${id}"
                        data-sender="${senderName}"
                        data-email="${senderEmail}"
                        data-message="${content}">Reply</button>`;
                let mark = `<button type="button"
                class="btn btn-info "           
                onclick="markAsRead(${id},${data[i].replied})">`;
                mark += (data[i].replied ? `Unread</button>` : `Read</button>`);
                let tr = $("<tr/>");
                tr.append(`
                            <td>${id}</td>
                            <td>${senderName}</td>
                            <td>${senderEmail}</td>
                            <td>${content}</td>
                            <td>${replied}</td>
                            <td>${mark}</td>
                           `);
                tableBody.append(tr);
            }
        })
}

$(document).on('click', '.btn-reply', function () {
    replySubject.val('');
    replyMessage.val('');
    replyId.val($(this).attr("data-id"));
    recipient.val($(this).attr("data-sender"));
    emailField.val($(this).attr("data-email"));
    message.val($(this).attr("data-message"));
    showInterestedBook($(this).attr("data-message")).then(r => {
    });
    theModal.modal('show');
});

$(document).on('click', '#submit-btn', async () => {
    let SimpleMailMessage = {
        subject: replySubject.val(),
        text: replyMessage.val(),
    };
    await fetch("/api/admin/feedback-request/reply/" + replyId.val(), {
        method: 'POST',
        body: JSON.stringify(SimpleMailMessage),
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    }).then((response) => {
        if (response.ok) {
            showAlert(`Message for ${emailField.val()} successfully sent`, 'success')
        } else {
            showAlert(`Message for ${emailField.val()} not sent!`, 'danger');
        }
    });
    theModal.modal('hide');
    await getFeedbackRequestTable();
});

async function showInterestedBook(message) {
    let bookId = '';
    interestedBookContainer.attr('style', 'display: none');
    if (message.includes("/page/")) {
        let index = message.indexOf("/page/") + 6;
        while (message[index] !== ' ' && message[index] !== undefined) {
            bookId += message[index];
            index++;
        }
        await fetch("/api/book/" + bookId + "?locale=en")
            .then(json).then((book) => {
                interestedBookTitle.html(`<span>${book.name}</span>`);
                interestedBookTitle.attr('href', "/page/" + bookId);
                interestedBookImage.attr('src', `/images/book${book.id}/${book.coverImage}`);
                interestedBookContainer.attr('style', 'display: inline');
            });
    }
}

async function showAlert(message, clazz) {
    if (document.getElementById(alertContainer.attr('id')).children.length > 3) {
        alertContainer.empty();
    }
    let alert = `<div class="alert alert-${clazz} alert-dismissible" role="alert">
                            <strong>${clazz}!</strong> <span>${message}</span>
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
    alertContainer.append(alert);
}

toggleReplied.change(() => {
    let repliedOn = toggleReplied.prop('checked');
    localStorage.setItem(localStorageToggleKey, repliedOn.toString());
    getFeedbackRequestTable(repliedOn).catch();
});

