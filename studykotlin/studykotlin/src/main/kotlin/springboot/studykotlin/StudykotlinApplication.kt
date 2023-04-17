package springboot.studykotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import springboot.studykotlin.auth.token.JwtTokenProvider

@SpringBootApplication
class StudykotlinApplication

fun main(args: Array<String>) {
	runApplication<StudykotlinApplication>(*args)
}
