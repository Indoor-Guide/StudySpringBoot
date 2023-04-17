package springboot.studykotlin.auth.token

import lombok.Builder
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.ToString

@ToString
@NoArgsConstructor
@Getter
@Builder
class Token {

    // Non-null로 선언한 프로퍼티를 선언과 동시에 초기화하지 않을 경우
    // Property must be initialized or be abstract와 같은 오류가 발생한다.
    // private var accessToken: String? = null
    private lateinit var accessToken: String
    private var refreshToken: String? = null

    // 만약 지역변수보다 적은 매개변수를 사용할 시, this를 사용해야 한다.
    // constructor(accessToken: String, refreshToken: String): this(accessToken){
    constructor(accessToken: String, refreshToken: String) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
    }

}