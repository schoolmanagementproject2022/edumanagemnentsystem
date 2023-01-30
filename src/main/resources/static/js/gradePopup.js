function PopUpShowGradeWednesday() {
    let dateValue = document.getElementById("wednesdayDate").innerText
    let courseName = document.getElementById("course-name").innerText
    let classId = document.getElementById("classId").value
    let courseId = document.getElementById("courseId").value
    document.getElementById("date-wednesday").value = dateValue
    document.getElementById("course-wednesday").innerText = courseName + ': Agenda ' + dateValue
    document.getElementById("course-wednesday-tooltip").innerText = courseName + ': Agenda ' + dateValue
    $.ajax({
        type: 'GET',
        url: "/agendas/check/" + classId + '/' + courseId + '/' + dateValue,
        dataType: 'json',
        contentType: "application/json",
        success: function (response) {
            if (response != null) {
                response = JSON.stringify(response);
                let parse = JSON.parse(response);
                if (document.getElementById("classwork-wednesday").value === "") {
                    document.getElementById("classwork-wednesday").value = parse.classwork

                }
                if (document.getElementById("homework-wednesday").value === "") {
                    document.getElementById("homework-wednesday").value = parse.homework
                }
                if (document.getElementById("test-wednesday").value === "") {
                    document.getElementById("test-wednesday").value = parse.test
                }
            }
            console.log(response);
        }
    })


    $("#popup-container-grade-wednesday").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}
function PopUpShowGradeMonday() {
    let dateValue = document.getElementById("mondayDate").innerText
    let courseName = document.getElementById("course-name").innerText
    let classId = document.getElementById("classId").value
    let courseId = document.getElementById("courseId").value
    document.getElementById("date-monday").value = dateValue
    document.getElementById("course-monday").innerText = courseName + ': Agenda ' + dateValue
    document.getElementById("course-monday-tooltip").innerText = courseName + ': Agenda ' + dateValue
    $.ajax({
        type: 'GET',
        url: "/agendas/check/" + classId + '/' + courseId + '/' + dateValue,
        dataType: 'json',
        contentType: "application/json",
        success: function (response) {
            if (response != null) {
                response = JSON.stringify(response);
                let parse = JSON.parse(response);
                if (document.getElementById("classwork-monday").value === "") {
                    document.getElementById("classwork-monday").value = parse.classwork

                }
                if (document.getElementById("homework-monday").value === "") {
                    document.getElementById("homework-monday").value = parse.homework
                }
                if (document.getElementById("test-monday").value === "") {
                    document.getElementById("test-monday").value = parse.test
                }
            }
            console.log(response);
        }
    })


    $("#popup-container-grade-monday").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}
function PopUpShowGradeTuesday() {
    let dateValue = document.getElementById("tuesdayDate").innerText
    let courseName = document.getElementById("course-name").innerText
    let classId = document.getElementById("classId").value
    let courseId = document.getElementById("courseId").value
    document.getElementById("date-tuesday").value = dateValue
    document.getElementById("course-tuesday").innerText = courseName + ': Agenda ' + dateValue
    document.getElementById("course-tuesday-tooltip").innerText = courseName + ': Agenda ' + dateValue
    $.ajax({
        type: 'GET',
        url: "/agendas/check/" + classId + '/' + courseId + '/' + dateValue,
        dataType: 'json',
        contentType: "application/json",
        success: function (response) {
            if (response != null) {
                response = JSON.stringify(response);
                let parse = JSON.parse(response);
                if (document.getElementById("classwork-tuesday").value === "") {
                    document.getElementById("classwork-tuesday").value = parse.classwork

                }
                if (document.getElementById("homework-tuesday").value === "") {
                    document.getElementById("homework-tuesday").value = parse.homework
                }
                if (document.getElementById("test-tuesday").value === "") {
                    document.getElementById("test-tuesday").value = parse.test
                }
            }
            console.log(response);
        }
    })


    $("#popup-container-grade-tuesday").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}
