package com.dailySync.user.entities;

import com.dailySync.user.dto.UserInfoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSetting {

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn (name = "user_id")
    private User user;

    @Column
    private Integer weight;
    @Column
    private Integer height;
    @Column
    private Integer recommendCal;
    @Column
    private String darkMode;

    public UserSetting(User user) {
        this.user = user;
    }

    public void update(UserInfoDto updateInfo) {
        this.weight = updateInfo.getWeight();
        this.height = updateInfo.getHeight();
        this.recommendCal = updateInfo.getRecommendCal();
    }
}
