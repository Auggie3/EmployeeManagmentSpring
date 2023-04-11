package com.auggie.EmployeeManagement.security.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JwtTokenDTO {
    private final String accessToken;
    private final String refreshToken;
    private final boolean admin;
}
