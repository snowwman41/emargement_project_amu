package com.projectIndustriel.server.entities;

import com.projectIndustriel.server.utils.CodeType;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "codes")
public class Code {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID codeId;
    private String qrCode;
    private String readableCode;
    @Column(unique = true)
    private String beaconId;
    @OneToOne
    @JoinColumn(name = "user_id")
    private Teacher teacher;

    public Code(){}

    public Code(String beaconId) {
        this.beaconId = (beaconId == null || beaconId.trim().isEmpty() ) ?
                generateBeaconId() : beaconId;
        updateCodes();
    }

    private String generateBeaconId() {
        //TODO verify that it doesnt already exist
        return UUID.randomUUID().toString();
    }

    public UUID getCodeId() {
        return codeId;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public String getBeaconId() {
        return beaconId;
    }

    public void updateCodes(){
        this.readableCode = String.valueOf((int)(Math.random() * 900000) + 100000);
        this.qrCode = String.valueOf((int)(Math.random() * 900000) + 100000);
    }

    public String getReadableCode() {
        return readableCode;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public boolean isValid(CodeType codeType, String code){
        if(codeType == CodeType.QR)
            return qrCode.equals(code);
        if(codeType == CodeType.BEACON)
            return beaconId.equals(code);
        System.out.println(readableCode.equals(code));
        return readableCode.equals(code);
    }
}