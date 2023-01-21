function PopUpShow() {
    $("#popup-container").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}

function PopUpShowImage() {
    $("#popup-container-image").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}

function PopUpShowImageDelete() {
    $("#popup-container-image-delete").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}

function PopUpHide() {
    window.location.href = $("#tab").text();
    $("#header").css("-webkit-filter", "blur(0px)");
    $("#header").css("pointer-events", "auto");
    $("#show-btn").css("pointer-events", "auto");
    $("#main-wrapper").css("pointer-events", "auto");
    $("#show-btn").css("-webkit-filter", "blur(0px)");
    $("#main-wrapper").css("-webkit-filter", "blur(0px)");
}

function PopUpShowImageFormat() {
    $("#popup-container-image-format").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}

function showInputFieldsOnMouseover() {
    var texttooltip = document.getElementsByClassName("form_input");
        var updatetitle = function () {
            this.setAttribute("title", this.value);
        };
        for (var i = 0; i < texttooltip.length; i++) {
            texttooltip[i].addEventListener('mouseover', updatetitle, false);
    }

}
function showDotsInLength(){
    var maxLength = 23;
    $('#classroomTeacher > option').text(function (i, text) {
        if (text.length > maxLength) {
            return text.substr(0, maxLength) + '...';
        }
    });
}

function keepMultipartFile() {
    // Get a reference to our file input
    const fileInput = document.querySelector('input[type="file"]');

    const multipartFile = document.getElementById("multipartFile").value
    const contentType = document.getElementById("contentType").value
    const originalFileName = document.getElementById("originalFileName").value
    const byteArray = document.getElementById("byteArray").value

    // Create a new File object
    const myFile = new File([multipartFile], originalFileName, {
        type: contentType,
        lastModified: new Date(),
    });

    // Now let's create a DataTransfer to get a FileList
    const dataTransfer = new DataTransfer();
    dataTransfer.items.add(myFile);
    fileInput.files = dataTransfer.files;




    // Get the input element
    // var fileInput = document.getElementById("picture");
    //
    // var multipartFile = document.getElementById("multipartFile").value
    //
    // var blob = new Blob([multipartFile.data], {type: multipartFile.contentType});
    //
    // var file = new File([blob], multipartFile.name, { type: multipartFile.contentType });

// Create a file list object
//     var fileList = new FileList({ 0: file, length: 1 });

// Set the value of the input element to the file list object
//     fileInput.files = fileList;
}