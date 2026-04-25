package com.shrirammedical.onlineShopping.user.service;

import com.shrirammedical.onlineShopping.user.entity.Address;

import java.util.List;

public interface AddressService {

    List<Address> getUserAddresses();
    Address defaultAddress();
    Address setAddress(Address address);
    Address updateAddress(Address address);
    void deleteAddress(Long addressId);
    Address lastUsedAddress();
}
