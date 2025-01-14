package project.homelearn.entity.curriculum;

import lombok.Getter;

@Getter
public enum CurriculumType {
    NCP("네이버 클라우드 데브옵스 과정"), AWS("AWS 과정");

    private final String description;

    CurriculumType(String description) {
        this.description = description;
    }

}