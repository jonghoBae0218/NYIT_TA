package com.bers.nyittutorassistant;

import java.io.Serializable;

public class QuestionData implements Serializable {
    public String title;
    public String tutorName;
    public String codes;
    public String description;
    public String category;

    public QuestionData(String title, String tutorName, String codes, String description, String category) {
        this.title = title;
        this.tutorName = tutorName;
        this.codes = codes;
        this.description = description;
        this.category = category;
    }
}
