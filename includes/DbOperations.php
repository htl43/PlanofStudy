<?php

    class DbOperations{

        private $con;

        function __construct(){

            require_once dirname(__FILE__).'/DbConnect.php';

            $db = new DbConnect();

            $this->con = $db->connect();

        }

        /*CRUD -> C -> CREATE */

        public function createUser($username, $pass, $studentID,$name){
            $student_id = (int)$studentID;
            if($this->isUserExist($username,$student_id)){
                return 0;
            }else{
                $password = md5($pass);
                $stmt = $this->con->prepare("INSERT INTO `user_info` (`username`, `password`, `student_id`,`name`) VALUES (?, ?, ?, ?);");
                $stmt->bind_param("ssss",$username,$password,$student_id,$name);

                if($stmt->execute()){
                    return 1;
                }else{
                    return 2;
                }
            }
        }

        public function userLogin($username, $pass){
            $password = md5($pass);
            $stmt = $this->con->prepare("SELECT student_id FROM user_info WHERE username = ? AND password = ?");
            $stmt->bind_param("ss",$username,$password);
            $stmt->execute();
            $stmt->store_result();
            return $stmt->num_rows > 0;
        }

        public function getUserByUsername($username){
            $stmt = $this->con->prepare("SELECT * FROM user_info WHERE username = ?");
            $stmt->bind_param("s",$username);
            $stmt->execute();
            return $stmt->get_result()->fetch_assoc();
        }


        private function isUserExist($username, $student_id){
            $stmt = $this->con->prepare("SELECT student_id FROM user_info WHERE username = ? OR student_id = ?");
            $stmt->bind_param("ss", $username, $student_id);
            $stmt->execute();
            $stmt->store_result();
            return $stmt->num_rows > 0;
        }

    }
