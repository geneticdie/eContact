<!DOCTYPE html>
<html>
<?php include '../template/head.php';  ?>

<body class="hold-transition sidebar-mini layout-fixed">
  <style> element.style { z-index: 9999; !Important} </style>
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
      <section class="content-header">
        <div class="container-fluid">
          <div class="row mb-2">
            <div class="col-sm-6">
              <h1>Calendar</h1>
            </div>
            <div class="col-sm-6">
              <ol class="breadcrumb float-sm-right">
                <li class="breadcrumb-item"><a href="#">Home</a></li>
                <li class="breadcrumb-item active">Calendar</li>
              </ol>
            </div>
          </div>
        </div><!-- /.container-fluid -->
      </section>

      <!-- Main content -->
      <section class="content">
        <div class="container-fluid">
          <div class="row">
            <div class="col-md-3">
              <div class="sticky-top mb-3">
                <!-- <div class="card">  ###comment
                  <div class="card-header">
                    <h3 class="card-title">Create Event</h3>
                  </div>
                  <div class="card-body">
                    <div class="btn-group" style="width: 100%; margin-bottom: 10px;">
                      ###- <button type="button" id="color-chooser-btn" class="btn btn-info btn-block dropdown-toggle" data-toggle="dropdown">Color <span class="caret"></span></button> ###
                      <ul class="fc-color-picker" id="color-chooser">
                        <li><a class="text-primary" href="#"><i class="fas fa-square"></i></a></li>
                        <li><a class="text-warning" href="#"><i class="fas fa-square"></i></a></li>
                        <li><a class="text-success" href="#"><i class="fas fa-square"></i></a></li>
                        <li><a class="text-danger" href="#"><i class="fas fa-square"></i></a></li>
                        <li><a class="text-muted" href="#"><i class="fas fa-square"></i></a></li>
                      </ul>
                    </div>
                    ### /btn-group ###
                    <div class="input-group">
                      <input id="new-event" type="text" class="form-control" placeholder="Event Title">

                      <div class="input-group-append">
                        <button id="add-new-event" type="button" class="btn btn-primary">Add</button>
                      </div>
                      ### /btn-group ###
                    </div>
                    ### /input-group ###
                  </div>
                </div> -->
                <!-- Information -->
                <div class="card">
                  <div class="card-header">
                    <h3 class="card-title">Meeting Information</h3>
                  </div>
                  <div class="card-body" id="form_group" style="display:none">
                    <div class="row" id="form_group_1" style="display:none">
                      <div class="col-sm-12">
                        <div class="form-group">
                          <label>Receiver Name : </label>
                          <div id="receiver_name"></div>
                        </div>
                      </div>
                      <div class="col-sm-12">
                        <div class="form-group">
                          <label>Note : </label>
                          <div id="note"></div>
                          <!-- <input type="textarea" class="form-control" placeholder="Note" id="note">-->
                        </div>
                      </div>
                      <div class="col-sm-12">
                        <div class="form-group">
                          <label>Meeting Date : </label>
                          <div id="meeting_date"></div>
                          <!--<input type="text" class="form-control" placeholder="Meeting Date" id="meeting_date">-->
                        </div>
                      </div>
                      <div class="col-sm-12">
                        <div class="form-group">
                          <label>Color : </label>
                          <div id="color"></div>
                          <!--<input type="text" class="form-control" placeholder="Color" id="color">-->
                        </div>
                      </div>
                    </div>
                    <!-- /input-group -->
                  </div>
                </div>
                <!-- Filtering -->
                <div class="card">
                  <div class="card-header">
                    <h3 class="card-title">Filter Search</h3>
                  </div>
                  <div class="card-body">
                    <div class="row">
                      <div class="col-sm-12">
                        <div class="form-group">
                          <label>Choose Staff : </label>
                          <select class="form-control select2bs4" style="width: 100%;" id="staff"></select>
                        </div>
                        <div class="form-group">
                          <label>Choose Staff : </label>
                          <select class="form-control select2bs4" style="width: 100%;" id="staff"></select>
                        </div>
                      </div>
                      <div class="col-sm-12">
                        <div class="col-sm-12">
                          <div class="form-group" id="meetingList" style="display:none">
                            <label>Meeting List (By Date) : </label>
                          </div>
                        </div>
                        <div id="external-events"></div>
                      </div>
                    </div>
                    <!-- /input-group -->
                  </div>
                </div>
                <!-- end Filtering -->
              </div>
            </div>
            <!-- /.col -->
            <div class="col-md-9">
              <div class="card card-primary sticky-top mb-9">
                <div class="card-body p-0">
                  <!-- THE CALENDAR -->
                  <div id="calendar"></div>
                </div>
                <!-- /.card-body -->
              </div>
              <!-- /.card -->
            </div>
            <!-- /.col -->
          </div>
          <!-- /.row -->
        </div><!-- /.container-fluid -->
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

  <!-- onLoad function -->
  <script type="text/javascript">
    window.onload = function() {
      initApp();
      calendarData("none");
    };
    $("#meeting_date").datepicker({
      changeMonth: true,
      changeYear: true
    });
    $("#meeting_date").datepicker( "option", "dateFormat", "M d, yy" );
    $('.select2bs4').select2({
      theme: 'bootstrap4'
    });
  </script>
  <!-- select data -->
  <script type="text/javascript">
    var staffRef = firebase.database().ref().child('Staffs');
    staffRef.on('value', function(snapshot) {
      $('#staff').empty();
      if (snapshot.exists()) {
        var content = '';
        content += '<option value="none">No Filter</option>';
        snapshot.forEach(function(childSnapshot) {
          content += '<option value="' + childSnapshot.key + '">' + childSnapshot.val().firstName + ' ' + childSnapshot.val().lastName + '</option>';
        });
        $('#staff').append(content);
      }
    });
  </script>
  <!-- select data change -->
  <script type="text/javascript">
    $('#staff').on('change', function() {
      calendarData(this.value);
    });
  </script>
  <!-- Calender Data -->
  <script type="text/javascript">
    var initStat = true;

    var colorArr = [];
    var masterRef = firebase.database().ref().child('Master');
    var color = masterRef.child('Colour');
    color.on('value', function(snapshot) {
      colorArr = [];
      if (snapshot.exists()) {
        colorArr = snapshot.val();
      }
      if(initStat === false){
        calendarData("none");
      }
    });

    function calendarData(id) {
      var arr = [];
      var meetingRef = firebase.database().ref().child('Meeting');
      meetingRef.orderByChild("meeting_date_int").on('value', function(snapshot) {
        if(initStat === false) {
          $('#calendar').html('');
          arr = [];
        }
        console.log(snapshot.val());
        if (snapshot.exists()) {
          $('#external-events').empty();
          var content = '';
          snapshot.forEach(function(childSnapshot) {
            var childVal = childSnapshot.val();
            if(id === "none") {
              $('#meetingList').hide();
              var arrtemp = {
                id: childSnapshot.key,
                title: childVal.receiver_name,
                start: new Date(childVal.meeting_date),
                allDay: true,
                textColor: 'white',
                backgroundColor: '#' + colorArr[childVal.colour].hexValue.slice(4),
                borderColor: '#' + colorArr[childVal.colour].hexValue.slice(4),
                description: childVal.note
              }
              arr.push(arrtemp);
            }
            else if(id === childVal.idStaff) {
              $('#meetingList').show();
              var arrtemp = {
                id: childSnapshot.key,
                title: childVal.receiver_name,
                start: new Date(childVal.meeting_date),
                allDay: true,
                textColor: 'white',
                backgroundColor: '#' + colorArr[childVal.colour].hexValue.slice(4),
                borderColor: '#' + colorArr[childVal.colour].hexValue.slice(4),
                description: childVal.note
              }
              arr.push(arrtemp);
              content += '<button onclick="detail(' + childSnapshot.key + ')" type="button" class="btn btn-block btn-sm" style="background-color:' + childVal.colour + '; color: white;">' + childVal.meeting_date + '</button>';
            }
          });
          $('#external-events').append(content);
        }
        var calendarEl = document.getElementById('calendar');
        var calendar = new FullCalendar.Calendar(calendarEl, {
          plugins: [ 'interaction', 'dayGrid' ],
          header: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth'
          },
          eventClick: function(info) {
            detail(info.event.id);
          },
          eventMouseEnter: function(info) {
            info.el.style.backgroundColor = '#555555',
            info.el.style.textColor = 'white'
          },
          eventMouseLeave: function(info) {
            info.el.style.backgroundColor = info.event.backgroundColor,
            info.el.style.textColor = info.event.textColor
          },
          editable: true,
          eventLimit: true, // allow "more" link when too many events
          events : arr
        });
        calendar.render();
        initStat = false;
      });

      $(function () {

        /* ADDING EVENTS */
        //var currColor = '#3c8dbc' //Red by default
        //Color chooser button
        //var colorChooser = $('#color-chooser-btn')
        /*$('#color-chooser > li > a').click(function (e) {
          e.preventDefault()
          //Save color
          currColor = $(this).css('color')
          //Add color effect to button
          $('#add-new-event').css({
            'background-color': currColor,
            'border-color'    : currColor
          })
        })
        $('#add-new-event').click(function (e) {
          e.preventDefault()
          //Get value and make sure it is not null
          var val = $('#new-event').val()
          if (val.length == 0) {
            return
          }

          //Create events
          var event = $('<div />')
          event.css({
            'background-color': currColor,
            'border-color'    : currColor,
            'color'           : '#fff'
          }).addClass('external-event')
          event.html(val)
          $('#external-events').prepend(event)

          //Add draggable funtionality
          ini_events(event)

          //Remove event from text input
          $('#new-event').val('')
        })*/
      });
    }
  </script>
  <!-- Meeting Details -->
  <script type="text/javascript">
    function detail(id) {
      $("#form_group").fadeIn();
      var meetingRefDetail = firebase.database().ref().child('Meeting/' + id);
      meetingRefDetail.on('value', function(snapshot) {
        if (snapshot.exists()) {
          console.log(snapshot.val());
          var color = snapshot.child('colour').val();
          var meeting_date = snapshot.child('meeting_date').val();
          var note = snapshot.child('note').val();
          var receiver_name = snapshot.child('receiver_name').val();
          var idStaff = snapshot.child('idStaff').val();

          $("#form_group_1").fadeOut(100, function() {
            $("#form_group_1").fadeIn(100);
            $("#color").html(color);
            $("#meeting_date").html((new Date(meeting_date)).toDateString());
            $("#note").html(note);
            $("#receiver_name").html(receiver_name);
          });
        }
      });
    }
  </script>
</body>

</html>
