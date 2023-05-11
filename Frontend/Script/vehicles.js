function addVehicle() {
    window.location.href = "./addvehicle.html";
}

let uuid = obj.uuid;

async function getVehicles() {
    let url = `http://localhost:2023/go_ride/vehicles/all/${uuid}`;

    try {
        let res = await fetch(url, {
            method: "GET",

            headers: {
                "Content-Type": "application/json",
            },
        });

        let vehicles = await res.json();
        let vehiclesContainer = document.querySelector("#vehicles-container");

        if (vehicles.length != 0) {
            vehicles.forEach((vehicle) => {
                let vehicleDiv = document.createElement("div");
                vehicleDiv.className = "vehicle";

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

                let availableDiv = document.createElement("div");
                let availableTag = document.createElement("h3");
                availableTag.innerText = "Availability: ";
                let available = document.createElement("p");

                if (available) {
                    available.innerText = "Available";
                    available.style.color = "#00b300";
                } else {
                    available.innerText = "Not Available";
                    available.style.color = "#ff0000";
                }

                availableDiv.append(availableTag, available);

                vehicleDiv.append(
                    regDiv,
                    typeDiv,
                    seatsDiv,
                    rateDiv,
                    availableDiv
                );
                vehiclesContainer.append(vehicleDiv);
            });
        }
    } catch (error) {
        console.log(error);
    }
}

getVehicles();
