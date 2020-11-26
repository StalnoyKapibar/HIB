let scrollOn = true;
let messagePackIndex;
let orders;
let orderIndex;
let iconOfPrice = " €";
let emails = [];
let toggleUsersDisabled = $("#toggle-users-disabled");
const localStorageUsersToggleKey = "toggle-users";

$(document).ready(function () {
    sessionStorage.getItem("toggle-users") === "true" ? toggleUsersDisabled.bootstrapToggle('on') : toggleUsersDisabled.bootstrapToggle('off');
    window.addEventListener(`resize`, event => {
        filterUl.width(filterInput.width() + 25);
    }, false);
});

toggleUsersDisabled.change(() => {
    let disabled = toggleUsersDisabled.prop('checked');
    sessionStorage.setItem(localStorageUsersToggleKey, disabled.toString());
    showUsers(!disabled);
});

async function showUsers(status) {
    await GET("/api/admin/userAccount?status=" + status)
        .then(json)
        .then((data) => {
            $('#users-body').empty();
            let users = data;
            let htmlUsers = ``;
            $.each(users, function (index) {
                emails[index] = users[index].email;
                htmlUsers += `<tr id="${users[index].email}-mark">
                                <td>${users[index].email}</td>
                                <td>${users[index].unrepliedFeedbacks} <button type="button" class="btn btn-primary arrow" onclick="showDetails('unrepliedFeedbacks', '${users[index].email}')" id="${users[index].email}-unrepliedFeedbacks-arrow">↓</button></td>
                                <td>${users[index].repliedFeedbacks} <button type="button" class="btn btn-primary arrow" onclick="showDetails('repliedFeedbacks', '${users[index].email}')" id="${users[index].email}-repliedFeedbacks-arrow">↓</button></td>
                                <td>${users[index].uprocessedOrders} <button type="button" class="btn btn-primary arrow" onclick="showDetails('uprocessedOrders', '${users[index].email}')" id="${users[index].email}-uprocessedOrders-arrow">↓</button></td>
                                <td>${users[index].processingOrders} <button type="button" class="btn btn-primary arrow" onclick="showDetails('processingOrders', '${users[index].email}')" id="${users[index].email}-processingOrders-arrow">↓</button></td>
                                <td>${users[index].completedOrders} <button type="button" class="btn btn-primary arrow" onclick="showDetails('completedOrders', '${users[index].email}')" id="${users[index].email}-completedOrders-arrow">↓</button></td>
                                <td>${users[index].deletedOrders} <button type="button" class="btn btn-primary arrow" onclick="showDetails('deletedOrders', '${users[index].email}')" id="${users[index].email}-deletedOrders-arrow">↓</button></td>
                                <td><button type="button" class="btn btn-primary arrow" onclick="showDetails('editUser', '${users[index].email}')" id="${users[index].email}-editUser-arrow">↓</button></td>
                              </tr>`;
                $('#users-body').html(htmlUsers);
            });
        });
}

