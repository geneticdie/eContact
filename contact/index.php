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
                      <th>Career Path</th>
                      <th>Character</th>
                      <th>Colour Relation</th>
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
                <!-- <h4 class="modal-title" id="modalTitle">Title Goes Here</h4> -->
                <h4 class="modal-title">Detail Information</h4>
                <button onclick="editClick()" id="editButton" type="button" class="btn btn-info"><i class="fas fa-lg fa-edit"></i></button>
                <button id="saveButton" type="button" class="btn btn-info" style="display:none"><i class="fas fa-lg fa-save"></i></button>
              </div>
              <div class="modal-body">
                <div class="row">
                  <div class="col-md-4">
                    <!-- <img src="https://sistempintar.com/ricky.png"> -->
                    <div class="card card-info">
                      <div class="card-header">
                        <h3 class="card-title">Profil Photo</h3>
                      </div>
                      <!-- /.card-header -->
                      <div class="card-body">
                        <form role="form">
                          <div class="row">
                            <div class="col-sm-1">
                            </div>
                            <div class="col-sm-10">
                              <div class="form-group" id="imageDiv">
                                <img id="image" class="images" src="../assets/siluet.jpg" style="width: 250px; height: 328px; object-fit: cover;">
                                <div class="middle">
                                  <i class="fas fa-4x fa-camera" onclick="changePicClick()"></i>
                                  <input type="file" id="my_file" style="display: none;" accept="image/*">
                                </div>
                              </div>
                            </div>
                            <div class="col-sm-1">
                            </div>
                          </div>
                        </form>
                      </div>
                      <!-- /.card-body -->
                    </div>
                  </div>
                  <div class="col-md-8">
                    <!-- Basic Information -->
                    <div class="card card-info">
                      <div class="card-header">
                        <h3 class="card-title">Basic Information</h3>
                      </div>
                      <div class="card-body">
                        <form role="form">
                          <div class="row">
                            <div class="col-sm-6">
                              <div class="form-group">
                                <label>First Name</label>
                                <input type="text" class="form-control" placeholder="First Name" id="firstName">
                              </div>
                            </div>
                            <div class="col-sm-6">
                              <div class="form-group">
                                <label>Last Name</label>
                                <input type="text" class="form-control" placeholder="Last Name" id="lastName">
                              </div>
                            </div>
                            <div class="col-sm-6">
                              <div class="form-group">
                                <label>Nick Name</label>
                                <input type="text" class="form-control" placeholder="Nick Name" id="nickName">
                              </div>
                            </div>
                            <div class="col-sm-6">
                              <div class="form-group">
                                <label>Address</label>
                                <input type="text" class="form-control" placeholder="Address" id="address">
                              </div>
                            </div>
                            <div class="col-sm-6">
                              <div class="form-group">
                                <label>Born Place</label>
                                <input type="text" class="form-control" placeholder="Born Place" id="bornPlace">
                              </div>
                            </div>
                            <div class="col-sm-6" id="bornDateView">
                              <div class="form-group">
                                <label>Born Date</label>
                                <input type="Text" class="form-control" placeholder="Born Date" id="bornDate">
                              </div>
                            </div>
                            <div class="col-sm-4">
                              <div class="form-group">
                                <label>Contact Number 1</label>
                                <input type="number" class="form-control" placeholder="Contact Number 1" id="ctcNum1">
                              </div>
                            </div>
                            <div class="col-sm-4">
                              <div class="form-group">
                                <label>Contact Number 2</label>
                                <input type="number" class="form-control" placeholder="Contact Number 2" id="ctcNum2">
                              </div>
                            </div>
                            <div class="col-sm-4">
                              <div class="form-group">
                                <label>Whatsapp Number</label>
                                <input type="number" class="form-control" placeholder="Whatsapp Number" id="waNum">
                              </div>
                            </div>
                          </div>
                        </form>
                      </div>
                    </div>
                    <!-- /.card -->
                  </div>
                  <div class="col-md-12">
                    <!-- Work Information -->
                    <div class="card card-info">
                      <div class="card-header">
                        <h3 class="card-title">Work Information</h3>
                      </div>
                      <!-- /.card-header -->
                      <div class="card-body">
                        <form role="form">
                          <div class="row">
                            <div class="col-sm-4">
                              <div class="form-group">
                                <label>Title Organization</label>
                                <input type="text" class="form-control" placeholder="Batch" id="title_organization">
                              </div>
                            </div>
                            <div class="col-sm-8">
                              <div class="form-group">
                                <label>NRP</label>
                                <input type="text" class="form-control" placeholder="NRP" id="nrp">
                              </div>
                            </div>
                            <div class="col-sm-4">
                              <div class="form-group">
                                <label>Organization</label>
                                <input type="text" class="form-control" placeholder="Batch" id="organization">
                              </div>
                            </div>
                            <div class="col-sm-4">
                              <div class="form-group">
                                <label>Batch</label>
                                <input type="text" class="form-control" placeholder="Batch" id="batch">
                              </div>
                            </div>
                            <div class="col-sm-4">
                              <div class="form-group">
                                <label>Career Path</label>
                                <select class="form-control select2bs4" style="width: 100%;" id="careerPath"></select>
                              </div>
                            </div>
                            <div class="col-sm-4">
                              <div class="form-group">
                                <label>Personal Network Colour</label>
                                <select class="form-control select2bs4" style="width: 100%;" id="color"></select>
                              </div>
                            </div>
                            <div class="col-sm-8">
                              <div class="form-group">
                                <label>Characters</label>
                                <select class="select2bs4" multiple="multiple" style="width: 100%;" id="character"></select>
                              </div>
                            </div>
                          </div>
                        </form>
                      </div>
                      <!-- /.card-body -->
                    </div>
                  </div>
                  <!--/.col (right) -->
                </div>
                <!--/.row -->
              </div>
              <div class="modal-footer justify-content-between">
                <button onclick="closeClick()" type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button onclick="editClick()" id="editButton2" type="button" class="btn btn-info"><i class="fas fa-lg fa-circle-notch fa-edit"></i> Edit</button>
                <button id="saveButton2" type="button" class="btn btn-info" style="display:none"><i class="fas fa-lg fa-save"></i> Save changes</button>
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
  <script type="text/javascript">
    window.onload = function() {
      initApp();
    };
    $("#bornDate").datepicker({
      changeMonth: true,
      changeYear: true
    });
    $("#bornDate").datepicker( "option", "dateFormat", "M d, yy" );
    $('.select2bs4').select2({
      theme: 'bootstrap4'
    });
  </script>
  <!-- Datatables data -->
  <script type="text/javascript">
    var initStat = true; var table;
    var staffRef = firebase.database().ref().child('Staffs');
    staffRef.on('value', function(snapshot) {
      console.log(initStat);
      if(initStat === false) {
        table.destroy();
        console.log("destroy");
      }
      $('#ex-table-tbody').empty();
      if (snapshot.exists()) {
        var content = '';
        snapshot.forEach(function(childSnapshot) {
          var childVal = childSnapshot.val();
          content += '<tr>';
          content += '<td>' + childVal.firstName + " " + childVal.lastName + '</td>';
          content += '<td>' + childVal.title_organization + '</td>';
          content += '<td>' + childVal.organization + '</td>';
          content += '<td>' + childVal.batch + '</td>';
          content += '<td>' + childVal.careerPath + '</td>';
          content += '<td>' + childVal.character + '</td>';
          content += '<td>' + childVal.colourRelation + '</td>';
          content += '<td><button onclick="onclickInfo(&#39;' + childSnapshot.key + '&#39;)" type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#modal-xl"><i class="fas fa-info"></i></button></td>';
          content += '</tr>';
        });
        $('#ex-table-tbody').append(content);
        table = $('#ex-table').DataTable();
        initStat = false;
      } else {
        var content = '';
        content += '<tr>';
        content += '<td colspan="7" align="center">No data available</td>';
        content += '</tr>';
        $('#ex-table-tbody').append(content);

        $("#ex-table").DataTable();
      }
    });
  </script>
  <!-- All Select Option data (Colour, Career Path, Organization, Title_Organization, Characters) -->
  <script type="text/javascript">
    var masterRef = firebase.database().ref().child('Master');
    var color = masterRef.child('Colour');
    color.on('value', function(snapshot) {
      $('#color').empty();
      if (snapshot.exists()) {
        var content = '';
        snapshot.forEach(function(childSnapshot) {
          if(childSnapshot.val() === true){
            content += '<option value="' + childSnapshot.key + '">' + childSnapshot.key + '</option>';
          }
        });
        $('#color').append(content);
      }
    });
    var careerPath = masterRef.child('Career Path');
    careerPath.on('value', function(snapshot) {
      $('#careerPath').empty();
      if (snapshot.exists()) {
        var content = '';
        snapshot.forEach(function(childSnapshot) {
          if(childSnapshot.val() === true){
            content += '<option value="' + childSnapshot.key + '">' + childSnapshot.key + '</option>';
          }
        });
        $('#careerPath').append(content);
      }
    });
    var character = masterRef.child('Characters');
    character.on('value', function(snapshot) {
      $('#character').empty();
      if (snapshot.exists()) {
        var content = '';
        snapshot.forEach(function(childSnapshot) {
          if(childSnapshot.val() === true){
            content += '<option value="' + childSnapshot.key + '">' + childSnapshot.key + '</option>';
          }
        });
        $('#character').append(content);
      }
    });
  </script>
  <!-- Onclick Event -->
  <script type="text/javascript">
    function changePicClick() {
      $("#my_file").click();
    }

    function editClick() {
      $("#firstName").removeAttr("disabled");
      $("#lastName").removeAttr("disabled");
      $("#nickName").removeAttr("disabled");
      $("#address").removeAttr("disabled");
      $("#bornPlace").removeAttr("disabled");
      $("#bornDate").removeAttr("disabled");
      $("#ctcNum1").removeAttr("disabled");
      $("#ctcNum2").removeAttr("disabled");
      $("#waNum").removeAttr("disabled");
      $("#nrp").removeAttr("disabled");
      $("#batch").removeAttr("disabled");
      $("#careerPath").removeAttr("disabled");
      $("#character").removeAttr("disabled");
      $("#title_organization").removeAttr("disabled");
      $("#organization").removeAttr("disabled");
      $("#color").removeAttr("disabled");
      $("#imageDiv").addClass("img");

      $("#editButton").fadeOut(100);
      $("#editButton2").fadeOut(100, function() {
        $("#saveButton").fadeIn(100);
        $("#saveButton2").fadeIn(100);
      });
    }

    var file;
    var statePicChange = false;
    document.getElementById('my_file').addEventListener('change', function(e) {
      document.getElementById('image').src = window.URL.createObjectURL(this.files[0]);
      file = e.target.files[0];
      statePicChange = true;
      console.log("statePicChange = true");
      console.log(file);
    });

    function saveClick(id) {
      if (statePicChange === true) {
        $(function() {
          const Toast = Swal.mixin({
            toast: true,
            position: 'top-end',
            showConfirmButton: false,
            timer: 3000
          });

          Toast.fire({
            type: 'info',
            title: ' Updating data\nUpon success will be notified'
          })
        });
        var uploadTask = firebase.storage().ref('Profile_Picture/' + id).put(file);
        uploadTask.on(firebase.storage.TaskEvent.STATE_CHANGED,
          function(snapshot) {
            closeClick();
            var progress = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
            console.log('Upload is ' + progress + '% done');
            switch (snapshot.state) {
              case firebase.storage.TaskState.PAUSED: // or 'paused'
                console.log('Upload is paused');
                break;
              case firebase.storage.TaskState.RUNNING: // or 'running'
                console.log('Upload is running');
                break;
            }
          }, function(error) {
          switch (error.code) {
            case 'storage/unauthorized':
              // User doesn't have permission to access the object
              console.log("unauthorized");
              break;
            case 'storage/canceled':
              // User canceled the upload
              console.log("canceled");
              break;
            case 'storage/unknown':
              // Unknown error occurred, inspect error.serverResponse
              console.log("unknown");
              break;
          }
        }, function() {
          // Upload completed successfully, now we can get the download URL
          uploadTask.snapshot.ref.getDownloadURL().then(function(downloadURL) {
            console.log('File available at', downloadURL);

            var bornDateTemp = $("#bornDate").datepicker("getDate");
            var bornDateForFirebase = bornDateTemp.toString().split(" ");
            var staffRefDetail = firebase.database().ref().child('Staffs/' + id);
            var characterToFirebase;
            if ($("#character").val() === null) {
              characterToFirebase = ""
            }
            else {
              characterToFirebase = $("#character").val().toString().replace(/,/g, ', ');
            }
            var updateData = {
              address : $("#address").val(),
              batch : $("#batch").val(),
              bornDate : bornDateForFirebase[1] + " " + bornDateForFirebase[2] + ", " + bornDateForFirebase[3],
              bornPlace : $("#bornPlace").val(),
              careerPath : $("#careerPath").val(),
              character : characterToFirebase,
              colourRelation : $("#color").val(),
              ctcNum1 : $("#ctcNum1").val(),
              ctcNum2 : $("#ctcNum2").val(),
              //date_entry : snapshot.child('date_entry').val(),
              firstName : $("#firstName").val(),
              lastName : $("#lastName").val(),
              nickName : $("#nickName").val(),
              nrp : $("#nrp").val(),
              organization : $("#organization").val(),
              profPicUrl : downloadURL,
              title_organization : $("#title_organization").val(),
              waNum : $("#waNum").val()
            };

            staffRefDetail.update(updateData)
            .then(function() {
              $(function() {
                const Toast = Swal.mixin({
                  toast: true,
                  position: 'top-end',
                  showConfirmButton: false,
                  timer: 3000
                });

                Toast.fire({
                  type: 'success',
                  title: ' Data have been updated'
                })
              });
              console.log('Update succeeded');
            })
            .catch(function(error) {
              window.alert("Data Update Error");
              console.log('Update failed');
            });
          });
        });
      }

      else {
        var bornDateTemp = $("#bornDate").datepicker("getDate");
        var bornDateForFirebase = bornDateTemp.toString().split(" ");
        var staffRefDetail = firebase.database().ref().child('Staffs/' + id);
        var characterToFirebase;
        if ($("#character").val() === null) {
          characterToFirebase = ""
        }
        else {
          characterToFirebase = $("#character").val().toString().replace(/,/g, ', ');
        }
        var updateData = {
          address : $("#address").val(),
          batch : $("#batch").val(),
          bornDate : bornDateForFirebase[1] + " " + bornDateForFirebase[2] + ", " + bornDateForFirebase[3],
          bornPlace : $("#bornPlace").val(),
          careerPath : $("#careerPath").val(),
          character : characterToFirebase,
          colourRelation : $("#color").val(),
          ctcNum1 : $("#ctcNum1").val(),
          ctcNum2 : $("#ctcNum2").val(),
          //date_entry : snapshot.child('date_entry').val(),
          firstName : $("#firstName").val(),
          lastName : $("#lastName").val(),
          nickName : $("#nickName").val(),
          nrp : $("#nrp").val(),
          organization : $("#organization").val(),
          //profPicUrl : downloadURL,
          title_organization : $("#title_organization").val(),
          waNum : $("#waNum").val()
        };

        staffRefDetail.update(updateData)
        .then(function() {
          $(function() {
            const Toast = Swal.mixin({
              toast: true,
              position: 'top-end',
              showConfirmButton: false,
              timer: 3000
            });

            Toast.fire({
              type: 'success',
              title: ' Data have been updated'
            })
          });
          console.log('Update succeeded');
        })
        .catch(function(error) {
          window.alert("Data Update Error");
          console.log('Update failed');
        });
        closeClick();
      }
    }

    function closeClick() {
      $("#firstName").attr("disabled", true);
      $("#lastName").attr("disabled", true);
      $("#nickName").attr("disabled", true);
      $("#address").attr("disabled", true);
      $("#bornPlace").attr("disabled", true);
      $("#bornDate").attr("disabled", true);
      $("#ctcNum1").attr("disabled", true);
      $("#ctcNum2").attr("disabled", true);
      $("#waNum").attr("disabled", true);
      $("#nrp").attr("disabled", true);
      $("#batch").attr("disabled", true);
      $("#careerPath").attr("disabled", true);
      $("#character").attr("disabled", true);
      $("#title_organization").attr("disabled", true);
      $("#organization").attr("disabled", true);
      $("#color").attr("disabled", true);
      $("#imageDiv").removeClass("img");

      $("#editButton").show();
      $("#editButton2").show();
      $("#saveButton").hide();
      $("#saveButton2").hide();
    }

    function onclickInfo(id) {
      var staffRefDetail = firebase.database().ref().child('Staffs/' + id);
      staffRefDetail.on('value', function(snapshot) {
        if (snapshot.exists()) {
          var address = snapshot.child('address').val();
          var batch = snapshot.child('batch').val();
          var bornDate = snapshot.child('bornDate').val();
          var bornPlace = snapshot.child('bornPlace').val();
          var careerPath = snapshot.child('careerPath').val();
          var character = snapshot.child('character').val();
          var colourRelation = snapshot.child('colourRelation').val();
          var ctcNum1 = snapshot.child('ctcNum1').val();
          var ctcNum2 = snapshot.child('ctcNum2').val();
          var date_entry = snapshot.child('date_entry').val();
          var firstName = snapshot.child('firstName').val();
          var lastName = snapshot.child('lastName').val();
          var nickName = snapshot.child('nickName').val();
          var nrp = snapshot.child('nrp').val();
          var organization = snapshot.child('organization').val();
          var profPicUrl = snapshot.child('profPicUrl').val();
          var title_organization = snapshot.child('title_organization').val();
          var waNum = snapshot.child('waNum').val();
          if (character === null){
              var characterArr = character;
          }
          else {
            var characterArr = character.split(", ")
          }
          $("#firstName").val(firstName);
          $("#lastName").val(lastName);
          $("#nickName").val(nickName);
          $("#address").val(address);
          $("#bornPlace").val(bornPlace);
          $("#bornDate").datepicker("setDate", new Date(bornDate));
          $("#ctcNum1").val(ctcNum1);
          $("#ctcNum2").val(ctcNum2);
          $("#waNum").val(waNum);
          $("#nrp").val(nrp);
          $("#batch").val(batch);
          $("#careerPath").val(careerPath).trigger("change");
          $("#title_organization").val(title_organization);
          $("#organization").val(organization);
          $("#color").val(colourRelation).trigger("change");
          $("#image").attr("src", profPicUrl);
          $("#character").val(characterArr).trigger("change");
          $("#saveButton").attr("onclick", "saveClick('" + id + "')");
          $("#saveButton2").attr("onclick", "saveClick('" + id + "')");
          closeClick();
        }
      });
    }
  </script>

</body>

</html>
