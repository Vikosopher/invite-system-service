package com.vik.invite_system_service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vik.invite_system_service.dto.AuthData;
import com.vik.invite_system_service.entity.User;
import com.vik.invite_system_service.util.KeyLoader;
import io.jsonwebtoken.*;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static String generateToken(User user) {
    try {
      // Load the private key using the KeyLoader class
      RSAPrivateKey privateKey = KeyLoader.loadPrivateKey("private_key.pem");
      Algorithm algorithm = Algorithm.RSA256(privateKey);
      String token =
              JWT.create()
                      .withSubject(String.valueOf(user.getId()))
                      .withIssuedAt(new Date())
                      .withExpiresAt(new Date(System.currentTimeMillis() + 60000))
                      .withIssuer("Vikash")
                      .withClaim("inviteId", String.valueOf(user.getId()))
                      .sign(algorithm);
      return token;
    } catch (Exception e) {
      throw new RuntimeException("Error generating JWT token", e);
    }
  }

  public String generateInviteToken(Long inviteId) {
    try {
      // Load the private key using the KeyLoader class
      RSAPrivateKey privateKey = KeyLoader.loadPrivateKey("private_key.pem");
      Algorithm algorithm = Algorithm.RSA256(privateKey);
      String token =
          JWT.create()
              .withSubject(String.valueOf(inviteId))
              .withIssuedAt(new Date())
              .withExpiresAt(new Date(System.currentTimeMillis() + 60000))
              .withIssuer("Vikash")
              .withClaim("inviteId", String.valueOf(inviteId))
              .sign(algorithm);
      return token;
    } catch (Exception e) {
      throw new RuntimeException("Error generating JWT token", e);
    }
  }

  public AuthData extractAllClaims(String token) {
    try {
      String[] parts = token.split("\\.");
      if (parts.length != 3) {
        throw new IllegalArgumentException("Invalid JWT token ");
      }

      String payload = new String(Base64.getDecoder().decode(parts[1]));

      return objectMapper.readValue(payload, AuthData.class);

    } catch (Exception e) {
      throw new RuntimeException("Failed to parse token", e);
    }
  }

  public AuthData verifyToken(String authToken) throws Exception {
    try {
      RSAPublicKey publicKey = KeyLoader.loadPublicKey("public_key.pem");
      Algorithm algorithm = Algorithm.RSA256(publicKey, null);

      JWTVerifier jwtTokenVerifier = JWT.require(algorithm).build();

      DecodedJWT verify = jwtTokenVerifier.verify(authToken);
      byte[] decode = Base64.getDecoder().decode(verify.getPayload());
      var decodedPayload = new String(decode);

      return objectMapper.readValue(decodedPayload, AuthData.class);
    } catch (JWTVerificationException e) {
      if (e instanceof TokenExpiredException) {
        throw new TokenExpiredException("Token expired", Instant.now());
      }
      throw new RuntimeException(e);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
