<?php
	function javaOutput($algo_id){
		$ret = exec('java -cp ../src Main '. $algo_id . ' ../src/Testcase.txt web');

		return $ret;
	}

	// echo javaOutput();
?>