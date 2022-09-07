function getPassword() {
    var length = 3,
        number = "0123456789",
        lowerCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
        upperCase = "abcdefghijklmnopqrstuvwxyz",
        symbol = "@#$%&*()",
        retVal = "";

    for (var i = 0, n = lowerCase.length; i < length; ++i) {
        retVal += number.charAt(Math.floor(Math.random() * n)) + lowerCase.charAt(Math.floor(Math.random() * n ))
            + upperCase.charAt(Math.floor(Math.random() * n )) + symbol.charAt(Math.floor(Math.random() * n ));
    }
    document.getElementById("password").value = retVal;
}

function myFunction() {
    var x = document.getElementById("password");
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
}