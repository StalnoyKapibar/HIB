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
let allOrders;

$(document).ready(function () {
    if (localStorage.getItem(localStorageToggleKey) === "true") {
        toggleReplied.bootstrapToggle('on');
    } else {
        getFeedbackRequestTable(false).then(r => {
        });
    }
    showListOrders();
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
                               class="btn btn-info btn-reply"
                               id="replyBtn"
                               data-id="${id}"
                               data-sender="${senderName}"
                               data-email="${senderEmail}"
                               data-message="${content}"
                               data-bookId="${bookId}"
                               data-bookName="${bookName}"
                               data-bookCoverImage="${bookCoverImage}"
                               onclick="showModalOfOrder(${id})">Reply</button>
         `
                                ;
                    mark = `<button type="button"
                            class="btn btn-info "           
                            onclick="markAsRead(${id},${data[i].replied})">Read</button>`;
                }
                 else if (data[i].replied === true && data[i].viewed === true) {
                    replied = `<input type="checkbox" disabled checked>`;
                    mark = `<button type="button"
                            class="btn btn-info "           
                            onclick="markAsRead(${id},${data[i].replied})">Unread</button>`;
                }
                 else if (data[i].replied === true && data[i].viewed === false) {
                    replied = `<input type="checkbox" disabled unchecked>`;
                    mark = `<button type="button"
                            class="btn btn-info "           
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

async function showModalOfOrder(index) {
    $('#chat').empty();
    $('#modalBody').empty();
    $('#contactsOfUser').empty();
    orderIndex = index;
    let order = allOrders[index-1];
    let items = order.items;
    $('#modalTitle').html(`Order № ${order.id}`);
    messagePackIndex = 0;
    document.getElementById("chat").setAttribute('onscroll', 'scrolling()');

    let htmlChat = ``;
    await fetch("/gmail/" + order.contacts.email + "/messages/" + "0")
        .then(json)
        .then((data) => {
            if (data[0] === undefined) {
                htmlChat += `<div id="chat-wrapper">`;
                htmlChat += `</div>`;
                htmlChat += `<textarea id="sent-message" class="form-control"></textarea>
                        </div><button class="float-right col-2 button btn-primary" type="button" id="send-button" onclick="sendGmailMessage('${order.contacts.email}', ${index})">Send</button>`
            } else {
                if (data[0].text === "noGmailAccess") {
                    htmlChat += `<div>
                                <span class="h3 col-10">Confirm gmail access to open chat:</span>
                                <a type="button" class="col-2 btn btn-primary float-right" href="${gmailAccessUrl.fullUrl}">
                                confirm</a>
                            </div>`
                } else {
                    htmlChat += `<div id="chat-wrapper">`;
                    for (let i = data.length - 1; i > -1; i--) {
                        htmlChat += `<p><b>${data[i].sender}</b></p>
                    <p>${data[i].text}</p>`
                    }
                    htmlChat += `</div>`;
                    htmlChat += `<textarea id="sent-message" class="form-control"></textarea>
                        </div><button class="float-right col-2 button btn-primary" type="button" id="send-button" onclick="sendGmailMessage('${order.contacts.email}', ${index})">Send</button>`
                }
            }
        });
    $('#chat').html(htmlChat);
    $('#chat').scrollTop(1000);

    let html = ``;
    html += `<thead><tr><th>Image</th>
                             <th>Name | Author</th>
                             <th></th>
                             <th>Price</th></tr></thead>`;
    $.each(items, function (index) {
        let book = items[index].book;
        html += `<tr><td class="align-middle"><img src="/images/book${book.id}/${book.coverImage}" style="max-width: 80px"></td>
                             <td width="350">${convertOriginalLanguageRows(book.originalLanguage.name, book.originalLanguage.nameTranslit)} | ${convertOriginalLanguageRows(book.originalLanguage.author, book.originalLanguage.authorTranslit)}</td>
                             <td></td>
                             <td>${convertPrice(book.price)}${iconOfPrice}</td></tr>`;
    });
    html += `<tr><td></td><td></td><td>Subtotal :</td><td> ${convertPrice(order.itemsCost)}${iconOfPrice}</td></tr>
                 <tr><td></td><td></td><td>Total :</td><td>${convertPrice(order.itemsCost + order.shippingCost)}${iconOfPrice}</td></tr>`;
    $('#modalBody').html(html);

    let htmlContact = ``;
    htmlContact += `<div class="panel panel-primary">
                        <div class="panel-body">
                            <div class="container mt-2">
                                <div class="col-8 p-4 mb-4  alert alert-info" role="alert">
                                    <h6>User <strong>contacts </strong></h6>
                                </div>`;
    for (let key in order.contacts) {
        if (order.contacts[key] !== "" && key !== "id" && key !== "comment") {
            htmlContact += `<div class="form-group row">
                        <label class="control-label col-sm-2 col-form-label">${key}</label>
                        <div class="col-md-5 pl-0 pr-1">
                            <input class="form-control" readonly  placeholder=${order.contacts[key]}>
                        </div>
                    </div>`;
        }
    }
    if (order.comment !== " ") {
        htmlContact += `<div class="form-group row">
                        <label class="control-label col-sm-2 col-form-label">Comment</label>
                        <div class="col-md-6 pl-0">
                            <textarea class="form-control" readonly  rows="5" placeholder="${order.comment}" ></textarea>
                        </div>
                    </div>`;
    }

    htmlContact += `</div></div>`;

    $('#contactsOfUser').html(htmlContact);
}

async function scrolling() {
    let order = allOrders[orderIndex];
    if ($('#chat').scrollTop() < 2) {
        messagePackIndex++;
        await fetch("/gmail/" + order.contacts.email + "/messages/" + messagePackIndex)
            .then(json)
            .then((data) => {
                if (data[0].text === "chat end") {
                    document.getElementById("chat").removeAttribute('onscroll');
                    return;
                }
                for (let i = 0; i < data.length; i++) {
                    let html = `<p><b>${data[i].sender}</b></p>
                    <p>${data[i].text}</p>`
                    document.getElementById("chat-wrapper").insertAdjacentHTML("afterbegin", html);
                }
            });
    }
}

function showListOrders() {
    fetch("/api/admin/getAllOrders")
        .then(json)
        .then(function (data) {

            allOrders = data;
        });
}

