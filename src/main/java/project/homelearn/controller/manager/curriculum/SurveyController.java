package project.homelearn.controller.manager.curriculum;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.homelearn.service.manager.ManagerCurriculumService;

@Slf4j
@RestController
@RequestMapping("/managers/manage-curriculums")
@RequiredArgsConstructor
public class SurveyController {

    private final ManagerCurriculumService managerCurriculumService;

    // 설문조사 시작 - 학생에게 알림보내야 함
    @PostMapping("/survey-start/{id}")
    public ResponseEntity<?> startSurvey(@PathVariable("id") Long id) {
        boolean result = managerCurriculumService.startSurveyProcess(id);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // 설문조사 마감
    @PostMapping("/survey-stop/{id}")
    public ResponseEntity<?> stopSurvey(@PathVariable("id") Long id) {
        boolean result = managerCurriculumService.stopSurveyProcess(id);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}