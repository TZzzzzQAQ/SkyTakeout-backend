package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     *
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * mapper层用于访问数据库中的数据，并未使用MyBatis配置文件
     *
     * @param employee
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/5
     **/
    @Insert("insert into employee " +
            "(name, username, password, phone, sex, id_number, create_time, update_time, " +
            "create_user, update_user, status) " +
            "values " +
            "(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{createTime}," +
            "#{updateTime},#{createUser},#{updateUser},#{status});")
    void saveEmployee(Employee employee);

    /**
     * 用于访问数据库中所有的员工信息，使用动态sql语句
     *
     * @param employeePageQueryDTO
     * @return com.github.pagehelper.Page<com.sky.entity.Employee>
     * @author TZzzQAQ
     * @create 2023/12/7
     **/
    Page<Employee> getAllEmployee(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 使用动态sql语句进行修改数据库中的信息，update语句为所有字段动态判断是否需要修改
     *
     * @param id
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/7
     **/
    void update(Employee.EmployeeBuilder id);
}
