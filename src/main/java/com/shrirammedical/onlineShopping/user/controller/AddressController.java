package com.shrirammedical.onlineShopping.user.controller;

import com.shrirammedical.onlineShopping.user.entity.Address;
import com.shrirammedical.onlineShopping.user.service.AddressService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/address")
@AllArgsConstructor
@PreAuthorize("hasRole('CUSTOMER')")
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/get")
    public List<Address> getAddress() {
        return addressService.getUserAddresses();
    }

    @GetMapping("/default")
    public Address getDefaultAddress() {
        return addressService.defaultAddress() == null
                ? null  // Return null if no default is set
                : addressService.defaultAddress();
    }

    @GetMapping("/last-used")
    public Address getLastUsedAddress() {
        return addressService.lastUsedAddress();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAddress(@RequestBody Address address) {
        log.info(address.toString());
        return addressService.setAddress(address) != null
                ? ResponseEntity.ok("Address added successfully")
                : ResponseEntity.status(500).body("Failed to add address");
    }

     @PutMapping("/update")
    public ResponseEntity<?> updateAddress(@RequestBody Address address) {
         return addressService.updateAddress(address) != null
                 ? ResponseEntity.ok("Address updated successfully")
                 : ResponseEntity.status(500).body("Failed to update address");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAddress(@RequestParam Long addressId) {
        try {
            addressService.deleteAddress(addressId);
            return ResponseEntity.ok("Address deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to delete address");
        }
    }

}
