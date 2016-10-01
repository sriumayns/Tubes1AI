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
          $result .= '      <td class='. ($data->available[$j] && ($i >= $data->hour->start) && ($i <= $data->hour->end) ? '' : 'red') . '>' . $temp . '</td>';
        }

        $result .= '   </tr>';
      }
      $result .= '  </tbody>';
      $result .= '</table>';

      return $result;
    }

    $datayy = json_decode(javaOutput());
    // print_r($datayy);
    $full_data = $datayy;

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
              STATISTICS
            </td>
          </tr>
          <tr>
            <td colspan="3">
              <hr style="margin: 2px 0;" />
            </td>
          </tr>
          <tr>
            <td style="text-align: left;">
              conflicts: 0
            </td>
            <td style="text-align: left;">
              &nbsp;&nbsp;&nbsp;&nbsp;
            </td>
            <td style="text-align: left;">
              accuracy: 0%
            </td>
          </tr>
        </table>
      </div>
    </div>
    <div class="row">
      <div class="col-sm-3 text-center setal">
        <div class="algorithm">
          ALGORITHM
        </div>
        <div class="choose">
          <input type="radio" name="algo" value="Hill_climbing" checked="checked" />Hill-climbing<br/>
          <input type="radio" name="algo" value="Simulated_Annealing" />Simulated Annealing<br/>
          <input type="radio" name="algo" value="Genetic_Algorithm" />Genetic Algorithm<br/>
          <input type="text" placeholder="File schedule" class="fs" />
        </div>
        <button type="button" class="btn btn-primary btn-lg pull-right generate">Generate</button>
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
</body>
</html>