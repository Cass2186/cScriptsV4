package scripts.Data;

import lombok.Getter;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class Rotations {

    @Getter
    public static int rotation = 0;


    public static void setRotation() {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        int currentTimeMins = (now.getHour() * 60) + now.getMinute();
        switch (currentTimeMins % 16) {
            case (0):
                rotation = 4;
                break;
            case (1):
                rotation = 2;
                break;
            case (2):
                rotation = 9;
                break;
            case (3):
                rotation = 11;
                break;
            case (4):
                rotation = 13;
                break;
            case (5):
                rotation = 1;
                break;
            case (6):
                rotation = 6;
                break;
            case (7):
                rotation = 15;
                break;
            case (8):
                rotation = 10;
                break;
            case (9):
                rotation = 8;
                break;
            case (10):
                rotation = 5;
                break;
            case (11):
                rotation = 3;
                break;
            case (12):
                rotation = 12;
                break;
            case (13):
                rotation = 14;
                break;
            case (14):
                rotation = 7;
                break;
            case (15):
                rotation = 4;
                break;
        }

    }
}