function PopUpShowGradeThursday() {
    let dateValue = document.getElementById("thursdayDate").innerText
    let courseName = document.getElementById("course-name").innerText
    let classId = document.getElementById("classId").value
    let courseId = document.getElementById("courseId").value
    document.getElementById("date-thursday").value = dateValue
    document.getElementById("course-thursday").innerText = courseName + ': Agenda ' + dateValue
    document.getElementById("course-thursday-tooltip").innerText = courseName + ': Agenda ' + dateValue
    $.ajax({
        type: 'GET',
        url: "/agendas/check/" + classId + '/' + courseId + '/' + dateValue,
        dataType: 'json',
        contentType: "application/json",
        success: function (response) {
            if (response != null) {
                response = JSON.stringify(response);
                let parse = JSON.parse(response);
                if (document.getElementById("classwork-thursday").value === "") {
                    document.getElementById("classwork-thursday").value = parse.classwork

                }
                if (document.getElementById("homework-thursday").value === "") {
                    document.getElementById("homework-thursday").value = parse.homework
                }
                if (document.getElementById("test-thursday").value === "") {
                    document.getElementById("test-thursday").value = parse.test
                }
            }
            console.log(response);
        }
    })


    $("#popup-container-grade-thursday").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}
 function PopUpShowGradeFriday() {
    let dateValue = document.getElementById("fridayDate").innerText
    let courseName = document.getElementById("course-name").innerText
    let classId = document.getElementById("classId").value
    let courseId = document.getElementById("courseId").value
    document.getElementById("date-friday").value = dateValue
    document.getElementById("course-friday").innerText = courseName + ': Agenda ' + dateValue
    document.getElementById("course-friday-tooltip").innerText = courseName + ': Agenda ' + dateValue
    $.ajax({
        type: 'GET',
        url: "/agendas/check/" + classId + '/' + courseId + '/' + dateValue,
        dataType: 'json',
        contentType: "application/json",
        success: function (response) {
            if (response != null) {
                response = JSON.stringify(response);
                let parse = JSON.parse(response);
                if (document.getElementById("classwork-friday").value === "") {
                    document.getElementById("classwork-friday").value = parse.classwork

                }
                if (document.getElementById("homework-friday").value === "") {
                    document.getElementById("homework-friday").value = parse.homework
                }
                if (document.getElementById("test-friday").value === "") {
                    document.getElementById("test-friday").value = parse.test
                }
            }
            console.log(response);
        }
    })


    $("#popup-container-grade-friday").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}
 function PopUpShowGradeSaturday() {
    let dateValue = document.getElementById("saturdayDate").innerText
    let courseName = document.getElementById("course-name").innerText
    let classId = document.getElementById("classId").value
    let courseId = document.getElementById("courseId").value
    document.getElementById("date-saturday").value = dateValue
    document.getElementById("course-saturday").innerText = courseName + ': Agenda ' + dateValue
    document.getElementById("course-saturday-tooltip").innerText = courseName + ': Agenda ' + dateValue
    $.ajax({
        type: 'GET',
        url: "/agendas/check/" + classId + '/' + courseId + '/' + dateValue,
        dataType: 'json',
        contentType: "application/json",
        success: function (response) {
            if (response != null) {
                response = JSON.stringify(response);
                let parse = JSON.parse(response);
                if (document.getElementById("classwork-saturday").value === "") {
                    document.getElementById("classwork-saturday").value = parse.classwork

                }
                if (document.getElementById("homework-saturday").value === "") {
                    document.getElementById("homework-saturday").value = parse.homework
                }
                if (document.getElementById("test-saturday").value === "") {
                    document.getElementById("test-saturday").value = parse.test
                }
            }
            console.log(response);
        }
    })


    $("#popup-container-grade-saturday").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}
function PopUpShowGradeSunday() {
   let dateValue = document.getElementById("sundayDate").innerText
    let courseName = document.getElementById("course-name").innerText
    let classId = document.getElementById("classId").value
    let courseId = document.getElementById("courseId").value
    document.getElementById("date-sunday").value = dateValue
    document.getElementById("course-sunday").innerText = studentName + ':' + dateValue
    document.getElementById("course-sunday-tooltip").innerText = courseName + ': Agenda ' + dateValue
    $.ajax({
        type: 'GET',
        url: "/agendas/check/" + classId + '/' + courseId + '/' + dateValue,
        dataType: 'json',
        contentType: "application/json",
        success: function (response) {
            if (response != null) {
                response = JSON.stringify(response);
                let parse = JSON.parse(response);
                if (document.getElementById("classwork-sunday").value === "") {
                    document.getElementById("classwork-sunday").value = parse.classwork

                }
                if (document.getElementById("homework-sunday").value === "") {
                    document.getElementById("homework-sunday").value = parse.homework
                }
                if (document.getElementById("test-sunday").value === "") {
                    document.getElementById("test-sunday").value = parse.test
                }
            }
            console.log(response);
        }
    })


    $("#popup-container-grade-sunday").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}

