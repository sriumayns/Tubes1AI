<?php
	function javaOutput($algo_id, $tc_name){
		$ret = exec('java -cp ../src Main '. $algo_id . ' ' . $tc_name . ' web');

		return $ret;
	}

	// echo javaOutput();
?>