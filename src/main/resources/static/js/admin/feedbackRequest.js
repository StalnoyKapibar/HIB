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
let allFeedBack;
let scrollOn = true;
let messagePackIndex;
let emails = [];

$(document).ready(function () {
    checkGmailFeedbacks();
    if (sessionStorage.getItem("details") !== null) {
        if (sessionStorage.getItem("details") === "Replied") {
            toggleReplied.bootstrapToggle('on');
        } else {
            getFeedbackRequestTable(false).then(r => {
            });
        }
    } else {
        if (sessionStorage.getItem("details") === null && localStorage.getItem(localStorageToggleKey) === "true") {
            toggleReplied.bootstrapToggle('on');
        } else {
            getFeedbackRequestTable(false).then(r => {
            });
        }
    }
    message.val($(this).attr("data-message"));
    window.addEventListener(`resize`, event => {
        filterUl.width(filterInput.width() + 25);
    }, false);
    sessionStorage.removeItem("details");

    setLocaleFields();
});

$('#feedback-request-modal')
    .on('hide.bs.modal', function() {
        getFeedbackRequestTable(false);
    })

async function markAsRead(id, viewed) {
    let message = "Mark this message as ";
    message += viewed ? "read?" : "unread?";
    if (confirm(message)) {
        fetch("/api/admin/feedback-request/" + id + "/" + viewed, {
            method: 'POST'
        }).then(r => getFeedbackRequestTable(!viewed));
    }
}

async function getFeedbackRequestTable(viewed) {
    $('#preloader').html(`
        <div class="progress">
            <div class="indeterminate"></div>
        </div>
    `)
    await fetch("/api/admin/feedback-request?viewed=" + viewed)
        .then(json)
        .then(async data => {
            let tmp = data;
            let feedbacks = [];
            for (let key in data) {
                emails.push(data[key].senderEmail)
            }
            await fetch ("/admin/unreadgmail/", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                body: JSON.stringify(emails)
            }).then(json).then(data => {
                feedbacks = tmp.map((item) => {
                    if (data.hasOwnProperty(item.senderEmail)) {
                        item.unreadgmail = data[item.senderEmail];
                        return item;
                    } else {
                        item.unreadgmail = false;
                        return item;
                    }
                })
            })
            return feedbacks;
        })
        .then((data) => {
            $('#preloader').empty();
            tableBody.empty();
            for (let i = 0; i < data.length; i++) {
                let id = data[i].id;
                let senderName = data[i].senderName;
                let senderEmail = data[i].senderEmail;
                let content = data[i].unreadgmail ? data[i].unreadgmail[1] : data[i].content;
                let bookId = null;
                let bookName = null;
                let bookCoverImage = null;
                if (data[i].book !== null) {
                    bookId = data[i].book.id;
                    bookName = data[i].book.name.en;
                    bookCoverImage = data[i].book.coverImage;
                }

                let replied = `<button type="button"
                               class="btn btn-info btn-reply reply-loc"
                               id="replyBtn"
                               data-id="${id}"
                               data-sender="${senderName}"
                               data-email="${senderEmail}"
                               data-message="${content}"
                               data-bookId="${bookId}"
                               data-bookName="${bookName}"
                               data-bookCoverImage="${bookCoverImage}"
                               onclick="showModalOfFeedBack(${id})">Reply</button>`;
                let mark;
                if (data[i].viewed === false) {
                    mark = `<button type="button"
                            class="btn btn-info read-loc"           
                            onclick="markAsRead(${id},true)">Read</button>`;
                } else {
                    replied = data[i].replied ? `<input type="checkbox" disabled checked>` :
                                                `<input type="checkbox" disabled unchecked>`;
                    mark = `<button type="button"
                            class="btn btn-info unread-loc"           
                            onclick="markAsRead(${id},false)">Unread</button>`;
                }

                let tr = $("<tr/>");
                if (id == sessionStorage.getItem("feedbackId")) {
                    tr.append(`
                            <td class="selected">${id}</td>
                            <td class="selected">${senderName}</td>
                            <td class="selected">${senderEmail}</td>
                            <td class="selected" ${data[i].unreadgmail ? 'class="unread"' : ''}>${content}</td>
                            <td class="selected">${replied}</td>
                            <td class="selected">${mark}</td>
                           `);
                    tableBody.append(tr);
                    sessionStorage.removeItem("feedbackId");
                } else {
                    tr.append(`
                            <td>${id}</td>
                            <td>${senderName}</td>
                            <td>${senderEmail}</td>
                            <td ${data[i].unreadgmail ? 'class="unread"' : ''}>${content}</td>
                            <td>${replied}</td>
                            <td>${mark}</td>
                           `);
                    tableBody.append(tr);
                }
            }
        })
    startCountOfFeedback();
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
    let viewedOn = toggleReplied.prop('checked');
    localStorage.setItem(localStorageToggleKey, viewedOn.toString());
    getFeedbackRequestTable(viewedOn).catch();
});

