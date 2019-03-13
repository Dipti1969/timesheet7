package com.timesheet.master;

import com.timesheet.bean.Department;
import com.timesheet.bean.Designation;
import java.util.List;

public interface MasterService {
    public void add(Department dept);
    public List<Department> select();
    public Department getById(int deptid);
    public boolean update(int deptid,String dept_name);
    public boolean delete(int deptid);
    public void adddesi(Designation desi);
    public List<Designation> selectdesi();
    public Designation getByDesi(int desiid);
    public boolean updateDesi(int desiid,String desi_name);
    public boolean deleteDesi(int desiid);
    public boolean checkDept(int deptid);
    public boolean checkDesi(int desiid);
    public boolean blockdept(int dept_id);
    public boolean unblockdept(int dept_id);
    public boolean blockdesi(int desi_id);
    public boolean unblockdesi(int desi_id);
}
