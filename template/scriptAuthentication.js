function login() {
  var email = document.getElementById("Username_field").value;
  var password = document.getElementById("Password_field").value;

  firebase.auth().signInWithEmailAndPassword(email, password)
    .then(function(result) {
      window.location.assign("index.php");
    }).catch(function(error) {
      // Handle Errors here.
      var errorCode = error.code;
      var errorMessage = error.message;
      if (errorCode === 'auth/invalid-email'){
        window.alert("Invalid Email");
      }
      else if (errorCode === 'auth/wrong-password'){
        window.alert("Wrong Password");
      }
      // ...
  });
}

function logout() {
  firebase.auth().signOut().then(function() {
    // Sign-out successful.
    window.alert("Sign Out Success")
  }).catch(function(error) {
    // An error happened.
    var errorCode = error.code;
    var errorMessage = error.message;

    window.alert("Error : " + errorMessage);
  });
}

function initApp() {
  firebase.auth().onAuthStateChanged(function(user) {
    if (user) {
      // User is signed in.
      email = user.email;
      //window.alert(email);
    } else {
      // No user is signed in.
      window.alert("Oops, You must sign in first");
      window.location.assign("../login");
    }
  });
}
