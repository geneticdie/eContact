<!DOCTYPE html>
<html>
<?php include '../template/head.php';  ?>
<!-- DataTables -->
<link rel="stylesheet" href="../plugins/datatables-bs4/css/dataTables.bootstrap4.css">
<body class="hold-transition sidebar-mini layout-fixed">
<div class="wrapper">

  <!-- Navbar -->
  <?php include '../template/navbar.php'; ?>
  <!-- /.navbar -->

  <!-- Main Sidebar Container -->
  <?php include '../template/menu.php'; ?>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0 text-dark">Contact List</h1>
          </div><!-- /.col -->
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">Contact List</li>
            </ol>
          </div><!-- /.col -->
        </div><!-- /.row -->
      </div><!-- /.container-fluid -->
    </div>
    <!-- /.content-header -->

    <!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-12">
          <div class="card">
            <!--<div class="card-header">
              <h3 class="card-title">Contact List</h3>
            </div>-->
            <!-- /.card-header -->
            <div class="card-body">
              <table id="ex-table" class="table table-bordered table-striped">
                <thead>
                  <tr>
                    <th>Address</th>
                    <th>Batch</th>
                    <th>Born Date</th>
                    <th>Born Place</th>
                    <th>Carrier Path</th>
                    <th>Character</th>
                    <th>Color Relation</th>
                  </tr>
                </thead>
              </table>
            </div>
            <!-- /.card-body -->
          </div>
          <!-- /.card -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
  <?php include '../template/footer.php'; ?>

  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Control sidebar content goes here -->
  </aside>
  <!-- /.control-sidebar -->
</div>
<!-- ./wrapper -->
 <?php include '../template/script.php'; ?>
 <!-- DataTables -->
<script src="../plugins/datatables/jquery.dataTables.js"></script>
<script src="../plugins/datatables-bs4/js/dataTables.bootstrap4.js"></script>
<!-- Firebase initial-->
<script src="https://www.gstatic.com/firebasejs/7.2.2/firebase-app.js"></script>
<script src="https://www.gstatic.com/firebasejs/7.2.2/firebase-analytics.js"></script>
<script src="https://www.gstatic.com/firebasejs/7.2.2/firebase-auth.js"></script>
<script src="https://www.gstatic.com/firebasejs/7.2.2/firebase-database.js"></script>
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

<script type="text/javascript">
  var staffRef = firebase.database().ref().child('Staffs');
  staffRef.on('value', function(snapshot) {
    console.log(snapshot.val());
    if(snapshot.exists()){
      $('#ex-table tbody').empty();
      var content = '';
      snapshot.forEach(function(childSnapshot) {
        var childVal = childSnapshot.val();
        content +='<tr>';
        content += '<td>' + childVal.address + '</td>';
        content += '<td>' + childVal.batch + '</td>';
        content += '<td>' + childVal.bornDate + '</td>';
        content += '<td>' + childVal.bornPlace + '</td>';
        content += '<td>' + childVal.carrierPath + '</td>';
        content += '<td>' + childVal.character + '</td>';
        content += '<td>' + childVal.colourRelation + '</td>';
        content += '</tr>';
      });
      $('#ex-table').append(content);
    }
  });
</script>
<script>
  $(function () {
    $("#ex-table").DataTable();
    $('#ex-t1able').DataTable({
      "paging": true,
      "lengthChange": false,
      "searching": false,
      "ordering": true,
      "info": true,
      "autoWidth": false,
    });
  });
</script>
</body>
</html>
