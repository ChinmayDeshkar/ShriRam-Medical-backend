package com.shrirammedical.onlineShopping.common.code.service;

import com.shrirammedical.onlineShopping.common.code.entity.Code;

import java.util.List;

public interface CodeService {
    // Coming in v3
    Code AddCode(Code code);
    List<Code> getCodesByType(String codeType);
    List<Code> getAllCodes();
    Code getCodeById(Long codeId);
    Code updateCode(Long codeId, Code code);
    void deleteCode(Long codeId);

}
