let obj = JSON.parse(localStorage.getItem("user"));

if (obj != undefined) {
    let uuid = obj.uuid;
    let role = obj.role.toLowerCase() + "s";

    if (obj.role == "ADMIN") {
        let vehicle = document.querySelector("#vehicle");
        vehicle.removeAttribute("hidden");
    }

    async function getUser() {
        let url = `http://localhost:2023/go_ride/${role}/${uuid}`;

        try {
            let res = await fetch(url, {
                method: "GET",

                headers: {
                    "Content-Type": "application/json",
                },
            });

            let user = await res.json();
            let navbarButton = document.querySelector("#profile > a");
            let nameArr = user.name.trim().split(" ");
            navbarButton.innerText = nameArr[0];
            navbarButton.removeAttribute("href");
        } catch (error) {
            console.log(error);
        }
    }

    getUser();

    let navbarButton = document.querySelector("#profile");
    let flag = false;
    let login = document.querySelector("#login");
    navbarButton.addEventListener("click", () => {
        if (flag) {
            login.setAttribute("style", "display: none");
            flag = false;
        } else {
            login.setAttribute("style", "display: block");
            flag = true;
        }
    });
}

function profile() {
    window.location = "./profile.html";
}

async function logout() {
    let uuid = obj.uuid;
    let url = `http://localhost:2023/go_ride/logout/${uuid}`;

    try {
        let res = await fetch(url, {
            method: "GET",

            headers: {
                "Content-Type": "application/json",
            },
        });
        localStorage.removeItem("user");
        window.location = "./index.html";
    } catch (error) {
        console.log(error);
    }
}

const menu = document.querySelector(".navbar > .menu ");

function openMenu() {
    menu.style.right = "0";
}

function closeMenu() {
    menu.style.right = "-100%";
}
