<?php

class DB_Functions {

	private $db;

	//put your code here
	// constructor
	function __construct() {
		require_once 'DB_Connect.php';
		// connecting to database
		$this->db = new DB_Connect();
		$this->db->connect();
	}

	// destructor
	function __destruct() {
			
	}

	/**
	 * Storing new user
	 * returns user details
	 */
	public function storeUser($name, $email, $password) {
		$password = md5($password);
		$result = mysql_query("INSERT INTO users(name, email, password) VALUES('$name', '$email', '$password')");
		// check for successful store
		if ($result) {
			// get user details
			$id = mysql_insert_id(); // last inserted id
			$result = mysql_query("SELECT * FROM users WHERE id = $id");
			// return user details
			return mysql_fetch_array($result);
		} else {
			return false;
		}
	}

	/**
	 * Get user by email and password
	 */
	public function getUserByEmailAndPassword($email, $password) {
		$result = mysql_query("SELECT * FROM users WHERE email = '$email'") or die(mysql_error());
		// check for result
		$no_of_rows = mysql_num_rows($result);
		if ($no_of_rows > 0) {
			$result = mysql_fetch_array($result);
			$password = md5($password);
			// check for password
			if ($password == $result['password']) {
				// user authentication details are correct
				return $result;
			}
		} else {
			// user not found
			return false;
		}
	}

	/**
	 * Check user is existed or not
	 */
	public function isUserExisted($email) {
		$result = mysql_query("SELECT email from users WHERE email = '$email'");
		$no_of_rows = mysql_num_rows($result);
		if ($no_of_rows > 0) {
			// user existed
			return true;
		} else {
			// user not existed
			return false;
		}
	}

}

?>