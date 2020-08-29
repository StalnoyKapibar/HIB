let allOrders;
let iconOfPrice = " €";
let statusOfOrder = "Unprocessed";
let gmailAccess = false;
let onlyUnread = false;
let preloader = false;
let unreadCheckbox = '';
let messagePackIndex;
let orderIndex;
let scrollOn = true;
let emails = [];
let currentPage = 1;


$(window).on("load", function () {
    if (sessionStorage.getItem("details") != null) {
        $('#statusSelector').val(sessionStorage.getItem("details"));
        statusOfOrder = sessionStorage.getItem("details");
        showListOrders();
        sessionStorage.removeItem("details");
    } else {
        showListOrders();
    }
    $('#statusSelector').change(function () {
        statusOfOrder = $(this).children("option:selected").val();
        currentPage = 1;
        showListOrders();
    });
});

$(document).ready(function () {
    document.getElementById("gmail-access").href = gmailAccessUrl.fullUrl;
    $('#preloader').empty();
    let url = window.location.href;
    if (url.search("code=") !== -1 || url.search("error=") !== -1) {
        document.getElementsByClassName("orders")[0].click();
    }
    window.addEventListener(`resize`, event => {
        filterUl.width(filterInput.width() + 25);
    }, false);

    setLocaleFields();
});

$('#adminOrderModal')
    .on('hide.bs.modal', function () {
        showListOrders();
    })

function convertPrice(price) {
    return price / 100;
}


async function setOrdersAmountInPage(amount) {
    let ordersAmountPerPage = document.querySelector('#ordersAmountPerPage');
    ordersAmountPerPage.textContent = amount;
    currentPage = 1;
    await showListOrders();
}

async function addPagination(totalPages) {
    let startIter = 1;
    let endIter = totalPages;
    let pag = '';
    pag = `<nav aria-label="Page navigation example">
                    <ul class="pagination">`;
    pag += currentPage === 1 ? `<li class="page-item disabled"><a class="page-link" href="#" tabindex="-1">` :
        `<li class="page-item"><a class="page-link" onclick="loadMore(1)" href="#">`;
    pag += `<span aria-hidden="true">&laquo;</span></a></li>`;
    for (let i = startIter; i < endIter + 1; i++) {
        if (currentPage === i) {
            pag += `<li class="page-item active"><a class="page-link" onclick="loadMore(${i})">${i}</a></li>`;
        } else {
            pag += `<li class="page-item"><a class="page-link" onclick="loadMore(${i})">${i}</a></li>`;
        }
    }
    pag += currentPage === totalPages ? `<li class="page-item disabled">` : `<li class="page-item">`
    pag += `<a class="page-link" onclick="loadMore(${totalPages})" href="#"><span aria-hidden="true">&raquo;</span></a></li>
                    </ul>
                </nav>`;
    document.querySelector('#rowForPagination').innerHTML = '';
    $("#rowForPagination").append(pag);
}


async function getOrdersData(page, size, status) {
    const url = `/api/admin/pageable/${page}/${size}/${status}`;
    const res = await fetch(url);
    const data = await res.json();
    return data;
}

