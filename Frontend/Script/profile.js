let uuid = obj.uuid;
let role = obj.role.toLowerCase() + "s";
let userverified = "NOT_VERIFIED";

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
        appendUser(user);
    } catch (error) {
        console.log(error);
    }
}

getUser();

function appendUser(user) {
    let profile = document.querySelector("#profilediv");
    profile.innerHTML = "";

    let emailDiv = document.createElement("div");
    emailDiv.className = "emailDiv";
    let email = document.createElement("h3");
    email.innerText = "Email: ";
    let emailId = document.createElement("p");
    emailId.innerText = user.email;
    emailId.setAttribute("id", "email");
    let verified = document.createElement("i");
    let verifiedType = document.createElement("p");
    verifiedType.className = "verifiedType";
    if (user.emailVerified == "VERIFIED") {
        userverified = "VERIFIED";
        verified.className = "fa-duotone fa-badge-check";
        verified.style =
            "--fa-primary-color: #006400; --fa-secondary-color: #006400";
        verifiedType.innerText = "Verified";
        verifiedType.style.color = "#006400";
        emailDiv.append(email, emailId, verified, verifiedType);
    } else {
        verified.className = "fa-duotone fa-seal-exclamation";
        verified.style =
            "--fa-primary-color: #ff0000; --fa-secondary-color: #ff0000";
        verifiedType.innerText = "Not Verified";
        verifiedType.style.color = "#ff0000";
        let verify = document.createElement("p");
        verify.innerText = "verify";
        verify.style.color = "#006400";
        verify.className = "verify";
        verify.setAttribute("onclick", "verify()");
        emailDiv.append(email, emailId, verified, verifiedType, verify);
    }

    let nameDiv = document.createElement("div");
    nameDiv.className = "nameDiv";
    let nameTag = document.createElement("h3");
    nameTag.innerText = "Name: ";
    let userName = document.createElement("p");
    userName.innerText = user.name;

    nameDiv.append(nameTag, userName);

    let addressDiv = document.createElement("div");
    addressDiv.className = "addressDiv";
    let addressTag = document.createElement("h3");
    addressTag.innerText = "Address: ";
    let city = document.createElement("p");
    city.innerText = user.address.city + ", ";
    let state = document.createElement("p");
    state.innerText = user.address.state + ", ";
    let pincode = document.createElement("p");
    pincode.innerText = user.address.pinCode + ".";

    addressDiv.append(addressTag, city, state, pincode);

    let roleDiv = document.createElement("div");
    let roleTag = document.createElement("h3");
    roleTag.innerText = "Role: ";
    let role = document.createElement("p");
    role.innerText = user.role;

    roleDiv.append(roleTag, role);

    if (user.role == "DRIVER") {
        let licDiv = document.createElement("div");
        licDiv.classList = "licDiv";
        let licTag = document.createElement("h3");
        licTag.innerText = "License No: ";
        let lic = document.createElement("p");
        lic.innerText = user.licenceNo;

        licDiv.append(licTag, lic);

        let availDiv = document.createElement("div");
        let availableTag = document.createElement("h3");
        availableTag.innerText = "Availablity: ";
        let available = document.createElement("p");

        if (user.available) {
            available.innerText = "Available";
        } else {
            available.innerText = "Not Available";
        }

        availDiv.append(availableTag, available);
        profile.append(
            emailDiv,
            nameDiv,
            addressDiv,
            roleDiv,
            licDiv,
            availDiv
        );
    } else {
        profile.append(emailDiv, nameDiv, addressDiv, roleDiv);
    }
}

function verify() {
    let otpdiv = document.createElement("div");
    otpdiv.setAttribute("id", "otpdiv");
    let otpBox = document.createElement("input");
    otpBox.className = "otpbox";
    otpBox.placeholder = "Enter 6 digit otp";
    otpBox.type = "number";
    otpBox.required = true;
    otpBox.addEventListener("mousewheel", function (e) {
        e.target.blur();
    });

    let button = document.createElement("button");
    button.innerText = "Submit OTP";
    let profile = document.querySelector("#profilediv");

    otpdiv.append(otpBox, button);
    profile.append(otpdiv);
    button.addEventListener("click", submitOtp);

    sendVerificationEmail();
}

async function sendVerificationEmail() {
    let url = `http://localhost:2023/go_ride/${role}/verify/${uuid}`;

    try {
        let res = await fetch(url, {
            method: "GET",

            headers: {
                "Content-Type": "application/json",
            },
        });

        let user = await res.json();
        appendUser(user);
    } catch (error) {}
}

async function submitOtp() {
    let otp = document.querySelector(".otpbox").value;
    if (otp < 100000 || otp > 999999) {
        window.alert("6 digit otp required.");
        return;
    }

    let otpValue = otp.toString();

    let url = `http://localhost:2023/go_ride/${role}/verify/${uuid}/${otpValue}`;

    try {
        let res = await fetch(url, {
            method: "PATCH",

            headers: {
                "Content-Type": "application/json",
            },
        });

        if (!res.ok) {
            let data = await res.json();
            let error = JSON.stringify(data);
            window.alert(JSON.parse(error).message);
        } else {
            window.alert("Email verification successfull!");
            getUser();
        }
    } catch (error) {}
}
