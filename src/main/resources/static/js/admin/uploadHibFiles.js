let addBookTab = $('#nav-profile-tab1');

function uploadOneFile() {
    $("#uploadOneFileHidden").trigger('click');
}

function uploadMultiplyFiles() {
    $("#uploadMultiplyFilesHidden").trigger('click');
}

function editHibFile() {
    addPage();
    loadBookFile();
    addBookTab.tab('show');
}