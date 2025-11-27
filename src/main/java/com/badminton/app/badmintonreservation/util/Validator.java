package com.badminton.app.badmintonreservation.util;

import java.time.LocalDate;
import java.time.LocalTime;

public class Validator {

    public static boolean isEmpty(String text) {
        return text == null || text.trim().isEmpty();
    }

    public static boolean tanggalTidakDiMasaLalu(LocalDate tgl) {
        return tgl.isBefore(LocalDate.now());
    }

    public static boolean jamValid(LocalTime start, LocalTime end) {
        return start.isBefore(end);
    }
}


