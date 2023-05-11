let numberInputs = document.querySelectorAll("input[type=number]");

numberInputs.forEach(function (input) {
    input.addEventListener("mousewheel", function (e) {
        e.target.blur();
    });
});

console.log(numberInputs);

let form = document.querySelector("form");
let select = document.querySelector("select");
select.addEventListener("change", setLic);

function setLic() {
    let role = document.querySelector("select").value;

    if (role == "DRIVER") {
        let lbl = document.querySelector("#license-label");
        lbl.removeAttribute("hidden");
        let lic = document.querySelector("#license");
        lic.removeAttribute("hidden");
        lic.setAttribute("required", true);
    } else {
        let lbl = document.querySelector("#license-label");
        lbl.setAttribute("hidden", true);
        let lic = document.querySelector("#license");
        lic.setAttribute("hidden", true);
        lic.removeAttribute("required");
    }
}

form.addEventListener("submit", signUp);

async function signUp(event) {
    event.preventDefault();

    let email = document.querySelector("#email").value;
    let name = document.querySelector("#name").value;
    let city = document.querySelector("#city").value;
    let state = document.querySelector("#state").value;
    let pinCode = document.querySelector("#pincode").value;
    let password = document.querySelector("#password").value;
    let role = document.querySelector("select").value;

    let signUp;

    if (role == "DRIVER") {
        let licenceNo = document.querySelector("#license").value;

        signUp = {
            email,
            name,
            address: {
                city,
                state,
                pinCode,
            },
            password,
            role,
            licenceNo,
        };
    } else {
        signUp = {
            email,
            name,
            address: {
                city,
                state,
                pinCode,
            },
            password,
            role,
        };
    }

    role = role.toLowerCase() + "s";

    let url = `http://localhost:2023/go_ride/${role}/`;

    try {
        let res = await fetch(url, {
            method: "POST",
            body: JSON.stringify(signUp),
            headers: {
                "Content-Type": "application/json",
            },
        });

        let user = await res.json();
        localStorage.setItem("user", JSON.stringify(user));
        window.location.href = "./home.html";
    } catch (error) {}
}

function showPassword() {
    let checkBox = document.querySelector("#show-password");
    let password = document.querySelector("#password");
    if (checkBox.checked == true) {
        password.type = "text";
    } else {
        password.type = "password";
    }
}
