<!DOCTYPE html>
<html>

<!-- HEAD -->
<?php include '../template/head.php'; ?>
<!-- END HEAD -->

<body class="hold-transition login-page">

<div class="login-box">
  <div class="login-logo">
    <a href="#"><b>E</b>Contact</a>
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
<script src="../plugins/jquery/jquery.min.js"></script>
<!-- Bootstrap 4 -->
<script src="../plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<?php include '../template/script.php'; ?>

<script type="text/javascript">
  window.onload = function() {
    firebase.auth().onAuthStateChanged(function(user) {
      if (user) {
        // User is signed in.
        window.location.assign("../index.php");
        //window.alert(email);
      }
    });
  };
</script>

<!-- Firebase Login -->
<script>
  function login() {
    var email = document.getElementById("Username_field").value;
    var password = document.getElementById("Password_field").value;

    firebase.auth().signInWithEmailAndPassword(email, password)
      .then(function(result) {
        window.location.assign("../index.php");
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
