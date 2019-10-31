<!DOCTYPE html>
<html>

<!-- HEAD -->
<?php include 'template/head.php'; ?>
<!-- END HEAD -->

<body class="hold-transition login-page">

<div class="login-box">
  <div class="login-logo">
    <a href="#"><b>Admin</b>Pintar</a>
  </div>
  <!-- /.login-logo -->
  <div class="card">
    <div class="card-body login-card-body">
      <p class="login-box-msg">Masuk untuk memulai sesi anda</p>
      <div class="input-group mb-3">
        <input name="email" type="text" class="form-control" placeholder="Username" id="Username_field">
        <div class="input-group-append">
          <div class="input-group-text">
            <span class="fas fa-envelope"></span>
          </div>
        </div>
      </div>
      <div class="input-group mb-3">
        <input name="password" type="password" class="form-control" placeholder="Password" id="Password_field">
        <div class="input-group-append">
          <div class="input-group-text">
            <span class="fas fa-lock"></span>
          </div>
        </div>
      </div>
      <div class="row">
        <!-- /.col -->
        <div class="col-12">
          <button onclick="login()" class="btn btn-info btn-block btn-flat">Sign In</button>
        </div>
        <!-- /.col -->
      </div>
    </div>
    <!-- /.login-card-body -->
  </div>
</div>
<!-- /.login-box -->

<!-- jQuery -->
<script src="../../plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="../../plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- Firebase initial-->
<script src="https://www.gstatic.com/firebasejs/7.2.2/firebase-app.js"></script>
<script src="https://www.gstatic.com/firebasejs/7.2.2/firebase-analytics.js"></script>
<script src="https://www.gstatic.com/firebasejs/7.2.2/firebase-auth.js"></script>
<script>
  // Your web app's Firebase configuration
  var firebaseConfig = {
    apiKey: "AIzaSyBemoHQRWu5S4pWsPwAq2sxk5dnIte1xAc",
    authDomain: "econtact-169b2.firebaseapp.com",
    databaseURL: "https://econtact-169b2.firebaseio.com",
    projectId: "econtact-169b2",
    storageBucket: "econtact-169b2.appspot.com",
    messagingSenderId: "57886648602",
    appId: "1:57886648602:web:f1c0cf87ef89a9b33fa4a3",
    measurementId: "G-3HDH6YZV8B"
  };
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);
  firebase.analytics();
</script>

<!-- Firebase Login -->
<script>
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
</script>

</body>
</html>
