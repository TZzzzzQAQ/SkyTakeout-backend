package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 登录员工
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1. Use employeeMapper to get data
        Employee employee = employeeMapper.getByUsername(username);

        //2. Handle exceptions (missing usernames, incorrect passwords, locked accounts)
        if (employee == null) {
            //missing account
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * Service层实现新增员工的接口
     *
     * @param employeeDTO
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/5
     **/
    @Override
    public void save(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        //使用BeanUtils.copyProperties拷贝属性
        BeanUtils.copyProperties(employeeDTO, employee);
        //配置剩余的属性password,status,createTime,updateTime,createUser,updateUser
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        employee.setStatus(StatusConstant.ENABLE);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        //利用ThreadLocal将封装在BaseContext中的用户id拿到
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.saveEmployee(employee);
    }

    /**
     * 分页查询所有员工信息
     *
     * @param employeePageQueryDTO
     * @return com.sky.result.PageResult
     * @author TZzzQAQ
     * @create 2023/12/7
     **/
    @Override
    public PageResult getAllEmployee(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());

        Page<Employee> page = employeeMapper.getAllEmployee(employeePageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 该函数用于修改员工账号状态，启用或者禁用
     *
     * @param status
     * @param id
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/7
     **/
    @Override
    public void changeEmployeeStatus(Integer status, Long id) {
        Employee employee = Employee.builder()
                .status(status)
                .id(id)
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();
        //使用builder服用mybatis的update语句，在动态sql中判断需要修改的信息，复用语句
        employeeMapper.update(employee);
    }

    /**
     * 实现接口，根据id回显员工信息
     *
     * @param id
     * @return com.sky.entity.Employee
     * @author TZzzQAQ
     * @create 2023/12/7
     **/
    @Override
    public Employee getEmployeeById(Integer id) {
        Employee employee = employeeMapper.getEmployeeById(id);
        employee.setPassword("******");
        return employee;
    }

    /**
     * 修改员工信息
     *
     * @param employeeDTO
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/7
     **/
    @Override
    public void changeEmployeeInfo(EmployeeDTO employeeDTO) {
        //使用对象拷贝将dto的数据传入employee中
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        //补充信息
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.update(employee);
    }

}
