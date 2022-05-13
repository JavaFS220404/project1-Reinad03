const url = "http://localhost:8080/ERSApp/core/"


let submitrequest = document.getElementById("submit");

submitrequest.addEventListener("click", addNewRequest);


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