async function showFullchat() {
    $('#fullchatModalLabel').html(`Chat with ${document.getElementById("email-chat").value}`);
    $('#chat').empty();
    $('#modalBody').empty();
    let email = document.getElementById("email-chat").value;
    scrollOn = true;
    messagePackIndex = 0;
    let htmlChat = ``;
    await fetch("/gmail/" + email + "/" + "0")
        .then(json)
        .then((data) => {
            if (data[0] === undefined) {
                htmlChat += `<div id="chat-wrapper">`;
                htmlChat += `</div>`
                htmlChat += `<div id="fields">
                                <input class="form-control" required type="text" id="subject" placeholder="Subject"/>
                             </div>`
                htmlChat += `<textarea id="sent-message" class="form-control" placeholder="Message"></textarea>

                        </div><button class="float-right col-2 btn btn-primary send-loc" type="button" id="send-button" onclick="sendGmailMessage('${email}')">Send</button>`

            } else {
                if (data[0].text === "chat end") {
                    htmlChat += `<div id="chat-wrapper">`;
                    htmlChat += `</div>`;
                    htmlChat += `<div id="fields">
                                    <input class="form-control" required type="text" id="subject" placeholder="Subject"/>
                                </div>`
                    htmlChat += `<textarea id="sent-message" class="form-control" placeholder="Message"></textarea>

                        </div><button class="float-right col-2 btn btn-primary send-loc" type="button" id="send-button" onclick="sendGmailMessage('${email}')">Send</button>`

                    scrollOn = false;
                } else if (data[0].text === "noGmailAccess") {
                    htmlChat += `<div>
                                <span class="h3 col-10 confirm-gmail-longphrase-loc">Confirm gmail access to open chat:</span>
                                <a type="button" class="col-2 btn btn-primary float-right confirm-loc" href="${gmailAccessUrl.fullUrl}">
                                Confirm</button>
                            </div>`
                } else {
                    htmlChat += `<div id="chat-wrapper">`;
                    for (let i = data.length - 1; i > -1; i--) {
                        if (data[i].sender === "me") {
                            htmlChat += `<div class="row"><div class="col-5"></div><div id="chat-mes" class="rounded col-7"><p><span>${data[i].sender}</span></p><p><span id="subject-mes">${data[i].subject}</span></p>
                                    <p>${data[i].text}</p></div></div>`
                        } else {
                            htmlChat += `<div class="row"><div id="chat-mes" class="rounded col-7"><p><span>${data[i].sender}</span></p><p><span id="subject-mes">${data[i].subject}</span></p>
                                                                            <p>${data[i].text}</p></div><div class="col-5"></div></div>`
                        }
                    }
                    htmlChat += `</div>`;
                    htmlChat += `<div id="fields">
                                    <input class="form-control" required type="text" id="subject" placeholder="Subject"/>
                                 </div>`
                    htmlChat += `<textarea id="sent-message" class="form-control" placeholder="Message"></textarea>

                        </div><button class="float-right col-2 btn btn-primary send-loc" type="button" id="send-button" onclick="sendGmailMessage('${email}')">Send</button>`

                }
            }
            $('#chat').html(htmlChat);
            $('#chat').scrollTop(2000);
            document.getElementById("chat").setAttribute('onscroll', 'scrolling(chat)');
        });
}

function sendGmailMessage(userId) {
    let sendButton = document.getElementById("send-button");
    sendButton.disabled = true;
    let message = document.getElementById("sent-message").value;
    if (message === "" || message == null || message == undefined) {
        sendButton.disabled = false;
        return;
    }
    let subject = document.getElementById("subject").value;
    if (subject === "" || subject == null || subject == undefined) {
        subject = "noSubject";
    }
    fetch("/gmail/" + userId + "/" + subject, {
        method: "POST",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(message),
    })
        .then(json)
        .then((data) => {
            let html = `<div class="row"><div class="col-5"></div><div id="chat-mes" class="rounded col-7"><p><h6><b>${data.sender}</b></h6></p><p><span id="subject-mes">${data.subject}</span></p>
                                    <p>${data.text}</p></div></div>`;
            let wrapper = document.getElementById("chat-wrapper");
            wrapper.insertAdjacentHTML("beforeend", html);
            document.getElementById("subject").value = "";
            document.getElementById("sent-message").value = "";
            sendButton.disabled = false;
        })
    // .then(() => {
    //     fetch("/admin/markasread?email=" + userId)
    //         .then(json)
    //         .then((data) => {
    //             console.log(data)
    //         })
    // });
}

function sendFeedbackGmailMessage(userId, feedbackId) {
    let sendButton = document.getElementById("send-button");
    sendButton.disabled = true;
    let message = document.getElementById("sent-message-feedback").value;
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
            document.getElementById("sent-message-feedback").value = "";
            sendButton.disabled = false;
        })
    //     .then(() => {
    //     fetch("/admin/markasread?email=" + userId)
    //         .then(json)
    //         .then((data) => {
    //             console.log(data)
    //         })
    // });
}

function sendOrderGmailMessage(userId, orderId) {
    let sendButton = document.getElementById("send-button");
    sendButton.disabled = true;
    let message = document.getElementById("sent-message-order").value;
    if (message === "" || message == null || message == undefined) {
        sendButton.disabled = false;
        return;
    }
    fetch("/gmail/" + userId + "/Order №" + orderId, {
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
            document.getElementById("sent-message-order").value = "";
            sendButton.disabled = false;
        })
        // .then(() => {
        //     fetch("/admin/markasread?email=" + userId)
        //         .then(json)
        //         .then((data) => {
        //             console.log(data)
        //         })
        // });
}

