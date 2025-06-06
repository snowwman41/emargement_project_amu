package com.projectIndustriel.server.entities;

import jakarta.persistence.*;
import com.projectIndustriel.server.utils.TimeUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public UUID id;
    public String sessionName;

    @ManyToOne
    @JoinColumn(name = "module_id")
    public Module module;
    public long startTime;
    public long endTime;
    public boolean active;

    @OneToMany(mappedBy = "session")
    public Set<Signature> signatures;

    public Session(String sessionName, Module module, long startTime, long endTime) {
        this.sessionName = sessionName;
        this.module = module;
        this.startTime = startTime;
        this.endTime = endTime;
        this.signatures = new HashSet<>();
        this.active = false;
    }

    public Session(){}

    private void setActive(boolean bool) {this.active = bool;}

    public void activate(){
        setActive(true);
    }

    public void deactivate(){
        setActive(false);
    }

//    public boolean validCode(SignatureDTO dto){
//        return verificationCode.equals(code);
//    }

//    public String getVerificationCode(){
//        //TODO only for testing purposis, should be deleted once functionality of class is verifid
//        return verificationCode;
//    }

    public boolean isOnDate(String date) {
        long[] dateEpoch = TimeUtils.convertToUnixTimestamps(date);
        return dateEpoch[0]<startTime && dateEpoch[1]>startTime;
    }
}
