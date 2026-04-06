package com.shrirammedical.onlineShopping.common.code.repository;

import com.shrirammedical.onlineShopping.common.code.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepo extends JpaRepository<Code, Long> {

    // Coming in v3
}
