package com.sky.service;

import com.sky.entity.AddressBook;

import java.util.List;

public interface AddressBookService {
    /**
     * 获取该用户所有的地址
     *
     * @return java.util.List<com.sky.entity.AddressBook>
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    List<AddressBook> getAllAddress();

    /**
     * 新增地址
     *
     * @param addressBook
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    void insertAddress(AddressBook addressBook);

    /**
     * 通过地址id获取地址信息
     *
     * @param id
     * @return com.sky.entity.AddressBook
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    AddressBook getAddressById(Long id);

    /**
     * 根据地址id修改地址信息
     *
     * @param addressBook
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    void setAddressById(AddressBook addressBook);

    /**
     * 获取该用户的默认地址
     *
     * @return com.sky.entity.AddressBook
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    AddressBook getDefaultAddress();

    /**
     * 设置该用户的默认地址
     *
     * @param addressBook
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    void setDefaultAddress(AddressBook addressBook);

    /**
     * 根据地址的id删除地址
     *
     * @param id
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    void deleteAddressById(Long id);
}
