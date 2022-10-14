function PopUpShowMonday() {
    $("#popup-container-monday").show();
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
}

function PopUpShowTuesday() {
    $("#popup-container-tuesday").show();
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
}

function PopUpShowWednesday() {
    $("#popup-container-wednesday").show();
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
}

function PopUpShowThursday() {
    $("#popup-container-thursday").show();
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
}

function PopUpShowFriday() {
    $("#popup-container-friday").show();
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
}

function PopUpShowSaturday() {
    $("#popup-container-saturday").show();
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
}

function PopUpShowSunday() {
    $("#popup-container-sunday").show();
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
}

function PopUpHide() {
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


function save(){
    //val dzerqov stex tal te html mej?
    var inputValue=$('#1').val();
    sessionStorage.setItem('save', inputValue);
    location.href='/classes/3a/timetable';
    SuccessPopupShow();
}

function SuccessPopupShow() {
    var value = sessionStorage.getItem('save').valueOf();
    if (value === "1") {
        return $("#popup-container").show();
    }
}

function SuccessPopupHide() {
    $("#popup-container").hide();
}