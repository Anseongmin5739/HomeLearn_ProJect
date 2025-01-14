package project.homelearn.entity.notification.teacher;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.homelearn.entity.board.QuestionBoard;
import project.homelearn.entity.board.comment.QuestionBoardComment;
import project.homelearn.entity.curriculum.Curriculum;
import project.homelearn.entity.inquiry.ManagerInquiry;
import project.homelearn.entity.inquiry.TeacherInquiry;

@Entity
@Getter @Setter
@Table(name = "teacher_notification")
public class TeacherNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curriculum_id", nullable = false)
    private Curriculum curriculum;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeacherNotificationType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionBoard questionBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_comment_id")
    private QuestionBoardComment questionBoardComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_inquiry_id")
    private TeacherInquiry teacherInquiry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_inquiry_id")
    private ManagerInquiry managerInquiry;
}