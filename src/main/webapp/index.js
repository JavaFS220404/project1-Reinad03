const url = "http://localhost:8080/ERSApp/core/"

let loginbtn = document.getElementById("loginButton");
let reimbbtn = document.getElementById("reimbButton");
//let submitrequest = document.getElementById("submit");
//let registerbutton = document.getElementById("registerButton");
//let newrequestbtn = document.getElementById("newReqButton")

loginbtn.addEventListener("click", login);
reimbbtn.addEventListener("click", getReimbursements);
//submitrequest.addEventListener("click", addNewRequest);
//registerbutton.addEventListener("click", register);
//newrequestbtn.addEventListener("click", newrequest)

function register() {
    location.href = "register.html";
};

function newrequest() {
    location.href = "employee-new-request.html";
};


async function login() {
    let uName = document.getElementById("username").value;
    let pWord = document.getElementById("password").value;

    let user = {
        username: uName,
        password: pWord
    };


    let response = await fetch(url + "login", {
        method: "POST",
        body: JSON.stringify(user),
        credentials: "include"
    });

    if (response.status === 200) {
        location.href = "employee-home-page.html";
    } else {
        console.log("could not log in");
        console.log(response);
    }


}

async function getReimbursements() {
    let response = await fetch(url + "reimbursements", {
        credentials: "include"
    });

    if (response.status === 200) {
        let list = await response.json();

        populateReimbursementTable(list);
    }
}

function populateReimbursementTable(list) {
    let tableBody = document.getElementById("table-body");
    tableBody.innerHTML = "";
    for (let reimb of list) {
        let row = document.createElement("tr");
        let id = document.createElement("td");
        let description = document.createElement("td");
        let type = document.createElement("td");
        let status = document.createElement("td");
        let amount = document.createElement("td");
        let submitted = document.createElement("td");
        let user = document.createElement("td");

        id.innerText = reimb.id;
        description.innerText = reimb.description;
        type.innerText = reimb.type;
        status.innerText = reimb.status;
        amount.innerText = reimb.amount;

        var timestamp = reimb.submitted;
        var date = new Date(timestamp);
        submitted.innerText = date.getDate() + "/" + (date.getMonth() + 1) + "/" + date.getFullYear() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        user.innerText = reimb.author.firstName + " " + reimb.author.lastName;

        row.appendChild(id);
        row.appendChild(description);
        row.appendChild(type);
        row.appendChild(status);
        row.appendChild(amount);
        row.appendChild(submitted);
        row.appendChild(user);
        tableBody.appendChild(row);
    }
}

async function addNewRequest() {

    let reimb = {
        id: 0,
        description: document.getElementById("description").value,
        type: document.getElementById("r-type").value,
        status: "PENDING",
        amount: document.getElementById("amount").value,
        submitted: null,
        author: null,
    }


    let response = await fetch(url + "reimbursements", {
        method: "POST",
        body: JSON.stringify(reimb),
        credentials: "include"
    });

    if (response.status === 201) {
        getReimbursements();
    } else {
        console.log("Could not add Reimbursement");
        console.log(response);
    }
}





