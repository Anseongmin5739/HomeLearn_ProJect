package project.homelearn.entity.vote;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.homelearn.entity.user.User;

@Entity
@Getter @Setter
@Table(name = "student_vote")
public class StudentVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vote_content_id", nullable = false)
    private VoteContent voteContent;

    public StudentVote() {
    }

    public StudentVote(User user, VoteContent voteContent) {
        this.user = user;
        this.voteContent = voteContent;
    }
}