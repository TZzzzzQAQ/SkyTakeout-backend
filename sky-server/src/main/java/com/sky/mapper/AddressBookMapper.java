package com.sky.mapper;

import com.sky.entity.AddressBook;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddressBookMapper {

    /**
     * 获取该用户所有的地址信息
     *
     * @param addressBook
     * @return java.util.List<com.sky.entity.AddressBook>
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    List<AddressBook> getAllAddress(AddressBook addressBook);

    /**
     * 插入新的地址信息
     *
     * @param addressBook
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    @Insert("insert into address_book (user_id, consignee, sex, phone, province_code, province_name, city_code, city_name" +
            ", district_code, district_name, detail, label) " +
            "values (#{userId},#{consignee},#{sex},#{phone},#{provinceCode},#{provinceName},#{cityCode},#{cityName}" +
            ",#{districtCode},#{districtName},#{detail},#{label});")
    void insertAddress(AddressBook addressBook);

    /**
     * 通过地址id获取地址信息
     *
     * @param id
     * @return com.sky.entity.AddressBook
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    @Select("select * from address_book where id = #{id};")
    AddressBook getAddressById(Long id);

    /**
     * 通过地址的id修改地址信息
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
     * @param addressBook
     * @return com.sky.entity.AddressBook
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    @Select("select * from address_book where user_id = #{userId} and is_default = #{isDefault};")
    AddressBook getDefaultAddress(AddressBook addressBook);

    /**
     * 更新所有的默认地址
     *
     * @param addressBook
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    @Update("update address_book set is_default = #{isDefault} where user_id = #{userId};")
    void updateAllDefault(AddressBook addressBook);

    /**
     * 更新一个默认地址
     *
     * @param addressBook
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    @Update("update address_book set is_default = #{isDefault} where user_id = #{userId} and id = #{id};")
    void updateDefaultAddress(AddressBook addressBook);

    /**
     * 根据地址id删除地址信息
     *
     * @param addressBook
     * @return void
     * @author TZzzQAQ
     * @create 2023/12/24
     **/
    @Delete("delete from address_book where id = #{id} and user_id = #{userId};")
    void delete(AddressBook addressBook);
}


