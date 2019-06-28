package com.tszj.chain.sdk.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.UUID;

public class JwtUtils {

    private static final long EXPIRE_TIME = 12 * 60 * 60 * 1000;

    private static final String CLAIM_NAME = "username";

    private static final String SECRET = "secret001";

    public static String createToken(String username) {
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        Algorithm algorithm = Algorithm.HMAC256(SECRET + username);
        return JWT.create()
                .withClaim(CLAIM_NAME, username)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    public static boolean verify(String username, String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET + username);
        JWTVerifier jwtVerifier = JWT.require(algorithm)
                .withClaim(CLAIM_NAME, username).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }

    public static String getUserName(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(CLAIM_NAME).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

}