async function showModalOfFeedBack(index) {
    $('#chat').empty();
    $('#modalBody').empty();
    $('#contactsOfRequester').empty();
    scrollOn = true;
    messagePackIndex = 0;
    let feedback;
    let book;
    await fetch("/api/admin/feedback-request/" + index)
        .then(json)
        .then((data) => {
            feedback = data;
            book = feedback.book;
            $('#modalTitle').html(`Feedback № ${feedback.id}`);
        });
    let htmlContact = ``;
    htmlContact += `<div class="panel panel-primary">
                        <div class="panel-body">
                            <div class="container mt-0 mb-0">
                                <div class="row" id="contacts-feedback">
                                    <div class="pl-3 pr-3 col-4" id="container-left">
                                        <div><h5>${feedback.senderName}</h5></div>
                                        <div><span id="email-modal-feedback">${feedback.senderEmail}</span></div>
                                    </div>
                                    <div class="pl-1 col-8" id="container-right"><span id="commentModal">${feedback.content}</span></div>
                                </div>`;
    if (book !== null && book !== undefined) {
        htmlContact += `<div class="row pl-3 pr-3">
                            <img alt="interested book" height="50"
                             id="interested-image"
                             src=/images/book${book.id}/${book.coverImage}
                             width="33">
                            <div class="col-sm-10" id="interested-title-container">
                                <a href="http://localhost:8080/page/8" id="interested-title" target="_blank">
                                    ${convertOriginalLanguageRows(book.originalLanguage.name, book.originalLanguage.nameTranslit)} | ${convertOriginalLanguageRows(book.originalLanguage.author, book.originalLanguage.authorTranslit)}
                                </a>
                            </div>
                        </div>`;
    }
    htmlContact += `</div></div></div>`;
    $('#contactsOfRequester').html(htmlContact);

    let htmlChat = ``;
    await fetch("/gmail/" + feedback.senderEmail + "/Feedback №" + feedback.id + "/" + "0")
        .then(json)
        .then((data) => {
            if (data[0] === undefined) {
                htmlChat += `<div id="chat-wrapper">`;
                htmlChat += `</div>`;
                htmlChat += `<textarea id="sent-message" class="form-control"></textarea>

                        </div><button class="float-right col-2 btn btn-primary send-loc" type="button" id="send-button" onclick="sendGmailMessage('${feedback.senderEmail}', ${feedback.id})">Send</button>`

            } else {
                if (data[0].text === "chat end") {
                    htmlChat += `<div id="chat-wrapper">`;
                    htmlChat += `</div>`;
                    htmlChat += `<textarea id="sent-message" class="form-control"></textarea>

                        </div><button class="float-right col-2 btn btn-primary send-loc" type="button" id="send-button" onclick="sendGmailMessage('${feedback.senderEmail}', ${feedback.id})">Send</button>`

                    scrollOn = false;
                } else if (data[0].text === "noGmailAccess") {
                    htmlChat += `<div>
                                <span class="h3 col-10 confirm-gmail-longphrase-loc">Confirm gmail access to open chat:</span>
                                <button class="col-2 btn btn-primary float-right confirm-loc" href="${gmailAccessUrl.fullUrl}">
                                Confirm</button>
                            </div>`
                } else {
                    htmlChat += `<div id="chat-wrapper">`;
                    for (let i = data.length - 1; i > -1; i--) {
                        if (data[i].sender === "me") {
                            htmlChat += `<div class="row"><div class="col-5"></div><div id="chat-mes" class="rounded col-7"><p><span>${data[i].sender}</span></p>
                                    <p>${data[i].text}</p></div></div>`
                        } else {
                            htmlChat += `<div class="row"><div id="chat-mes" class="rounded col-7"><p><span>${data[i].sender}</span></p>
                                                                            <p>${data[i].text}</p></div><div class="col-5"></div></div>`
                        }
                    }
                    htmlChat += `</div>`;
                    htmlChat += `<textarea id="sent-message" class="form-control"></textarea>

                        </div><button class="float-right col-2 btn btn-primary send-loc" type="button" id="send-button" onclick="sendGmailMessage('${feedback.senderEmail}', ${feedback.id})">Send</button>`

                }
            }
        });
    $('#chat').html(htmlChat);
    $('#chat').scrollTop(2000);

    // let htmlChat = ``;
    // $('#chat').empty();
    // $('#modalBody').empty();
    // $('#contactsOfUser').empty();
    // let senderNameTemp;
    // // let feedBack = allFeedBack[index];
    // document.getElementById("chat").setAttribute('onscroll', 'scrolling()');
    // await fetch("/api/admin/feedback-request/" + index)
    //     .then(json)
    //     .then((data) => {
    //         /*senderNameTemp = data.senderEmail;*/
    //         senderNameTemp = "orlov.leo12@gmail.com";
    //         console.log(data.content);
    //         htmlChat += data.content;
    //     });
    // await fetch("/gmail/" + senderNameTemp + "/messages/" + "0")
    //     .then(json)
    //     .then((data) => {
    //         if (data[0] === undefined) {
    //             htmlChat += `<div id="chat-wrapper">`;
    //             htmlChat += `</div>`;
    //             htmlChat += `<textarea id="sent-message" class="form-control"></textarea>
    //                     </div><button class="float-right col-2 button btn-primary" type="button" id="send-button" onclick="sendGmailMessage('${senderNameTemp}', ${index})">Send</button>`
    //         } else {
    //             if (data[0].text === "noGmailAccess") {
    //                 htmlChat += `<div>
    //                             <span class="h3 col-10">Confirm gmail access to open chat:</span>
    //                             <a type="button" class="col-2 btn btn-primary float-right" href="${gmailAccessUrl.fullUrl}">
    //                             confirm</a>
    //                         </div>`
    //             } else {
    //                 htmlChat += `<div id="chat-wrapper">`;
    //                 for (let i = data.length - 1; i > -1; i--) {
    //                     htmlChat += `<p><b>${data[i].sender}</b></p>
    //                 <p>${data[i].text}</p>`
    //                 }
    //                 htmlChat += `</div>`;
    //                 htmlChat += `<textarea id="sent-message" class="form-control"></textarea>
    //                     </div><button class="float-right col-2 button btn-primary" type="button" id="send-button" onclick="sendGmailMessage('${senderNameTemp}', ${index})">Send</button>`
    //             }
    //         }
    //     });
    // $('#chat').html(htmlChat);
    // $('#chat').scrollTop(1000);

    // let html = ``;
    // html += `<thead><tr><th>Image</th>
    //                          <th>Name | Author</th>
    //                          <th></th>
    //                          <th>Price</th></tr></thead>`;
    // $.each(items, function (index) {
    //     let book = items[index].book;
    //     html += `<tr><td class="align-middle"><img src="/images/book${book.id}/${book.coverImage}" style="max-width: 80px"></td>
    //                          <td width="350">${convertOriginalLanguageRows(book.originalLanguage.name, book.originalLanguage.nameTranslit)} | ${convertOriginalLanguageRows(book.originalLanguage.author, book.originalLanguage.authorTranslit)}</td>
    //                          <td></td>
    //                          <td>${convertPrice(book.price)}${iconOfPrice}</td></tr>`;
    // });
    // html += `<tr><td></td><td></td><td>Subtotal :</td><td> ${convertPrice(order.itemsCost)}${iconOfPrice}</td></tr>
    //              <tr><td></td><td></td><td>Total :</td><td>${convertPrice(order.itemsCost + order.shippingCost)}${iconOfPrice}</td></tr>`;
    // $('#modalBody').html(html);

    // let htmlContact = ``;
    // htmlContact += `<div class="panel panel-primary">
    //                     <div class="panel-body">
    //                         <div class="container mt-2">
    //                             <div class="col-8 p-4 mb-4  alert alert-info" role="alert">
    //                                 <h6>User <strong>contacts </strong></h6>
    //                             </div>`;
    // for (let key in order.contacts) {
    //     if (order.contacts[key] !== "" && key !== "id" && key !== "comment") {
    //         htmlContact += `<div class="form-group row">
    //                     <label class="control-label col-sm-2 col-form-label">${key}</label>
    //                     <div class="col-md-5 pl-0 pr-1">
    //                         <input class="form-control" readonly  placeholder=${order.contacts[key]}>
    //                     </div>
    //                 </div>`;
    //     }
    // }
    // if (order.comment !== " ") {
    //     htmlContact += `<div class="form-group row">
    //                     <label class="control-label col-sm-2 col-form-label">Comment</label>
    //                     <div class="col-md-6 pl-0">
    //                         <textarea class="form-control" readonly  rows="5" placeholder="${order.comment}" ></textarea>
    //                     </div>
    //                 </div>`;
    // }
    //
    // htmlContact += `</div></div>`;
    //
    // $('#contactsOfUser').html(htmlContact);
    document.getElementById("chat").setAttribute('onscroll', 'scrolling(' + JSON.stringify(feedback) + ')');

    setLocaleFields();
}

