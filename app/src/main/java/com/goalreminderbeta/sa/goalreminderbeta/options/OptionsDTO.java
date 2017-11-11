package com.goalreminderbeta.sa.goalreminderbeta.options;

import com.orm.SugarRecord;

public class OptionsDTO extends SugarRecord  {

    private boolean soundConfig = true; // по умолчанию звук вкл

    public OptionsDTO(boolean soundConfig) {
        this.soundConfig = soundConfig;
    }

    public OptionsDTO() {
    }

    public boolean getSoundConfig() {
        return soundConfig;
    }

    public void setSoundConfig(boolean soundConfig) {
        this.soundConfig = soundConfig;
    }
}
