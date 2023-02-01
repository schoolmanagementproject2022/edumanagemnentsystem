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
    let classId = document.getElementById("classId").value
    let courseId = document.getElementById("courseId").value
    table.addEventListener("click", function (event) {
        const selectedRow = event.target.closest("td");
        const selectedRowId = selectedRow.getAttribute("id");
        var studentName = document.getElementById(selectedRowId).innerText
        document.getElementById("student-monday-tooltip").innerText = studentName + ':' + dateValue
        document.getElementById("students-monday").innerText = studentName + ':' + dateValue

        console.log("Selected row ID:", selectedRowId);
    });
    $.ajax({
        type: 'GET',
        url: "/agendas/check/" + classId + '/' + courseId + '/' + dateValue,
        dataType: 'json',
        contentType: "application/json",
        success: function (response) {
            if (response != null) {
                response = JSON.stringify(response);
                let parse = JSON.parse(response);
                if (document.getElementById("classwork-grade-monday").value === "") {
                    document.getElementById("classwork-grade-monday").value = parse.classwork

                }
                if (document.getElementById("homework-grade-monday").value === "") {
                    document.getElementById("homework-grade-monday").value = parse.homework
                }
                if (document.getElementById("test-grade-monday").value === "") {
                    document.getElementById("test-grade-monday").value = parse.test
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
    let classId = document.getElementById("classId").value
    let courseId = document.getElementById("courseId").value
    table.addEventListener("click", function (event) {
        const selectedRow = event.target.closest("td");
        const selectedRowId = selectedRow.getAttribute("id");
        var studentName = document.getElementById(selectedRowId).innerText
        document.getElementById("student-tuesday-tooltip").innerText = studentName + ':' + dateValue
        document.getElementById("students-tuesday").innerText = studentName + ':' + dateValue

        console.log("Selected row ID:", selectedRowId);
    });
    $.ajax({
        type: 'GET',
        url: "/agendas/check/" + classId + '/' + courseId + '/' + dateValue,
        dataType: 'json',
        contentType: "application/json",
        success: function (response) {
            if (response != null) {
                response = JSON.stringify(response);
                let parse = JSON.parse(response);
                if (document.getElementById("classwork-grade-tuesday").value === "") {
                    document.getElementById("classwork-grade-tuesday").value = parse.classwork

                }
                if (document.getElementById("homework-grade-tuesday").value === "") {
                    document.getElementById("homework-grade-tuesday").value = parse.homework
                }
                if (document.getElementById("test-grade-tuesday").value === "") {
                    document.getElementById("test-grade-tuesday").value = parse.test
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
    let classId = document.getElementById("classId").value
    let courseId = document.getElementById("courseId").value
    table.addEventListener("click", function (event) {
        const selectedRow = event.target.closest("td");
        const selectedRowId = selectedRow.getAttribute("id");
        var studentName = document.getElementById(selectedRowId).innerText
        document.getElementById("student-wednesday-tooltip").innerText = studentName + ':' + dateValue
        document.getElementById("students-wednesday").innerText = studentName + ':' + dateValue

        console.log("Selected row ID:", selectedRowId);
    });
    $.ajax({
        type: 'GET',
        url: "/agendas/check/" + classId + '/' + courseId + '/' + dateValue,
        dataType: 'json',
        contentType: "application/json",
        success: function (response) {
            if (response != null) {
                response = JSON.stringify(response);
                let parse = JSON.parse(response);
                if (document.getElementById("classwork-grade-wednesday").value === "") {
                    document.getElementById("classwork-grade-wednesday").value = parse.classwork

                }
                if (document.getElementById("homework-grade-wednesday").value === "") {
                    document.getElementById("homework-grade-wednesday").value = parse.homework
                }
                if (document.getElementById("test-grade-wednesday").value === "") {
                    document.getElementById("test-grade-wednesday").value = parse.test
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
    let courseName = document.getElementById("course-name").innerText
    let classId = document.getElementById("classId").value
    let courseId = document.getElementById("courseId").value
    table.addEventListener("click", function (event) {
        const selectedRow = event.target.closest("td");
        const selectedRowId = selectedRow.getAttribute("id");
        var studentName = document.getElementById(selectedRowId).innerText
        document.getElementById("student-thursday-tooltip").innerText = studentName + ':' + dateValue
        document.getElementById("students-thursday").innerText = studentName + ':' + dateValue
        document.getElementById("date-thursday-grade").value = dateValue
        table.addEventListener("click", function(event) {
                 const selectedRow = event.target.closest("td");
                 const selectedRowId = selectedRow.getAttribute("id");
                 selectedRow.getAttribute("id").value;
                  var  studentName = document.getElementById(selectedRowId).innerText
                document.getElementById("student-thursday-tooltip").innerText = studentName + ':' + dateValue
                document.getElementById("students-thursday").innerText = studentName + ':' + dateValue
                document.getElementById("students-thursday-grade").value = selectedRowId

        console.log("Selected row ID:", selectedRowId);
    });
    $.ajax({
        type: 'GET',
        url: "/agendas/check/" + classId + '/' + courseId + '/' + dateValue,
        dataType: 'json',
        contentType: "application/json",
        success: function (response) {
            if (response != null) {
                response = JSON.stringify(response);
                let parse = JSON.parse(response);
                if (document.getElementById("classwork-grade-thursday").value === "") {
                    document.getElementById("classwork-grade-thursday").value = parse.classwork

                }
                if (document.getElementById("homework-grade-thursday").value === "") {
                    document.getElementById("homework-grade-thursday").value = parse.homework
                }
                if (document.getElementById("test-grade-thursday").value === "") {
                    document.getElementById("test-grade-thursday").value = parse.test
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
    let classId = document.getElementById("classId").value
    let courseId = document.getElementById("courseId").value
    table.addEventListener("click", function (event) {
        const selectedRow = event.target.closest("td");
        const selectedRowId = selectedRow.getAttribute("id");
        var studentName = document.getElementById(selectedRowId).innerText
        document.getElementById("student-friday-tooltip").innerText = studentName + ':' + dateValue
        document.getElementById("students-friday").innerText = studentName + ':' + dateValue

        console.log("Selected row ID:", selectedRowId);
    });
    $.ajax({
        type: 'GET',
        url: "/agendas/check/" + classId + '/' + courseId + '/' + dateValue,
        dataType: 'json',
        contentType: "application/json",
        success: function (response) {
            if (response != null) {
                response = JSON.stringify(response);
                let parse = JSON.parse(response);
                if (document.getElementById("classwork-grade-friday").value === "") {
                    document.getElementById("classwork-grade-friday").value = parse.classwork

                }
                if (document.getElementById("homework-grade-friday").value === "") {
                    document.getElementById("homework-grade-friday").value = parse.homework
                }
                if (document.getElementById("test-grade-friday").value === "") {
                    document.getElementById("test-grade-friday").value = parse.test
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
    let classId = document.getElementById("classId").value
    let courseId = document.getElementById("courseId").value
    document.getElementById("date-saturday").value = dateValue
    table.addEventListener("click", function (event) {
        const selectedRow = event.target.closest("td");
        const selectedRowId = selectedRow.getAttribute("id");
        var studentName = document.getElementById(selectedRowId).innerText
        document.getElementById("student-saturday-tooltip").innerText = studentName + ':' + dateValue
        document.getElementById("students-saturday").innerText = studentName + ':' + dateValue

        console.log("Selected row ID:", selectedRowId);
    });

    $.ajax({
        type: 'GET',
        url: "/agendas/check/" + classId + '/' + courseId + '/' + dateValue,
        dataType: 'json',
        contentType: "application/json",
        success: function (response) {
            if (response != null) {
                response = JSON.stringify(response);
                let parse = JSON.parse(response);
                if (document.getElementById("classwork-grade-saturday").value === "") {
                    document.getElementById("classwork-grade-saturday").value = parse.classwork

                }
                if (document.getElementById("homework-grade-saturday").value === "") {
                    document.getElementById("homework-grade-saturday").value = parse.homework
                }
                if (document.getElementById("test-grade-saturday").value === "") {
                    document.getElementById("test-grade-saturday").value = parse.test
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
    const table = document.getElementById("table");
    table.addEventListener("click", function (event) {
        const selectedRow = event.target.closest("td");
        const selectedRowId = selectedRow.getAttribute("id");
        var studentName = document.getElementById(selectedRowId).innerText
        document.getElementById("student-sunday-tooltip").innerText = studentName + ':' + dateValue
        document.getElementById("students-sunday").innerText = studentName + ':' + dateValue
        console.log("Selected row ID:", selectedRowId);
    });

    let classId = document.getElementById("classId").value
    let courseId = document.getElementById("courseId").value
    document.getElementById("date-sunday").value = dateValue
    $.ajax({
        type: 'GET',
        url: "/agendas/check/" + classId + '/' + courseId + '/' + dateValue,
        dataType: 'json',
        contentType: "application/json",
        success: function (response) {
            if (response != null) {
                response = JSON.stringify(response);
                let parse = JSON.parse(response);
                if (document.getElementById("classwork-grade-sunday").value === "") {
                    document.getElementById("classwork-grade-sunday").value = parse.classwork

                }
                if (document.getElementById("homework-grade-sunday").value === "") {
                    document.getElementById("homework-grade-sunday").value = parse.homework
                }
                if (document.getElementById("test-grade-sunday").value === "") {
                    document.getElementById("test-grade-sunday").value = parse.test
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