async function scrolling() {
    document.getElementById("chat").removeAttribute('onscroll');
    if ($('#chat').scrollTop() < 5 && $('#chat').scrollTop() > 0) {
        $('#chat').scrollTop(10);
        messagePackIndex++;
        await fetch("/gmail/" + document.getElementById("email-chat").value + "/" + messagePackIndex)
            .then(json)
            .then((data) => {
                if (data[0].text === "chat end") {
                    scrollOn = false;
                    return;
                }
                let html;
                for (let i = 0; i < data.length; i++) {
                    if (data[i].sender === "me") {
                        html = `<div class="row"><div class="col-5"></div><div id="chat-mes" class="rounded col-7"><p><h6><b>${data[i].sender}</b></h6></p><p><span id="subject-mes">${data[i].subject}</span></p>
                                    <p>${data[i].text}</p></div></div>`;
                    } else {
                        html = `<div class="row"><div id="chat-mes" class="rounded col-7"><p><h6><b>${data[i].sender}</b></h6></p><p><span id="subject-mes">${data[i].subject}</span></p>
                                                                            <p>${data[i].text}</p></div><div class="col-5"></div></div>`;
                    }
                    document.getElementById("chat-wrapper").insertAdjacentHTML("afterbegin", html);
                }
            });
    }
    if (scrollOn) {
        document.getElementById("chat").setAttribute('onscroll', 'scrolling()');
    } else {
        document.getElementById("chat").removeAttribute('onscroll');
    }
}

async function scrollingFeedback(feedback) {
    document.getElementById("feedbacks-chat").removeAttribute('onscroll');
    if ($('#feedbacks-chat').scrollTop() < 5 && $('#feedbacks-chat').scrollTop() > 0) {
        $('#feedbacks-chat').scrollTop(10);
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
        document.getElementById("chat").setAttribute('onscroll', 'scrollingFeedback(' + JSON.stringify(feedback) + ')');
    } else {
        document.getElementById("chat").removeAttribute('onscroll');
    }
}

async function scrollingOrders() {
    document.getElementById("orderChat").removeAttribute('onscroll');
    let order = orders[orderIndex];
    if ($('#orderChat').scrollTop() < 5 && $('#chat').scrollTop() > 0) {
        $('#orderChat').scrollTop(10);
        messagePackIndex++;
        await fetch("/gmail/" + order.contacts.email + "/Order №" + order.id + "/" + messagePackIndex)
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
        document.getElementById("orderChat").setAttribute('onscroll', 'scrollingOrders()');
    } else {
        document.getElementById("orderChat").removeAttribute('onscroll');
    }
}

function showDetails(details, email) {
    switch (details) {
        case 'unrepliedFeedbacks':
            showUnrepliedFeedbacks(details, email);
            break;
        case 'repliedFeedbacks':
            showRepliedFeedbacks(details, email);
            break;
        case 'uprocessedOrders':
            showUprocessedOrders(details, email);
            break;
        case 'processingOrders':
            showProcessingOrders(details, email);
            break;
        case 'completedOrders':
            showCompletedOrders(details, email);
            break;
        case 'deletedOrders':
            showDeletedOrders(details, email);
            break;
        case 'canceledOrders':
            showCanceledOrders(details, email);
            break;
        case 'editUser':
            showEditUser(details, email);
            break;
    }
}

function hideDetails(arrowId, details, email) {
    document.getElementById(email + '-' + details).remove();

    downArrow(arrowId, details, email);
}

async function showUnrepliedFeedbacks(details, email) {
    let htmlDetails = ``;
    htmlDetails += `<tr id="${email}-${details}">
                        <td colspan="7" class="pt-0 pb-0">`
    await fetch("/api/admin/feedback-request/" + email + "/replied?replied=" + 'false')
        .then(json)
        .then((data) => {
            let feedBacks = data;
            $.each(feedBacks, function (index) {
                htmlDetails += `<div class="row active-cell-details">
                                    <div class="col-2">
                                        <div>Feedback №${feedBacks[index].id}</div>
                                        <div>${feedBacks[index].senderName}</div>
                                    </div>
                                    <div class="col-8">
                                        ${feedBacks[index].content}
                                    </div>
                                    <div class="col-2">
                                    <button class="btn btn-primary btn-block" type="button" onclick="sendToFeedbackTab(${feedBacks[index].id}, 'Unreplied')">Manage</button>
                                    </div>
                                </div>`
            });
            htmlDetails += `    </td>
                            </tr>`
        });
    document.getElementById(email + "-mark").insertAdjacentHTML("afterend", htmlDetails);
    upArrow(email + '-' + details + '-arrow', details, email);
}

