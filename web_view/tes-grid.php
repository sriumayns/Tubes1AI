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

    function f_create_table($data){
      $result = '';
      $result .= '<table class="table table-bordered">';
      $result .= '  <thead>';
      $result .= '    <tr>';
      $result .= '      <th>Jam</th>';
      $result .= '      <th>Senin</th>';
      $result .= '      <th>Selasa</th>';
      $result .= '      <th>Rabu</th>';
      $result .= '      <th>Kamis</th>';
      $result .= '      <th>Jumat</th>';
      $result .= '    </tr>';
      $result .= '  </thead>';
      $result .= '  <tbody>';
      for($i = 7; $i <= 17; $i++){
        $result .= '    <tr>';
        $result .= '      <td>' . $i . '</td>';

        for($j = 1; $j <= 5; $j++){
          $temp = '';
          if(isset($data['jadwal'][$j][$i])){
            foreach ($data['jadwal'][$j][$i] as $k => $v) {
              $temp .= $v . '<br/>';
            }
          }
          $result .= '      <td class='. (in_array($j, $data['available']) && ($i >= $data['hour']['start']) && ($i <= $data['hour']['end']) ? '' : 'red') . '>' . $temp . '</td>';
        }

        $result .= '   </tr>';
      }
      $result .= '  </tbody>';
      $result .= '</table>';

      return $result;
    }

    $full_data = array(
      array(
        'nama_ruangan' => '7602',
        'hour' => array(
          'start' => 7,
          'end' => 14
        ),
        'available' => array(1, 3, 5),
        'jadwal' => array(
          '1' => array(
            '7' => array('IF1234'),
            '8' => array('IF1234'),
            '9' => array('IF1234'),
            '10' => array('IF1234', 'IF3104')
          ),
          '3' => array(),
          '5' => array()
        )
      ),
      array(
        'nama_ruangan' => '7603',
        'hour' => array(
          'start' => 7,
          'end' => 13
        ),
        'available' => array(2, 4, 5),
        'jadwal' => array(
          '2' => array(),
          '4' => array(),
          '5' => array()
        )
      ),
      array(
        'nama_ruangan' => 'Labdas II Informatika',
        'hour' => array(
          'start' => 7,
          'end' => 13
        ),
        'available' => array(2, 4, 5),
        'jadwal' => array(
          '2' => array(),
          '4' => array(),
          '5' => array()
        )
      )
    );

    $nav_tab_html = '<ul class="nav nav-tabs">';
    $content_tab_html = '<br/><div class="tab-content">';

    $i = 0;
    foreach ($full_data as $key => $value) {
      $nav_tab_html .= '<li ' . ($i == 0 ? 'class="active"' : '') .
                       '><a data-toggle="tab" href="#id_' . $key . '">' . $value['nama_ruangan'] . '</a></li>';
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
	    		<input type="radio" name="algo" value="Hill_climbing" />Hill-climbing<br/>
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