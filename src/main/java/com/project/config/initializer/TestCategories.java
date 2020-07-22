package com.project.config.initializer;

import com.project.model.Category;
import com.project.model.LocaleString;
import com.project.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

public class TestCategories {

    /*@Autowired
    CategoryService categoryService;

    public void init() {

        // ONLY NO PARENT
        Category history = new Category(new LocaleString("История", "History", "Récit", "Storia", "Geschichte", "Příběh", "Ιστορία"), null);
        Category documents = new Category(new LocaleString("Документы", "Documents", "Les documents", "Documenti", "Unterlagen", "Dokumenty", "Εγγραφα"), null);
        Category magazines = new Category(new LocaleString("Журналы", "Magazines", "Les magazines", "Riviste", "Zeitschriften", "Časopisy", "Περιοδικά"), null);
        Category culture = new Category(new LocaleString("Культура", "Culture", "Culture", "Cultura", "Kultur", "Kultura", "Πολιτισμός"), null);
        //History
        Category greek = new Category(new LocaleString("Греческий", "Greek", "Grec", "Greca", "griechisch" , "řecký", "Ελληνικά"), 1l); //Id 5
        Category greekWar = new Category(new LocaleString("Война", "War", "Guerre", "Guerra", "Krieg", "Válka",  "Πόλεμος"), 6l);
        Category forein = new Category(new LocaleString("Зарубежный", "Foreign", "Étranger", "Straniero", "Ausländisch", "zahraniční", "Ξένο"), 1l);
        Category russia = new Category(new LocaleString("Русский", "Russia", "Russie", "Russia", "Russland", "Rusko", "Ρωσία"), 7l);
        //Docs
        Category greekDocs = new Category(new LocaleString("Греческий", "Greek", "Grec", "Greca", "griechisch" , "řecký", "Ελληνικά"), 2l);
        Category foreinDoc = new Category(new LocaleString("Зарубежный", "Foreign", "Étranger", "Straniero", "Ausländisch", "zahraniční", "Ξένο"), 2l);
        //Magazines
        Category greekMagazines = new Category(new LocaleString("Греческий", "Greek", "Grec", "Greca", "griechisch" , "řecký", "Ελληνικά"), 3l);
        Category foreinMagazines = new Category(new LocaleString("Зарубежный", "Foreign", "Étranger", "Straniero", "Ausländisch", "zahraniční", "Ξένο"), 3l); //Id 10
        //Culture
        Category greekCulture = new Category(new LocaleString("Греческий", "Greek", "Grec", "Greca", "griechisch" , "řecký", "Ελληνικά"), 4l);
        Category foreinCulture = new Category(new LocaleString("Зарубежный", "Foreign", "Étranger", "Straniero", "Ausländisch", "zahraniční", "Ξένο"), 4l);
        Category russianCulture = new Category(new LocaleString("Русский", "Russia", "Russie", "Russia", "Russland", "Rusko", "Ρωσία"), 12l);
        Category germanCulture = new Category(new LocaleString("Германия", "Germany", "Allemagne", "Germania", "Deutschland", "Německo", "Γερμανία"),  12l);
        
        categoryService.addCategory(history);
        categoryService.addCategory(documents);
        categoryService.addCategory(magazines);
        categoryService.addCategory(culture);
        categoryService.addCategory(greek);
        categoryService.addCategory(greekWar);
        categoryService.addCategory(forein);
        categoryService.addCategory(russia);
        categoryService.addCategory(greekDocs);
        categoryService.addCategory(foreinDoc);
        categoryService.addCategory(greekMagazines);
        categoryService.addCategory(foreinMagazines);
        categoryService.addCategory(greekCulture);
        categoryService.addCategory(foreinCulture);
        categoryService.addCategory(russianCulture);
        categoryService.addCategory(germanCulture);

    }*/
}
