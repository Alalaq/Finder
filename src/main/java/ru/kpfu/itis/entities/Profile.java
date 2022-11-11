package ru.kpfu.itis.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Alisher Khabibullin (Alalaq) <alisher.khabibullin@gmail.com>
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Profile {
    private long id;
    private String name;
    private String city;
    private String sex;
    private Integer age;
    private String description;
    private List<String> hobbies;
    private Integer minAgePrefered;
    private Integer maxAgePrefered;
    private String sexPrefered;
    private String connect;
}
