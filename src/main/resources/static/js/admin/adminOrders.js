let allOrders;
let iconOfPrice = " €";
let statusOfOrder = "Processing";
let btnDisplay = "d-inline";
let messagePackIndex;
let orderIndex;
let scrollOn = true;

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
        setLocaleFields();
    });
});

$(document).ready(function () {
    document.getElementById("gmail-access").href = gmailAccessUrl.fullUrl;

    let url = window.location.href;
    if (url.search("code=") !== -1 || url.search("error=") !== -1) {
        document.getElementsByClassName("orders")[0].click();
    }
    setLocaleFields();
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
                             <th class="email-label">Email</th>
                             <th class="first-name-loc">First name</th>
                             <th class="last-name-loc">Last Name</th>
                             <th class="date-of-order-loc">Date of Order</th>
                             <th class="status-loc">Status</th>
                             <th class="details-of-order-loc">Details of Order</th>
                             <th class="edit-loc">Edit</th>
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

                    html += `<td><a  href="#" data-toggle="modal" class="show-details-loc" data-target="#adminOrderModal" onclick="showModalOfOrder(${index})" > Show details </a></td>
                          <td><button class="btn btn-danger delete-loc" onclick=orderDelete(${order.id})>Delete</button></td>
                          <td><button class="btn btn-success ${btnDisplay} complete-loc" onclick=orderComplete(${order.id})>Complete</button></td>`;
                    if (order.status === "COMPLETED") {
                        html += `<td><button class="btn btn-success uncomplete-loc" onclick=orderUnComplete(${order.id})>Uncomplete</button></td>`;
                    }
                    html += `</tr>`;

                    $('#adminListOrders').html(html);
                }
            });
        });
    setLocaleFields();
}

async function showModalOfOrder(index) {
    $('#chat').empty();
    $('#modalBody').empty();
    $('#contactsOfUser').empty();
    scrollOn = true;
    orderIndex = index;
    let order = allOrders[index];
    let items = order.items;
    $('#modalTitle').html(`Order № ${order.id}`);
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
    await fetch("/gmail/" + order.contacts.email + "/messages/" + "0")
        .then(json)
        .then((data) => {
            if (data[0] === undefined) {
                htmlChat += `<div id="chat-wrapper">`;
                htmlChat += `</div>`;
                htmlChat += `<textarea id="sent-message" class="form-control"></textarea>

                        </div><button class="float-right col-2 btn btn-primary send-loc" type="button" id="send-button" onclick="sendGmailMessage('${order.contacts.email}', ${index})">Send</button>`

            } else {
                if (data[0].text === "noGmailAccess") {
                    htmlChat += `<div>
                                <span class="h3 col-10 confirm-gmail-longphrase-loc">Confirm gmail access to open chat:</span>
                                <a type="button" class="col-2 btn btn-primary float-right confirm-loc" href="${gmailAccessUrl.fullUrl}">
                                Confirm</a>
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

                        </div><button class="float-right col-2 btn btn-primary send-loc" type="button" id="send-button" onclick="sendGmailMessage('${order.contacts.email}', ${index})">Send</button>`

                }
            }
        });
    $('#chat').html(htmlChat);
    $('#chat').scrollTop(2000);
    const orderedBooks = await getOrderedBooks();

    let html = ``;
    html += `<thead><tr><th class="image-loc">Image</th>
                             <th class="name-author-loc">Name | Author</th>
                             <th></th>
                             <th class="price-loc">Price</th></tr></thead>`;
    $.each(items, function (index) {
        let book = items[index].book;
        let countUsers = 0;
        let isLastOrder = `<td width="350">${convertOriginalLanguageRows(book.originalLanguage.name, book.originalLanguage.nameTranslit)} | ${convertOriginalLanguageRows(book.originalLanguage.author, book.originalLanguage.authorTranslit)}</td>`;
        for (let i = 0; i < orderedBooks.length; i++) {
            for (let j = 0; j < orderedBooks[i].items.length; j++) {
                let numberOfBook = orderedBooks[i].items[j].book.originalLanguage;
                if (book.originalLanguage.id == numberOfBook.id) {
                    countUsers++;
                    if (countUsers >= 2) {
                        isLastOrder = `<td width="350">${convertOriginalLanguageRows(book.originalLanguage.name, book.originalLanguage.nameTranslit)} | ${convertOriginalLanguageRows(book.originalLanguage.author, book.originalLanguage.authorTranslit)}
                        <div style ="color: red; font-weight: 900;">This book was ordered by several people!</div></td>`
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
    $('#modalBody').html(html);
    document.getElementById("chat").setAttribute('onscroll', 'scrolling()');

    setLocaleFields();
}

async function getOrderedBooks() {
    const url = '/api/admin/getAllOrders';
    const res = await fetch(url);
    const data = await res.json();
    console.log(data);
    return data;
}

async function scrolling() {
    document.getElementById("chat").removeAttribute('onscroll');
    let order = allOrders[orderIndex];
    if ($('#chat').scrollTop() < 5 && $('#chat').scrollTop() > 0) {
        $('#chat').scrollTop(10);
        messagePackIndex++;
        await fetch("/gmail/" + order.contacts.email + "/messages/" + messagePackIndex)
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
        document.getElementById("chat").setAttribute('onscroll', 'scrolling()');
    } else {
        document.getElementById("chat").removeAttribute('onscroll');
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
            let html = `<div class="row"><div class="col-5"></div><div id="chat-mes" class="rounded col-7"><p><h6><b>${data.sender}</b></h6></p>
                                    <p>${data.text}</p></div></div>`;
            let wrapper = document.getElementById("chat-wrapper");
            wrapper.insertAdjacentHTML("beforeend", html);
            document.getElementById("sent-message").value = "";
            sendButton.disabled = false;
        });
}

