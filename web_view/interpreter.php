<?php
	function javaOutput(){
		$ret = exec('java -cp ../src Main 1 ../src/Testcase.txt web');

		return $ret;
	}

	// echo javaOutput();
?>