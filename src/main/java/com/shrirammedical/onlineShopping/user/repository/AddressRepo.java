package com.shrirammedical.onlineShopping.user.repository;

import com.shrirammedical.onlineShopping.user.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {

    List<Address> findByUserId(String userId);

    @Query("SELECT a FROM Address a WHERE a.userId = :userId AND a.isDefault = true")
    Address findDefaultAddressByUserId(@Param("userId") String userId);
    @Query("SELECT a FROM Address a WHERE a.userId = :userId AND lastUsed = true")
    Address findLastUsedAddressByUserId(@Param("userId") String userId);

}
