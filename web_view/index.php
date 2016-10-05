<!DOCTYPE html>
<html>
<head>
  <title></title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="css/bootstrap.min.css">
  <link rel="stylesheet" href="css/my.css">
  <script src="js/jquery.min.js"></script>
  <script src="js/jquery-ui.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
</head>
<body>
   <?php
    require('./interpreter.php');

    // larik berisi data-data warna sel pelajaran pada tabel
    $color_data = array();

    // mengembalikan nilai random 150 sampai 255 dalam format hexa
    function random_color_part() {
        return str_pad( dechex( mt_rand( 150, 255 ) ), 2, '0', STR_PAD_LEFT);
    }

    // mengembalikan gabungan tiga nilai random format hexa -> sebagai kode warna
    function random_color() {
        return random_color_part() . random_color_part() . random_color_part();
    }

    // mengembalikan warna yang tersedia untuk course unik,
    // jika warna pernah di defenisikan maka warna tersebut
    // dikembalikan
    function get_color($course_name){
      global $color_data;
      if(isset($color_data[$course_name]))
        return $color_data[$course_name];

      $color_data[$course_name] = random_color();
      return $color_data[$course_name];
    }

    //
    // menciptakan tabel baru berdasarkan data ruangan kelas
    // dengan struktur sebagai berikut:
    //
    // data: {
    //   available: [],
    //   hour: {
    //     start,
    //     end
    //   },
    //   jadwal: [
    //     [
    //       mata_kuliah,
    //       ... // length = 10 (jam 07.00 s/d 17.00)
    //     ],
    //     ... // length = 5 (senin s/d jumat)
    //   ]
    // }
    //
    function f_create_table($data){
      $result = '';
      $result .= '<table class="course-tab table table-bordered">';
      $result .= '  <thead>';
      $result .= '    <tr>';
      $result .= '      <th class="thead" style="text-align: center; width: 100px;">Jam</th>';
      $result .= '      <th class="thead">Senin</th>';
      $result .= '      <th class="thead">Selasa</th>';
      $result .= '      <th class="thead">Rabu</th>';
      $result .= '      <th class="thead">Kamis</th>';
      $result .= '      <th class="thead">Jumat</th>';
      $result .= '    </tr>';
      $result .= '  </thead>';
      $result .= '  <tbody class="data-tbody">';

      // Mengisi table body
      for($i = 7; $i <= 17; $i++){

        // open tag TR
        $result .= '<tr>';

        // open-close tag TD jam ke-$i
        $result .= '<td class="thead" style="text-align: center; font-weight: bold;">' . ($i < 10 ? '0' : '') . $i . '.00</td>';

        for($j = 0; $j <= 4; $j++){
          $room_is_avail = ($data->available[$j]) && ($i >= $data->hour->start) && ($i <= $data->hour->end);

          $temp = '';
          $arr_matkul = explode('`', $data->jadwal[$j][$i]);

          // membungkus mata kuliah (bisa lebih dari satu) dalam div setiap matkul
          for($p = 0; $p < sizeof($arr_matkul); $p++){
            if($arr_matkul[$p] != '')

              // open-close tag DIV
              $temp .= ('<div class="sel_matkul" id="') . ($room_is_avail ? '' : 'x') . 
                       ('sel-') . $i . ('-') . $j . ('" ') . ('" style="background-color: #') .
                       get_color($arr_matkul[$p] . $j) . ('">') . $arr_matkul[$p] . ('</div>');
          }

          // open-close tag TD
          $result .= ('<td id="') . ('drop-') . $i . ('-') . $j . ('" ') . ('" style="padding: 0" ') . 
                     ($room_is_avail ? '' : 'class="red"') . ('>') . $temp . ('</td>');
        }

        // close tag TR
        $result .= '</tr>';
      }

      $result .= '  </tbody>';
      $result .= '</table>';

      return $result;
    }

    // For debugging purpose
    //
    // echo javaOutput(1, "tc/kosong");
    // exit();

    // inisialisasi id algoritma (1 = Hill Climbing)
    $algo_id = 1;

    if(isset($_POST['algorithm_id']))
      $algo_id = $_POST['algorithm_id'];

    // inisialisasi testcase algoritma (tc/kosong = testcase kosong)
    $tc_name = "tc/kosong";
    $tc_real_name = "";
    if(isset($_FILES["testcase"])){
      $tc_name = $_FILES["testcase"]["tmp_name"];
      $tc_real_name = basename($_FILES["testcase"]["name"]);
    }

    // encoding java output to json
    $output_java = json_decode(javaOutput($algo_id, $tc_name));

    // For debugging purpose
    //
    // var_dump($output_java);

    $full_data = $output_java->data_tabel;

    // Navigasi Tab Tabel
    $nav_tab_html = '<ul class="nav nav-tabs">';
    $content_tab_html = '<br/><div class="tab-content">';

    $i = 0;
    foreach ($full_data as $key => $value) {
      $nav_tab_html .= '<li ' . ($i == 0 ? 'class="active"' : '') .
                       '><a data-toggle="tab" href="#id_' . $key . '">' . $value->nama_ruangan . '</a></li>';
      $content_tab_html .= '<div id="id_' . $key . '" class="tab-pane fade' . ($i == 0 ? ' in active' : '') . '">';
      $content_tab_html .= f_create_table($value);
      $content_tab_html .= '</div>';
      $i++;
    }

    $nav_tab_html .= '</ul>';
    $content_tab_html .= '</div>';
  ?>


    <!-- HTML Section -->
  <style type="text/css"></style>
  <div class="container-fluid">
    <div class="row">
      <div class="col-sm-6">
        <div class="crazy">
          <span class="x">Schedule</span><span class="y">Generator</span>
        </div>
      </div>
      <div class="col-sm-6" style="text-align: right;">
        <table class="statistic">
          <tr>
            <td colspan="3" style="text-align: left;">
              STATISTICS <b><?php if($tc_real_name != "") echo $tc_real_name . " (" . ($algo_id == 1 ? "Hill-climbing" : ($algo_id == 2 ? "Simulated Annealing" : "Genetic Algorithm")) . ")"; ?></b>
            </td>
          </tr>
          <tr>
            <td colspan="3">
              <hr style="margin: 2px 0;" />
            </td>
          </tr>
          <tr>
            <td style="text-align: left;">
              conflicts: <span id="conflictn"><?php echo $output_java->konflik ?></span>
            </td>
            <td style="text-align: left;">
              &nbsp;&nbsp;&nbsp;&nbsp;
            </td>
            <td style="text-align: left;">
              accuracy:  <?php echo $output_java->akurasi ?>%
            </td>
          </tr>
        </table>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-3 text-center setal">
        <form method='post' enctype='multipart/form-data' action='<?php echo htmlspecialchars($_SERVER["PHP_SELF"]) ?>'>
          <div class="algorithm">
            ALGORITHM
          </div>
          <div class="choose">
            <input type="text" name="dump" id="file_input_trigger" placeholder="File schedule"class="fs" />
            <div id="to_be_hide">
              <input id="al_hc" type="radio" name="algorithm_id" value="1" <?php echo ($algo_id == 1) ? 'checked="checked"' : '' ?> /><label for="al_hc">Hill-climbing</label><br/>
              <input id="al_sa" type="radio" name="algorithm_id" value="2" <?php echo ($algo_id == 2) ? 'checked="checked"' : '' ?> /><label for="al_sa">Simulated Annealing</label><br/>
              <input id="al_ga" type="radio" name="algorithm_id" value="3" <?php echo ($algo_id == 3) ? 'checked="checked"' : '' ?> /><label for="al_ga">Genetic Algorithm</label>
            </div>
            <input type="file" name="testcase" id="file_input_ya" accept="*" class="hidden" />
          </div>
          <button type="submit" class="btn btn-primary btn-lg pull-right generate">Generate</button>
        </form>
        <div class="help-about">
          <div>
            Help
          </div>
          
          <div>
            About
          </div>
        </div>
      </div>
      <div class="col-sm-9">

        <!-- Main Tab Nav -->
        <?php echo $nav_tab_html; ?>

        <!-- Main Tab Content -->
        <?php echo $content_tab_html; ?>
      </div>
    </div>
  </div>
  <script type="text/javascript">
    var conflict_n = 0;
    $(document).ready(function(){

      function updateConflict(){
        conflict_n = 0;
        var arrBodyTable = $(".data-tbody");
        console.log(arrBodyTable);
        for(var i = 0; i < arrBodyTable.length; i++){
          var tr = arrBodyTable[i].children;
          for(var j = 0; j < tr.length; j++){
            var td = tr[j].children;
            for(var k = 0; k < td.length; k++){
              if(td[k].children.length > 1)
                conflict_n++;
            }
          }
        }
      }
        
      $('#file_input_trigger').click(function(){
        $('#file_input_ya').trigger('click');
      });
      $("#file_input_ya").change(function (){
        var fileName = $(this).val().split('\\');
        $('#file_input_trigger').val(fileName[fileName.length - 1]);

        if(fileName.length > 0)
          $('#to_be_hide').show();
      });
      $('#to_be_hide').hide();

      var c = {};

      $("[id^=sel]").css('cursor', 'pointer');

      $("[id^=sel]").draggable({
        cursor: 'move',
        helper: function() {
          //debugger;
          return $("<div style='padding: 10px; background-color: " + $(this).css('background-color') + "'></div>").append($(this).html());
        },
        start: function(event, ui) {
          c.source_parent = $(this).parent;
          $(this).hide();
          c.source = $(this);
        },
        stop: function(event, ui){
          $(this).show();
        }
      });

      $("[id^=drop]").droppable({
        drop: function(event, ui) {
          $(this).append(c.source);
          $('div').remove('#' + c.source.id);
          
          setTimeout(function() {
            updateConflict();  
            $("#conflictn").html(conflict_n);
          }, 100);
          
        }
      });

    });
  </script>
</body>
</html>