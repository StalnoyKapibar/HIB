//Список всех созданных временных папок изображений
let picsTempFoldersList;

//Установка языковых параметров страницы
$(document).ready(function () {
    setLocaleFields();
    loadAllHIBs();
});

//Общий список книг
let bookListLocal = [];

//Функция загрузки таблицы предпоказа всех HIB-файлов, хранящихся на сервере в папке "./HIB/"
async function loadAllHIBs() {

    $.ajax({
        type: "GET",
        url: "/api/admin/get-all-existing-hibs",
        success: (books) => {
            if (picsTempFoldersList === undefined || picsTempFoldersList.length !== 0) {
                picsTempFoldersList = [];
            }
            if (books.length !== 0) {
                renderTableHead();
            }
            for (let i = 0; i < books.length; i++) {
                bookListLocal[i] = books[i];
                picsTempFoldersList[i] = books[i].originalLanguage.name;
                appendTableElement(books[i], i);
            }
        }
    })
}


//Функция загрузки новых HIB-файлов на сервер
async function uploadNewHIBs(){
    let inputHIBs = document.getElementById('add-hib-files-input').files;

    let data = new FormData();
    for (let i = 0; i < inputHIBs.length; i++) {
        data.append("hib", inputHIBs[i]);
    }

    $.ajax({
        type: "POST",
        url: '/api/admin/upload-new-hibs',
        data: data,
        processData: false,
        contentType: false,
    }).then(r => {
        location.reload();
    });
}

//Функция сборки сущности Book для передачи в контроллер
function assembleBook(book, str) {
    let json = {};

    json["name"] = book.name;
    json["author"] = book.author;
    json["originalLanguageName"] = book.originalLanguageName;
    json["edition"] = book.edition;
    json["listImage"] = book.listImage;
    json["pages"] = book.pages;
    json["price"] = book.price;
    json["yearOfEdition"] = book.yearOfEdition;
    json["description"] = book.description;
    json["coverImage"] = book.coverImage;
    json["category"] = book.category;
    json["show"] = true;

    if (str === 1) {
        json["originalLanguage"] = book.originalLanguage;
        return json;
    } else if (str === 0) {
        json["originalLanguage"] = book.originalLanguage;
        return JSON.stringify(json);
    }
}

//Функция отправки всех книг из списка "bookListLocal" на сервер (для функции uploadAll ниже)
async function listUpload() {
    let listForUpload = [];
    for (let i = 0; i < bookListLocal.length; i++) {
        listForUpload[i] = (assembleBook(bookListLocal[i], 1));
    }
    fetch('/api/admin/upload-all-books', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(listForUpload)
    }).then(r => {
        if (window.opener !== null) {
            opener.location.reload();
        }
        window.close();
    });
}

//Функция сохранения на сервер всех книг из соотв. HIB-файлов, удаление HIB-файлов
async function uploadAll() {
    if (bookListLocal.length === 0) {
        alert("There are no books to upload!")
    } else {
        if (confirm("Upload all books unedited? \n" +
            "This operation will delete all local HIB-files!")) {
            listUpload();
        }
    }
}

//Переход на страницу редактирования книги из HIB-файла и ее дальнейшее сохранение
async function uploadToEdit(id){
    let book = bookListLocal[id];
    window.open('/admin/book/' + book.originalLanguage.author, '_blank');
}

//Функция удаления HIB-файла с хранилища сервера
function deleteHIBFile(id) {
    fetch('/api/admin/delete-HIB-file?name=' + bookListLocal[id].originalLanguage.author, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
    }).then(r => {
        location.reload();
    })
}

