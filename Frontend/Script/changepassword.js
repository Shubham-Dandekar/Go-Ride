function showPassword() {
    let checkBox = document.querySelector("#show-password");
    let oldPass = document.querySelector("#old");
    let newPass = document.querySelector("#new");
    if (checkBox.checked == true) {
        oldPass.type = "text";
        newPass.type = "text";
    } else {
        oldPass.type = "password";
        newPass.type = "password";
    }
}

let form = document.querySelector("form");

form.addEventListener("submit", changePassword);

async function changePassword(event) {
    let uuid = obj.uuid;
    let role = obj.role.toLowerCase() + "s";

    event.preventDefault();

    let oldPassword = document.querySelector("#old").value;
    let newPassword = document.querySelector("#new").value;

    let password = {
        oldPassword,
        newPassword,
    };

    let url = `http://localhost:2023/go_ride/${role}/${uuid}/update/password`;

    try {
        let res = await fetch(url, {
            method: "PATCH",
            body: JSON.stringify(password),
            headers: {
                "Content-Type": "application/json",
            },
        });
        if (res.ok) {
            window.alert("Password updated successfully!");
            localStorage.removeItem("user");
            window.location.href = "./home.html";
        } else {
            window.alert("Check your credentials!");
        }
    } catch (error) {
        window.alert(error.message);
    }
}
