package project.homelearn.service.manager;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.homelearn.dto.manager.CurriculumAddDto;
import project.homelearn.entity.curriculum.Curriculum;
import project.homelearn.entity.curriculum.CurriculumType;
import project.homelearn.repository.curriculum.CurriculumRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ManagerService {

    private final CurriculumRepository curriculumRepository;

    public boolean addCurriculum(CurriculumAddDto curriculumAddDto) {
        try {
            CurriculumType type = curriculumAddDto.getType();
            Long count = curriculumRepository.findCountByType(type);
            log.info("count = {}", count);

            Curriculum curriculum = new Curriculum();
            Long th = count + 1;
            curriculum.setTh(th);
            curriculum.setColor(curriculumAddDto.getColor());
            curriculum.setStartDate(curriculumAddDto.getStartDate());
            curriculum.setEndDate(curriculumAddDto.getEndDate());
            curriculum.setType(type);

            if (type.equals(CurriculumType.NCP)) {
                curriculum.setName("네이버 클라우드 데브옵스 " + th + "기");
            } else {
                curriculum.setName("AWS " + th + "기");
            }

            curriculumRepository.save(curriculum);
            return true;

        } catch (Exception e) {
            log.error("Error adding curriculum", e);
            return false;
        }
    }
}