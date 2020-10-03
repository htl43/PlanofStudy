<?php

require_once '../includes/DbOperations.php';

$response = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
	if(isset($_POST['username']) and isset($_POST['password'])){
		$db = new DbOperations();

		if($db->userLogin($_POST['username'], $_POST['password'])){
			$user_info = $db->getUserByUsername($_POST['username']);
			$response['error'] = false;
			$response['student_id'] = (String)$user_info['student_id'];
			$response['name'] = $user_info['name'];
			$response['username'] = $user_info['username'];
		}else{
			$response['error'] = true;
			$response['message'] = "Invalid username or password";
		}

	}else{
		$response['error'] = true;
		$response['message'] = "Required fields are missing";
	}
}

echo json_encode($response);
