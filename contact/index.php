<!DOCTYPE html>
<html>
<?php include '../template/head.php';  ?>
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
                    <th>Name</th>
                    <th>Title Organization</th>
                    <th>Organization</th>
                    <th>Batch</th>
                    <th>Carrier Path</th>
                    <th>Character</th>
                    <th>Color Relation</th>
                    <th></th>
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

<!-- Datatables data -->
<script type="text/javascript">
  var staffRef = firebase.database().ref().child('Staffs');
  staffRef.on('value', function(snapshot) {
    console.log(snapshot.val());
    if(snapshot.exists()){
      $('#ex-table tbody').empty();
      var content = '';
      content +='<tbody>';
      snapshot.forEach(function(childSnapshot) {
        var childVal = childSnapshot.val();
        content +='<tr>';
        content += '<td>' + childVal.firstName + " " + childVal.lastName + '</td>';
        content += '<td>' + childVal.title_organization + '</td>';
        content += '<td>' + childVal.organization + '</td>';
        content += '<td>' + childVal.batch + '</td>';
        content += '<td>' + childVal.carrierPath + '</td>';
        content += '<td>' + childVal.character + '</td>';
        content += '<td>' + childVal.colourRelation + '</td>';
        content += '<td></td>';
        content += '</tr>';
      });
      content +='</tbody>';
      $('#ex-table').append(content);

      $("#ex-table").DataTable();
    }
  });
</script>
<script type="text/javascript">window.onload = function() { initApp(); };</script>
</body>
</html>
