package shedulerservice.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class Utils {


    public static final long EXPIRATION_TIME = 864000000; // 10 days

    @Value("${tokenSecret}")
    private String tokenSecret;

    public String generateSchedulerToken(String userId) {
        String token = Jwts.builder()
                .setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, tokenSecret
                )
                .compact();
        return token;
    }
}
