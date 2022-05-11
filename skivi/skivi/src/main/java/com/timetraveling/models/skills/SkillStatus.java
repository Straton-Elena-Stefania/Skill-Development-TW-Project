package com.timetraveling.models.skills;

import com.google.gson.annotations.SerializedName;

public enum SkillStatus {
    @SerializedName("0")
    UNACTIVATED,

    @SerializedName("1")
    ACTIVATED,

    @SerializedName("2")
    FAVOURITE,
}
