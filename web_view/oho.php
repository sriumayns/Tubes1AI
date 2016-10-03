<?php 
	$color_data = array();

    function random_color_part() {
        return str_pad( dechex( mt_rand( 0, 255 ) ), 2, '0', STR_PAD_LEFT);
    }

    function random_color() {
        return random_color_part() . random_color_part() . random_color_part();
    }

    function get_color($course_name){
      global $color_data;
      if(isset($color_data[$course_name])){
      	echo "masukk diaaa.";
        return $color_data[$course_name];
      }

      $color_data[$course_name] = random_color();
      return $color_data[$course_name];
    }

    echo get_color("IF1120<br/>"); echo "<br/>";
    echo get_color("IF1120<br/>"); echo "<br/>";
    echo get_color("IF1120<br/>"); echo "<br/>";
 ?>