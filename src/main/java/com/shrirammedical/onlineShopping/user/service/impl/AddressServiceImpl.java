package com.shrirammedical.onlineShopping.user.service.impl;

import com.shrirammedical.onlineShopping.user.entity.Address;
import com.shrirammedical.onlineShopping.user.repository.AddressRepo;
import com.shrirammedical.onlineShopping.user.service.AddressService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepo;

    /**
     * @return
     */
    @Override
    public List<Address> getUserAddresses() {
        String userId = getCurrentUser();
        List<Address> addresses = addressRepo.findByUserId(userId);
        if(addresses.isEmpty()){
            throw new RuntimeException("No Address found for user: " + userId);
        }

        return addresses;
    }

    /**
     * @return
     */
    @Override
    public Address defaultAddress() {
        String userId = getCurrentUser();
        return addressRepo.findDefaultAddressByUserId(userId);
    }

    /**
     * @param address
     * @return
     */
    @Override
    public Address setAddress(Address address) {
        if (addressRepo.existsAddressByAddressLine1AndAddressLine2(address.getAddressLine1(), address.getAddressLine2())) {
            return null;
        }
        log.debug("Address: " + address.toString());
        String userId = getCurrentUser();
        Address address1 = new Address();
        address1.setUserId(userId);
        address1.setAddressLine1(address.getAddressLine1());
        address1.setAddressLine2(address.getAddressLine2());
        address1.setCity(address.getCity());
        address1.setState(address.getState());
        address1.setPinCode(address.getPinCode());
        address1.setCountry(address.getCountry());
        address1.setDefault(address.isDefault());
        address1.setLastUsed(true);
        log.debug("Address1: " + address1.toString());
        return addressRepo.save(address1);
    }

    /**
     * @param address
     * @return
     */
    @Override
    public Address updateAddress(Address address) {
        Address address1 = addressRepo.findById(address.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + address.getAddressId()));

        if(address.isDefault()){
            address1.setDefault(true);
        }
        if(address.isLastUsed()){
            address1.setLastUsed(true);
        }
        if(address.getAddressLine1() != null){
            address1.setAddressLine1(address.getAddressLine1());
        }
        if(address.getAddressLine2() != null){
            address1.setAddressLine2(address.getAddressLine2());
        }
        if(address.getCity() != null){
            address1.setCity(address.getCity());
        }
        if(address.getState() != null){
            address1.setState(address.getState());
        }
        if(address.getPinCode() != null){
            address1.setPinCode(address.getPinCode());
        }
        if(address.getCountry() != null){
            address1.setCountry(address.getCountry());
        }
        return addressRepo.save(address1);
    }

    /**
     * @param addressId
     */
    @Override
    public void deleteAddress(Long addressId) {
        addressRepo.deleteById(addressId);
    }

    /**
     * @return
     */
    @Override
    public Address lastUsedAddress() {
        String userId = getCurrentUser();
        return addressRepo.findLastUsedAddressByUserId(userId);
    }

    // ----------------------- Private Methods ------------------ //

    private String getCurrentUser(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
