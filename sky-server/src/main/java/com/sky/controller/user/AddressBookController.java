package com.sky.controller.user;

import com.sky.entity.AddressBook;
import com.sky.result.Result;
import com.sky.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Api(tags = "Client地址簿接口")
@RequestMapping("/user/addressBook")
public class AddressBookController {
    @Autowired
    private AddressBookService addressBookService;

    /**
     * 获取当前用户所有的地址详情
     *
     * @return com.sky.result.Result<java.util.List < com.sky.entity.AddressBook>>
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    @GetMapping("/list")
    @ApiOperation("查询当前用户的所有地址信息")
    public Result<List<AddressBook>> getAllAddress() {
        log.info("查询当前用户的所有地址信息");
        List<AddressBook> addressBooks = addressBookService.getAllAddress();
        return Result.success(addressBooks);
    }

    /**
     * 为当前用户新增一个收货地址
     *
     * @param addressBook
     * @return com.sky.result.Result<java.lang.String>
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    @PostMapping
    @ApiOperation("新增地址")
    public Result<String> insertAddress(@RequestBody AddressBook addressBook) {
        log.info("新增一个邮寄地址：{}", addressBook);
        addressBookService.insertAddress(addressBook);
        return Result.success();
    }

    /**
     * 根据地址的id查询地址信息，用户回显修改地址
     *
     * @param id
     * @return com.sky.result.Result<com.sky.entity.AddressBook>
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    @GetMapping("/{id}")
    @ApiOperation("根据id查询地址")
    public Result<AddressBook> getAddressById(@PathVariable Long id) {
        log.info("根据id查询地址：{}", id);
        AddressBook addressBook = addressBookService.getAddressById(id);
        return Result.success(addressBook);
    }

    /**
     * 根据id修改地址
     *
     * @param addressBook
     * @return com.sky.result.Result<java.lang.String>
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    @PutMapping()
    @ApiOperation("根据id修改地址")
    public Result<String> setAddressById(@RequestBody AddressBook addressBook) {
        log.info("根据id修改地址：{}", addressBook);
        addressBookService.setAddressById(addressBook);
        return Result.success();
    }

    /**
     * 查询该用户的唯一默认地址
     *
     * @return com.sky.result.Result<com.sky.entity.AddressBook>
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    @GetMapping("/default")
    @ApiOperation("查询默认地址")
    public Result<AddressBook> getDefaultAddress() {
        log.info("查询该用户的默认地址");
        AddressBook addressBook = addressBookService.getDefaultAddress();
        if (addressBook == null) {
            return Result.error("该用户没有设置默认地址");
        }
        return Result.success(addressBook);
    }

    /**
     * 将该用户的一个地址设置为默认地址
     *
     * @param addressBook
     * @return com.sky.result.Result<java.lang.String>
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    @PutMapping("/default")
    @ApiOperation("设置默认地址")
    public Result<String> setDefaultAddress(@RequestBody AddressBook addressBook) {
        log.info("设置{}为默认地址", addressBook);
        addressBookService.setDefaultAddress(addressBook);
        return Result.success();
    }

    /**
     * 根据地址的id删除该地址
     *
     * @param id
     * @return com.sky.result.Result<java.lang.String>
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    @DeleteMapping
    @ApiOperation("根据id删除地址")
    public Result<String> deleteAddressById(@RequestParam Long id) {
        log.info("根据id{}删除地址", id);
        addressBookService.deleteAddressById(id);
        return Result.success();
    }
}
