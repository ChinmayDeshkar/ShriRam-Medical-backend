package com.shrirammedical.onlineShopping.common.code.service.impl;

import com.shrirammedical.onlineShopping.common.code.entity.Code;
import com.shrirammedical.onlineShopping.common.code.service.CodeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeServiceImpl implements CodeService {

    // Coming in v3
    /**
     * @param code
     * @return
     */
    @Override
    public Code AddCode(Code code) {
        return null;
    }

    /**
     * @param codeType
     * @return
     */
    @Override
    public List<Code> getCodesByType(String codeType) {
        return List.of();
    }

    /**
     * @return
     */
    @Override
    public List<Code> getAllCodes() {
        return List.of();
    }

    /**
     * @param codeId
     * @return
     */
    @Override
    public Code getCodeById(Long codeId) {
        return null;
    }

    /**
     * @param codeId
     * @param code
     * @return
     */
    @Override
    public Code updateCode(Long codeId, Code code) {
        return null;
    }

    /**
     * @param codeId
     */
    @Override
    public void deleteCode(Long codeId) {

    }
}
