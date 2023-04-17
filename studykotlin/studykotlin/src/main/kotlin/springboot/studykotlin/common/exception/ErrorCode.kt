package springboot.studykotlin.common.exception

import org.springframework.http.HttpStatus

interface ErrorCode {

    // error 상태 확인하기 위해 사용한 메서드
    fun getStatus(): HttpStatus

    // 메세지 보낼 때 사용된 메서드
    fun getMessage(): String
}