//Функция отрисовки шапки таблицы
function renderTableHead(){
    $('#uploaded-HIB-Files').append(`<table class="table table-sm table-striped text-center bg-white" id="hibFilesTable">
                            <thead>
                                <tr height="50">
                                    <th width="10"></th>
                                    <th></th>
                                    <th>For all: 
                                    <select id="selectLangForAll" onchange="showAllFields()">
                                        <option value="ru">RU</option>
                                        <option value="en">EN</option>
                                        <option value="de">DE</option>
                                        <option value="fr">FR</option>
                                        <option value="it">IT</option>
                                        <option value="gr">GR</option>
                                        <option value="cs">CS</option>
                                    </select>
                                    </th>
                                    <th class="name-loc">Name</th>
                                    <th class="author-loc">Author</th>
                                    <th class="pages-loc">Pages</th>
                                    <th class="edition-loc">Edition</th>
                                    <th><span class="price-loc">Price</span>, €</th>
                                    <th class="categories-loc">Category</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody id="tableBody"></tbody>
                            </table>`);
}

//Функция выбора языка отображения полей для всех книг в таблице
function showAllFields() {
    let index = document.getElementById("selectLangForAll").options.selectedIndex;
    for (let i = 0; i < bookListLocal.length; i++) {
        document.getElementById("selectLang" + i).options.selectedIndex = index;
        showField(i);
    }
}

//Функция выбора языка для отображения полей книги
function showField(bookId) {
    let index = document.getElementById("selectLang" + bookId).options.selectedIndex;
    let lang = document.getElementById("selectLang" + bookId).options[index].value;
    $('#tableBody').find('#bookName' + bookId).html(bookListLocal[bookId].name[lang]);
    $('#tableBody').find('#bookAuthor' + bookId).html(bookListLocal[bookId].author[lang]);
    $('#tableBody').find('#bookEdition' + bookId).html(bookListLocal[bookId].edition[lang] + " (" + bookListLocal[bookId].yearOfEdition + ")");
    $('#tableBody').find('#bookCategory' + bookId).html(bookListLocal[bookId].category.name[lang]);
}

//Функция добавления строки таблицы с информацией о книге
async function appendTableElement(book, i){
    let picsFolderName = book.originalLanguage.name;

    let coverImage = JSON.stringify(book.coverImage);
    let path = "/images/tmp/" + picsFolderName + "/" + coverImage.replace(new RegExp('"','g'), '');
    $('#tableBody').append(`<tr id="tr${i}">
                           <td width="10"></td>
                           <td class="align-middle"><img src="${path}" style="max-width: 100px"></td>
                           <td class="align-middle">
                                <select id="selectLang${i}" onchange="showField(${i})">
                                    <option value="ru">RU</option>
                                    <option value="en">EN</option>
                                    <option value="de">DE</option>
                                    <option value="fr">FR</option>
                                    <option value="it">IT</option>
                                    <option value="gr">GR</option>
                                    <option value="cs">CS</option>
                                </select>
                           </td>
                           <td id="bookName${i}" class="align-middle"></td>
                           <td id="bookAuthor${i}" class="align-middle"></td>
                           <td class="align-middle">${JSON.stringify(book.pages)}</td>
                           <td id="bookEdition${i}" class="align-middle"></td>
                           <td class="align-middle">${JSON.stringify(book.price)}</td>
                           <td id="bookCategory${i}" class="align-middle"></td>
                           <td class="align-middle">
                                <button class="btn btn-info delete-loc" onclick="uploadToEdit(${i})">Edit and save</button>
                                <br/><br/>
                                <button class="btn btn-danger delete-loc" onclick="deleteHIBFile(${i})">Delete</button>
                           </td></tr>`);
    //Установка языка для отображения полей книги по-умолчанию в соответствии с выбранным языком на сайте
    let currentHomeLang = $("#selectLang" + i + " > option:contains(" + currentLang.toUpperCase() + ")");
    document.getElementById("selectLang" + i).options.selectedIndex = currentHomeLang.index();
    document.getElementById("selectLangForAll").options.selectedIndex = currentHomeLang.index();
    showField(i);
}

//Функция преобразования объекта, полученной из контроллера, в объект для работы в JS
function json(response) {
    return response.json()
}
