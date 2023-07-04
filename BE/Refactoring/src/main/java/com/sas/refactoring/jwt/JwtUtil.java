package com.sas.refactoring.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtUtil {

    private static final String SECRET_KEY = "insertYourSecretKeyinsertYourSecretKeyinsertYourSecretKey"; //시크릿 키 설정

    //Access Token 생성
    public String createAccessToken(Long memberId) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plus(30, ChronoUnit.MINUTES);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = expirationTime.atZone(zoneId);
        Date expirationDate = Date.from(zonedDateTime.toInstant());
        return createToken(memberId, "access-token", expirationDate);
    }

    //Refresh Token 생성
    public String createRefreshToken(Long memberId) {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plus(1, ChronoUnit.DAYS);
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = expirationTime.atZone(zoneId);
        Date expirationDate = Date.from(zonedDateTime.toInstant());
        return createToken(memberId, "refresh-token", expirationDate);
    }

    //JWT 토큰 생성 후 리턴
    public static String createToken(Long memberId, String subject, Date date) {
        //토큰 중복 or 만료 여부
//        Member member = memberService.getMemberById(memberId).orElseThrow(
//                () -> new UnAuthenticationException(CustomExceptionStatus.AUTHENTICATION_MEMBER_IS_NULL));

        //1. 헤더 객체 생성. 헤더에는 필수적인 정보만 들어갈 것.
        Map<String, Object> headerMap = new HashMap<String, Object>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        //2. 클레임 객체 생성 : JWT의 페이로드에 저장될 클레임(토큰에 저장할 사용자 정보 혹은 기타 데이터.. )을 설정하는데 사용
//        Claims claims = Jwts.claims().setSubject(subject); 이런 방식도 가능
        Map<String, Object> claims = new HashMap<String, Object>();
        claims.put("name", "이름");
        claims.put("id", "id들어감");


        //3. Jwts.builder를 사용해 빌더 객체를 리턴.
        //리턴된 값은 FE쪽으로 처리됨
        return Jwts.builder()
                .setHeader(headerMap) //1번의 헤더맵 설정 - header
                .setClaims(claims) //2번의 클레임을 설정 - messagebody, claim
                .setExpiration(date) //시간 설정
                .setSubject(subject)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) //시크릿 키 활용, 서명 생성
                .compact(); //JWT를 생성하고 문자열로 반환
    }

    public static boolean validateToken(String token) {
        try{
            Jwts.parser().setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token); // parseClaimsJws 메서드를 사용해 토큰을 파싱합니다.
            log.info("올바른 토큰입니다.");
            return true;
        }
        catch (Exception e) {
            log.info("올바르지 않은 토큰입니다");
            return false;
        }
    }
}
