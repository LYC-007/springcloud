package com.data.framework.annotation;

import org.springframework.util.DigestUtils;

public class PasswordEncryptStrategy implements IEncryptResultFieldStrategy {
    @Override
    public String encrypt(String source) {
        if (source == null) {
            return null;
        }
        return new String(DigestUtils.md5Digest(source.getBytes()));
    }


}
