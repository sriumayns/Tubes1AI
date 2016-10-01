<?php
	function javaOutput(){
		$ret = exec('java -cp ../java_to_be_interpret Interpreter');

		return $ret;
	}
?>