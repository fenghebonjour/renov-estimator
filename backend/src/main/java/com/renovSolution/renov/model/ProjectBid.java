package com.renovSolution.renov.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.SEQUENCE;


@Entity(name="ProjectBid")
@Table(name="project_bid")
public class ProjectBid implements Serializable {

    @Id
    @SequenceGenerator(
            name="projectBid_sequence",
            sequenceName = "projectBid_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "projectBid_sequence"
    )

    @Column(
            name="project_bid_id",
            nullable = false,
            updatable = false
    )
    private Long id;

    @Column(
            name="request_date",
            nullable = false,
            columnDefinition = "DATE"
    )
    private LocalDate requestDate;

    @Column(
            name="deadline",
            nullable = false,
            columnDefinition = "DATE"
    )
    private LocalDate deadline;

    @Column(
            name="work_start_date",
            nullable = false,
            columnDefinition = "DATE"
    )
    private LocalDate workStartDate;

    @Column(
            name="work_end_date",
            nullable = false,
            columnDefinition = "DATE"
    )
    private LocalDate workEndDate;

    @Column(
            name="status",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String status;

    @Column(
            name="type",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String type;

    @ManyToOne
    @JoinColumn(
            name="user_id",
            referencedColumnName = "user_id",
            foreignKey = @ForeignKey(
                    name="project_bid_client_fk"
            )
    )
    @JsonBackReference(value="client-projectBid")
    private Client client;

    @OneToMany(
            mappedBy = "projectBid",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            fetch = FetchType.LAZY
    )
    @JsonManagedReference(value="projectBid-serviceOffer")
    private List<ServiceOffer> serviceOffers = new ArrayList<>();

    public ProjectBid() {
    }

    public ProjectBid(LocalDate requestDate, LocalDate deadline, LocalDate workStartDate,
                      LocalDate workEndDate, String status, String type) {
        this.requestDate = requestDate;
        this.deadline = deadline;
        this.workStartDate = workStartDate;
        this.workEndDate = workEndDate;
        this.status = status;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public LocalDate getWorkStartDate() {
        return workStartDate;
    }

    public void setWorkStartDate(LocalDate workStartDate) {
        this.workStartDate = workStartDate;
    }

    public LocalDate getWorkEndDate() {
        return workEndDate;
    }

    public void setWorkEndDate(LocalDate workEndDate) {
        this.workEndDate = workEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<ServiceOffer> getServiceOffers() {
        return serviceOffers;
    }

    public void addServiceOffer(ServiceOffer serviceOffer) {
        if (!serviceOffers.contains(serviceOffer)) {
            serviceOffers.add(serviceOffer);
            serviceOffer.setProjectBid(this);
        }
    }

    @Override
    public String toString() {
        return "ProjectBid{" +
                "id=" + id +
                ", requestDate=" + requestDate +
                ", deadline=" + deadline +
                ", workStartDate=" + workStartDate +
                ", workEndDate=" + workEndDate +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
