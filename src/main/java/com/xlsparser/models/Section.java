package com.xlsparser.models;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;

    @OneToMany(mappedBy = "section")
    private Set<GeoClass> geologicalClasses;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setGeologicalClasses(Set<GeoClass> geologicalClasses) {
        this.geologicalClasses = geologicalClasses;
    }

    public Set<GeoClass> getGeologicalClasses() {
        return geologicalClasses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Job getJob() {
        return job;
    }

    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", geologicalClasses:" + geologicalClasses +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return Objects.equals(id, section.id) &&
                Objects.equals(name, section.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, job, geologicalClasses);
    }
}
