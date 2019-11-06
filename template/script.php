<!-- jQuery -->
<script src="../plugins/jquery/jquery.min.js"></script>
<!-- jQuery UI 1.11.4 -->
<script src="../plugins/jquery-ui/jquery-ui.min.js"></script>
<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
<script>
  $.widget.bridge('uibutton', $.ui.button)
</script>
<!-- Bootstrap 4 -->
<script src="../plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- ChartJS -->
<script src="../plugins/chart.js/Chart.min.js"></script>
<!-- Sparkline -->
<script src="../plugins/sparklines/sparkline.js"></script>
<!-- JQVMap -->
<script src="../plugins/jqvmap/jquery.vmap.min.js"></script>
<script src="../plugins/jqvmap/maps/jquery.vmap.usa.js"></script>
<!-- jQuery Knob Chart -->
<script src="../plugins/jquery-knob/jquery.knob.min.js"></script>
<!-- daterangepicker -->
<script src="../plugins/moment/moment.min.js"></script>
<script src="../plugins/daterangepicker/daterangepicker.js"></script>
<!-- Tempusdominus Bootstrap 4 -->
<script src="../plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
<!-- Summernote -->
<script src="../plugins/summernote/summernote-bs4.min.js"></script>
<!-- overlayScrollbars -->
<script src="../plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
<!-- AdminLTE App -->
<script src="../dist/js/adminlte.js"></script>
<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
<script src="../dist/js/pages/dashboard.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="../dist/js/demo.js"></script>
<!-- Date Picker -->
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<!-- SweetAlert2 -->
<script src="../plugins/sweetalert2/sweetalert2.min.js"></script>

<script scr="scriptAuthentication.js"></script>

<!-- Firebase-->
<script src="https://www.gstatic.com/firebasejs/7.2.2/firebase-app.js"></script>
<script src="https://www.gstatic.com/firebasejs/7.2.2/firebase-analytics.js"></script>
<script src="https://www.gstatic.com/firebasejs/7.2.2/firebase-auth.js"></script>
<script src="https://www.gstatic.com/firebasejs/7.2.2/firebase-database.js"></script>
<!-- Firebase Config -->
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
<!-- Firebase Authentication -->
<script type="text/javascript">
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
      window.location.assign("../login/index.php");
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
        document.getElementById("preloader").style.display="none";
        document.getElementById("mainContent").style.display="block";
      } else {
        // No user is signed in.
        window.location.assign("../login");
      }
    });
  }
</script>
