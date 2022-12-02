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

function chooseTeachersOfConcreteCourse() {
    let elem = document.getElementById("course")
    let elem1 = document.getElementById("teacher")
    elem.addEventListener("click", (item) => {
        fetch(`http://localhost:8082/teacherByCourseId?teachers=${item.target.value}`).then(data => {
            return data.json()
        })
            .then(elem => {
                elem1.innerHTML = "";
                let a = document.createElement("option")
                a.textContent = "Select teacher"
                a.setAttribute("disabled", true)
                a.setAttribute("selected", true)
                a.setAttribute("hidden", true)
                elem1.appendChild(a)
                if (elem.length === 0) {
                    let a = document.createElement("option")
                    a.textContent = "Select teacher"
                    a.setAttribute("disabled", true)
                    elem1.appendChild(a)
                }
                elem.forEach(item => {
                    let newElem = document.createElement("option")
                    newElem.textContent = item.name + " " + item.surname
                    newElem.setAttribute("value", item.id);
                    elem1.appendChild(newElem)
                })
            })
    })
}

