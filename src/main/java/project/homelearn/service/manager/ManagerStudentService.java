package project.homelearn.service.manager;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.homelearn.dto.manager.ManagerStudentDto;
import project.homelearn.dto.manager.StudentAddDto;
import project.homelearn.entity.curriculum.Curriculum;
import project.homelearn.entity.student.Student;
import project.homelearn.entity.user.EmailCode;
import project.homelearn.repository.curriculum.CurriculumRepository;
import project.homelearn.repository.user.EmailCodeRepository;
import project.homelearn.repository.user.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ManagerStudentService {

    private final EmailCodeService emailCodeService;

    private final StudentRepository studentRepository;
    private final EmailCodeRepository emailCodeRepository;
    private final CurriculumRepository curriculumRepository;

    //필터링 x : 전체 학생 조회
    public Page<ManagerStudentDto> getStudents(int size, int page){
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> studentPage = studentRepository.findAllByOrderByCreatedDateDesc(pageable);

        List<ManagerStudentDto> studentDto = getManagerStudentDto(studentPage);

        return new PageImpl<>(studentDto, pageable, studentPage.getTotalElements());
    }

    //필터링 o : 교육과정명 기준 학생 조회
    public Page<ManagerStudentDto> getStudentsWithCurriculumName(int size, int page, String curriculumName){
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> studentPage = studentRepository.findByCurriculumName(pageable, curriculumName);

        List<ManagerStudentDto> studentDto = getManagerStudentDto(studentPage);

        return new PageImpl<>(studentDto, pageable, studentPage.getTotalElements());
    }


    //필터링 o : 기수 + 교육과정명 기준 학생 조회
    public Page<ManagerStudentDto> getStudentsWithCurriculumNameAndCurriculumTh(int size, int page, String curriculumName, Long curriculumTh){
        Pageable pageable = PageRequest.of(page, size);
        Page<Student> studentPage = studentRepository.findByCurriculumThAndCurriculumName(pageable, curriculumName, curriculumTh);

        List<ManagerStudentDto> studentDto =getManagerStudentDto(studentPage);

        return new PageImpl<>(studentDto, pageable, studentPage.getTotalElements());
    }

    //학생 DTO 매핑 메소드
    private static List<ManagerStudentDto> getManagerStudentDto(Page<Student> studentPage) {
        return studentPage.stream()
                .map(student -> new ManagerStudentDto(
                        student.getName(),
                        student.getCurriculum().getTh(),
                        student.getCurriculum().getName(),
                        student.getPhone(),
                        student.getEmail()))
                .collect(Collectors.toList());
    }

    // 학생 등록 (상담 후)
    public boolean addStudent(StudentAddDto studentAddDto) throws MessagingException {
        String email = studentAddDto.getEmail();
        String code = emailCodeService.sendVerificationCode(email);
        Curriculum curriculum = curriculumRepository.findByFullName(studentAddDto.getCurriculumFullName());
        log.info("fullName = {}", studentAddDto.getCurriculumFullName());

        EmailCode emailCode = new EmailCode();
        emailCode.setName(studentAddDto.getName());
        emailCode.setEmail(email);
        emailCode.setCurriculum(curriculum);
        emailCode.setCode(code);
        emailCodeRepository.save(emailCode);
        return true;
    }
}