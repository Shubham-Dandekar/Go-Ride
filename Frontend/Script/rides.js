function addRide() {
    window.location.href = "./addride.html";
}

let uuid = obj.uuid;
let role = obj.role.toLowerCase() + "s";

async function getRides() {
    let url;

    if (role === "admins") {
        url = `http://localhost:2023/go_ride/rides/all/${uuid}`;
    } else {
        url = `http://localhost:2023/go_ride/rides/${role}/${uuid}`;
    }

    try {
        let res = await fetch(url, {
            method: "GET",

            headers: {
                "Content-Type": "application/json",
            },
        });

        let rides = await res.json();
        let rideContainer = document.querySelector("#rides-container");

        if (role !== "customers") {
            rideContainer.innerHTML = "";
        }

        rides.forEach((ride) => {
            let rideDiv = document.createElement("div");
            rideDiv.className = "ride";

            let rideIdDiv = document.createElement("div");
            let rideIdTag = document.createElement("h3");
            rideIdTag.innerText = "Ride Id: ";
            let rideId = document.createElement("p");
            rideId.innerText = ride.id;

            rideIdDiv.append(rideIdTag, rideId);

            let boardingLocationDiv = document.createElement("div");
            let boardingLocationTag = document.createElement("h3");
            boardingLocationTag.innerText = "Boarding Location: ";
            let boardingLocation = document.createElement("p");
            boardingLocation.innerText = ride.boardingLocation;

            boardingLocationDiv.append(boardingLocationTag, boardingLocation);

            let boardingTimeDiv = document.createElement("div");
            let boardingTimeTag = document.createElement("h3");
            boardingTimeTag.innerText = "Boarding Date Time: ";
            let boardingTime = document.createElement("p");
            boardingTime.innerText = ride.boardingDateTime;

            boardingTimeDiv.append(boardingTimeTag, boardingTime);

            let destinationLocationDiv = document.createElement("div");
            let destinationLocationTag = document.createElement("h3");
            destinationLocationTag.innerText = "Destination Location: ";
            let destinationLocation = document.createElement("p");
            destinationLocation.innerText = ride.destinationLocation;

            destinationLocationDiv.append(
                destinationLocationTag,
                destinationLocation
            );

            let noOfPassengersDiv = document.createElement("div");
            let noOfPassengersTag = document.createElement("h3");
            noOfPassengersTag.innerText = "No Of Passengers: ";
            let noOfPassengers = document.createElement("p");
            noOfPassengers.innerText = ride.noOfPassengers;

            noOfPassengersDiv.append(noOfPassengersTag, noOfPassengers);

            let distanceInKmDiv = document.createElement("div");
            let distanceInKmTag = document.createElement("h3");
            distanceInKmTag.innerText = "Distance: ";
            let distanceInKm = document.createElement("p");
            distanceInKm.innerText = ride.distanceInKm + " Km";

            distanceInKmDiv.append(distanceInKmTag, distanceInKm);

            let driverDiv = document.createElement("div");
            let driverTag = document.createElement("h3");
            driverTag.innerText = "Driver: ";
            let driver = document.createElement("p");
            driver.innerText = ride.driver;

            driverDiv.append(driverTag, driver);

            let vehicleRegNoDiv = document.createElement("div");
            let vehicleRegNoTag = document.createElement("h3");
            vehicleRegNoTag.innerText = "Vehicle Reg. No: ";
            let vehicleRegNo = document.createElement("p");
            vehicleRegNo.innerText = ride.vehicleRegNo;

            vehicleRegNoDiv.append(vehicleRegNoTag, vehicleRegNo);

            let billDiv = document.createElement("div");
            let billTag = document.createElement("h3");
            billTag.innerText = "Bill: ";
            let bill = document.createElement("p");
            bill.innerText = "₹ " + ride.bill;

            billDiv.append(billTag, bill);

            let statusDiv = document.createElement("div");
            let statusTag = document.createElement("h3");
            statusTag.innerText = "Status: ";
            let status = document.createElement("p");
            status.innerText = ride.status;

            statusDiv.append(statusTag, status);

            rideDiv.append(
                rideIdDiv,
                boardingLocationDiv,
                boardingTimeDiv,
                destinationLocationDiv,
                noOfPassengersDiv,
                distanceInKmDiv,
                driverDiv,
                vehicleRegNoDiv,
                billDiv,
                statusDiv
            );

            if (ride.status == "CONFIRMED") {
                let buttonDiv = document.createElement("div");
                buttonDiv.className = "ride-button";
                let cancel = document.createElement("button");
                cancel.innerText = "Cancel Ride";
                cancel.setAttribute("Id", "cancelride");

                let complete = document.createElement("button");
                complete.innerText = "Complete Ride";

                status.style.color = "#00b300";

                buttonDiv.append(cancel, complete);
                rideDiv.append(buttonDiv);

                cancel.addEventListener("click", () => {
                    candelRide(ride.id);
                });

                complete.addEventListener("click", () => {
                    completeRide(ride.id);
                });
            } else if (ride.status == "CANCELLED") {
                status.style.color = "#ff0000";
            } else {
                status.style.color = "#2e5cb8";
            }
            rideContainer.append(rideDiv);
        });
    } catch (error) {
        console.log(error);
    }
}

getRides();

async function candelRide(rideId) {
    let url = `http://localhost:2023/go_ride/rides/${uuid}/${rideId}`;
    try {
        let res = await fetch(url, {
            method: "PATCH",

            headers: {
                "Content-Type": "application/json",
            },
        });
        if (res.ok) {
            window.alert("Ride cancelled successfully!");
            window.location.reload();
        } else {
            let data = await res.json();
            let error = JSON.stringify(data);
            window.alert(JSON.parse(error).message);
        }
    } catch (error) {}
}

async function completeRide(rideId) {
    let url = `http://localhost:2023/go_ride/rides/complete/${uuid}/${rideId}`;
    try {
        let res = await fetch(url, {
            method: "PATCH",

            headers: {
                "Content-Type": "application/json",
            },
        });
        if (res.ok) {
            window.alert("Ride completed successfully!");
            window.location.reload();
        } else {
            let data = await res.json();
            let error = JSON.stringify(data);
            window.alert(JSON.parse(error).message);
        }
    } catch (error) {}
}
