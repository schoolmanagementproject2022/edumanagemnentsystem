// var host = 'http://edumanagement-env.eba-n3xmzppc.eu-north-1.elasticbeanstalk.com'
var host = 'http://localhost:8082'

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

function PopUpShowGradeMonday() {
    let dateValue = document.getElementById("mondayDate").innerText
    const selectedRow = event.target.closest("td");
    const selectedRowId = selectedRow.getAttribute("id");
    document.getElementById("date-monday-grade").value = dateValue
    table.addEventListener("click", function (event) {
        selectedRow.getAttribute("id").value;
        var studentName = document.getElementById(selectedRowId).innerText
        document.getElementById("student-monday-tooltip").innerText = studentName + ':' + dateValue
        document.getElementById("students-monday").innerText = studentName + ':' + dateValue
        document.getElementById("students-monday-grade").value = selectedRowId

        console.log("Selected row ID:", selectedRowId);
    });
    $.ajax({
        type: 'GET',
        url: "/agendas/check/grades/" + selectedRowId,
        dataType: 'json',
        contentType: "application/json",
        success: function (response) {
            if (response != null) {
                response = JSON.stringify(response);
                let parse = JSON.parse(response);
                if (document.getElementById("monday-classwork-grade").value === "") {
                    document.getElementById("monday-classwork-grade").value = parse.gradeClasswork
                }
                if (document.getElementById("monday-homework-grade").value === "") {
                    document.getElementById("monday-homework-grade").value = parse.gradeHomework
                }
                if (document.getElementById("monday-test-grade").value === "") {
                    document.getElementById("monday-test-grade").value = parse.gradeTest
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
    document.getElementById("date-tuesday-grade").value = dateValue
    const selectedRow = event.target.closest("td");
    const selectedRowId = selectedRow.getAttribute("id");
    table.addEventListener("click", function (event) {
        selectedRow.getAttribute("id").value;
        var studentName = document.getElementById(selectedRowId).innerText
        document.getElementById("student-tuesday-tooltip").innerText = studentName + ':' + dateValue
        document.getElementById("students-tuesday").innerText = studentName + ':' + dateValue
        document.getElementById("students-tuesday-grade").value = selectedRowId

        console.log("Selected row ID:", selectedRowId);
    });
    $.ajax({
        type: 'GET',
        url: "/agendas/check/grades/" + selectedRowId,
        dataType: 'json',
        contentType: "application/json",
        success: function (response) {
            if (response != null) {
                response = JSON.stringify(response);
                let parse = JSON.parse(response);
                if (document.getElementById("tuesday-classwork-grade").value === "") {
                    document.getElementById("tuesday-classwork-grade").value = parse.gradeClasswork
                }
                if (document.getElementById("tuesday-homework-grade").value === "") {
                    document.getElementById("tuesday-homework-grade").value = parse.gradeHomework
                }
                if (document.getElementById("tuesday-test-grade").value === "") {
                    document.getElementById("tuesday-test-grade").value = parse.gradeTest
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

function PopUpShowGradeWednesday() {
    let dateValue = document.getElementById("wednesdayDate").innerText
    const selectedRow = event.target.closest("td");
    const selectedRowId = selectedRow.getAttribute("id");
    document.getElementById("date-wednesday-grade").value = dateValue
    table.addEventListener("click", function (event) {
        selectedRow.getAttribute("id").value;
        var studentName = document.getElementById(selectedRowId).innerText
        document.getElementById("student-wednesday-tooltip").innerText = studentName + ':' + dateValue
        document.getElementById("students-wednesday").innerText = studentName + ':' + dateValue
        document.getElementById("students-wednesday-grade").value = selectedRowId

        console.log("Selected row ID:", selectedRowId);
    });
    $.ajax({
        type: 'GET',
        url: "/agendas/check/grades/" + selectedRowId,
        dataType: 'json',
        contentType: "application/json",
        success: function (response) {
            if (response != null) {
                response = JSON.stringify(response);
                let parse = JSON.parse(response);
                if (document.getElementById("wednesday-classwork-grade").value === "") {
                    document.getElementById("wednesday-classwork-grade").value = parse.gradeClasswork
                }
                if (document.getElementById("wednesday-homework-grade").value === "") {
                    document.getElementById("wednesday-homework-grade").value = parse.gradeHomework
                }
                if (document.getElementById("wednesday-test-grade").value === "") {
                    document.getElementById("wednesday-test-grade").value = parse.gradeTest
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

function PopUpShowGradeThursday() {
    let dateValue = document.getElementById("thursdayDate").innerText
    console.log(dateValue.valueOf())
    const selectedRow = event.target.closest("td");
    const selectedRowId = selectedRow.getAttribute("id");
    document.getElementById("date-thursday-grade").value = dateValue
        table.addEventListener("click", function(event) {
                 selectedRow.getAttribute("id").value;
                  var  studentName = document.getElementById(selectedRowId).innerText
                document.getElementById("student-thursday-tooltip").innerText = studentName + ':' + dateValue
                document.getElementById("students-thursday").innerText = studentName + ':' + dateValue
                document.getElementById("students-thursday-grade").value = selectedRowId

                 console.log("Selected row ID:", selectedRowId);
               });
    $.ajax({
        type: 'GET',
        url: "/agendas/check/grades/" + selectedRowId,
        dataType: 'json',
        contentType: "application/json",
        success: function (response) {
            if (response != null) {
                response = JSON.stringify(response);
                let parse = JSON.parse(response);
                if (document.getElementById("thursday-classwork-grade").value === "") {
                    document.getElementById("thursday-classwork-grade").value = parse.gradeClasswork
                }
                if (document.getElementById("thursday-homework-grade").value === "") {
                    document.getElementById("thursday-homework-grade").value = parse.gradeHomework
                }
                if (document.getElementById("thursday-test-grade").value === "") {
                    document.getElementById("thursday-test-grade").value = parse.gradeTest
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
    const selectedRow = event.target.closest("td");
    const selectedRowId = selectedRow.getAttribute("id");
    document.getElementById("date-friday-grade").value = dateValue
    table.addEventListener("click", function (event) {
        selectedRow.getAttribute("id").value;
        var studentName = document.getElementById(selectedRowId).innerText
        document.getElementById("student-friday-tooltip").innerText = studentName + ':' + dateValue
        document.getElementById("students-friday").innerText = studentName + ':' + dateValue
        document.getElementById("students-friday-grade").value = selectedRowId

        console.log("Selected row ID:", selectedRowId);
    });
    $.ajax({
        type: 'GET',
        url: "/agendas/check/grades/" + selectedRowId,
        dataType: 'json',
        contentType: "application/json",
        success: function (response) {
            if (response != null) {
                response = JSON.stringify(response);
                let parse = JSON.parse(response);
                if (document.getElementById("friday-classwork-grade").value === "") {
                    document.getElementById("friday-classwork-grade").value = parse.gradeClasswork
                }
                if (document.getElementById("friday-homework-grade").value === "") {
                    document.getElementById("friday-homework-grade").value = parse.gradeHomework
                }
                if (document.getElementById("friday-test-grade").value === "") {
                    document.getElementById("friday-test-grade").value = parse.gradeTest
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
    const selectedRow = event.target.closest("td");
    const selectedRowId = selectedRow.getAttribute("id");
    document.getElementById("date-saturday-grade").value = dateValue
    table.addEventListener("click", function (event) {
        selectedRow.getAttribute("id").value;
        var studentName = document.getElementById(selectedRowId).innerText
        document.getElementById("student-saturday-tooltip").innerText = studentName + ':' + dateValue
        document.getElementById("students-saturday").innerText = studentName + ':' + dateValue
        document.getElementById("students-saturday-grade").value = selectedRowId

        console.log("Selected row ID:", selectedRowId);
    });
    $.ajax({
        type: 'GET',
        url: "/agendas/check/grades/" + selectedRowId,
        dataType: 'json',
        contentType: "application/json",
        success: function (response) {
            if (response != null) {
                response = JSON.stringify(response);
                let parse = JSON.parse(response);
                if (document.getElementById("saturday-classwork-grade").value === "") {
                    document.getElementById("saturday-classwork-grade").value = parse.gradeClasswork
                }
                if (document.getElementById("saturday-homework-grade").value === "") {
                    document.getElementById("saturday-homework-grade").value = parse.gradeHomework
                }
                if (document.getElementById("saturday-test-grade").value === "") {
                    document.getElementById("saturday-test-grade").value = parse.gradeTest
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
    const selectedRow = event.target.closest("td");
    const selectedRowId = selectedRow.getAttribute("id");
    document.getElementById("date-sunday-grade").value = dateValue
    table.addEventListener("click", function (event) {
        selectedRow.getAttribute("id").value;
        var studentName = document.getElementById(selectedRowId).innerText
        document.getElementById("student-sunday-tooltip").innerText = studentName + ':' + dateValue
        document.getElementById("students-sunday").innerText = studentName + ':' + dateValue
        document.getElementById("students-sunday-grade").value = selectedRowId

        console.log("Selected row ID:", selectedRowId);
    });
    $.ajax({
        type: 'GET',
        url: "/agendas/check/grades/" + selectedRowId,
        dataType: 'json',
        contentType: "application/json",
        success: function (response) {
            if (response != null) {
                response = JSON.stringify(response);
                let parse = JSON.parse(response);
                if (document.getElementById("sunday-classwork-grade").value === "") {
                    document.getElementById("sunday-classwork-grade").value = parse.gradeClasswork
                }
                if (document.getElementById("sunday-homework-grade").value === "") {
                    document.getElementById("sunday-homework-grade").value = parse.gradeHomework
                }
                if (document.getElementById("sunday-test-grade").value === "") {
                    document.getElementById("sunday-test-grade").value = parse.gradeTest
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