function hideMondayGradePopup() {
    let dateValue = document.getElementById("mondayDate").innerText
    window.location.href = host + `/classes/${id}/journal/${course_id}?date=` + dateValue;
    $("#header").css("-webkit-filter", "blur(0px)");
    $("#header").css("pointer-events", "auto");
    $("#show-btn").css("pointer-events", "auto");
    $("#main-wrapper").css("pointer-events", "auto");
    $("#show-btn").css("-webkit-filter", "blur(0px)");
    $("#main-wrapper").css("-webkit-filter", "blur(0px)");
}
function hideTuesdayGradePopup() {
    let dateValue = document.getElementById("tuesdayDate").innerText
    window.location.href = host + `/classes/${id}/journal/${course_id}?date=` + dateValue;
    $("#header").css("-webkit-filter", "blur(0px)");
    $("#header").css("pointer-events", "auto");
    $("#show-btn").css("pointer-events", "auto");
    $("#main-wrapper").css("pointer-events", "auto");
    $("#show-btn").css("-webkit-filter", "blur(0px)");
    $("#main-wrapper").css("-webkit-filter", "blur(0px)");
}
function hideWednesdayGradePopup() {
    let dateValue = document.getElementById("wednesdayDate").innerText
    window.location.href = host + `/classes/${id}/journal/${course_id}?date=` + dateValue;
    $("#header").css("-webkit-filter", "blur(0px)");
    $("#header").css("pointer-events", "auto");
    $("#show-btn").css("pointer-events", "auto");
    $("#main-wrapper").css("pointer-events", "auto");
    $("#show-btn").css("-webkit-filter", "blur(0px)");
    $("#main-wrapper").css("-webkit-filter", "blur(0px)");
}
function hideThursdayGradePopup() {
    let dateValue = document.getElementById("thursdayDate").innerText
    window.location.href = host + `/classes/${id}/journal/${course_id}?date=` + dateValue;
    $("#header").css("-webkit-filter", "blur(0px)");
    $("#header").css("pointer-events", "auto");
    $("#show-btn").css("pointer-events", "auto");
    $("#main-wrapper").css("pointer-events", "auto");
    $("#show-btn").css("-webkit-filter", "blur(0px)");
    $("#main-wrapper").css("-webkit-filter", "blur(0px)");
}
function hideFridayGradePopup() {
    let dateValue = document.getElementById("fridayDate").innerText
    window.location.href = host + `/classes/${id}/journal/${course_id}?date=` + dateValue;
    $("#header").css("-webkit-filter", "blur(0px)");
    $("#header").css("pointer-events", "auto");
    $("#show-btn").css("pointer-events", "auto");
    $("#main-wrapper").css("pointer-events", "auto");
    $("#show-btn").css("-webkit-filter", "blur(0px)");
    $("#main-wrapper").css("-webkit-filter", "blur(0px)");
}
function hideSaturdayGradePopup() {
    let dateValue = document.getElementById("saturdayDate").innerText
    window.location.href = host + `/classes/${id}/journal/${course_id}?date=` + dateValue;
    $("#header").css("-webkit-filter", "blur(0px)");
    $("#header").css("pointer-events", "auto");
    $("#show-btn").css("pointer-events", "auto");
    $("#main-wrapper").css("pointer-events", "auto");
    $("#show-btn").css("-webkit-filter", "blur(0px)");
    $("#main-wrapper").css("-webkit-filter", "blur(0px)");
}
function hideSundayGradePopup() {
    let dateValue = document.getElementById("sundayDate").innerText
    window.location.href = host + `/classes/${id}/journal/${course_id}?date=` + dateValue;
    $("#header").css("-webkit-filter", "blur(0px)");
    $("#header").css("pointer-events", "auto");
    $("#show-btn").css("pointer-events", "auto");
    $("#main-wrapper").css("pointer-events", "auto");
    $("#show-btn").css("-webkit-filter", "blur(0px)");
    $("#main-wrapper").css("-webkit-filter", "blur(0px)");
}

