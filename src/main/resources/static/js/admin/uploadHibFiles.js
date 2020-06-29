var uploadedBookName = null;
let addBookTab = $('#nav-profile-tab1');
let hibFilesTableBody = $("#hibFilesTable tbody");
let alertFileNotFound = '';

$(document).ready(function () {
    renderHibFilesTable();
    setLocaleFields();
});

function uploadOneFile() {
    $("#uploadOneFileHidden").trigger('click');
}

function uploadMultiplyFiles() {
    let files = $('#add-hib-files-input')[0].files;
    if (files.length === 0) {
        alert(alertFileNotFound);
    } else {
        fetch("/api/admin/upload-multiply-files", {
            method: 'POST',
            body: files
        })
            .then($("#uploadMultiplyFilesHidden").trigger('click'))
            .then($(this).tab('show'));
    }
}

function editHibFile() {
    addPage();
    loadBookFile();
    $('#add-hib-files').tab('show');
}

function renderHibFilesTable() {
    GET("/api/admin/hib-dto")
        .then(json)
        .then((resp) => {
            hibFilesTableBody.empty();
            for (let i = 0; i < resp.length; i++) {
                let avatar = resp[i].imageAsBase64;
                let name = resp[i].nameOfBook;
                let fileName = resp[i].name;
                let tr = $("<tr/>");
                tr.append(`<td width="10"></td>
                           <td class="align-middle"><img src="data:image/png;base64,${avatar}" style="max-width: 60px"></td>
                           <td class="align-middle">${name}</td>
                           <td class="align-middle"><button data-filename="${fileName}" class="btn btn-metro btn-info upload-for-sale">Upload for sale</button></td>
                           <td class="align-middle"><button data-filename="${fileName}" class="btn btn-info delete-hib-file delete-loc" style="background-color: #ff4500" data-id="5">Delete</button></td>`)
                hibFilesTableBody.append(tr);
            }
        });
}


$(document).on('click', ".upload-for-sale", async function () {
    await GET("/api/admin/hib?name=" + $(this).attr("data-filename"))
        .then(json)
        .then((resp) => {
            uploadedBookName = $(this).attr("data-filename");
            addPage();
            addValueToFields(resp);
            addBookTab.tab('show');
        })
});

$(document).on('click', ".delete-hib-file", function () {
    sendDeleteRequest($(this).attr("data-filename"))
});

function sendDeleteRequest(name) {
    fetch(`/api/admin/hib/${name}`, {
        method: 'DELETE'
    }).then(renderHibFilesTable);
}

