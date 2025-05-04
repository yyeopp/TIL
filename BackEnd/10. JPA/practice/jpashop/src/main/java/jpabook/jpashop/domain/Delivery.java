package jpabook.jpashop.domain;

import jakarta.persistence.*;

@Entity
public class Delivery  extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "DELIVERY_ID")
    private Long id;

    private String city;
    private String street;
    private String zipcode;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;
}
