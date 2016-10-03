<!DOCTYPE html>
<html>
<head>
  <title></title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="css/bootstrap.min.css">
  <link rel="stylesheet" href="css/my.css">
  <script src="js/jquery.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
</head>
<body>
   <?php
    require('./interpreter.php');

    $color_data = array();

    function random_color_part() {
        return str_pad( dechex( mt_rand( 150, 255 ) ), 2, '0', STR_PAD_LEFT);
    }

    function random_color() {
        return random_color_part() . random_color_part() . random_color_part();
    }

    function get_color($course_name){
      global $color_data;
      if(isset($color_data[$course_name]))
        return $color_data[$course_name];

      $color_data[$course_name] = random_color();
      return $color_data[$course_name];
    }

    // echo random_color();
    function f_create_table($data){
      $result = '';
      $result .= '<table class="table table-bordered">';
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
      $result .= '  <tbody>';
      for($i = 7; $i <= 17; $i++){
        $result .= '    <tr>';
        $result .= '      <td class="thead" style="text-align: center; font-weight: bold;">' . ($i < 10 ? '0' : '') . $i . '.00</td>';

        for($j = 0; $j <= 4; $j++){
          $temp = '';
          $temp .= $data->jadwal[$j][$i] . '<br/>';
          $result .= '      <td '. 
            (
              $data->available[$j] && ($i >= $data->hour->start) && ($i <= $data->hour->end) 
              ? ('style="background-color: #' . (
                $temp != "<br/>"
                ? get_color($temp . $j)
                : 'FFFFFF'
              ) . '"') 
              : 'class="red"'
            ) . '>' . $temp . '</td>';
        }

        $result .= '   </tr>';
      }
      $result .= '  </tbody>';
      $result .= '</table>';

      return $result;
    }

    // echo javaOutput(1, "tc/kosong");
    // exit();

    $algo_id = 1;
    if(isset($_POST['algorithm_id']))
      $algo_id = $_POST['algorithm_id'];

    $tc_name = "tc/kosong";
    $tc_real_name = "";
    if(isset($_FILES["testcase"])){
      $tc_name = $_FILES["testcase"]["tmp_name"];
      $tc_real_name = basename($_FILES["testcase"]["name"]);
    }

    // echo basename($_POST["testcase"]["name"]);

    // echo "<div>" . $algo_id . "</div>";
    
    $datayy = json_decode(javaOutput($algo_id, $tc_name));
    // var_dump($datayy);
    $full_data = $datayy->data_tabel;

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
              conflicts: <?php echo $datayy->konflik ?>
            </td>
            <td style="text-align: left;">
              &nbsp;&nbsp;&nbsp;&nbsp;
            </td>
            <td style="text-align: left;">
              accuracy:  <?php echo $datayy->akurasi ?>%
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
    $(document).ready(function(){
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
    });
  </script>
</body>
</html>