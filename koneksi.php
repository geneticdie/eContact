<?php 
date_default_timezone_set('Asia/Jakarta');

// Check connection using Procedural

$svr_name = "localhost";
$user = "root";
$password = "";
$dbname = "teknik";

$koneksi = mysqli_connect($svr_name,$user,$password,$dbname);

if (mysqli_connect_errno()){
	echo "Koneksi database gagal : " . mysqli_connect_error();
}

// Check connection using PDO

$host = $svr_name; // Nama hostnya
$username = $user; // Username
$password = $password; // Password (Isi jika menggunakan password)
$database = $dbname; // Nama databasenya


// Koneksi ke MySQL dengan PDO
$pdo = new PDO('mysql:host='.$host.';dbname='.$database, $username, $password);


try{
	$pdo = new PDO('mysql:host='.$host.';dbname='.$database, $username, $password);
}
catch (PDOException $e){
	exit("Error: " . $e->getMessage());
}
 
?>