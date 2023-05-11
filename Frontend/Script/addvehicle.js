let numberInputs = document.querySelectorAll("input[type=number]");

numberInputs.forEach(function (input) {
    input.addEventListener("mousewheel", function (e) {
        e.target.blur();
    });
});

let user = JSON.parse(localStorage.getItem("user"));

let uuid = user.uuid;

let form = document.querySelector("form");
form.addEventListener("submit", addVehicle);

async function addVehicle(event) {
    event.preventDefault();

    let registrationNo = document.querySelector("#registrationNo").value;
    let vehicleType = document.querySelector("#vehicleType").value;
    let seats = document.querySelector("#seats").value;
    let perKmRate = document.querySelector("#perKmRate").value;

    let vehicle = {
        registrationNo,
        vehicleType,
        seats,
        perKmRate,
    };

    let url = `http://localhost:2023/go_ride/vehicles/${uuid}`;

    try {
        let res = await fetch(url, {
            method: "POST",
            body: JSON.stringify(vehicle),
            headers: {
                "Content-Type": "application/json",
            },
        });

        if (res.ok) {
            window.alert("Vehicle added successfully!");
            window.location.href = "./vehicle.html";
        } else {
            let data = await res.json();
            window.alert(data.message);
        }
    } catch (error) {
        window.alert(error.message);
    }
}
