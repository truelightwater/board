package com.example.borad.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AbstractMethodError.class)
@Getter
public class BaseEntity {

    @CreationTimestamp                  // 생성시 시간
    @Column(updatable = false)          // 수정시에는 관여하지 않는다.
    private LocalDateTime createdTime;

    @UpdateTimestamp                    // 수정시 시간
    @Column(insertable = false)         // 입력시에는 관여하지 않는다.
    private LocalDateTime updatedTime;

}
