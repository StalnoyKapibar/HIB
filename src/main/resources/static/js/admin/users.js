let users;
let htmlUsers = ``;
let scrollOn = true;

$(document).ready(async function () {

    await fetch("/api/admin/userAccount")
        .then(json)
        .then((data) => {
           users = data;
            $.each(users, function (index) {
                htmlUsers += `<tr id="${users[index].email}-mark">
                                <td>${users[index].email}</td>
                                <td>${users[index].unrepliedFeedbacks} <button type="button" class="btn btn-primary arrow" onclick="showDetails('unrepliedFeedbacks', '${users[index].email}')" id="${users[index].email}-unrepliedFeedbacks-arrow">↓</button></td>
                                <td>${users[index].repliedFeedbacks} <button type="button" class="btn btn-primary arrow" onclick="showDetails('repliedFeedbacks', '${users[index].email}')" id="${users[index].email}-repliedFeedbacks-arrow">↓</button></td>
                                <td>${users[index].uprocessedOrders} <button type="button" class="btn btn-primary arrow" onclick="showDetails('uprocessedOrders', '${users[index].email}')" id="${users[index].email}-uprocessedOrders-arrow">↓</button></td>
                                <td>${users[index].processingOrders} <button type="button" class="btn btn-primary arrow" onclick="showDetails('processingOrders', '${users[index].email}')" id="${users[index].email}-processingOrders-arrow">↓</button></td>
                                <td>${users[index].completedOrders} <button type="button" class="btn btn-primary arrow" onclick="showDetails('completedOrders', '${users[index].email}')" id="${users[index].email}-completedOrders-arrow">↓</button></td>
                                <td>${users[index].deletedOrders} <button type="button" class="btn btn-primary arrow" onclick="showDetails('deletedOrders', '${users[index].email}')" id="${users[index].email}-deletedOrders-arrow">↓</button></td>
                              </tr>`;
                $('#users-body').html(htmlUsers);
            });
        });
});

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
                                Confirm</a>
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
            document.getElementById("chat").setAttribute('onscroll', 'scrolling()');
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
            let html = `<div class="row"><div class="col-5"></div><div id="chat-mes" class="rounded col-7"><p><h6><b>${data.sender}</b></h6></p>
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
    }
    // let htmlDetails = ``;
    // if (details === 'unrepliedFeedbacks' || details === 'repliedFeedbacks') {
    //     htmlDetails += `<tr id="${email}-${details}">
    //                         <td colspan="7" class="pt-0 pb-0">
    //                             <div class="row active-cell-details">
    //                                 <div class="col-2">
    //                                     <div>Fedback №7</div>
    //                                     <div>Алексей</div>
    //                                 </div>
    //                                 <div class="col-8">
    //                                     Здравствуйте, у меня возник вопрос по оплате. Какие сервисы электронных платежей
    //                                     поддерживает ваш сайт? И могу ли я заказать книгу в Сибирь?\t\t
    //                                 </div>
    //                                 <div class="col-2">
    //                                     <button class="btn btn-info reply" type="button">Reply</button>
    //                                     <button class="btn btn-info" type="button" id="mark-as-read">Mark as read</button>
    //                                 </div>
    //                             </div>
    //                             <div class="row active-cell-details">
    //                                 <div class="col-2">
    //                                     <div>Fedback №7</div>
    //                                     <div>Алексей</div>
    //                                 </div>
    //                                 <div class="col-8">
    //                                     Здравствуйте, у меня возник вопрос по оплате. Какие сервисы электронных платежей
    //                                     поддерживает ваш сайт? И могу ли я заказать книгу в Сибирь?\t\t
    //                                 </div>
    //                                 <div class="col-2">
    //                                     <button class="btn btn-info reply" type="button">Reply</button>
    //                                     <button class="btn btn-info" type="button" id="mark-as-read">Mark as read</button>
    //                                 </div>
    //                             </div>
    //                         </td>
    //                     </tr>`
    //     document.getElementById(email + "-mark").insertAdjacentHTML("afterend", htmlDetails);
    //     let width = $('#mark-as-read').width();
    //     $('.reply').width(width);
    //     upArrow(email + '-' + details + '-arrow', details, email);
    // }
}