async function showRepliedFeedbacks(details, email) {
    let htmlDetails = ``;
    htmlDetails += `<tr id="${email}-${details}">
                        <td colspan="7" class="pt-0 pb-0">`
    await fetch("/api/admin/feedback-request/" + email + "/replied?replied=" + 'true')
        .then(json)
        .then((data) => {
            let feedBacks = data;
            $.each(feedBacks, function (index) {
                htmlDetails += `<div class="row active-cell-details">
                                    <div class="col-2">
                                        <div>Feedback №${feedBacks[index].id}</div>
                                        <div>${feedBacks[index].senderName}</div>
                                    </div>
                                    <div class="col-8">
                                        ${feedBacks[index].content}
                                    </div>
                                    <div class="col-2">
                                        <button class="btn btn-primary btn-block" type="button" onclick="sendToFeedbackTab(${feedBacks[index].id}, 'Replied')">Manage</button>
                                    </div>
                                </div>`
            });
            htmlDetails += `    </td>
                            </tr>`
        });
    document.getElementById(email + "-mark").insertAdjacentHTML("afterend", htmlDetails);
    upArrow(email + '-' + details + '-arrow', details, email);
}

async function showUprocessedOrders(details, email) {
    let htmlDetails = ``;
    htmlDetails += `<tr id="${email}-${details}">
                        <td colspan="7" class="pt-0 pb-0">`
    await fetch("/api/admin/order/" + email + "/" + details)
        .then(json)
        .then((data) => {
            orders = data;
            $.each(orders, function (index) {
                htmlDetails += `<div class="row active-cell-details">
                                    <div class="col-2">
                                        <div>Order №${orders[index].id}</div>
                                        <div>${orders[index].userDTO.firstName} ${orders[index].userDTO.lastName}</div>
                                        <div>${orders[index].data}</div>
                                    </div>
                                    <div class="col-8">
                                        <div>${orders[index].comment}</div>
                                        <div><a href="#" data-target="#order-modal" data-toggle="modal" onclick="showModalOfOrder(${index})">Show details</a></div>
                                    </div>
                                    <div class="col-2">
                                        <button class="btn btn-primary btn-block" type="button" onclick="sendToOrderTab(${orders[index].id}, 'Unprocessed')">Manage</button>
<!--                                    <button class="btn btn-danger btn-block" type="button" onclick="orderDelete(${orders[index].id})">Delete</button>-->
<!--                                    <button class="btn btn-success btn-block" type="button" onclick="orderProcess(${orders[index].id})">Process</button>-->
                                    </div>
                                </div>`
            });
            htmlDetails += `    </td>
                            </tr>`
        });
    document.getElementById(email + "-mark").insertAdjacentHTML("afterend", htmlDetails);
    upArrow(email + '-' + details + '-arrow', details, email);
}

async function showProcessingOrders(details, email) {
    let htmlDetails = ``;
    htmlDetails += `<tr id="${email}-${details}">
                        <td colspan="7" class="pt-0 pb-0">`
    await fetch("/api/admin/order/" + email + "/" + details)
        .then(json)
        .then((data) => {
            orders = data;
            $.each(orders, function (index) {
                htmlDetails += `<div class="row active-cell-details">
                                    <div class="col-2">
                                        <div>Order №${orders[index].id}</div>
                                        <div>${orders[index].userDTO.firstName} ${orders[index].userDTO.lastName}</div>
                                        <div>${orders[index].data}</div>
                                    </div>
                                    <div class="col-8">
                                        <div>${orders[index].comment}</div>
                                        <div><a href="#" data-target="#order-modal" data-toggle="modal" onclick="showModalOfOrder(${index})">Show details</a></div>
                                    </div>
                                    <div class="col-2">
                                        <button class="btn btn-primary btn-block" type="button" onclick="sendToOrderTab(${orders[index].id}, 'Processing')">Manage</button>
<!--                                    <button class="btn btn-danger btn-block" type="button" onclick="orderDelete(${orders[index].id})">Delete</button>-->
<!--                                    <button class="btn btn-success btn-block" type="button" onclick="orderComplete(${orders[index].id})">Complete</button>-->
                                    </div>
                                </div>`
            });
            htmlDetails += `    </td>
                            </tr>`
        });
    document.getElementById(email + "-mark").insertAdjacentHTML("afterend", htmlDetails);
    upArrow(email + '-' + details + '-arrow', details, email);
}

