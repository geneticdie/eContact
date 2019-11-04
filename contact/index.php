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
                <tbody id="ex-table-tbody">
                  <tr>
                    <td colspan="8" align="center"><i class="fas fa-lg fa-circle-notch fa-spin"></i></td>
                  </tr>
                </tbody>
              </table>
            </div>
            <!-- /.card-body -->
          </div>
          <!-- /.card -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->

      <!-- Modal -->
      <div class="modal fade" id="modal-xl">
        <div class="modal-dialog modal-xl">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title" id="modalTitle">Extra Large Modal</h4>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <p>One fine body&hellip;</p>
            </div>
            <div class="modal-footer justify-content-between">
              <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary">Save changes</button>
            </div>
          </div>
          <!-- /.modal-content -->
        </div>
        <!-- /.modal-dialog -->
      </div>
      <!-- /.modal -->

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
<!-- onLoad function -->
<script type="text/javascript">window.onload = function() { initApp(); };</script>

<!-- Datatables data -->
<script type="text/javascript">
  var staffRef = firebase.database().ref().child('Staffs');
  staffRef.on('value', function(snapshot) {
    if(snapshot.exists()){
      $('#ex-table tbody').empty();
      var content = '';
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
        content += '<td><button onclick="onclickInfo(&#39;' + childSnapshot.key + '&#39;)" type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#modal-xl"><i class="fas fa-info"></i></button></td>';
        content += '</tr>';
      });
      $('#ex-table-tbody').append(content);

      $("#ex-table").DataTable();
    }
    else {
      $('#ex-table tbody').empty();
      var content = '';
      content +='<tr>';
      content += '<td colspan="7" align="center">No data available</td>';
      content += '</tr>';
      $('#ex-table-tbody').append(content);

      $("#ex-table").DataTable();
    }
  });
</script>

<!-- Information Modal -->
<script>
  function onclickInfo(id) {
    var staffRefDetail = firebase.database().ref().child('Staffs/' + id);
    staffRefDetail.on('value', function(snapshot) {
      if(snapshot.exists()){
        var address= snapshot.child('address').val();
        var batch= snapshot.child('batch').val();
        var bornDate= snapshot.child('bornDate').val();
        var bornPlace= snapshot.child('bornPlace').val();
        var carrierPath= snapshot.child('carrierPath').val();
        var character= snapshot.child('character').val();
        var colourRelation= snapshot.child('colourRelation').val();
        var ctcNum1= snapshot.child('ctcNum1').val();
        var ctcNum2= snapshot.child('ctcNum2').val();
        var date_entry= snapshot.child('date_entry').val();
        var firstName = snapshot.child('firstName').val();
        var lastName = snapshot.child('lastName').val();
        var nickname= snapshot.child('nickname').val();
        var nrp= snapshot.child('nrp').val();
        var organization= snapshot.child('organization').val();
        var profPicUrl= snapshot.child('profPicUrl').val();
        var title_organization= snapshot.child('title_organization').val();
        var waNum= snapshot.child('waNum').val();
        var name = firstName + " " + lastName;
        document.getElementById("modalTitle").innerHTML = name;
      }
      else {

      }
    });
  }
</script>

</body>
</html>