async function scrolling(feedback) {
    document.getElementById("chat").removeAttribute('onscroll');
    if ($('#chat').scrollTop() < 5 && $('#chat').scrollTop() > 0) {
        $('#chat').scrollTop(10);
        messagePackIndex++;
        await fetch("/gmail/" + feedback.senderEmail + "/Feedback №" + feedback.id + "/" + messagePackIndex)
            .then(json)
            .then((data) => {
                if (data[0].text === "chat end") {
                    scrollOn = false;
                    return;
                }
                let html;
                for (let i = 0; i < data.length; i++) {
                    if (data[i].sender === "me") {
                        html = `<div class="row"><div class="col-5"></div><div id="chat-mes" class="rounded col-7"><p><h6><b>${data[i].sender}</b></h6></p>
                                    <p>${data[i].text}</p></div></div>`;
                    } else {
                        html = `<div class="row"><div id="chat-mes" class="rounded col-7"><p><h6><b>${data[i].sender}</b></h6></p>
                                                                            <p>${data[i].text}</p></div><div class="col-5"></div></div>`;
                    }
                    document.getElementById("chat-wrapper").insertAdjacentHTML("afterbegin", html);
                }
            });
    }
    if (scrollOn) {
        document.getElementById("chat").setAttribute('onscroll', 'scrolling(' + JSON.stringify(feedback) + ')');
    } else {
        document.getElementById("chat").removeAttribute('onscroll');
    }
}