async function renderPageData(data) {

    const lastOrderedBooks = await getLastOrderedBooks();
    const booksAvailable = await getBooksAvailability();
    let orders = data;
    for (let key in data) {
        emails.push(data[key].userDTO.email)
    }
    await fetch("/admin/unreademails/", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(emails)
    }).then(json).then(emails => {
        if (emails['gmailAccess']) {
            if (!onlyUnread) {
                for (let key in orders) {
                    orders[key].userDTO.isUnread = emails[orders[key].userDTO.email]
                }
            } else {
                orders = {}
                for (let key in data) {
                    if (emails[data[key].userDTO.email]) {
                        orders[key] = data[key];
                        orders[key].userDTO.isUnread = emails[data[key].userDTO.email];
                    }
                }
            }
            unreadCheckbox = `
                <div>
                    <h3 class="only-unread-text">Only unread messages</h3>
                </div>
                <div>
                    <input data-size="md" data-toggle="toggle" id="toggleOnlyUnread" type="checkbox" ${onlyUnread ? 'checked' : ''}>
                 </div>`
        }
    });

    $('#adminListOrders').empty();
    $('#preloader').empty();
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
        let isOrderEnable = true;
        let isBookAvailable = true;
        order.items.forEach((item) => {
            // check each book in order for availability and lastorder
            if (order.status === "UNPROCESSED") {
                isOrderEnable = !lastOrderedBooks.includes(item.book.id);
                isBookAvailable = booksAvailable.includes(item.book.id);
            }
        });

        if (order.status === statusOfOrder.toUpperCase() || statusOfOrder === "All") {
            if (order.id == sessionStorage.getItem("orderId")) {
                html += `<tbody ><tr class="selected"`;
                sessionStorage.removeItem("orderId");
            } else {
                html += `<tbody ><tr `;
            }
            if (!isOrderEnable || !isBookAvailable) {
                html += `style = "background-color: #FFB3B3" `;
            }

            html += `> <td> ${order.id}</td>`;
            for (let key in order.userDTO) {
                if (key === "email" || key === "firstName" || key === "lastName") {
                    html += `<td class=${order.userDTO.isUnread ? 'unread' : ''}> ${order.userDTO[key]}</td>`;
                }
            }
            html += `<td class=${order.userDTO.isUnread ? 'unread' : ''}>${order.data}</td>
                         <td class=${order.userDTO.isUnread ? 'unread' : ''}>${order.status} </td>`;

            html += `<td>
                                <div class="show-details-container">
                                    <div class="show-details-text">
                                        <a href="#" data-toggle="modal" class="show-details-loc" data-target="#adminOrderModal" onclick="showModalOfOrder(${index})" >
                                            Show details
                                        </a>
                                    </div>
                                    <div class="show-details-icon">
                                         ${order.userDTO.isUnread ? '<i class="material-icons">email</i>' : ''} 
                                    </div>
                                </div>
                             </td>`
            if (order.status !== "DELETED") {
                html += `<td><button class="btn btn-danger delete-loc" onclick=orderDelete(${order.id})>Delete</button></td>`;
            }
            if (order.status === "PROCESSING") {
                html += `<td><button class="btn btn-success complete-loc" onclick=orderComplete(${order.id})>Complete</button></td>`;
            }
            if (order.status === "COMPLETED") {
                html += `<td><button class="btn btn-success uncomplete-loc" onclick=orderUnComplete(${order.id})>Uncomplete</button></td>`;
            }
            if (order.status === "UNPROCESSED") {
                html += `<td><button class="btn btn-success uncomplete-loc" onclick=orderProcess(${order.id})`;
                if (!isOrderEnable || !isBookAvailable) {
                    html += ` disabled="disabled"`;
                }
                html += `>Process</button></td>`;
            }
            html += `</tr>`;
            if (!isOrderEnable) {
                html += `<tr style = "background-color: #FFB3B3; color: red; font-weight: 900"><td colspan="9">This order contains book that is already included in order with status PROCESSING. </td></tr>`;
            } else if (!isBookAvailable) {
                html += `<tr style = "background-color: #FFB3B3; color: #ff0000; font-weight: 900"><td colspan="9">This order contains books that are not available on the web-site. </td></tr>`;
            }

            $('#adminListOrders').html(html);
            $('#unread-checkbox').html(unreadCheckbox);
            $('#toggleOnlyUnread').on('change', () => {
                onlyUnread = $('#toggleOnlyUnread').is(":checked");
                showListOrders();
            });
        }
    });
    setLocaleFields();
}

async function loadMore(pageNumber) {
    currentPage = pageNumber;
    await showListOrders();
}

