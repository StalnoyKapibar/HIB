package com.project.transliterate;

import com.ibm.icu.text.Transliterator;
import org.springframework.stereotype.Component;

@Component
public class TransliterateImp implements Transliterate {

    @Override
    public String transliterate(String text) {
        return Transliterator
                .getInstance("Any-Latin; Latin-ASCII")
                .transliterate(text)
                .replaceAll("\"|\'", "");
    }
}