async function scrolling() {
    let order = allFeedBack[orderIndex];
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

// get all feedbacks instead of viewed and replied
async function getAllFeedbacks() {
    await GET("/api/admin/feedback-request")
        .then(json)
        .then((data) => {
            allFeedBack = data;
        });
}

// get all unread emails
async function getGmailUnreadEmails() {
    return await POST ("/admin/unreadgmail/", JSON.stringify(emails), JSON_HEADER)
        .then(json)
        .then((data) => {
            return data;
    });
}

// consolidate unread feedbacks
async function consolidateEmails() {
    return await getAllFeedbacks()
        .then(async () => {
            let tmp = allFeedBack;
            let feedbacks = [];
            for (let key in allFeedBack) {
                emails.push(allFeedBack[key].senderEmail)
            }
            await getGmailUnreadEmails()
                .then((data) => {
                    feedbacks = tmp.map((item) => {
                        if (data.hasOwnProperty(item.senderEmail)) {
                            item.unreadgmail = data[item.senderEmail];
                            return item;
                        } else {
                            item.unreadgmail = false;
                            return item;
                        }
                    });
                });
            return feedbacks;
        });
}

// check gmail answers and mark feedbacks
async function checkGmailFeedbacks() {
    await consolidateEmails()
        .then((data) => {
            for (let i = 0; i < data.length; i++) {
                if (data[i].unreadgmail) {
                    markFeedback(data[i].id, false);
                }
            }
        });
}

function sendGmailMessage(userId, feedbackId) {
    let sendButton = document.getElementById("send-button");
    sendButton.disabled = true;
    let message = document.getElementById("sent-message").value;
    if (message === "" || message == null || message == undefined) {
        sendButton.disabled = false;
        return;
    }
    fetch("/gmail/" + userId + "/Feedback №" + feedbackId, {
        method: "POST",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(message),
    })
        .then(json)
        .then((data) => {
            let html = `<div class="row"><div class="col-5"></div><div id="chat-mes" class="rounded col-7"><p><h6><b>${data.sender}</b></h6></p>
                                    <p>${data.text}</p></div></div>`;
            let wrapper = document.getElementById("chat-wrapper");
            wrapper.insertAdjacentHTML("beforeend", html);
            document.getElementById("sent-message").value = "";
            sendButton.disabled = false;
        }).then(() => {
        fetch("/admin/markasread?email=" + userId)
            .then(json)
    })
        .then(r => {
            markFeedback(feedbackId, true);
    });
}

// mark feedback replied and viewed fields
async function markFeedback(feedbackId, mark) {
    await fetch("/api/admin/feedback-request/replied/" + feedbackId + "/" + mark, {
        method: "POST"
    }).then(() => {
        startCountOfFeedback();
    });
}

// function sendGmailMessage(userId, index) {
//     let sendButton = document.getElementById("send-button");
//     sendButton.disabled = true;
//     let subject = "Feedback #" + index + "&nbsp";
//     let message = document.getElementById("sent-message").value;
//     let messageComplite = subject+message;
//
//     fetch("/gmailFeedBack/" + userId + "/messages", {
//         method: "POST",
//         headers: {
//             "Content-Type": "application/json;charset=utf-8"
//         },
//         body: JSON.stringify(messageComplite),
//     })
//         .then(json)
//         .then((data) => {
//             let html = `<p><b>${data.sender}</b></p>
//                         <p>${data.text}</p>`
//             let wrapper = document.getElementById("chat-wrapper");
//             wrapper.insertAdjacentHTML("beforeend", html);
//             document.getElementById("sent-message").value = "";
//             sendButton.disabled = false;
//         });
// }

// liveSearch

let filterInput = $('#feedback-chat');
let filterUl = $('.ul-feedbacks');
filterUl.width(filterInput.width() + 7);

// Проверка при каждом вводе символа
filterInput.bind('input propertychange', function () {
    if ($(this).val() !== '') {
        filterUl.fadeIn(100);
        findElem(filterUl, emails, $(this).val());

    } else {
        filterUl.fadeOut(100);
    }
    editTable($(this).val());
});

//  При клике на эллемент выпадающего списка, присваиваем значение в инпут и ставим триггер на его изменение
filterUl.on('click', '.js-searchInput', function (e) {
    $('#feedback-chat').val('');
    filterInput.val($(this).text());
    filterInput.trigger('change');
    filterUl.fadeOut(100);
    editTable($(this).text());
});

function editTable(inputValue) {
    let rows = document.getElementsByTagName("tbody").item(0).getElementsByTagName("tr");
    let rowEmail;
    $.each(rows, function (index) {
        rowEmail = rows[index].querySelector('td:nth-child(3)').textContent;
        if (rowEmail.match(inputValue)) {
            rows[index].removeAttribute("hidden")
        } else {
            rows[index].setAttribute("hidden", "hidden");
        }
    })
}

