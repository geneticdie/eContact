<!DOCTYPE html>
<html>
<?php include '../template/head.php';  ?>
<body class="hold-transition sidebar-mini layout-fixed">
<?php include '../template/stylePreloader.php';  ?>
<div class="wrapper" id="mainContent" style="display:none">

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

      <!-- Modal Contact Information -->
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
              <div class="row">
                <!-- left column -->
                <div class="col-md-6">
                  <!-- general form elements -->
                  <div class="card card-primary">
                    <div class="card-header">
                      <h3 class="card-title">Profile</h3>
                    </div>
                    <!-- /.card-header -->
                    <!-- form start -->
                    <form role="form">
                      <div class="card-body">
                        <div class="form-group">
                          <label for="exampleInputEmail1">Email address</label>
                          <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Enter email">
                        </div>
                        <div class="form-group">
                          <label for="exampleInputPassword1">Password</label>
                          <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
                        </div>
                        <div class="form-group">
                          <label for="exampleInputFile">File input</label>
                          <div class="input-group">
                            <div class="custom-file">
                              <input type="file" class="custom-file-input" id="exampleInputFile">
                              <label class="custom-file-label" for="exampleInputFile">Choose file</label>
                            </div>
                            <div class="input-group-append">
                              <span class="input-group-text" id="">Upload</span>
                            </div>
                          </div>
                        </div>
                        <div class="form-check">
                          <input type="checkbox" class="form-check-input" id="exampleCheck1">
                          <label class="form-check-label" for="exampleCheck1">Check me out</label>
                        </div>
                      </div>
                      <!-- /.card-body -->

                      <div class="card-footer">
                        <button type="submit" class="btn btn-primary">Submit</button>
                      </div>
                    </form>
                  </div>
                  <!-- /.card -->

                  <!-- Form Element sizes -->
                  <div class="card card-success">
                    <div class="card-header">
                      <h3 class="card-title">Different Height</h3>
                    </div>
                    <div class="card-body">
                      <input class="form-control form-control-lg" type="text" placeholder=".form-control-lg">
                      <br>
                      <input class="form-control" type="text" placeholder="Default input">
                      <br>
                      <input class="form-control form-control-sm" type="text" placeholder=".form-control-sm">
                    </div>
                    <!-- /.card-body -->
                  </div>
                  <!-- /.card -->

                  <div class="card card-danger">
                    <div class="card-header">
                      <h3 class="card-title">Different Width</h3>
                    </div>
                    <div class="card-body">
                      <div class="row">
                        <div class="col-3">
                          <input type="text" class="form-control" placeholder=".col-3">
                        </div>
                        <div class="col-4">
                          <input type="text" class="form-control" placeholder=".col-4">
                        </div>
                        <div class="col-5">
                          <input type="text" class="form-control" placeholder=".col-5">
                        </div>
                      </div>
                    </div>
                    <!-- /.card-body -->
                  </div>
                  <!-- /.card -->

                  <!-- Input addon -->
                  <div class="card card-info">
                    <div class="card-header">
                      <h3 class="card-title">Input Addon</h3>
                    </div>
                    <div class="card-body">
                      <div class="input-group mb-3">
                        <div class="input-group-prepend">
                          <span class="input-group-text">@</span>
                        </div>
                        <input type="text" class="form-control" placeholder="Username">
                      </div>

                      <div class="input-group mb-3">
                        <input type="text" class="form-control">
                        <div class="input-group-append">
                          <span class="input-group-text">.00</span>
                        </div>
                      </div>

                      <div class="input-group">
                        <div class="input-group-prepend">
                          <span class="input-group-text">$</span>
                        </div>
                        <input type="text" class="form-control">
                        <div class="input-group-append">
                          <span class="input-group-text">.00</span>
                        </div>
                      </div>

                      <h4>With icons</h4>

                      <div class="input-group mb-3">
                        <div class="input-group-prepend">
                          <span class="input-group-text"><i class="fas fa-envelope"></i></span>
                        </div>
                        <input type="email" class="form-control" placeholder="Email">
                      </div>

                      <div class="input-group mb-3">
                        <input type="text" class="form-control">
                        <div class="input-group-append">
                          <span class="input-group-text"><i class="fas fa-check"></i></span>
                        </div>
                      </div>

                      <div class="input-group">
                        <div class="input-group-prepend">
                          <span class="input-group-text">
                            <i class="fas fa-dollar-sign"></i>
                          </span>
                        </div>
                        <input type="text" class="form-control">
                        <div class="input-group-append">
                          <div class="input-group-text"><i class="fas fa-ambulance"></i></div>
                        </div>
                      </div>

                      <h5 class="mt-4 mb-2">With checkbox and radio inputs</h5>

                      <div class="row">
                        <div class="col-lg-6">
                          <div class="input-group">
                            <div class="input-group-prepend">
                              <span class="input-group-text">
                                <input type="checkbox">
                              </span>
                            </div>
                            <input type="text" class="form-control">
                          </div>
                          <!-- /input-group -->
                        </div>
                        <!-- /.col-lg-6 -->
                        <div class="col-lg-6">
                          <div class="input-group">
                            <div class="input-group-prepend">
                              <span class="input-group-text"><input type="radio"></span>
                            </div>
                            <input type="text" class="form-control">
                          </div>
                          <!-- /input-group -->
                        </div>
                        <!-- /.col-lg-6 -->
                      </div>
                      <!-- /.row -->

                      <h5 class="mt-4 mb-2">With buttons</h5>

                      <p>Large: <code>.input-group.input-group-lg</code></p>

                      <div class="input-group input-group-lg mb-3">
                        <div class="input-group-prepend">
                          <button type="button" class="btn btn-warning dropdown-toggle" data-toggle="dropdown">
                            Action
                          </button>
                          <ul class="dropdown-menu">
                            <li class="dropdown-item"><a href="#">Action</a></li>
                            <li class="dropdown-item"><a href="#">Another action</a></li>
                            <li class="dropdown-item"><a href="#">Something else here</a></li>
                            <li class="dropdown-divider"></li>
                            <li class="dropdown-item"><a href="#">Separated link</a></li>
                          </ul>
                        </div>
                        <!-- /btn-group -->
                        <input type="text" class="form-control">
                      </div>
                      <!-- /input-group -->

                      <p>Normal</p>
                      <div class="input-group mb-3">
                        <div class="input-group-prepend">
                          <button type="button" class="btn btn-danger">Action</button>
                        </div>
                        <!-- /btn-group -->
                        <input type="text" class="form-control">
                      </div>
                      <!-- /input-group -->

                      <p>Small <code>.input-group.input-group-sm</code></p>
                      <div class="input-group input-group-sm">
                        <input type="text" class="form-control">
                        <span class="input-group-append">
                          <button type="button" class="btn btn-info btn-flat">Go!</button>
                        </span>
                      </div>
                      <!-- /input-group -->
                    </div>
                    <!-- /.card-body -->
                  </div>
                  <!-- /.card -->
                  <!-- Horizontal Form -->
                  <div class="card card-info">
                    <div class="card-header">
                      <h3 class="card-title">Horizontal Form</h3>
                    </div>
                    <!-- /.card-header -->
                    <!-- form start -->
                    <form class="form-horizontal">
                      <div class="card-body">
                        <div class="form-group row">
                          <label for="inputEmail3" class="col-sm-2 col-form-label">Email</label>
                          <div class="col-sm-10">
                            <input type="email" class="form-control" id="inputEmail3" placeholder="Email">
                          </div>
                        </div>
                        <div class="form-group row">
                          <label for="inputPassword3" class="col-sm-2 col-form-label">Password</label>
                          <div class="col-sm-10">
                            <input type="password" class="form-control" id="inputPassword3" placeholder="Password">
                          </div>
                        </div>
                        <div class="form-group row">
                          <div class="offset-sm-2 col-sm-10">
                            <div class="form-check">
                              <input type="checkbox" class="form-check-input" id="exampleCheck2">
                              <label class="form-check-label" for="exampleCheck2">Remember me</label>
                            </div>
                          </div>
                        </div>
                      </div>
                      <!-- /.card-body -->
                      <div class="card-footer">
                        <button type="submit" class="btn btn-info">Sign in</button>
                        <button type="submit" class="btn btn-default float-right">Cancel</button>
                      </div>
                      <!-- /.card-footer -->
                    </form>
                  </div>
                  <!-- /.card -->

                </div>
                <!--/.col (left) -->
                <!-- right column -->
                <div class="col-md-6">
                  <!-- general form elements disabled -->
                  <div class="card card-warning">
                    <div class="card-header">
                      <h3 class="card-title">General Elements</h3>
                    </div>
                    <!-- /.card-header -->
                    <div class="card-body">
                      <form role="form">
                        <div class="row">
                          <div class="col-sm-6">
                            <!-- text input -->
                            <div class="form-group">
                              <label>Text</label>
                              <input type="text" class="form-control" placeholder="Enter ...">
                            </div>
                          </div>
                          <div class="col-sm-6">
                            <div class="form-group">
                              <label>Text Disabled</label>
                              <input type="text" class="form-control" placeholder="Enter ..." disabled>
                            </div>
                          </div>
                        </div>
                        <div class="row">
                          <div class="col-sm-6">
                            <!-- textarea -->
                            <div class="form-group">
                              <label>Textarea</label>
                              <textarea class="form-control" rows="3" placeholder="Enter ..."></textarea>
                            </div>
                          </div>
                          <div class="col-sm-6">
                            <div class="form-group">
                              <label>Textarea Disabled</label>
                              <textarea class="form-control" rows="3" placeholder="Enter ..." disabled></textarea>
                            </div>
                          </div>
                        </div>

                        <!-- input states -->
                        <div class="form-group">
                          <label class="col-form-label" for="inputSuccess"><i class="fas fa-check"></i> Input with
                            success</label>
                          <input type="text" class="form-control is-valid" id="inputSuccess" placeholder="Enter ...">
                        </div>
                        <div class="form-group">
                          <label class="col-form-label" for="inputWarning"><i class="far fa-bell"></i> Input with
                            warning</label>
                          <input type="text" class="form-control is-warning" id="inputWarning" placeholder="Enter ...">
                        </div>
                        <div class="form-group">
                          <label class="col-form-label" for="inputError"><i class="far fa-times-circle"></i> Input with
                            error</label>
                          <input type="text" class="form-control is-invalid" id="inputError" placeholder="Enter ...">
                        </div>

                        <div class="row">
                          <div class="col-sm-6">
                            <!-- checkbox -->
                            <div class="form-group">
                              <div class="form-check">
                                <input class="form-check-input" type="checkbox">
                                <label class="form-check-label">Checkbox</label>
                              </div>
                              <div class="form-check">
                                <input class="form-check-input" type="checkbox" checked>
                                <label class="form-check-label">Checkbox checked</label>
                              </div>
                              <div class="form-check">
                                <input class="form-check-input" type="checkbox" disabled>
                                <label class="form-check-label">Checkbox disabled</label>
                              </div>
                            </div>
                          </div>
                          <div class="col-sm-6">
                            <!-- radio -->
                            <div class="form-group">
                              <div class="form-check">
                                <input class="form-check-input" type="radio" name="radio1">
                                <label class="form-check-label">Radio</label>
                              </div>
                              <div class="form-check">
                                <input class="form-check-input" type="radio" name="radio1" checked>
                                <label class="form-check-label">Radio checked</label>
                              </div>
                              <div class="form-check">
                                <input class="form-check-input" type="radio" disabled>
                                <label class="form-check-label">Radio disabled</label>
                              </div>
                            </div>
                          </div>
                        </div>

                        <div class="row">
                          <div class="col-sm-6">
                            <!-- select -->
                            <div class="form-group">
                              <label>Select</label>
                              <select class="form-control">
                                <option>option 1</option>
                                <option>option 2</option>
                                <option>option 3</option>
                                <option>option 4</option>
                                <option>option 5</option>
                              </select>
                            </div>
                          </div>
                          <div class="col-sm-6">
                            <div class="form-group">
                              <label>Select Disabled</label>
                              <select class="form-control" disabled>
                                <option>option 1</option>
                                <option>option 2</option>
                                <option>option 3</option>
                                <option>option 4</option>
                                <option>option 5</option>
                              </select>
                            </div>
                          </div>
                        </div>

                        <div class="row">
                          <div class="col-sm-6">
                            <!-- Select multiple-->
                            <div class="form-group">
                              <label>Select Multiple</label>
                              <select multiple class="form-control">
                                <option>option 1</option>
                                <option>option 2</option>
                                <option>option 3</option>
                                <option>option 4</option>
                                <option>option 5</option>
                              </select>
                            </div>
                          </div>
                          <div class="col-sm-6">
                            <div class="form-group">
                              <label>Select Multiple Disabled</label>
                              <select multiple class="form-control" disabled>
                                <option>option 1</option>
                                <option>option 2</option>
                                <option>option 3</option>
                                <option>option 4</option>
                                <option>option 5</option>
                              </select>
                            </div>
                          </div>
                        </div>
                      </form>
                    </div>
                    <!-- /.card-body -->
                  </div>
                  <!-- /.card -->
                  <!-- general form elements disabled -->
                  <div class="card card-secondary">
                    <div class="card-header">
                      <h3 class="card-title">Custom Elements</h3>
                    </div>
                    <!-- /.card-header -->
                    <div class="card-body">
                      <form role="form">
                        <div class="row">
                          <div class="col-sm-6">
                            <!-- checkbox -->
                            <div class="form-group">
                              <div class="custom-control custom-checkbox">
                                <input class="custom-control-input" type="checkbox" id="customCheckbox1" value="option1">
                                <label for="customCheckbox1" class="custom-control-label">Custom Checkbox</label>
                              </div>
                              <div class="custom-control custom-checkbox">
                                <input class="custom-control-input" type="checkbox" id="customCheckbox2" checked>
                                <label for="customCheckbox2" class="custom-control-label">Custom Checkbox checked</label>
                              </div>
                              <div class="custom-control custom-checkbox">
                                <input class="custom-control-input" type="checkbox" id="customCheckbox3" disabled>
                                <label for="customCheckbox3" class="custom-control-label">Custom Checkbox disabled</label>
                              </div>
                            </div>
                          </div>
                          <div class="col-sm-6">
                            <!-- radio -->
                            <div class="form-group">
                              <div class="custom-control custom-radio">
                                <input class="custom-control-input" type="radio" id="customRadio1" name="customRadio">
                                <label for="customRadio1" class="custom-control-label">Custom Radio</label>
                              </div>
                              <div class="custom-control custom-radio">
                                <input class="custom-control-input" type="radio" id="customRadio2" name="customRadio" checked>
                                <label for="customRadio2" class="custom-control-label">Custom Radio checked</label>
                              </div>
                              <div class="custom-control custom-radio">
                                <input class="custom-control-input" type="radio" id="customRadio3" disabled>
                                <label for="customRadio3" class="custom-control-label">Custom Radio disabled</label>
                              </div>
                            </div>
                          </div>
                        </div>

                        <div class="row">
                          <div class="col-sm-6">
                            <!-- select -->
                            <div class="form-group">
                              <label>Custom Select</label>
                              <select class="custom-select">
                                <option>option 1</option>
                                <option>option 2</option>
                                <option>option 3</option>
                                <option>option 4</option>
                                <option>option 5</option>
                              </select>
                            </div>
                          </div>
                          <div class="col-sm-6">
                            <div class="form-group">
                              <label>Custom Select Disabled</label>
                              <select class="custom-select" disabled>
                                <option>option 1</option>
                                <option>option 2</option>
                                <option>option 3</option>
                                <option>option 4</option>
                                <option>option 5</option>
                              </select>
                            </div>
                          </div>
                        </div>

                        <div class="row">
                          <div class="col-sm-6">
                            <!-- Select multiple-->
                            <div class="form-group">
                              <label>Custom Select Multiple</label>
                              <select multiple class="custom-select">
                                <option>option 1</option>
                                <option>option 2</option>
                                <option>option 3</option>
                                <option>option 4</option>
                                <option>option 5</option>
                              </select>
                            </div>
                          </div>
                          <div class="col-sm-6">
                            <div class="form-group">
                              <label>Custom Select Multiple Disabled</label>
                              <select multiple class="custom-select" disabled>
                                <option>option 1</option>
                                <option>option 2</option>
                                <option>option 3</option>
                                <option>option 4</option>
                                <option>option 5</option>
                              </select>
                            </div>
                          </div>
                        </div>

                        <div class="form-group">
                          <div class="custom-control custom-switch">
                            <input type="checkbox" class="custom-control-input" id="customSwitch1">
                            <label class="custom-control-label" for="customSwitch1">Toggle this custom switch element</label>
                          </div>
                        </div>
                        <div class="form-group">
                          <div class="custom-control custom-switch custom-switch-off-danger custom-switch-on-success">
                            <input type="checkbox" class="custom-control-input" id="customSwitch3">
                            <label class="custom-control-label" for="customSwitch3">Toggle this custom switch element with custom colors danger/success</label>
                          </div>
                        </div>
                        <div class="form-group">
                          <div class="custom-control custom-switch">
                            <input type="checkbox" class="custom-control-input" disabled id="customSwitch2">
                            <label class="custom-control-label" for="customSwitch2">Disabled custom switch element</label>
                          </div>
                        </div>
                        <div class="form-group">
                          <label for="customRange1">Custom range</label>
                          <input type="range" class="custom-range" id="customRange1">
                        </div>
                        <div class="form-group">
                          <label for="customRange1">Custom range (custom-range-danger)</label>
                          <input type="range" class="custom-range custom-range-danger" id="customRange1">
                        </div>
                        <div class="form-group">
                          <label for="customRange1">Custom range (custom-range-teal)</label>
                          <input type="range" class="custom-range custom-range-teal" id="customRange1">
                        </div>
                        <div class="form-group">
                          <!-- <label for="customFile">Custom File</label> -->

                          <div class="custom-file">
                            <input type="file" class="custom-file-input" id="customFile">
                            <label class="custom-file-label" for="customFile">Choose file</label>
                          </div>
                        </div>
                        <div class="form-group">
                        </div>
                      </form>
                    </div>
                    <!-- /.card-body -->
                  </div>
                  <!-- /.card -->
                </div>
                <!--/.col (right) -->
              </div>
              <!--/.row -->
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
