package project.homelearn.entity.manager;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.homelearn.entity.BaseEntity;

@Entity
@Getter @Setter
@Table(name = "manager_board")
public class ManagerBoard extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private boolean emergency;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_path")
    private String filePath;
}