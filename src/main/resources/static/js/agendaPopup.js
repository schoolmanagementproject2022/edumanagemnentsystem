function PopUpShowAgendaMonday() {
    let dateValue = document.getElementById("mondayDate").innerText
    let courseName = document.getElementById("course-name").innerText
    document.getElementById("date-monday").value = dateValue
    document.getElementById("course-monday").innerText = courseName + ': Agenda ' + dateValue
    $("#popup-container-agenda-monday").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}

function PopUpShowAgendaTuesday() {
    let dateValue = document.getElementById("tuesdayDate").innerText
    let courseName = document.getElementById("course-name").innerText
    document.getElementById("course-tuesday").innerText = courseName + ': Agenda ' + dateValue
    document.getElementById("date-tuesday").value= dateValue
    $("#popup-container-agenda-tuesday").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}

function PopUpShowAgendaWednesday() {
    let dateValue = document.getElementById("wednesdayDate").innerText
    let courseName = document.getElementById("course-name").innerText
    document.getElementById("course-wednesday").innerText = courseName + ': Agenda ' + dateValue
    document.getElementById("date-wednesday").value= dateValue
    $("#popup-container-agenda-wednesday").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}

function PopUpShowAgendaThursday() {
    let dateValue = document.getElementById("thursdayDate").innerText
    let courseName = document.getElementById("course-name").innerText
    document.getElementById("course-thursday").innerText = courseName + ': Agenda ' + dateValue
    document.getElementById("date-thursday").value= dateValue
    $("#popup-container-agenda-thursday").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}

function PopUpShowAgendaFriday() {
    let dateValue = document.getElementById("fridayDate").innerText
    let courseName = document.getElementById("course-name").innerText
    document.getElementById("course-friday").innerText = courseName + ': Agenda ' + dateValue
    document.getElementById("date-friday").value= dateValue
    $("#popup-container-agenda-friday").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}

function PopUpShowAgendaSaturday() {
    let dateValue = document.getElementById("saturdayDate").innerText
    let courseName = document.getElementById("course-name").innerText
    document.getElementById("course-saturday").innerText = courseName + ': Agenda ' + dateValue
    document.getElementById("date-saturday").value= dateValue
    $("#popup-container-agenda-saturday").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}

function PopUpShowAgendaSunday() {
    let dateValue = document.getElementById("sundayDate").innerText
    let courseName = document.getElementById("course-name").innerText
    document.getElementById("course-sunday").innerText = courseName + ': Agenda ' + dateValue
    document.getElementById("date-sunday").value= dateValue
    $("#popup-container-agenda-sunday").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}

function hideMondayPopup() {
    let dateValue = document.getElementById("mondayDate").innerText
    window.location.href = `http://localhost:8082/classes/${id}/journal/${course_id}?date=` + dateValue;
    $("#header").css("-webkit-filter", "blur(0px)");
    $("#header").css("pointer-events", "auto");
    $("#show-btn").css("pointer-events", "auto");
    $("#main-wrapper").css("pointer-events", "auto");
    $("#show-btn").css("-webkit-filter", "blur(0px)");
    $("#main-wrapper").css("-webkit-filter", "blur(0px)");
}

function hideTuesdayPopup() {
    let dateValue = document.getElementById("tuesdayDate").innerText
    window.location.href = `http://localhost:8082/classes/${id}/journal/${course_id}?date=` + dateValue;
    $("#header").css("-webkit-filter", "blur(0px)");
    $("#header").css("pointer-events", "auto");
    $("#show-btn").css("pointer-events", "auto");
    $("#main-wrapper").css("pointer-events", "auto");
    $("#show-btn").css("-webkit-filter", "blur(0px)");
    $("#main-wrapper").css("-webkit-filter", "blur(0px)");
}

function hideWednesdayPopup() {
    let dateValue = document.getElementById("wednesdayDate").innerText
    window.location.href = `http://localhost:8082/classes/${id}/journal/${course_id}?date=` + dateValue;
    $("#header").css("-webkit-filter", "blur(0px)");
    $("#header").css("pointer-events", "auto");
    $("#show-btn").css("pointer-events", "auto");
    $("#main-wrapper").css("pointer-events", "auto");
    $("#show-btn").css("-webkit-filter", "blur(0px)");
    $("#main-wrapper").css("-webkit-filter", "blur(0px)");
}

function hideThursdayPopup() {
    let dateValue = document.getElementById("thursdayDate").innerText
    window.location.href = `http://localhost:8082/classes/${id}/journal/${course_id}?date=` + dateValue;
    $("#header").css("-webkit-filter", "blur(0px)");
    $("#header").css("pointer-events", "auto");
    $("#show-btn").css("pointer-events", "auto");
    $("#main-wrapper").css("pointer-events", "auto");
    $("#show-btn").css("-webkit-filter", "blur(0px)");
    $("#main-wrapper").css("-webkit-filter", "blur(0px)");
}

function hideFridayPopup() {
    let dateValue = document.getElementById("fridayDate").innerText
    window.location.href = `http://localhost:8082/classes/${id}/journal/${course_id}?date=` + dateValue;
    $("#header").css("-webkit-filter", "blur(0px)");
    $("#header").css("pointer-events", "auto");
    $("#show-btn").css("pointer-events", "auto");
    $("#main-wrapper").css("pointer-events", "auto");
    $("#show-btn").css("-webkit-filter", "blur(0px)");
    $("#main-wrapper").css("-webkit-filter", "blur(0px)");
}

function hideSaturdayPopup() {
    let dateValue = document.getElementById("saturdayDate").innerText
    window.location.href = `http://localhost:8082/classes/${id}/journal/${course_id}?date=` + dateValue;
    $("#header").css("-webkit-filter", "blur(0px)");
    $("#header").css("pointer-events", "auto");
    $("#show-btn").css("pointer-events", "auto");
    $("#main-wrapper").css("pointer-events", "auto");
    $("#show-btn").css("-webkit-filter", "blur(0px)");
    $("#main-wrapper").css("-webkit-filter", "blur(0px)");
}

function hideSundayPopup() {
    let dateValue = document.getElementById("sundayDate").innerText
    window.location.href = `http://localhost:8082/classes/${id}/journal/${course_id}?date=` + dateValue;
    $("#header").css("-webkit-filter", "blur(0px)");
    $("#header").css("pointer-events", "auto");
    $("#show-btn").css("pointer-events", "auto");
    $("#main-wrapper").css("pointer-events", "auto");
    $("#show-btn").css("-webkit-filter", "blur(0px)");
    $("#main-wrapper").css("-webkit-filter", "blur(0px)");
}
