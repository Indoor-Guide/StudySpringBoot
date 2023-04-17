package springboot.studykotlin.common.exception.auth

import springboot.studykotlin.common.exception.CustomException
import springboot.studykotlin.common.exception.ErrorCode

class InvalidTokenException: CustomException{
    companion object{
        private val CODE = AuthErrorCode.INVALID_TOKEN
    }

    constructor(errorCode: AuthErrorCode) : super(errorCode)

    constructor() : this(CODE)
}