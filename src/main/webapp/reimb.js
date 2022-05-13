const url = "http://localhost:8080/ERSApp/core/"

let reimbbtn = document.getElementById("reimbButton");
reimbbtn.addEventListener("click", getReimbursements);

let newrequestbtn = document.getElementById("newReqButton");
newrequestbtn.addEventListener("click", newrequest);
let logOut = document.getElementById("logout");
newrequestbtn.addEventListener("click", logOut);

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

function newrequest() {
    location.href = "employee-new-request.html";
};

function logout() {
    location.href = "index.html";
};