async function showCompletedOrders(details, email) {
    let htmlDetails = ``;
    htmlDetails += `<tr id="${email}-${details}">
                        <td colspan="7" class="pt-0 pb-0">`
    await fetch("/api/admin/order/" + email + "/" + details)
        .then(json)
        .then((data) => {
            orders = data;
            $.each(orders, function (index) {
                htmlDetails += `<div class="row active-cell-details">
                                    <div class="col-2">
                                        <div>Order №${orders[index].id}</div>
                                        <div>${orders[index].userDTO.firstName} ${orders[index].userDTO.lastName}</div>
                                        <div>${orders[index].data}</div>
                                    </div>
                                    <div class="col-8">
                                        <div>${orders[index].comment}</div>
                                        <div><a href="#" data-target="#order-modal" data-toggle="modal" onclick="showModalOfOrder(${index})">Show details</a></div>
                                    </div>
                                    <div class="col-2">
                                        <button class="btn btn-primary btn-block" type="button" onclick="sendToOrderTab(${orders[index].id}, 'Completed')">Manage</button>
<!--                                    <button class="btn btn-danger btn-block" type="button" onclick="orderDelete(${orders[index].id})">Delete</button>-->
<!--                                    <button class="btn btn-success btn-block" type="button" onclick="orderUnComplete(${orders[index].id})">Uncomplete</button>-->
                                    </div>
                                </div>`
            });
            htmlDetails += `    </td>
                            </tr>`
        });
    document.getElementById(email + "-mark").insertAdjacentHTML("afterend", htmlDetails);
    upArrow(email + '-' + details + '-arrow', details, email);
}

async function showDeletedOrders(details, email) {
    let htmlDetails = ``;
    htmlDetails += `<tr id="${email}-${details}">
                        <td colspan="7" class="pt-0 pb-0">`
    await fetch("/api/admin/order/" + email + "/" + details)
        .then(json)
        .then((data) => {
            orders = data;
            $.each(orders, function (index) {
                htmlDetails += `<div class="row active-cell-details">
                                    <div class="col-2">
                                        <div>Order №${orders[index].id}</div>
                                        <div>${orders[index].userDTO.firstName} ${orders[index].userDTO.lastName}</div>
                                        <div>${orders[index].data}</div>
                                    </div>
                                    <div class="col-8">
                                        <div>${orders[index].comment}</div>
                                        <div><a href="#" data-target="#order-modal" data-toggle="modal" onclick="showModalOfOrder(${index})">Show details</a></div>
                                    </div>
                                    <div class="col-2">
                                        <button class="btn btn-primary btn-block" type="button" onclick="sendToOrderTab(${orders[index].id}, 'Deleted')">Manage</button>
                                    </div>
                                </div>`
            });
            htmlDetails += `    </td>
                            </tr>`
        });
    document.getElementById(email + "-mark").insertAdjacentHTML("afterend", htmlDetails);
    upArrow(email + '-' + details + '-arrow', details, email);
}

async function showCanceledOrders(details, email) {
    let htmlDetails = ``;
    htmlDetails += `<tr id="${email}-${details}">
                        <td colspan="7" class="pt-0 pb-0">`
    await fetch("/api/admin/order/" + email + "/" + details)
        .then(json)
        .then((data) => {
            orders = data;
            $.each(orders, function (index) {
                htmlDetails += `<div class="row active-cell-details">
                                    <div class="col-2">
                                        <div>Order №${orders[index].id}</div>
                                        <div>${orders[index].userDTO.firstName} ${orders[index].userDTO.lastName}</div>
                                        <div>${orders[index].data}</div>
                                    </div>
                                    <div class="col-8">
                                        <div>${orders[index].comment}</div>
                                        <div><a href="#" data-target="#order-modal" data-toggle="modal" onclick="showModalOfOrder(${index})">Show details</a></div>
                                    </div>
                                    <div class="col-2">
                                        <button class="btn btn-primary btn-block" type="button" onclick="sendToOrderTab(${orders[index].id}, 'Canceled')">Manage</button>
                                    </div>
                                </div>`
            });
            htmlDetails += `    </td>
                            </tr>`
        });
    document.getElementById(email + "-mark").insertAdjacentHTML("afterend", htmlDetails);
    upArrow(email + '-' + details + '-arrow', details, email);
}

