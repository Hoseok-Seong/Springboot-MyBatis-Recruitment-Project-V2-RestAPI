package shop.mtcoding.job.dto.bookmark;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookmartRespDto {
    private int id;
    private int userId;
    private int recruitmentId;
    private Timestamp createdAt;
}
