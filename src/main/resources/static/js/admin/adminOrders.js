let allOrders;
let iconOfPrice = " €";
let statusOfOrder = "Processing";
let btnDisplay = "d-inline";
let messagePackIndex;
let orderIndex;

$(window).on("load", function () {
    showListOrders();
    $('#statusCheckbox').change(function () {
        if ($(this).prop('checked') === true) {
            statusOfOrder = "Processing";
            btnDisplay = "d-inline";
        } else {
            statusOfOrder = "Completed";
            btnDisplay = "d-none";
        }
        showListOrders();
    });
});

$(document).ready(function () {
    document.getElementById("gmail-access").href = gmailAccessUrl.fullUrl;

    let url = window.location.href;
    if (url.search("code=") !== -1 || url.search("error=") !== -1) {
        document.getElementsByClassName("orders")[0].click();
    }
});

function convertPrice(price) {
    return price / 100;
}

function showListOrders() {
    fetch("/api/admin/getAllOrders")
        .then(json)
        .then(function (data) {
            $('#adminListOrders').empty();
            allOrders = data;
            let order;
            let html = `<thead ><tr><th>№</th>
                             <th>Email</th>
                             <th>First name</th>
                             <th>Last Name</th>
                             <th>Date of Order</th>
                             <th>Status</th>
                             <th>Details of Order</th>
                             <th>Edit</th>
                             <th></th></tr></thead>`;
            $.each(data, function (index) {
                order = data[index];
                if (order.status === statusOfOrder.toUpperCase()) {
                    html += `<tbody ><tr > <td> ${order.id}</td>`;
                    for (let key in order.userDTO) {
                        if (key === "email" || key === "firstName" || key === "lastName") {
                            html += `<td > ${order.userDTO[key]}</td>`;
                        }
                    }
                    html += `<td>${order.data}</td>
                         <td>${order.status} </td>`;
                    html += `<td><a  href="#" data-toggle="modal" data-target="#adminOrderModal" onclick="showModalOfOrder(${index})" > Show details </a></td>
                          <td><button class="btn btn-danger " onclick=orderDelete(${order.id})>Delete</button></td>
                          <td><button class="btn btn-success ${btnDisplay} " onclick=orderComplete(${order.id})>Complete</button></td>`;
                    if (order.status === "COMPLETED") {
                        html += `<td><button class="btn btn-success" onclick=orderUnComplete(${order.id})>Uncomplete</button></td>`;
                    }
                    html += `</tr>`;
                    $('#adminListOrders').html(html);
                }
            });
        });
}

async function showModalOfOrder(index) {
    $('#chat').empty();
    $('#modalBody').empty();
    $('#contactsOfUser').empty();
    orderIndex = index;
    let order = allOrders[index];
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

function orderComplete(id) {
    if (confirm('Do you really want to COMPLETE order?')) {
        fetch("/api/admin/completeOrder/" + id, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json;charset=utf-8"
            },
            body: JSON.stringify(id),
        }).then(r => showListOrders())
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
        }).then(r => showListOrders())
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
        }).then(r => showListOrders())
    }
}

function sendGmailMessage(userId, index) {
    let sendButton = document.getElementById("send-button");
    sendButton.disabled = true;
    let message = document.getElementById("sent-message").value;
    if (message === "" || message == null || message == undefined) {
        sendButton.disabled = false;
        return;
    }
    fetch("/gmail/" + userId + "/messages", {
        method: "POST",
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(message),
    })
        .then(json)
        .then((data) => {
            let html = `<p><b>${data.sender}</b></p>
                        <p>${data.text}</p>`
            let wrapper = document.getElementById("chat-wrapper");
            wrapper.insertAdjacentHTML("beforeend", html);
            document.getElementById("sent-message").value = "";
            sendButton.disabled = false;
        });
}

