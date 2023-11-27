package com.sparta.instaclone.domain.user;

public enum UserEnum {;

    private final String userName;

    UserEnum(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public static boolean contains(String userName) {
        for (UserEnum allowedUserName : UserEnum.values()) {
            if (allowedUserName.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }
}

