package com.timetraveling.utils.presentation;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class JSONUserExclusionStrategy implements ExclusionStrategy {

    @Override
    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        return "password".equalsIgnoreCase(fieldAttributes.getName())
                    || "profilePicture".equalsIgnoreCase(fieldAttributes.getName())
                    || "profilePictureType".equalsIgnoreCase(fieldAttributes.getName())
                    || "email".equalsIgnoreCase(fieldAttributes.getName())
                    || "admin".equalsIgnoreCase(fieldAttributes.getName());
    }

    @Override
    public boolean shouldSkipClass(Class clazz) {
        return false;
    }
}