async function showListOrders() {
    $('#preloader').html(`
        <div class="progress">
            <div class="indeterminate"></div>
        </div>
    `)
    let size = document.querySelector('#ordersAmountPerPage').textContent;
    const testData = await getOrdersData(currentPage - 1, size, statusOfOrder.toUpperCase());
    const totalPages = testData.totalPages
    await addPagination(totalPages);
    const pageData = testData.listOrderDTO;
    renderPageData(pageData);
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
    await fetch("/gmail/" + order.contacts.email + "/Order №" + order.id + "/" + "0")
        .then(json)
        .then((data) => {
            if (data[0] === undefined) {
                htmlChat += `<div id="chat-wrapper">`;
                htmlChat += `</div>`;
                htmlChat += `<textarea id="sent-message" class="form-control"></textarea>

                        </div><button class="float-right col-2 btn btn-primary send-loc" type="button" id="send-button" onclick="sendGmailMessage('${order.contacts.email}', ${allOrders[orderIndex].id})">Send</button>`

            } else {
                if (data[0].text === "chat end") {
                    htmlChat += `<div id="chat-wrapper">`;
                    htmlChat += `</div>`;
                    htmlChat += `<textarea id="sent-message" class="form-control"></textarea>

                        </div><button class="float-right col-2 btn btn-primary send-loc" type="button" id="send-button" onclick="sendGmailMessage('${order.contacts.email}', ${allOrders[orderIndex].id})">Send</button>
                              <button class="float-right col-2 btn btn-primary send-loc" type="button" id="mark-button" onclick="markGmailMessageRead('${order.contacts.email}', ${allOrders[orderIndex].id})">Mark as read</button>`

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
                    htmlChat += `<textarea id="sent-message" class="form-control"></textarea>

                        </div><button class="float-right col-2 btn btn-primary send-loc" type="button" id="send-button" onclick="sendGmailMessage('${order.contacts.email}', ${allOrders[orderIndex].id})">Send</button>
                                <button class="float-right col-2 btn btn-primary send-loc" type="button" id="mark-button" onclick="markGmailMessageRead('${order.contacts.email}', ${allOrders[orderIndex].id})">Mark as read</button>`

                }
            }
        });
    $('#chat').html(htmlChat);
    $('#chat').scrollTop(2000);

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
            if (allOrdersforModal[i].status === "UNPROCESSED" || allOrdersforModal[i].status === "PROCESSING") {
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
    $('#modalBody').html(html);
    document.getElementById("chat").setAttribute('onscroll', 'scrolling()');
    setLocaleFields();
}

async function getAllOrders() {
    const url = '/api/admin/getAllOrders';
    const res = await fetch(url);
    const data = await res.json();
    return data;
}

async function scrolling() {
    document.getElementById("chat").removeAttribute('onscroll');
    let order = allOrders[orderIndex];
    if ($('#chat').scrollTop() < 5 && $('#chat').scrollTop() > 0) {
        $('#chat').scrollTop(10);
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

function orderProcess(id) {
    if (confirm('Do you really want to PROCESS order?')) {
        fetch("/api/admin/processOrder/" + id, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json;charset=utf-8"
            },
            body: JSON.stringify(id),
        }).then(r => startCountOfOrder())
            .then(r => showListOrders())
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
        }).then(r => startCountOfOrder())
            .then(r => showListOrders())
    }
}

function sendGmailMessage(userId, orderId) {
    let sendButton = document.getElementById("send-button");
    sendButton.disabled = true;
    let message = document.getElementById("sent-message").value;
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
            document.getElementById("sent-message").value = "";
            sendButton.disabled = false;
        })
        .then(() => {
            markGmailMessageRead(userId, orderId);
        });
}

function markGmailMessageRead(userId, orderId){
    let urlToMarkMessage = "/admin/markmessageasread/" + userId + "/" + orderId;
    fetch(urlToMarkMessage,{
        method: "GET"
    }).then(
        function(response) {
            if (response.status === 200) {
                response.json().then(function (data) {
                    if (data.markasread){
                        alert("Message was marked as read.");
                    }
                });
            } else {
                alert("Something went wrong.");
            }
            response.json().then(function (data) {
                console.log(data);
            });
        }
    );
}

async function getLastOrderedBooks() {
    const url = '/api/book/lastOrderedBooks';
    const res = await fetch(url);
    const data = await res.json();
    return data;
}

/* check DB marker 'isShow' in Book table
*  returns books ID's
* */
async function getBooksAvailability() {
    const url = '/api/book/booksAvailability';
    const res = await fetch(url);
    const data = await res.json();
    return data;
}

// liveSearch

let filterInput = $('#order-chat');
let filterUl = $('.ul-orders');
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
    $('#order-chat').val('');
    filterInput.val($(this).text());
    filterInput.trigger('change');
    filterUl.fadeOut(100);
    editTable($(this).text());
});

function editTable(inputValue) {
    let rows = document.getElementsByTagName("tbody").item(0).getElementsByTagName("tr");
    let rowEmail;
    $.each(rows, function (index) {
        rowEmail = rows[index].querySelector('td:nth-child(2)').textContent;
        if (rowEmail.match(inputValue)) {
            rows[index].removeAttribute("hidden")
        } else {
            rows[index].setAttribute("hidden", "hidden");
        }
    });
}