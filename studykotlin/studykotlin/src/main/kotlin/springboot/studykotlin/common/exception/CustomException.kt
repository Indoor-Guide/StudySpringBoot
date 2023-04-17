package springboot.studykotlin.common.exception

import lombok.AllArgsConstructor
import lombok.Getter


@Getter
@AllArgsConstructor
public open class CustomException(private val errorCode: ErrorCode): RuntimeException() {
}