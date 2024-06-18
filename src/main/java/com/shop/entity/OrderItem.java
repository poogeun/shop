package com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.time.LocalDateTime;
import java.util.Locale;

@Entity
@Getter
@Setter
public class OrderItem extends BaseEntity{
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //fetch = FetchType.LAZY 지연로딩
    @JoinColumn(name = "item_id") // 외래키
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id") // 외래키
    private Order order;

    private int orderPrice;
    private int count;
    //private LocalDateTime regTime;
    //private LocalDateTime updateTime;
}
