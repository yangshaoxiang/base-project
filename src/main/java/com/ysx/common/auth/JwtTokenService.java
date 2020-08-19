package com.ysx.common.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 *  @Description: jwt Token 生成服务
 * @author ysx
 */
@Service
public class JwtTokenService {
    @Resource
	private JwtAuthProperties jwtAuthProperties;

	/**
	 * 由字符串生成加密key
	 *
	 */
	private  SecretKey generalKey() {
		byte[] encodedKey = Base64Utils.decodeFromString(jwtAuthProperties.getSecretKey());
		return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
	}

	public  String createJwt(String id, String subject)  {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		SecretKey key = generalKey();
		JwtBuilder builder = Jwts.builder().setExpiration(new Date(  System.currentTimeMillis() +  jwtAuthProperties.getTtlMillis() )).setId(id).setIssuedAt(new Date()).setSubject(subject).signWith(signatureAlgorithm, key);
		return builder.compact();
	}


	Claims parseJwt(String jwt)  {
		SecretKey key = generalKey();
		return Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody();
	}

}