function hideDetails(arrowId, details, email) {
    document.getElementById(email + '-' + details).firstChild.remove();
    document.getElementById(email + '-' + details).firstChild.remove();

    downArrow(arrowId, details, email);
}

async function showUnrepliedFeedbacks(details, email) {
    let htmlDetails = ``;
    // await fetch("/api/admin/feedback-request?replied=" + 'false')
    //     .then(json)
    //     .then((data) => {
    //         allFeedBack = data;
    //     });
    htmlDetails += `<tr id="${email}-${details}">
                            <td colspan="7" class="pt-0 pb-0">
                                <div class="row active-cell-details">
                                    <div class="col-2">
                                        <div>Fedback №7</div>
                                        <div>Алексей</div>
                                    </div>
                                    <div class="col-8">
                                        Здравствуйте, у меня возник вопрос по оплате. Какие сервисы электронных платежей 
                                        поддерживает ваш сайт? И могу ли я заказать книгу в Сибирь?\t\t
                                    </div>
                                    <div class="col-2">
                                        <button class="btn btn-info reply" type="button">Reply</button>
                                        <button class="btn btn-info" type="button" id="mark-as-read">Mark as read</button>
                                    </div>
                                </div>
                                <div class="row active-cell-details">
                                    <div class="col-2">
                                        <div>Fedback №7</div>
                                        <div>Алексей</div>
                                    </div>
                                    <div class="col-8">
                                        Здравствуйте, у меня возник вопрос по оплате. Какие сервисы электронных платежей 
                                        поддерживает ваш сайт? И могу ли я заказать книгу в Сибирь?\t\t
                                    </div>
                                    <div class="col-2">
                                        <button class="btn btn-info reply" type="button">Reply</button>
                                        <button class="btn btn-info" type="button" id="mark-as-read">Mark as read</button>
                                    </div>
                                </div>
                            </td>
                        </tr>`
    document.getElementById(email + "-mark").insertAdjacentHTML("afterend", htmlDetails);
    let width = $('#mark-as-read').width();
    $('.reply').width(width);
    upArrow(email + '-' + details + '-arrow', details, email);
}

async function showRepliedFeedbacks(details, email) {
    let htmlDetails = ``;
    htmlDetails += `<tr id="${email}-${details}">
                            <td colspan="7" class="pt-0 pb-0">
                                <div class="row active-cell-details">
                                    <div class="col-2">
                                        <div>Fedback №7</div>
                                        <div>Алексей</div>
                                    </div>
                                    <div class="col-8">
                                        Здравствуйте, у меня возник вопрос по оплате. Какие сервисы электронных платежей 
                                        поддерживает ваш сайт? И могу ли я заказать книгу в Сибирь?\t\t
                                    </div>
                                    <div class="col-2">
                                        <button class="btn btn-info reply" type="button">Reply</button>
                                        <button class="btn btn-info" type="button" id="mark-as-read">Mark as read</button>
                                    </div>
                                </div>
                                <div class="row active-cell-details">
                                    <div class="col-2">
                                        <div>Fedback №7</div>
                                        <div>Алексей</div>
                                    </div>
                                    <div class="col-8">
                                        Здравствуйте, у меня возник вопрос по оплате. Какие сервисы электронных платежей 
                                        поддерживает ваш сайт? И могу ли я заказать книгу в Сибирь?\t\t
                                    </div>
                                    <div class="col-2">
                                        <button class="btn btn-info reply" type="button">Reply</button>
                                        <button class="btn btn-info" type="button" id="mark-as-read">Mark as read</button>
                                    </div>
                                </div>
                            </td>
                        </tr>`
    document.getElementById(email + "-mark").insertAdjacentHTML("afterend", htmlDetails);
    let width = $('#mark-as-read').width();
    $('.reply').width(width);
    upArrow(email + '-' + details + '-arrow', details, email);
}

async function showUprocessedOrders(details, email) {
    let htmlDetails = ``;
}

async function showProcessingOrders(details, email) {
    let htmlDetails = ``;
}

async function showCompletedOrders(details, email) {
    let htmlDetails = ``;
}

async function showDeletedOrders(details, email) {
    let htmlDetails = ``;
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

