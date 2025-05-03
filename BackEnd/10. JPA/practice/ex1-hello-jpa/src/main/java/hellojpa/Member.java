package hellojpa;

import jakarta.persistence.*;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "name")
    private String username;

    public Member() {

    }

}
