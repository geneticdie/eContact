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
                            <h1 class="m-0 text-dark">Meeting</h1>
                        </div><!-- /.col -->
                        <div class="col-sm-6">
                            <ol class="breadcrumb float-sm-right">
                                <li class="breadcrumb-item"><a href="#">Home</a></li>
                                <li class="breadcrumb-item active">Meeting</li>
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
                        <section class="content">
                            <div class="container-fluid">
                                <div class="row">
                                    <!-- left column -->
                                    <div class="col-md-12">
                                        <div class="card card-info">
                                            <div class="card-header">
                                                <h3 class="card-title">Add Meeting</h3>
                                            </div>
                                            <!-- /.card-header -->
                                            <!-- form start -->
                                            <form role="form">
                                                <div class="card-body">
                                                    <div class="form-group">
                                                        <label>Name</label>
                                                        <select class="form-control">
                                                            <option>Name 1</option>
                                                            <option>Name 2</option>
                                                            <option>Name 3</option>
                                                        </select>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Meeting Date</label>
                                                        <input type="date" class="form-control">
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Colour</label>
                                                        <select class="form-control">
                                                            <option>Colour 1</option>
                                                            <option>Colour 2</option>
                                                            <option>Colour 3</option>
                                                        </select>
                                                    </div>
                                                    <div class="form-group">
                                                        <label>Notes</label>
                                                        <textarea class="form-control" rows="3" placeholder="Notes here ..."></textarea>
                                                    </div>
                                                    <!-- /.card-body -->

                                                    <div class="card">
                                                        <button type="submit" class="btn btn-info btn-block">Submit</button>
                                                    </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <!-- /.row -->
                            </div><!-- /.container-fluid -->
                        </section>
                    </div>
                    <!-- /.col -->
                </div>

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
        $("#bornDate").datepicker("option", "dateFormat", "M d, yy");
    </script>
    <!-- Datatables data -->
    <script type="text/javascript">
        var staffRef = firebase.database().ref().child('Staffs');
        staffRef.on('value', function(snapshot) {
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
                    content += '<td>' + childVal.carrierPath + '</td>';
                    content += '<td>' + childVal.character + '</td>';
                    content += '<td>' + childVal.colourRelation + '</td>';
                    content += '<td><button onclick="onclickInfo(&#39;' + childSnapshot.key + '&#39;)" type="button" class="btn btn-info btn-sm" data-toggle="modal" data-target="#modal-xl"><i class="fas fa-info"></i></button></td>';
                    content += '</tr>';
                });
                $('#ex-table-tbody').append(content);

                $("#ex-table").DataTable();

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
    <!-- Information Modal -->
    <script type="text/javascript">
        function onclickInfo(id) {
            var staffRefDetail = firebase.database().ref().child('Staffs/' + id);
            staffRefDetail.on('value', function(snapshot) {
                if (snapshot.exists()) {
                    var address = snapshot.child('address').val();
                    var batch = snapshot.child('batch').val();
                    var bornDate = snapshot.child('bornDate').val();
                    var bornPlace = snapshot.child('bornPlace').val();
                    var carrierPath = snapshot.child('carrierPath').val();
                    var character = snapshot.child('character').val();
                    var colourRelation = snapshot.child('colourRelation').val();
                    var ctcNum1 = snapshot.child('ctcNum1').val();
                    var ctcNum2 = snapshot.child('ctcNum2').val();
                    var date_entry = snapshot.child('date_entry').val();
                    var firstName = snapshot.child('firstName').val();
                    var lastName = snapshot.child('lastName').val();
                    var nickName = snapshot.child('nickname').val();
                    var nrp = snapshot.child('nrp').val();
                    var organization = snapshot.child('organization').val();
                    var profPicUrl = snapshot.child('profPicUrl').val();
                    var title_organization = snapshot.child('title_organization').val();
                    var waNum = snapshot.child('waNum').val();
                    var bornDateDetails = bornDate.split("-");
                    $("#firstName").val(firstName);
                    $("#lastName").val(lastName);
                    $("#nickName").val(nickName);
                    $("#address").val(address);
                    $("#bornPlace").val(bornPlace);
                    $("#bornDate").val(bornDate);
                    $("#ctcNum1").val(ctcNum1);
                    $("#ctcNum2").val(ctcNum2);
                    $("#waNum").val(waNum);
                    $("#nrp").val(nrp);
                    $("#batch").val(batch);
                    $("#carrierPath").val(carrierPath);
                    $("#character").val(character);
                } else {

                }
            });
        }
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
            $("#carrierPath").removeAttr("disabled");
            $("#character").removeAttr("disabled");
            $("#title_organization").removeAttr("disabled");
            $("#organization").removeAttr("disabled");
            $("#color").removeAttr("disabled");
            $("#imageDiv").addClass("img");

            $("#editButton").fadeOut(100);
            $("#editButton2").fadeOut(100, function() {
                $("#saveButton").fadeIn(100);
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
                var uploadTask = firebase.storage().ref('Profile_Picture/' + id).put(file);
                uploadTask.on(firebase.storage.TaskEvent.STATE_CHANGED,
                    function(snapshot) {
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
                    },
                    function(error) {
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
                    },
                    function() {
                        // Upload completed successfully, now we can get the download URL
                        uploadTask.snapshot.ref.getDownloadURL().then(function(downloadURL) {
                            console.log('File available at', downloadURL);

                            var bornDateTemp = $("#bornDate").datepicker("getDate");
                            var bornDateForFirebase = bornDateTemp.toString().split(" ");
                            var staffRefDetail = firebase.database().ref().child('Staffs/' + id);
                            var updateData = {
                                address: $("#address").val(),
                                batch: $("#batch").val(),
                                bornDate: bornDateForFirebase[1] + " " + bornDateForFirebase[2] + ", " + bornDateForFirebase[3],
                                bornPlace: $("#bornPlace").val(),
                                carrierPath: $("#carrierPath").val(),
                                character: $("#character").val(),
                                colourRelation: $("#color").val(),
                                ctcNum1: $("#ctcNum1").val(),
                                ctcNum2: $("#ctcNum2").val(),
                                //date_entry : snapshot.child('date_entry').val(),
                                firstName: $("#firstName").val(),
                                lastName: $("#lastName").val(),
                                nickname: $("#nickName").val(),
                                nrp: $("#nrp").val(),
                                organization: $("#organization").val(),
                                profPicUrl: downloadURL,
                                title_organization: $("#title_organization").val(),
                                waNum: $("#waNum").val()
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
                                            title: 'Data have been updated'
                                        })
                                    });
                                    console.log('Update succeeded');
                                })
                                .catch(function(error) {
                                    window.alert("Data Update Error");
                                    console.log('Update failed');
                                });
                            closeClick();
                        });
                    });
            } else {
                var bornDateTemp = $("#bornDate").datepicker("getDate");
                var bornDateForFirebase = bornDateTemp.toString().split(" ");
                var staffRefDetail = firebase.database().ref().child('Staffs/' + id);
                var updateData = {
                    address: $("#address").val(),
                    batch: $("#batch").val(),
                    bornDate: bornDateForFirebase[1] + " " + bornDateForFirebase[2] + ", " + bornDateForFirebase[3],
                    bornPlace: $("#bornPlace").val(),
                    carrierPath: $("#carrierPath").val(),
                    character: $("#character").val(),
                    colourRelation: $("#color").val(),
                    ctcNum1: $("#ctcNum1").val(),
                    ctcNum2: $("#ctcNum2").val(),
                    //date_entry : snapshot.child('date_entry').val(),
                    firstName: $("#firstName").val(),
                    lastName: $("#lastName").val(),
                    nickname: $("#nickName").val(),
                    nrp: $("#nrp").val(),
                    organization: $("#organization").val(),
                    //profPicUrl : downloadURL,
                    title_organization: $("#title_organization").val(),
                    waNum: $("#waNum").val()
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
                                title: 'Data have been updated'
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
    </script>

</body>

</html>