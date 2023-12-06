package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * 该接口用于员工新增
     *
 * @param employeeDTO
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/5
     **/

    void save(EmployeeDTO employeeDTO);

    PageResult getAllEmployee(EmployeePageQueryDTO employeePageQueryDTO);

    void changeEmployeeStatus(Integer status, Long id);
}
