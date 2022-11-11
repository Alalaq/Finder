package ru.kpfu.itis.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Alisher Khabibullin (Alalaq) <alisher.khabibullin@gmail.com>
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Like {
    private long idOfUser;
    private Long idOfUserWhoLiked;
}
