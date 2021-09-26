function validate() {
    var login = document.getElementById("login").value;
    var password = document.getElementById("password").value;

    if (login == null || login == "") {
        alert("Name can't be blank");
        event.preventDefault();
    } else if (password == null || password == "") {
        alert("Password must be at least 6 characters long.");
        event.preventDefault();
    } else {
        return true;
    }
}