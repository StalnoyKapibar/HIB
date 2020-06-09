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
    setLocaleFields();
});

function markAsRead(id, replied) {
    let message = "Mark this message as ";
    message += replied ? "unread?" : "read?";
    if (confirm(message)) {
        fetch("/api/admin/feedback-request/" + id + "/" + replied, {
            method: 'POST'
        }).then(r => getFeedbackRequestTable(replied));
    }
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
                let bookId = null;
                let bookName = null;
                let bookCoverImage = null;
                if (data[i].book !== null) {
                    bookId = data[i].book.id;
                    bookName = data[i].book.name.en;
                    bookCoverImage = data[i].book.coverImage;
                }

                let replied;
                let mark;
                if (data[i].replied === false) {
                    replied = `<button type="button"
                               class="btn btn-info btn-reply reply-loc"
                               id="replyBtn"
                               data-id="${id}"
                               data-sender="${senderName}"
                               data-email="${senderEmail}"
                               data-message="${content}"
                               data-bookId="${bookId}"
                               data-bookName="${bookName}"
                               data-bookCoverImage="${bookCoverImage}">Reply</button>`;
                    mark = `<button type="button"
                            class="btn btn-info read-loc"           
                            onclick="markAsRead(${id},${data[i].replied})">Read</button>`;
                }
                 else if (data[i].replied === true && data[i].viewed === true) {
                    replied = `<input type="checkbox" disabled checked>`;
                    mark = `<button type="button"
                            class="btn btn-info unread-loc"           
                            onclick="markAsRead(${id},${data[i].replied})">Unread</button>`;
                }
                 else if (data[i].replied === true && data[i].viewed === false) {
                    replied = `<input type="checkbox" disabled unchecked>`;
                    mark = `<button type="button"
                            class="btn btn-info unread-loc"           
                            onclick="markAsRead(${id},${data[i].replied})">Unread</button>`;
                }

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
    setLocaleFields();
}

$(document).on('click', '.btn-reply', function () {
    replySubject.val('');
    replyMessage.val('');
    replyId.val($(this).attr("data-id"));
    recipient.val($(this).attr("data-sender"));
    emailField.val($(this).attr("data-email"));
    message.val($(this).attr("data-message"));
    showInterestedBook($(this).attr("data-bookId"), $(this).attr("data-bookName"), $(this).attr("data-bookCoverImage"));
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
    getFeedbackRequestTable(false).then(r => {
    });
});

function showInterestedBook(bookId, bookName, bookCoverImage) {
    if (bookId !== "null") {
        interestedBookContainer.attr('style', 'display: none');
        interestedBookTitle.html(`<span>${bookName}</span>`);
        interestedBookTitle.attr('href', "/page/" + bookId);
        interestedBookImage.attr('src', `/images/book${bookId}/${bookCoverImage}`);
        interestedBookContainer.attr('style', 'display: inline');
    } else {
        interestedBookContainer.attr('style', 'display: none');
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

