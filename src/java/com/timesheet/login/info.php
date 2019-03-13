?php
 // Include confi.php
 include_once('conf.php');
 
 $uid = isset($_GET['username']) ? mysql_real_escape_string($_GET['username']) :  "";
 $pid = isset($_GET['password']) ? mysql_real_escape_string($_GET['password']) :  "";
 if(!empty($uid) && !empty($pid){
 String query = "select emp_id, emp_password, emp_fname, emp_lname, dept_id, role_id, emp_email,emp_status,time_right from employee_master where emp_id='" + username + "' and emp_password='" + password + "'";
 $qur = mysql_query(query);
 $result =array();
 while($r = mysql_fetch_array($qur)){
 extract($r);
 $result[] = array("emp_id" => $emp_id, "name" => $emp_fname+" "+$emp_lname, $"email" => $emp_email, 'status' => $emp_status); 
 }
 $json = array("status" => 1, "info" => $result);
 }else{
 $json = array("status" => 0, "msg" => "User ID not define");
 }
 @mysql_close($conn);
 
 /* Output header */
 header('Content-type: application/json');
 echo json_encode($json);