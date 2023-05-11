let numberInputs = document.querySelectorAll("input[type=number]");

numberInputs.forEach(function (input) {
    input.addEventListener("mousewheel", function (e) {
        e.target.blur();
    });
});

let form = document.querySelector("form");

form.addEventListener("submit", signIn);

async function signIn(event) {
    event.preventDefault();

    let email = document.querySelector("#email").value;
    let password = document.querySelector("#password").value;
    let role = document.querySelector("select").value;

    let logIn = {
        email,
        password,
        role,
    };

    let url = `http://localhost:2023/go_ride/logIn`;

    try {
        let res = await fetch(url, {
            method: "POST",
            body: JSON.stringify(logIn),
            headers: {
                "Content-Type": "application/json",
            },
        });
        if (res.ok) {
            let user = await res.json();
            localStorage.setItem("user", JSON.stringify(user));
            window.location.href = "./home.html";
        } else {
            let data = await res.json();
            window.alert(data.message);
        }
    } catch (error) {
        console.log(error);
    }
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
