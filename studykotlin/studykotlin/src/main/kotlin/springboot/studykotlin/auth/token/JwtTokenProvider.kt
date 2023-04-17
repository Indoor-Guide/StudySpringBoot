package springboot.studykotlin.auth.token


import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.SignatureException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import springboot.studykotlin.common.exception.auth.InvalidTokenException
import java.util.*
import javax.annotation.PostConstruct


@Component
class JwtTokenProvider(
        @Value("\${spring.jwt.secret}")
        private var secretKey: String
) {
    // 넘겨받는 데이터는 이와 같이 사용한다.
    // @Value("\${spring.jwt.secret}") 어노테이션 : spring.jwt.secret 값을 주입받기 위한 어노테이션
    // 5일 세션

    // java에서 static 전역변수와 같은 처리할 때 companion object를 사용한다.
    companion object {
        private const val accessToken = 1000L * 60L * 60L * 24L * 30L * 12L
        private const val refreshToken = 1000L * 60L * 60L * 24L * 30L * 12L
        private const val AUTHORITES_KEY = "role"
    }

    // 함수 선언할 때는 fun
    // secretKey encoding
    @PostConstruct
    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }


    // JWT 토큰 생성, accessToken과 refreshToken을 생성한다. (로그인 한 사용자에게 호출)
    public fun createToken(userId: String, role: String): Token {
        // Claims, 클라이언트에 대한 정보가 담겨있다.
        // iss, sub, aud, exp, nbf, iat, jti와 같은 기본 정보가 들어간다.
        val now = Date()
        val claims: Claims = Jwts.claims().setSubject(userId) // Jwt payload에 저장된 정보단위

        claims["sub"] = userId
        claims["role"] = role

        // Token 클래스
        // accessToken, refreshToken
        return Token(Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(Date(now.time + accessToken))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact(),
                Jwts.builder().setExpiration(Date(now.time + refreshToken))
                        .signWith(SignatureAlgorithm.HS256, secretKey)
                        .compact())

    }

    // 접근 가능한 토큰 발급
    public fun generateAccessToken(userId: String, role: String): String {
        val tokenPeriod = 1000L * 60L * 60L * 3L

        val claims: Claims = Jwts.claims().setSubject(userId)
        claims["sub"] = userId
        claims["role"] = role

        val now = Date()

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(Date(now.time + tokenPeriod))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact()
    }

    // access token의 유효성을 검증하고, 해당 액세스 토큰이 아직 유효한지 여부를 Boolean형식으로 반환하는 기능을 수행한다.
    fun verifyToken(token: String): Boolean {
        try {
            val claims: Jws<Claims> = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            return claims.body.expiration.after(Date())
        } catch (e: Exception) {
            return false
        }
    }

    // refresh token의 유효성을 검증하고, 만료 시간(expiration time)을 반환하는 기능을 수행
    fun verifyRefreshToken(token: String): Long {
        try {
            val claims: Jws<Claims> = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            return claims.body.expiration.time
        } catch (e: Exception) {
            throw InvalidTokenException()
        }
    }

    // 주어진 JWT 토큰이 만료되었는지 확인하고, 만료되었다면 false, 유효할 시 true
    fun checkTokenExpired(token: String): Boolean {
        try {
            val claims: Jws<Claims> = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            val time: Long = claims.body.expiration.time
            // expiration : JWT 토큰의 만료시간을 나타내는 필드

            return time > 1L
        } catch (e: Exception) {
            throw InvalidTokenException()
        }
    }


    fun getUserId(token: String): String{
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
    }

    fun getAuthentication(accessToken: String): Authentication{
        val claims:Claims = parseClaims(accessToken)

        if(claims.get(AUTHORITES_KEY) == null ) throw RuntimeException("권한 정보가 없는 토큰입니다.")

        val socialId: String = claims.subject
        val currentUserDetails: CurrentUserDetails
    }

    private fun parseClaims(accessToken: String): Claims {
        try{
            return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(accessToken).body
        }catch(e: ExpiredJwtException){
            throw InvalidTokenException()
        }catch(e: SignatureException){
            throw InvalidTokenException()
        }
    }
}