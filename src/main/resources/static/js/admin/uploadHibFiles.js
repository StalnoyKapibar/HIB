
//Установка языковых параметров страницы
$(document).ready(function () {
    setLocaleFields();
});

//Общий список книг
let bookListLocal = [];

//Список всех созданных временных папок с картинками для последующей очистки
let picsFoldersNames = [];

//Подготовка и отрисовка таблицы
async function prepareTable(){
    if (bookListLocal.length === 0) {
        renderTableHead();
    }
    fillBookList();
}

//Функция наполнения списка книг из загруженных в форму HIB-файлов
async function fillBookList(){
    let books = document.getElementById('add-hib-files-input').files;
    let booksLength = books.length;
    let constant = bookListLocal.length
    let nextId;

    $.ajax({
        type: "GET",
        async: false,
        url: '/getNextIdOfBooks',
        success: (id) => {
            nextId = id;
        },
        error: (e) => {
            console.log("ERROR: ", e);
        }
    });

    for (let i = 0; i < booksLength; i++) {
        fetch('/api/admin/get-book-from-json', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: books[i]
        })
            .then(json)
            .then(async function (book) {
                console.log("Success! HIB-file added");
                bookListLocal[i + constant] = book;
                picsFoldersNames.push(book.originalLanguage.name);
                appendTableElement(book, i + constant);
            });
    }
}

//Функция сборки сущности Book для передачи в контроллер
function assembleBook(book, str, id) {
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

    //Установка доступности книги к покупке
    if (id !== undefined) {
        json["show"] = (!$("#disabled" + id).is(':checked'));
    }

    if (str === 1) {
        json["originalLanguage"] = book.originalLanguage;
        return json;
    } else if (str === 2) {
        return JSON.stringify(json); //Необходимо для сравнения двух книг при добавлении в bookListLocal
    } else if (str === 0) {
        json["originalLanguage"] = book.originalLanguage;
        return JSON.stringify(json);
    }
}

//Функция всех книг из списка "bookListLocal" на сервер (для функции uploadAll ниже)
async function listUpload() {
    let listForUpload = [];
    for (let i = 0; i < bookListLocal.length; i++) {
        listForUpload[i] = (assembleBook(bookListLocal[i], 1, i));
    }
    fetch('/api/admin/upload-all-books', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(listForUpload)
    }).then(r => {
        opener.location.reload();
        window.close();
    });
}

//Функция загрузки всех книг из списка "bookListLocal"
async function uploadAll() {
    if (bookListLocal.length === 0) {
        alert("There are no books to upload!")
    } else {
        listUpload();
    }
}

//Функция удаления временных папок хранения изображений
async function clearTempPics(picsFolder){
    fetch('/api/admin/clear-temp-pics', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: picsFolder
    }).then();
}

//Функция загрузки одной книги
async function uploadBook(id){
    let book = bookListLocal[id];

    fetch('/api/admin/upload-book', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: assembleBook(book, 0, id)
    }).then(r => {
        if (bookListLocal.length < 1) {
            opener.location.reload();
            window.close();
        }
    });

    deleteFromBookList(id);
}

//Функция удаления книги из списка и таблицы
function deleteFromBookList(id, tempPicsDelete) {
    if (bookListLocal.length === 1) {
        if (tempPicsDelete === 1) {
            clearTempPics(bookListLocal[id].originalLanguage.name);
        }
        bookListLocal.splice(id, 1);
        $('#uploaded-HIB-Files').empty();
    } else {
        if (tempPicsDelete === 1) {
            clearTempPics(bookListLocal[id].originalLanguage.name);
        }
        bookListLocal.splice(id, 1);
        $('#tableBody').empty();
        for (let i = 0; i < bookListLocal.length; i++) {
            appendTableElement(bookListLocal[i], i);
        }
    }
}

//Функция отрисовки шапки таблицы
function renderTableHead(){
    $('#uploaded-HIB-Files').append(`<h6 class="text-center">Add more HIB-files if needed</h6>
                            <table class="table table-sm table-striped text-center bg-white" id="hibFilesTable">
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
                                <br/><br/>
                                <div class="custom-control custom-switch align-items-center">
                                    <!-- Переключатель деактиватора книги -->
                                    <input class="custom-control-input" type="checkbox" id="disabled${i}" checked>
                                    <label class="custom-control-label" for="disabled${i}"><b>Disabled</b></label>
                                </div>
                           </td>
                           <td id="bookName${i}" class="align-middle"></td>
                           <td id="bookAuthor${i}" class="align-middle"></td>
                           <td class="align-middle">${JSON.stringify(book.pages)}</td>
                           <td id="bookEdition${i}" class="align-middle"></td>
                           <td class="align-middle">${JSON.stringify(book.price)}</td>
                           <td id="bookCategory${i}" class="align-middle"></td>
                           <td class="align-middle">
                                <button class="btn btn-danger delete-loc" onclick="deleteFromBookList(${i}, 1)">Delete</button>
                                <br/><br/>
                                <button class="btn btn-info delete-loc" onclick="uploadBook(${i})">Upload</button>
                           </td></tr>`);
    //Установка языка для отображения полей книги по-умолчанию в соответствии с выбранным языком на сайте
    let currentHomeLang = $("#selectLang" + i + " > option:contains(" + currentLang.toUpperCase() + ")");
    document.getElementById("selectLang" + i).options.selectedIndex = currentHomeLang.index();
    document.getElementById("selectLangForAll").options.selectedIndex = currentHomeLang.index();
    showField(i);
}

//Функция преобразования объекта книги, полученной из контроллера, в объект для работы в JS
function json(response) {
    return response.json()
}
