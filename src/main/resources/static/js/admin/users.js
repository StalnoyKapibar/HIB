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
                                <td>${users[index].unrepliedFeedbacks} <button type="button" class="btn btn-primary" onclick="showDetails('unrepliedFeedbacks', '${users[index].email}')">↓</button></td>
                                <td>${users[index].repliedFeedbacks} <button type="button" class="btn btn-primary" onclick="showDetails('repliedFeedbacks', '${users[index].email}')">↓</button></td>
                                <td>${users[index].uprocessedOrders} <button type="button" class="btn btn-primary" onclick="showDetails('uprocessedOrders', '${users[index].email}')">↓</button></td>
                                <td>${users[index].processingOrders} <button type="button" class="btn btn-primary" onclick="showDetails('processingOrders', '${users[index].email}')">↓</button></td>
                                <td>${users[index].completedOrders} <button type="button" class="btn btn-primary" onclick="showDetails('completedOrders', '${users[index].email}')">↓</button></td>
                                <td>${users[index].deletedOrders} <button type="button" class="btn btn-primary" onclick="showDetails('deletedOrders', '${users[index].email}')">↓</button></td>
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
    let htmlDetails = ``;
    if (details === 'unrepliedFeedbacks' || details === 'repliedFeedbacks') {
        htmlDetails += `<tr id="${email}-${details}">
                            <td colspan="7">asdad</td>
                        </tr>`
        document.getElementById(email + "-mark").insertAdjacentHTML("afterend", htmlDetails);
    }
}

