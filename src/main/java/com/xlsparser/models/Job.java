package com.xlsparser.models;

import javax.persistence.*;
import java.util.Set;


@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String status;


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    @OneToMany(mappedBy = "job")
    private Set<Section> sections;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }

    public Set<Section> getSections() {
        return sections;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", file=" + file +
                ", sections=" + sections +
                '}';
    }
}
