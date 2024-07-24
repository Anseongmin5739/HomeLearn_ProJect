package project.homelearn.controller.manager;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.homelearn.dto.manager.manage.MangerTeacherDto;
import project.homelearn.dto.manager.enroll.TeacherEnrollDto;
import project.homelearn.service.manager.ManagerTeacherService;

@Slf4j
@RestController
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerTeacherController {

    private final ManagerTeacherService managerTeacherService;

    @GetMapping("/manage-teacher")
    public ResponseEntity<?> teacherList(@RequestParam(name = "page", defaultValue = "0") int page,
                                              @RequestParam(name = "curriculumName", required = false) String curriculumName,
                                              @RequestParam(name = "isAssigned", required = false, defaultValue = "true")boolean isAssigned){

        Page<MangerTeacherDto> teachers;
        if(curriculumName != null && !curriculumName.isEmpty()){
            teachers = managerTeacherService.getTeachersWithCurriculumName(15, page, curriculumName);
        }
        else{
            if(isAssigned){
                teachers = managerTeacherService.getTeachers(15, page);
            }
            else{
                teachers = managerTeacherService.getTeachersCurriculumIsNull(15, page);
            }
        }
        if(teachers != null && !teachers.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(teachers);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    // 강사 등록
    @PostMapping("/manage-teacher/enroll")
    public ResponseEntity<?> enrollTeacher(@Valid @RequestBody TeacherEnrollDto teacherEnrollDto) {
        boolean result = managerTeacherService.enrollTeacher(teacherEnrollDto);
        if (result) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
