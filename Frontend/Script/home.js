let user = JSON.parse(localStorage.getItem("user"));

let container = document.querySelector(".container > div");
if (user != undefined) {
    container.remove();
}
