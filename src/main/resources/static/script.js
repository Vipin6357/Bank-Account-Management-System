// Create Account
function createAccount() {
    const name = document.getElementById("name").value;
    const balance = document.getElementById("balance").value;

    fetch(`/api/bank/create?name=${name}&initialBalance=${balance}`, {
        method: "POST"
    })
    .then(res => res.json())
    .then(data => {
        document.getElementById("createResult").innerText =
            `Account Created! Number: ${data.accountNumber}, Balance: ${data.balance}`;
    });
}

// Deposit
function deposit() {
    const accNo = document.getElementById("depAccNo").value;
    const amount = document.getElementById("depAmount").value;

    fetch(`/api/bank/deposit?accountNumber=${accNo}&amount=${amount}`, {
        method: "POST"
    })
    .then(res => res.text())
    .then(msg => {
        document.getElementById("depResult").innerText = msg;
    });
}

// Withdraw
function withdraw() {
    const accNo = document.getElementById("withAccNo").value;
    const amount = document.getElementById("withAmount").value;

    fetch(`/api/bank/withdraw?accountNumber=${accNo}&amount=${amount}`, {
        method: "POST"
    })
    .then(res => res.text())
    .then(msg => {
        document.getElementById("withResult").innerText = msg;
    });
}

// Check Balance
function checkBalance() {
    const accNo = document.getElementById("chkAccNo").value;

    fetch(`/api/bank/balance?accountNumber=${accNo}`)
    .then(res => res.text())
    .then(msg => {
        document.getElementById("balanceResult").innerText = msg;
    });
}

// View All Accounts
function getAllAccounts() {
    fetch(`/api/bank/all`)
    .then(res => res.json())
    .then(accounts => {
        const container = document.getElementById("allAccounts");
        container.innerHTML = "";
        accounts.forEach(acc => {
            const div = document.createElement("div");
            div.innerText = `AccNo: ${acc.accountNumber} | Name: ${acc.name} | Balance: ${acc.balance}`;
            container.appendChild(div);
        });
    });
}
