package com.project.service;

import com.ibm.icu.text.Transliterator;
import org.springframework.stereotype.Component;

@Component
public class TransliterateForBookImpl implements TransliterateForBook {

    @Override
    public String transliterate(String text) {
        return Transliterator
                .getInstance("Any-Latin; Latin-ASCII")
                .transliterate(text)
                .replaceAll("\"|\'", "");
    }
}
