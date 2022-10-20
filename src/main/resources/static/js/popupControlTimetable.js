function PopUpShowMonday() {
    obj.refreshValue = "notToReload";
    console.log("Changed to notToReload");
    $("#popup-container-monday").show();
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
}

function PopUpShowTuesday() {
    obj.refreshValue = "notToReload";
    console.log("Changed to notToReload");
    $("#popup-container-tuesday").show();
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
}

function PopUpShowWednesday() {
    obj.refreshValue = "notToReload";
    console.log("Changed to notToReload");
    $("#popup-container-wednesday").show();
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
}

function PopUpShowThursday() {
    obj.refreshValue = "notToReload";
    console.log("Changed to notToReload");
    $("#popup-container-thursday").show();
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
}

function PopUpShowFriday() {
    obj.refreshValue = "notToReload";
    console.log("Changed to notToReload");
    $("#popup-container-friday").show();
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
}


function PopUpShowSunday() {
    obj.refreshValue = "notToReload";
    console.log("Changed to notToReload");
    $("#popup-container-sunday").show();
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
}

function PopUpShowSaturday() {
    obj.refreshValue = "notToReload";
    console.log("Changed to notToReload");
    $("#popup-container-saturday").show();
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
}

function PopUpHide() {
    obj.refreshValue = "toReload";
    console.log("Changed to toReload");
    clearErrorMessageOnPopUpClose();
    $('select').prop('selectedIndex', 0);
    $("#popup-container-monday").hide();
    $("#popup-container-tuesday").hide();
    $("#popup-container-wednesday").hide();
    $("#popup-container-thursday").hide();
    $("#popup-container-friday").hide();
    $("#popup-container-saturday").hide();
    $("#popup-container-sunday").hide();
    $("#main-wrapper").css("-webkit-filter", "blur(0px)");
    $("#header").css("-webkit-filter", "blur(0px)");
    $("#main-wrapper").css("pointer-events", "auto");
    $("#header").css("pointer-events", "auto");
    $("#show-btn").css("pointer-events", "auto");
    $("#show-btn").css("-webkit-filter", "blur(0px)");
}

function SuccessPopupHide() {
    location.href = $("#timetableUrl").text();
}

function clearErrorMessageOnPopUpClose() {
    var error = document.getElementById('error');
    if (error !== null) {
        error.innerHTML = " ";
    } else {
        error = document.getElementById('error');
    }
}


