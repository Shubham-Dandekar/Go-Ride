let user = JSON.parse(localStorage.getItem("user"));

if (user != undefined) {
  let container = document.querySelector(".container > div");
  container.remove();
}
