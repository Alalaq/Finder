package ru.kpfu.itis.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class University {
    private Long id;
    private String title;
    private String city;

    @Override
    public String toString() {
        return title;
    }
}
