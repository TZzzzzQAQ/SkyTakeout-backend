package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.entity.AddressBook;
import com.sky.mapper.AddressBookMapper;
import com.sky.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    private AddressBookMapper addressBookMapper;

    /**
     * 获取用户所有的地址信息
     *
     * @return java.util.List<com.sky.entity.AddressBook>
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    @Override
    public List<AddressBook> getAllAddress() {
        AddressBook addressBook = AddressBook.builder()
                .userId(BaseContext.getCurrentId())
                .build();
        return addressBookMapper.getAllAddress(addressBook);
    }

    /**
     * 插入新的地址
     *
     * @param addressBook
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    @Override
    public void insertAddress(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(0);
        addressBookMapper.insertAddress(addressBook);
    }

    /**
     * 根据地址id获取地址信息
     *
     * @param id
     * @return com.sky.entity.AddressBook
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    @Override
    public AddressBook getAddressById(Long id) {
        return addressBookMapper.getAddressById(id);
    }

    /**
     * 根据地址的id更新地址信息
     *
     * @param addressBook
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    @Override
    public void setAddressById(AddressBook addressBook) {
        addressBookMapper.setAddressById(addressBook);
    }

    /**
     * 获取该用户的默认地址
     *
     * @return com.sky.entity.AddressBook
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    @Override
    public AddressBook getDefaultAddress() {
        AddressBook addressBook = AddressBook.builder()
                .isDefault(1)
                .userId(BaseContext.getCurrentId())
                .build();
        return addressBookMapper.getDefaultAddress(addressBook);
    }

    /**
     * 设置该用户的默认地址
     *
     * @param addressBook
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    @Override
    public void setDefaultAddress(AddressBook addressBook) {
        addressBook.setIsDefault(0);
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBookMapper.updateAllDefault(addressBook);
        addressBook.setIsDefault(1);
        addressBookMapper.updateDefaultAddress(addressBook);
    }

    /**
     * 根据用户地址的id删除地址
     *
     * @param id
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    @Override
    public void deleteAddressById(Long id) {
        AddressBook addressBook = AddressBook.builder()
                .userId(BaseContext.getCurrentId())
                .id(id)
                .build();
        addressBookMapper.delete(addressBook);
    }
}
