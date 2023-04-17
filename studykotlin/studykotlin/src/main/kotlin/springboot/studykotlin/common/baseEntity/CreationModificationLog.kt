package springboot.studykotlin.common.baseEntity

import lombok.Getter
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@MappedSuperclass
@Getter
@EntityListeners(value = [AuditingEntityListener::class])
open class CreationModificationLog {

    // 생성된 시간
    @CreatedDate
    @Column(updatable = false) // 변경되지 않게한다.
    private val createdAt: LocalDateTime? = null

    // 업데이트 된 시간
    @LastModifiedDate
    @Column
    private val updatedAt: LocalDateTime? = null

}