package springboot.studykotlin.common.exception.auth

import lombok.AllArgsConstructor
import lombok.Getter
import org.springframework.http.HttpStatus
import springboot.studykotlin.common.exception.ErrorCode

@Getter
@AllArgsConstructor
enum class AuthErrorCode(private val status: HttpStatus, private val message: String) : ErrorCode{

    // ErrorCode 저장한 enum 클래스

    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다."),
    INVALID_USER_TOKEN(HttpStatus.BAD_REQUEST, "사용자의 토큰정보와 일치하지 않습니다."),
    NOT_EXPIRED(HttpStatus.BAD_REQUEST, "만료되지 않은 토큰입니다.");

    override fun getStatus(): HttpStatus  = status
    override fun getMessage(): String = message



}