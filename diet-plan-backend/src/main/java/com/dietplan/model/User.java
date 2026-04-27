package com.dietplan.model;

import com.dietplan.model.enums.*;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Profile fields
    private Integer age;
    private Double weightKg;
    private Double heightCm;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private ActivityLevel activityLevel;

    /**
     * BMI = weight(kg) / (height(m))^2
     */
    @Transient
    public Double getBmi() {
        if (weightKg == null || heightCm == null) return null;
        double heightM = heightCm / 100.0;
        return Math.round((weightKg / (heightM * heightM)) * 10.0) / 10.0;
    }

    /**
     * Harris-Benedict equation + activity multiplier
     */
    @Transient
    public Integer getDailyCalorieTarget() {
        if (weightKg == null || heightCm == null || age == null || gender == null) return null;

        double bmr;
        if (gender == Gender.MALE) {
            bmr = 88.362 + (13.397 * weightKg) + (4.799 * heightCm) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * weightKg) + (3.098 * heightCm) - (4.330 * age);
        }

        double multiplier = 1.2; // default SEDENTARY
        if (activityLevel != null) {
            multiplier = switch (activityLevel) {
                case SEDENTARY       -> 1.2;
                case LIGHTLY_ACTIVE  -> 1.375;
                case MODERATELY_ACTIVE -> 1.55;
                case VERY_ACTIVE     -> 1.725;
                case EXTRA_ACTIVE    -> 1.9;
            };
        }
        return (int) Math.round(bmr * multiplier);
    }

    // UserDetails overrides
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired()    { return true; }
    @Override
    public boolean isAccountNonLocked()     { return true; }
    @Override
    public boolean isCredentialsNonExpired(){ return true; }
    @Override
    public boolean isEnabled()              { return true; }
}
