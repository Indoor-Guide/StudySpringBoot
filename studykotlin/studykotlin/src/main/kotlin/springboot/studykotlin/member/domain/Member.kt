package springboot.studykotlin.member.domain

import lombok.AccessLevel
import lombok.Getter
import lombok.NoArgsConstructor
import springboot.studykotlin.common.baseEntity.CreationModificationLog
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // : 인자 없는 생성자를 생성하며, protected로 지정
class Member(
        @Id
        @GeneratedValue(generator = "uuidssafy")
        @Column(name = "member_id", columnDefinition = "uuid")
        private val id: UUID? = null,
        private var nickname: String? = null,
        private var socialId: String? = null,
        private var token: String? = null
) : CreationModificationLog() {
}