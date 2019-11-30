package com.pfa.fatboar.FatboarBack.models;

import javax.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int ticketNumber;

    private float value;

    /*
    * 0: ticket created on DB
    * 1: ticket validated by the client
    * 2: ticket validated by the restaurant employee */
    private int state;

    //@ManyToOne
    //@JoinColumn(name = "user_id", nullable = false)
    @Column(name = "user_id")
    private Long user;

    @OneToOne
    private Gain gain;

    public Ticket() {
    }

    public Ticket(int ticketNumber, float value, int state, Long user, Gain gain) {
        this.ticketNumber = ticketNumber;
        this.value = value;
        this.state = state;
        this.user = user;
        this.gain = gain;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Gain getGain() {
        return gain;
    }

    public void setGain(Gain gain) {
        this.gain = gain;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