async function showEditUser(details, email) {
    let htmlDetails = ``;
    htmlDetails += `<tr id="${email}-${details}">
                        <td colspan="7" class="pt-0 pb-0">`
    await fetch(`/api/admin/user/${email}/`)
        .then(json)
        .then((data) => {
            let user = data;
            let enableDisable;
            if (user.enabled) {
                enableDisable = `<button class="btn btn-danger btn-block" type="button" onclick="enableDisableUser('${user.email}', 'disable')">Disable</button>`
            } else {
                enableDisable = `<button class="btn btn-success btn-block" type="button" onclick="enableDisableUser('${user.email}', 'enable')">Enable</button>`
            }
                htmlDetails += `<div class="row active-cell-details">
                                    <div class="col-8">
                                        <div>Id: ${user.id}, First name: ${user.firstName}, Last name: ${user.lastName}, Phone: ${user.phone}</div>
                                    </div>
                                    <div class="col-2">` +
                                        enableDisable +
                                    `</div>
                                </div>`
            });
            htmlDetails += `    </td>
                            </tr>`
    document.getElementById(email + "-mark").insertAdjacentHTML("afterend", htmlDetails);
    upArrow(email + '-' + details + '-arrow', details, email);
}

async function enableDisableUser(email, status) {
    await fetch("/api/admin/user/" + email + "/" + status, {
        method: 'PATCH'
        }).then((data) => {
            document.getElementById(`${email}-editUser`).remove();
            showDetails('editUser', `${email}`);
            location.reload();
        });
}

function downArrow(arrowId, details, email) {
    let activeArrow = document.getElementById(arrowId);
    activeArrow.removeAttribute("class");
    activeArrow.removeAttribute("onclick");

    let arrows = document.getElementsByClassName('arrow');
    Array.from(arrows).forEach((arrow) => arrow.removeAttribute('disabled'));

    document.getElementById(arrowId).innerText = "↓";
    activeArrow.setAttribute('class', 'btn btn-primary arrow');
    activeArrow.setAttribute('onclick', 'showDetails(' + '\'' + details + '\', \'' + email + '\')');

    activeArrow.parentElement.removeAttribute("class");
}

function upArrow(arrowId, details, email) {
    let activeArrow = document.getElementById(arrowId);
    activeArrow.removeAttribute("class");
    activeArrow.removeAttribute("onclick");

    let arrows = document.getElementsByClassName('arrow');
    Array.from(arrows).forEach((arrow) => arrow.setAttribute('disabled', 'disabled'));

    document.getElementById(arrowId).innerText = "↑";
    activeArrow.setAttribute('class', 'btn btn-success arrow');
    activeArrow.setAttribute('onclick', 'hideDetails(\'' + arrowId + '\', ' + '\'' + details + '\', \'' + email + '\')');
    activeArrow.parentElement.setAttribute("class", 'active-cell');
}

