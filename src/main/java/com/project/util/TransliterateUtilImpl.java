package com.project.util;

import com.ibm.icu.text.Transliterator;
import org.springframework.stereotype.Component;

@Component
public class TransliterateUtilImpl implements TransliterateUtil {

    @Override
    public String transliterate(String text) {
        return Transliterator
                .getInstance("Any-Latin; Latin-ASCII")
                .transliterate(text)
                .replaceAll("\"|\'", "");
    }
}
