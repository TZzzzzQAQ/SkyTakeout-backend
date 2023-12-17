package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("员工退出")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * 该函数用于新增员工
     *
     * @param employeeDTO
     * @return com.sky.result.Result<com.sky.dto.EmployeeDTO>
     * @author TZzzQAQ
     * @create 2023/12/5
     **/
    @PostMapping
    @ApiOperation("员工新增")
    public Result<EmployeeDTO> save(@RequestBody EmployeeDTO employeeDTO) {
        //生成日志记录要新增员工的信息
        log.info("现在要新增员工{}", employeeDTO);
        //传入employeeDTO参数到Service层
        employeeService.save(employeeDTO);
        return Result.success();
    }

    /**
     * 此函数用于向客户端返回所有的员工信息
     *
     * @param employeePageQueryDTO
     * @return com.sky.result.Result<com.sky.result.PageResult>
     * @author TZzzQAQ
     * @create 2023/12/7
     **/
    @GetMapping("/page")
    @ApiOperation("员工分页查询数据")
    public Result<PageResult> getAllEmployee(EmployeePageQueryDTO employeePageQueryDTO) {
        log.info("客户端发起分页查询员工所有信息{}", employeePageQueryDTO);
        PageResult pageResult = employeeService.getAllEmployee(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 修改员工状态，启用或者禁用
     *
     * @param status
     * @param id
     * @return com.sky.result.Result
     * @author TZzzQAQ
     * @create 2023/12/7
     **/
    @PostMapping("/status/{status}")
    @ApiOperation("启用、禁用员工账号")
    public Result changeEmployeeStatus(@PathVariable Integer status, Long id) {
        log.info("修改{}员工状态信息{}", id, status);
        employeeService.changeEmployeeStatus(status, id);
        return Result.success();
    }

    /**
     * 根据员工id回显员工的信息
     *
     * @param id
     * @return com.sky.result.Result<com.sky.entity.Employee>
     * @author TZzzQAQ
     * @create 2023/12/7
     **/
    @GetMapping("/{id}")
    @ApiOperation("根据id查询员工")
    public Result<Employee> getEmployeeById(@PathVariable Integer id) {
        log.info("客户端正在查询id为：{}的员工", id);
        Employee employee = employeeService.getEmployeeById(id);
        return Result.success(employee);
    }

    /**
     * 修改员工信息
     *
 * @param employeeDTO
     * @return com.sky.result.Result<com.sky.dto.EmployeeDTO>
     * @author TZzzQAQ
     * @create 2023/12/7
     **/
    @PutMapping
    @ApiOperation("编辑员工信息")
    public Result<EmployeeDTO> changeEmployeeInfo(@RequestBody EmployeeDTO employeeDTO) {
        log.info("编辑{}号员工的信息", employeeDTO.getId());
        employeeService.changeEmployeeInfo(employeeDTO);
        return Result.success();
    }
}
