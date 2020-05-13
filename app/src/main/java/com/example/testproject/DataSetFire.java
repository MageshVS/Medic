package com.example.testproject;

public class DataSetFire {
    private String name;
    private String date;
    private String hdate;
    private String time;
    private String htime;
    private String problem;
    private String hospital_name;
    private String blood;

    public DataSetFire() {
        //Empty Constructor Needed
    }

    public DataSetFire(String name, String date,String time , String problem, String hospital_name,
                       String blood, String hdate, String htime) {
        this.name = name;
        this.date = date;
        this.problem = problem;
        this.time = time;
        this.hospital_name = hospital_name;
        this.blood = blood;
        this.hdate = hdate;
        this.htime = htime;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getProblem() {
        return problem;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public String getBlood() {
        return blood;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getHdate() {
        return hdate;
    }

    public String getHtime() {
        return htime;
    }

    public void setHdate(String hdate) {
        this.hdate = hdate;
    }

    public void setHtime(String htime) {
        this.htime = htime;
    }
}

