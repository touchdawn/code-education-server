package com.itheima.util;

import com.itheima.domain.User;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

public class JwtUtil {
    private static long time = 1000*60*60*24;
    private static String signature = "admin";
    public static String createToken(User user){
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
            //header
            .setHeaderParam("typ", "JWT")
            .setHeaderParam("alg","HS256")
            //payload
            .claim("userEmail",user.getEmail())
            .claim("role",user.getType())
            .setSubject("admin-test")
            .setExpiration(new Date(System.currentTimeMillis() +time))
            .setId(UUID.randomUUID().toString())
            //signature
            .signWith(SignatureAlgorithm.HS256,signature)
            .compact();
        System.out.println(jwtToken);
        return jwtToken;
    }

    public static boolean checkToken(String token) {
        //if (token == null){
        //    return false;
        //}
        //    try {
        //        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
        //        System.out.println(claimsJws);
        //
        //    } catch (NullPointerException | IllegalArgumentException | MalformedJwtException e) {
        //       return false;
        //    }
        //    return true;
        //}
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
        return true;
    }
}