async function showModalOfFeedBack(index) {
    $('#feedbacks-chat').empty();
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
                htmlChat += `<textarea id="sent-message-feedback" class="form-control"></textarea>

                        </div><button class="float-right col-2 btn btn-primary send-loc" type="button" id="send-button" onclick="sendFeedbackGmailMessage('${feedback.senderEmail}', ${feedback.id})">Send</button>`

            } else {
                if (data[0].text === "chat end") {
                    htmlChat += `<div id="chat-wrapper">`;
                    htmlChat += `</div>`;
                    htmlChat += `<textarea id="sent-message-feedback" class="form-control"></textarea>

                        </div><button class="float-right col-2 btn btn-primary send-loc" type="button" id="send-button" onclick="sendFeedbackGmailMessage('${feedback.senderEmail}', ${feedback.id})">Send</button>`

                    scrollOn = false;
                } else if (data[0].text === "noGmailAccess") {
                    htmlChat += `<div>
                                <span class="h3 col-10 confirm-gmail-longphrase-loc">Confirm gmail access to open chat:</span>
                                <a type="button" class="col-2 btn btn-primary float-right confirm-loc" href="${gmailAccessUrl.fullUrl}">
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
                    htmlChat += `<textarea id="sent-message-feedback" class="form-control"></textarea>

                        </div><button class="float-right col-2 btn btn-primary send-loc" type="button" id="send-button" onclick="sendFeedbackGmailMessage('${feedback.senderEmail}', ${feedback.id})">Send</button>`

                }
            }
        });
    $('#feedbacks-chat').html(htmlChat);
    $('#feedbacks-chat').scrollTop(2000);
    document.getElementById("feedbacks-chat").setAttribute('onscroll', 'scrollingFeedback(' + JSON.stringify(feedback) + ')');
    setLocaleFields();
}

async function showModalOfOrder(index) {
    $('#orderChat').empty();
    $('#orderModalBody').empty();
    $('#contactsOfUser').empty();
    scrollOn = true;
    orderIndex = index;
    let order = orders[index];
    let items = order.items;
    $('#modalOrderTitle').html(`Order № ${order.id}`);
    messagePackIndex = 0;

    if (order.contacts.email == "") {
        order.contacts.email = order.userDTO.email;
    }

    let htmlContact = ``;
    let emailModal = order.contacts.email;
    let phoneModal = order.contacts.phone;
    let commentModal = order.comment;
    htmlContact += `<div class="panel panel-primary">
                        <div class="panel-body">
                            <div class="container mt-0 mb-0">
                                <div class="row" id="contacts">
                                    <div class="pl-3 pr-3 col-4" id="container-left">
                                        <div><h5>${emailModal}</h5></div>
                                        <div><span id="phoneModal">${phoneModal}</span></div>
                                    </div>
                                    <div class="pl-1 col-8" id="container-right"><span id="commentModal">${commentModal}</span></div>
                                </div>`;
    htmlContact += `</div></div></div>`;
    $('#contactsOfUser').html(htmlContact);

    let htmlChat = ``;
    await fetch("/gmail/" + order.contacts.email + "/Order №" + order.id + "/" + "0")
        .then(json)
        .then((data) => {
            if (data[0] === undefined) {
                htmlChat += `<div id="chat-wrapper">`;
                htmlChat += `</div>`;
                htmlChat += `<textarea id="sent-message-order" class="form-control"></textarea>

                        </div><button class="float-right col-2 btn btn-primary send-loc" type="button" id="send-button" onclick="sendOrderGmailMessage('${order.contacts.email}', ${orders[orderIndex].id})">Send</button>`

            } else {
                if (data[0].text === "chat end") {
                    htmlChat += `<div id="chat-wrapper">`;
                    htmlChat += `</div>`;
                    htmlChat += `<textarea id="sent-message-order" class="form-control"></textarea>

                        </div><button class="float-right col-2 btn btn-primary send-loc" type="button" id="send-button" onclick="sendOrderGmailMessage('${order.contacts.email}', ${orders[orderIndex].id})">Send</button>`

                    scrollOn = false;
                } else if (data[0].text === "noGmailAccess") {
                    htmlChat += `<div>
                                <span class="h3 col-10 confirm-gmail-longphrase-loc">Confirm gmail access to open chat:</span>
                                <a type="button" class="col-2 btn btn-primary float-right confirm-loc" href="${gmailAccessUrl.fullUrl}">
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
                    htmlChat += `<textarea id="sent-message-order" class="form-control"></textarea>

                        </div><button class="float-right col-2 btn btn-primary send-loc" type="button" id="send-button" onclick="sendOrderGmailMessage('${order.contacts.email}', ${orders[orderIndex].id})">Send</button>`

                }
            }
        });
    $('#orderChat').html(htmlChat);
    $('#orderChat').scrollTop(2000);

    const allOrdersforModal = await getAllOrders();

    let html = ``;
    html += `<thead><tr><th class="image-loc">Image</th>
                             <th class="name-author-loc">Name | Author</th>
                             <th></th>
                             <th class="price-loc">Price</th></tr></thead>`;
    $.each(items, function (index) {
        let book = items[index].book;
        let countUsers = 0;
        let isLastOrder = `<td width="350">${convertOriginalLanguageRows(book.originalLanguage.name, book.originalLanguage.nameTranslit)} | ${convertOriginalLanguageRows(book.originalLanguage.author, book.originalLanguage.authorTranslit)}</td>`;
        for (let i = 0; i < allOrdersforModal.length; i++) {
            if (allOrdersforModal[i].status === "UNPROCESSED") {
                for (let j = 0; j < allOrdersforModal[i].items.length; j++) {
                    let numberOfBook = allOrdersforModal[i].items[j].book.originalLanguage;
                    if (book.originalLanguage.id == numberOfBook.id) {
                        countUsers++;
                        if (countUsers >= 2) {
                            isLastOrder = `<td width="350">${convertOriginalLanguageRows(book.originalLanguage.name, book.originalLanguage.nameTranslit)} | ${convertOriginalLanguageRows(book.originalLanguage.author, book.originalLanguage.authorTranslit)}
                        <div style ="color: red; font-weight: 900;">This book was ordered by several people!</div></td>`
                        }
                    }
                }
            }
        }
        html += `<tr><td class="align-middle"><img src="/images/book${book.id}/${book.coverImage}" style="max-width: 80px"></td>
            ${isLastOrder}
            <td></td>
            <td>${convertPrice(book.price)}${iconOfPrice}</td></tr>`;
    });
    html += `<tr><td></td><td></td><td><span class="subtotal-loc">Subtotal</span> :</td><td> ${convertPrice(order.itemsCost)}${iconOfPrice}</td></tr>
                 <tr><td></td><td></td><td><span class="total-loc">Total</span> :</td><td>${convertPrice(order.itemsCost + order.shippingCost)}${iconOfPrice}</td></tr>`;
    $('#orderModalBody').html(html);
    document.getElementById("orderChat").setAttribute('onscroll', 'scrollingOrders()');

    setLocaleFields();
}

