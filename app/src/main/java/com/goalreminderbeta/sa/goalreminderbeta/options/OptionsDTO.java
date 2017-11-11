package com.goalreminderbeta.sa.goalreminderbeta.options;

import com.orm.SugarRecord;

/**
 * Created by stas0 on 10.11.2017.
 */

public class OptionsDTO extends SugarRecord{

    private boolean correct = true; // по умолчанию звук вкл

    public OptionsDTO(boolean correct) {
        this.correct = correct;
    }

    public OptionsDTO() {

    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    @Override
    public String toString() {
        return "OptionsDTO{" +
                "correct=" + correct +
                '}';
    }
}
