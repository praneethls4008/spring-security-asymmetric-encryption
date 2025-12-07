package com.learning.spring_security_asymmetric_encryption.security;

import com.learning.spring_security_asymmetric_encryption.exception.BusinessException;
import com.learning.spring_security_asymmetric_encryption.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String TOKEN_TYPE = "token_type";
    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    @Value("${application.security.jwt.access-token.expiration}")
    private long accessTokenExpiration;

    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshTokenExpiration;

    public JwtService() throws Exception {
        this.privateKey = KeyUtils.loadPrivateKey("keys/local-only/private_key.pem");
        this.publicKey = KeyUtils.loadPublicKey("keys/local-only/public_key.pem");
    }

    public String generateAccessToken(final String username){
        final Map<String, Object> claims = new HashMap<>();
        claims.put(TOKEN_TYPE, "ACCESS_TOKEN");
        return buildToken(username, claims, this.accessTokenExpiration);
    }

    public String generateRefreshToken(final String username){
        final Map<String, Object> claims = new HashMap<>();
        claims.put(TOKEN_TYPE, "REFRESH_TOKEN");
        return buildToken(username, claims, this.refreshTokenExpiration);
    }

    private String buildToken(
            final String username,
            final Map<String, Object> claims,
            final long expiration){
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(this.privateKey)
                .compact();
    }


    private Claims extractClaims(final String token){
        try{
            return Jwts.parser()
                    .verifyWith(this.publicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (final JwtException e){
            throw new BusinessException(ErrorCode.JWT_INVALID, e.getMessage());
        }
    }

    public String extractUsername(final String token){
        return extractClaims(token).getSubject();
    }

    public boolean isTokenValid(final String token, final String expectedUsername){
        final String username = extractUsername(token);
        return (username.equals(expectedUsername) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token){
        return extractExpiryDate(token).before(new Date());
    }

    public Date extractExpiryDate(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }

    public String refreshAccessToken(final String refreshToken) {
        final Claims claims = extractClaims(refreshToken);
        if (!"REFRESH_TOKEN".equals(claims.get(TOKEN_TYPE))) {
            throw new BusinessException(ErrorCode.REFRESH_TOKEN_TYPE_ERROR, claims.get(TOKEN_TYPE));
        }
        if (isTokenExpired(refreshToken)) {
            throw new BusinessException(ErrorCode.REFRESH_TOKEN_EXPIRED, claims.get(TOKEN_TYPE));
        }
        final String username = claims.getSubject();
        return generateAccessToken(username);
    }



}
