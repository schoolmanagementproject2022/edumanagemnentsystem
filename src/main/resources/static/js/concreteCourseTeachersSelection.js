// var host = 'http://edumanagement-env.eba-n3xmzppc.eu-north-1.elasticbeanstalk.com'
var host ='http://localhost:8082'


function chooseTeachersOfConcreteCourse() {
    let elem = document.getElementById("course")
    let elem1 = document.getElementById("teacher")
    elem.addEventListener("change", (item) => {
        fetch(host+`/teacherByCourseId?teachers=${item.target.value}`).then(data => {
            return data.json()
        })
            .then(elem => {
                elem1.innerHTML = "";
                let a = document.createElement("option")
                a.textContent = "Select teacher"
                a.setAttribute("disabled", true)
                a.setAttribute("selected", true)
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

function chooseTeachersOfConcreteCourseAfterError() {
    let elem1 = document.getElementById("teacher")
    var popupContainer = document.getElementById("popup-container");
    popupContainer.addEventListener("mouseenter", (ev) => {
        var val = $("#course option:selected").val();
        fetch(host+`/teacherByCourseId?teachers=${val}`).then(data => {
            return data.json()
        })
            .then(elem => {
                elem1.innerHTML = "";
                let a = document.createElement("option")
                a.textContent = "Select teacher"
                a.setAttribute("disabled", true)
                a.setAttribute("selected", true)
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
