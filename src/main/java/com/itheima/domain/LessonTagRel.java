package com.itheima.domain;
import lombok.Data;
import java.util.Date;

@Data
public class LessonTagRel {
        private Integer id;
        private String tag;
        private Integer lessonId;
        private Date createdAt;
}

