package project.homelearn.repository.user;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import project.homelearn.dto.manager.manage.curriculum.CurriculumBasicDto;
import project.homelearn.dto.manager.manage.student.SpecificStudentDto;
import project.homelearn.entity.student.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // 필터링 x : 전체 학생 조회
    Page<Student> findAllByOrderByCreatedDateDesc(Pageable pageable);

    // 필터링 o : 교육과정명 기준 학생 조회
    @Query("SELECT s FROM Student s JOIN FETCH s.curriculum c WHERE c.name = :curriculumName")
    Page<Student> findByCurriculumName(Pageable pageable, @Param("curriculumName") String curriculumName);

    // 필터링 o : 기수 + 교육과정명 기준 학생 조회
    @Query("SELECT s FROM Student s JOIN FETCH s.curriculum c WHERE c.th = :curriculumTh AND c.name = :curriculumName")
    Page<Student> findByCurriculumThAndCurriculumName(Pageable pageable, @Param("curriculumName") String curriculumName, @Param("curriculumTh") Long curriculumTh);

    // 설문 시작 후 해당 커리큘럼의 모든 학생의 설문 완료 상태를 false로 변경
    @Modifying
    @Transactional
    @Query("update Student s set s.surveyCompleted = false where s.curriculum.id = :curriculumId")
    void updateSurveyCompletedFalse(@Param("curriculumId") Long curriculumId);

    @Query("select new project.homelearn.dto.manager.manage.student.SpecificStudentDto(s.name, s.email, s.phone, s.gender) from Student s where s.id =:studentId")
    SpecificStudentDto findSpecificStudentDto(@Param("studentId") Long studentId);

    @Query("select new project.homelearn.dto.manager.manage.curriculum.CurriculumBasicDto(c.name, c.th, c.startDate, c.endDate) " +
            "from Student s join fetch Curriculum c on s.curriculum.id = c.id " +
            "where s.id = :studentId")
    CurriculumBasicDto findStudentCurriculum(@Param("studentId") Long studentId);
}