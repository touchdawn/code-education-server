package com.itheima.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HomeworkDoneDomain {
    private Integer homeworkId;
    private Integer studentId;
}
