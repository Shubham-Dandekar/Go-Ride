let numberInputs = document.querySelectorAll("input[type=number]");

numberInputs.forEach(function (input) {
    input.addEventListener("mousewheel", function (e) {
        e.target.blur();
    });
});

let user = JSON.parse(localStorage.getItem("user"));

let uuid = user.uuid;

async function getVehicles() {
    let url = `http://localhost:2023/go_ride/vehicles/available/${uuid}`;

    try {
        let res = await fetch(url, {
            method: "GET",

            headers: {
                "Content-Type": "application/json",
            },
        });

        if (res.ok) {
            let vehiclesContainer = document.querySelector("#vehicles > div");
            let vehicles = await res.json();

            if (vehicles.length !== 0) {
                vehicles.forEach((vehicle) => {
                    let vehicleDiv = document.createElement("div");

                    let regDiv = document.createElement("div");
                    let regTag = document.createElement("h3");
                    regTag.innerText = "Registration No: ";
                    let reg = document.createElement("p");
                    reg.innerText = vehicle.registrationNo;

                    regDiv.append(regTag, reg);

                    let typeDiv = document.createElement("div");
                    let typeTag = document.createElement("h3");
                    typeTag.innerText = "Type: ";
                    let type = document.createElement("p");
                    type.innerText = vehicle.vehicleType;

                    typeDiv.append(typeTag, type);

                    let seatsDiv = document.createElement("div");
                    let seatsTag = document.createElement("h3");
                    seatsTag.innerText = "Seats: ";
                    let seats = document.createElement("p");
                    seats.innerText = vehicle.seats;

                    seatsDiv.append(seatsTag, seats);

                    let rateDiv = document.createElement("div");
                    let rateTag = document.createElement("h3");
                    rateTag.innerText = "Per Km Rate: ";
                    let rate = document.createElement("p");
                    rate.innerText = "₹ " + vehicle.perKmRate;

                    rateDiv.append(rateTag, rate);

                    vehicleDiv.append(regDiv, typeDiv, seatsDiv, rateDiv);
                    vehiclesContainer.append(vehicleDiv);
                });
            } else {
                let msg = document.createElement("h3");
                msg.innerText =
                    "Sorry, No vehicle available at this point of time!";
                msg.className = "error";

                vehiclesContainer.append(msg);
            }
        }
    } catch (error) {
        console.log(error);
    }
}

getVehicles();

let form = document.querySelector("form");
form.addEventListener("submit", bookRide);

async function bookRide(event) {
    event.preventDefault();

    let boardingLocation = document.querySelector("#boardingloc").value;
    let destinationLocation = document.querySelector("#destloc").value;
    let boardingDateTime = document.querySelector("#boardingtime").value;
    let noOfPassengers = document.querySelector("#passangers").value;
    let distanceInKm = document.querySelector("#distance").value;
    let vehicleRegNo = document.querySelector("#vehiclereg").value;

    let ride = {
        boardingLocation,
        destinationLocation,
        boardingDateTime,
        noOfPassengers,
        distanceInKm,
        vehicleRegNo,
    };

    console.log(ride);

    let url = `http://localhost:2023/go_ride/rides/${uuid}`;

    try {
        let res = await fetch(url, {
            method: "POST",
            body: JSON.stringify(ride),
            headers: {
                "Content-Type": "application/json",
            },
        });

        if (res.ok) {
            window.alert("Ride booked successfully!");
            window.location.href = "./rides.html";
        } else {
            let data = await res.json();
            window.alert(data.message);
        }
    } catch (error) {
        window.alert(error.message);
    }
}
