package com.timesheet.util;
import com.timesheet.bean.Employee;

public class SecurityUtils {

	public static boolean isAuthorizedUser(Employee employee, int userRoleID)
	{
		boolean isAuthorized = false;
		if(employee!=null)
		{
			Integer roleID = employee.getRole_id();
			if(roleID!=0 && roleID==userRoleID)
			{
				isAuthorized=true;
			}
		}
		return isAuthorized;
	}
}
