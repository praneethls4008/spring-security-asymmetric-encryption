package com.learning.spring_security_asymmetric_encryption.role;

import com.learning.spring_security_asymmetric_encryption.common.BaseEntity;
import com.learning.spring_security_asymmetric_encryption.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ROLES")
public class Role extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "roles")
    public List<User> users;
}
