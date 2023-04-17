package springboot.studykotlin.common.baseEntity

import lombok.Getter
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@Getter
@EntityListeners(value = [AuditingEntityListener::class])
abstract class CreationLog {
    @CreatedDate
    @Column(updatable = false)
    private val createdAt: LocalDateTime? = null
}