let form = document.querySelector("form");

form.addEventListener("submit", signIn);

async function signIn(event) {
  event.preventDefault();

  let email = document.querySelector("#email").value;
  let role = document.querySelector("select").value.toLowerCase() + "s";

  let url = `http://localhost:2023/go_ride/${role}/forgotPassword/${email}`;

  try {
    let res = await fetch(url, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    });

    if (!res.ok) {
      window.alert("Check your email & role!");
    } else {
      window.alert("Password changed successfully!");
    }
  } catch (error) {
    console.log(error);
  }
}
