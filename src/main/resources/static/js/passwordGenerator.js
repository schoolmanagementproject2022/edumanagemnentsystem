function getPassword() {
    var length = 3,
        upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
        lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz",
        numbers = "0123456789",
        symbols = "@#$%&*()",
        password = "";

    for (var i = 0; i < length; ++i) {
        password += numbers.charAt(Math.floor(Math.random() * numbers.length)) + lowerCaseLetters.charAt(Math.floor(Math.random() * lowerCaseLetters.length)) +
            upperCaseLetters.charAt(Math.floor(Math.random() * upperCaseLetters.length)) + symbols.charAt(Math.floor(Math.random() * symbols.length));
    }
    document.getElementById("password").value = password;

}