async function markAsRead(id, viewed) {
    let message = "Mark this message as ";
    message += viewed ? "read?" : "unread?";
    activeBtn = document.querySelector('.active-cell button');
    if (confirm(message)) {
        await fetch("/api/admin/feedback-request/" + id + "/" + viewed, {
            method: 'POST'
        })
        location.reload();
    }
}

async function getAllOrders() {
    const url = '/api/admin/getAllOrders';
    const res = await fetch(url);
    const data = await res.json();
    return data;
}

function convertPrice(price) {
    return price / 100;
}

function orderComplete(id) {
    if (confirm('Do you really want to COMPLETE order?')) {
        fetch("/api/admin/completeOrder/" + id, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json;charset=utf-8"
            },
            body: JSON.stringify(id),
        })
        location.reload();
    }
}

function orderProcess(id) {
    if (confirm('Do you really want to PROCESS order?')) {
        fetch("/api/admin/processOrder/" + id, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json;charset=utf-8"
            },
            body: JSON.stringify(id),
        })
        location.reload();
    }
}

function orderUnComplete(id) {
    if (confirm('Do you really want to UNCOMPLETE order?')) {
        fetch("/api/admin/unCompleteOrder/" + id, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json;charset=utf-8"
            },
            body: JSON.stringify(id),
        })
        location.reload();
    }
}

function orderDelete(id) {
    if (confirm('Do you really want to DELETE order?')) {
        fetch("/api/admin/deleteOrder/" + id, {
            method: "POST",
            headers: {
                "Content-Type": "application/json;charset=utf-8"
            },
            body: JSON.stringify(id),
        });
        location.reload();
    }
}

// liveSearch

let filterInput = $('#email-chat');
let filterUl = $('.ul-emails');
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
    $('#email-chat').val('');
    filterInput.val($(this).text());
    filterInput.trigger('change');
    filterUl.fadeOut(100);
    editTable($(this).text());
});

function editTable(inputValue) {
    let rows = document.getElementById("users-body").getElementsByTagName("tr");
    let rowId;
    $.each(rows, function (index) {
        rowId = (rows[index].id).substr(0, rows[index].id.length - 5);
        if (rowId.match(inputValue)) {
            rows[index].removeAttribute("hidden");
        } else {
            rows[index].setAttribute("hidden", "hidden");
        }
    });
}

//End liveSearch

function sendToOrderTab(orderId, details) {
    sessionStorage.setItem("orderId", orderId);
    sessionStorage.setItem("details", details);
    document.getElementsByClassName("orders").item(0).click();
}

function sendToFeedbackTab(feedbackId, details) {
    sessionStorage.setItem("feedbackId", feedbackId);
    sessionStorage.setItem("details", details);
    document.getElementsByClassName("feedbacks").item(0).click();
}
