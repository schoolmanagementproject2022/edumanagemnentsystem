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
function hideWednesdayGradePopup() {
    let dateValue = document.getElementById("tuesdayDate").innerText
    window.location.href = host + `/classes/${id}/journal/${course_id}?date=` + dateValue;
    $("#header").css("-webkit-filter", "blur(0px)");
    $("#header").css("pointer-events", "auto");
    $("#show-btn").css("pointer-events", "auto");
    $("#main-wrapper").css("pointer-events", "auto");
    $("#show-btn").css("-webkit-filter", "blur(0px)");
    $("#main-wrapper").css("-webkit-filter", "blur(0px)");
}
function PopUpShowGradeWednesday() {
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


    $("#popup-container-grade-wednesday").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}
function hideMondayGradePopup() {
    let dateValue = document.getElementById("tuesdayDate").innerText
    window.location.href = host + `/classes/${id}/journal/${course_id}?date=` + dateValue;
    $("#header").css("-webkit-filter", "blur(0px)");
    $("#header").css("pointer-events", "auto");
    $("#show-btn").css("pointer-events", "auto");
    $("#main-wrapper").css("pointer-events", "auto");
    $("#show-btn").css("-webkit-filter", "blur(0px)");
    $("#main-wrapper").css("-webkit-filter", "blur(0px)");
}
function PopUpShowGradeMonday() {
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


    $("#popup-container-grade-monday").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}
function PopUpShowGradeThursday() {
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


    $("#popup-container-grade-thursday").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}
 function PopUpShowGradeFriday() {
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


    $("#popup-container-grade-friday").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}
 function PopUpShowGradeSaturday() {
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


    $("#popup-container-grade-saturday").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}
function PopUpShowGradeSunday() {
//    let dateValue = document.getElementById("tuesdayDate").innerText
    let courseName = document.getElementById("course-name").innerText
    let studentName = document.querySelector('[student]').innerText
    let classId = document.getElementById("classId").value
    let courseId = document.getElementById("courseId").value
    document.getElementById("date-tuesday").value = dateValue
    document.getElementById("student-sunday").innerText = studentName + ':' + dateValue
    document.getElementById("student-sunday-tooltip").innerText = courseName + ': Agenda ' + dateValue
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


    $("#popup-container-grade-sunday").show();
    $("#header").css("-webkit-filter", "blur(10px)");
    $("#header").css("pointer-events", "none");
    $("#show-btn").css("pointer-events", "none");
    $("#main-wrapper").css("pointer-events", "none");
    $("#show-btn").css("-webkit-filter", "blur(10px)");
    $("#main-wrapper").css("-webkit-filter", "blur(10px)");
}
