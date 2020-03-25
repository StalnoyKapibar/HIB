package com.project.config.initializer;

import com.project.model.BookDTO;
import com.project.model.Image;
import com.project.model.LocaleString;
import com.project.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*
    Add to DB 20 test book
 */
public class TestDataInit {
    @Autowired
    BookService bookService;

    private int bookId;

    public void init() {
        File img = new File("img");
        File[] contentImg = img.listFiles();
        for (File file : contentImg) {
            deleteDir(file);
        }
        Path destDir = Paths.get("img");
        File imgTest = new File("imgtestbook");
        File[] listOfFiles = imgTest.listFiles();
        for (File file : listOfFiles) {
            try {
                FileSystemUtils.copyRecursively(file.toPath(), destDir.resolve(file.getName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BookDTO bookDTO1 = new BookDTO();
        bookDTO1.setAuthor(new LocaleString("Павел Астахов", "Pavel Astakhov", "Pavel Astakhov", "Pavel Astakhov", "Pavel Astakhov", "Pavel Astakhov", "Πάβελ Αστακόφ"));
        bookDTO1.setName(new LocaleString("Инвестор", "Investor", "Investisseur", "investitore", "Investeerder", "Investor", "Επενδυτής"));
        bookAddImgandAddBookToDb(bookDTO1);
        BookDTO bookDTO2 = new BookDTO();
        bookDTO2.setAuthor(new LocaleString("Татьяна Устинова", "Tatyana Ustinova", "Tatyana Ustinova", "Tatyana Ustinova", "Tatyana Ustinova", "Tatyana Ustinova", "Tatyana Ustinova"));
        bookDTO2.setName(new LocaleString("Серьга Артемиды", "Earring of Artemis", "Boucle d'oreille d'Artémis", "Orecchino di Artemide", "Ohrring von Artemis", "Náušnice Artemis", "Σκουλαρίκι της Άρτεμης"));
        bookAddImgandAddBookToDb(bookDTO2);
        BookDTO bookDTO3 = new BookDTO();
        bookDTO3.setAuthor(new LocaleString("Дарья Донцова", "Daria Dontsova", "Daria Dontsova", "Daria Dontsova", "Daria Dontsova", "Daria Dontsová", "Ντάρια Ντόντσοβα"));
        bookDTO3.setName(new LocaleString("Большой куш нищей герцогини", "Big jackpot to the poor duchess", "Gros jackpot pour la pauvre duchesse", "Grande jackpot alla povera duchessa", "Großer Jackpot für die arme Herzogin", "Velký jackpot pro chudé vévodkyně", "Μεγάλο τζάκποτ στην κακή δούκισσα"));
        bookAddImgandAddBookToDb(bookDTO3);
        BookDTO bookDTO4 = new BookDTO();
        bookDTO4.setAuthor(new LocaleString("Дэвид Кушнер", "David Kushner", "David Kushner", "David Kushner", "David Kushner", "David Kushner", "Ντέιβιντ Κουσνέρ"));
        bookDTO4.setName(new LocaleString("Повелители DOOM", "Overlords of doom", "Overlords of doom", "Signori della rovina", "Oberherren des Untergangs", "Vládci zkázy", "Υπερβολείς της μοίρας"));
        bookAddImgandAddBookToDb(bookDTO4);
        BookDTO bookDTO5 = new BookDTO();
        bookDTO5.setAuthor(new LocaleString("Мария Воронова", "Maria Voronova", "Maria Voronova", "Maria Voronova", "Maria Voronova", "Maria Voronová", "Μαρία Βορονόβα"));
        bookDTO5.setName(new LocaleString("Сама виновата", "You are the one to blame", "C'est à blâmer", "È da incolpare", "Es ist schuld", "Je to vina", "Είναι φταίξιμο"));
        bookAddImgandAddBookToDb(bookDTO5);
        BookDTO bookDTO6 = new BookDTO();
        bookDTO6.setAuthor(new LocaleString("Анна Матвеева", "Anna Matveeva", "Anna Matveeva", "Anna Matveeva", "Anna Matveeva", "Anna Matveeva", "Άννα Ματβέεβα"));
        bookDTO6.setName(new LocaleString("Лолотта и другие парижские истории", "Lolotte and other Parisian stories", "Lolotte et autres histoires parisiennes", "Lolotte e altre storie parigine", "Lolotte und andere Pariser Geschichten", "Lolotte a další pařížské příběhy", "Lolotte και άλλες παριζιάνικες ιστορίες"));
        bookAddImgandAddBookToDb(bookDTO6);
        BookDTO bookDTO7 = new BookDTO();
        bookDTO7.setAuthor(new LocaleString("Гузель Яхина", "Guzel Yakhina", "Guzel Yakhina", "Guzel Yakhina", "Guzel Yakhina", "Guzel Yakhina", "Guzel Yakhina"));
        bookDTO7.setName(new LocaleString("Зулейха открывает глаза", "Zuleikha opens her eyes", "Zuleikha ouvre les yeux", "Zuleikha apre gli occhi", "Zuleikha öffnet die Augen", "Zuleikha otevře oči", "Η Zuleikha ανοίγει τα μάτια της"));
        bookAddImgandAddBookToDb(bookDTO7);
        BookDTO bookDTO8 = new BookDTO();
        bookDTO8.setAuthor(new LocaleString("Джордж Р. Р. Мартин", "George R. R. Martin", "George R. R. Martin", "George R. R. Martin", "George R. R. Martin", "George R. R. Martin", "George R. R. Martin"));
        bookDTO8.setName(new LocaleString("Пламя и кровь. Кровь драконов", "Flames and blood. Dragon blood", "Flammes et sang. Sang de dragon", "Fiamme e sangue. Sangue di drago", "Flammen und Blut. Drachenblut", "Plameny a krev. Dračí krev", "Φλόγες και αίμα. Δράκος του αίματος"));
        bookAddImgandAddBookToDb(bookDTO8);
        BookDTO bookDTO9 = new BookDTO();
        bookDTO9.setAuthor(new LocaleString("Анджей Сапковский", "Andrzej Sapkowski", "Andrzej Sapkowski", "Andrzej Sapkowski", "Andrzej Sapkowski", "Andrzej Sapkowski", "Andrzej Sapkowski"));
        bookDTO9.setName(new LocaleString("Меч Предназначения", "Destination Sword", "Épée de destination", "Spada di destinazione", "Zielschwert", "Cílový meč", "Destination Sword"));
        bookAddImgandAddBookToDb(bookDTO9);
        BookDTO bookDTO10 = new BookDTO();
        bookDTO10.setAuthor(new LocaleString("Елизавета Соболянская", "Elizabeth Sobolyanskaya", "Elizabeth Sobolyanskaya", "Elizabeth Sobolyanskaya", "Elizabeth Sobolyanskaya", "Elizabeth Sobolyanskaya", "Elizabeth Sobolyanskaya"));
        bookDTO10.setName(new LocaleString("Зануда в Академии Драконов", "Bore at the Dragon Academy", "Alésage à la Dragon Academy", "Alesato alla Dragon Academy", "Langeweile in der Drachenakademie", "Narodil se na Dragon Academy", "Βρέθηκε στην Ακαδημία Dragon"));
        bookAddImgandAddBookToDb(bookDTO10);
        BookDTO bookDTO11 = new BookDTO();
        bookDTO11.setAuthor(new LocaleString("Джон Маррс", "John Marrs", "John Marrs", "John Marrs", "John Marrs", "John Marrs", "John Marrs"));
        bookDTO11.setName(new LocaleString("Единственный", "The One", "Le seul", "L'unico", "Das einzige", "Jediné", "Το μόνο"));
        bookAddImgandAddBookToDb(bookDTO11);
        BookDTO bookDTO12 = new BookDTO();
        bookDTO12.setAuthor(new LocaleString("Татьяна Устинова", "Tatyana Ustinova", "Tatyana Ustinova", "Tatyana Ustinova", "Tatyana Ustinova", "Tatyana Ustinova", "Tatyana Ustinova"));
        bookDTO12.setName(new LocaleString("Призрак Канта", "Ghost of Kant", "Fantôme de Kant", "Ghost of Kant", "Geist von Kant", "Duch Kant", "Φάντασμα του Καντ"));
        bookAddImgandAddBookToDb(bookDTO12);
        BookDTO bookDTO13 = new BookDTO();
        bookDTO13.setAuthor(new LocaleString("Надежда Волгина", "Nadezhda Volgina", "Nadezhda Volgina", "Nadezhda Volgina", "Nadezhda Volgina", "Nadezhda Volgina", "Nadezhda Volgina"));
        bookDTO13.setName(new LocaleString("Страсть в искупление", "Passion for Atonement", "Passion pour l'expiation", "Passione per l'espiazione", "Leidenschaft für das Sühnopfer", "Vášeň pro usmíření", "Πάθος για Εξιλέωση"));
        bookAddImgandAddBookToDb(bookDTO13);
        BookDTO bookDTO14 = new BookDTO();
        bookDTO14.setAuthor(new LocaleString("Вячеслав Кумин", "Vyacheslav Kumin", "Vyacheslav Kumin", "Vyacheslav Kumin", "Vyacheslav Kumin", "Vyacheslav Kumin", "Βιτσάσελαβ Κουμιν"));
        bookDTO14.setName(new LocaleString("Прыжок Феникса", "Phoenix Jump", "Phoenix Jump", "Phoenix Jump", "Phoenix Jump", "Phoenix Jump", "Φοίνιξ άλμα"));
        bookAddImgandAddBookToDb(bookDTO14);
        BookDTO bookDTO15 = new BookDTO();
        bookDTO15.setAuthor(new LocaleString("Альбина Уральская", "Albina Uralskaya", "Albina Uralskaya", "Albina Uralskaya", "Albina Uralskaya", "Albina Uralskaya", "Albina Uralskaya"));
        bookDTO15.setName(new LocaleString("Чемодан, портал, Земля – Екатеринбург!", "Suitcase, portal, Earth - Yekaterinburg!", "Valise, portail, Terre - Iekaterinbourg!", "Valigia, portale, Terra - Ekaterinburg!", "Koffer, Portal, Erde - Jekaterinburg!", "Kufr, portál, Země - Jekatěrinburg!", "Βαλίτσα, πύλη, Γη - Εκατερίνμπουργκ!"));
        bookAddImgandAddBookToDb(bookDTO15);
        BookDTO bookDTO16 = new BookDTO();
        bookDTO16.setAuthor(new LocaleString("Роман Злотников, Антон Краснов", "Roman Zlotnikov, Anton Krasnov", "Roman Zlotnikov, Anton Krasnov", "Roman Zlotnikov, Anton Krasnov", "Roman Zlotnikov, Anton Krasnov", "Roman Zlotnikov, Anton Krasnov", "Ρωμαίος Zlotnikov, Anton Krasnov"));
        bookDTO16.setName(new LocaleString("Псевдоним бога", "Alias of god", "Alias de Dieu", "Alias di dio", "Alias Gottes", "Alias boha", "Ψευδώνυμο του Θεού"));
        bookAddImgandAddBookToDb(bookDTO16);
        BookDTO bookDTO17 = new BookDTO();
        bookDTO17.setAuthor(new LocaleString("Владимир Колычев", "Vladimir Kolychev", "Vladimir Kolychev", "Vladimir Kolychev", "Vladimir Kolychev", "Vladimir Kolychev", "Vladimir Kolychev"));
        bookDTO17.setName(new LocaleString("Вечность и еще два дня", "Eternity and two more days", "Éternité et deux jours de plus", "Eternità e altri due giorni", "Ewigkeit und noch zwei Tage", "Věčnost a další dva dny", "Eternity και δύο ακόμη ημέρες"));
        bookAddImgandAddBookToDb(bookDTO17);
        BookDTO bookDTO18 = new BookDTO();
        bookDTO18.setAuthor(new LocaleString("Елена Лапышева", "Elena Lapysheva", "Elena Lapysheva", "Elena Lapysheva", "Elena Lapysheva", "Elena Lapysheva", "Έλενα Λαπίσεβα"));
        bookDTO18.setName(new LocaleString("Я хочу тебя! Идеальное тело. Секреты кремлевского тренера", "I want you! Perfect body. Secrets of the Kremlin coach", "Je te veux! Le corps parfait. Les secrets de l'entraîneur du Kremlin", "Ti voglio! Il corpo perfetto I segreti dell'allenatore del Cremlino", "Ich will dich! Der perfekte Körper. Geheimnisse des Kreml-Trainers", "Chci tě! Dokonalé tělo. Tajemství trenéra Kremlu", "Σας θέλω! Το τέλειο σώμα. Τα μυστικά του προπονητή του Κρεμλίνου"));
        bookAddImgandAddBookToDb(bookDTO18);
        BookDTO bookDTO19 = new BookDTO();
        bookDTO19.setAuthor(new LocaleString("Геннадий Кибардин", "Gennady Kibardin", "Gennady Kibardin", "Gennady Kibardin", "Gennady Kibardin", "Gennady Kibardin", "Γκενάντι Κιμπαρντίν"));
        bookDTO19.setName(new LocaleString("Быстрая ходьба лечит", "Brisk walking heals", "La marche rapide guérit", "La camminata veloce guarisce", "Zügiges Gehen heilt", "Rychle se léčí", "Το βιαστικό περπάτημα θεραπεύει"));
        bookAddImgandAddBookToDb(bookDTO19);
        BookDTO bookDTO20 = new BookDTO();
        bookDTO20.setAuthor(new LocaleString("Стивен Кинг", "Stephen King", "Stephen King", "Stephen King", "Stephen King", "Stephen King", "Στέφανος βασιλιάς"));
        bookDTO20.setName(new LocaleString("Противостояние", "Confrontation", "Confrontation", "", "Konfrontation", "Konfrontace", "Αντιπαράθεση"));
        bookAddImgandAddBookToDb(bookDTO20);
    }

    private void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (!Files.isSymbolicLink(f.toPath())) {
                    deleteDir(f);
                }
            }
        }
        file.delete();
    }

    private void bookAddImgandAddBookToDb(BookDTO bookDTO) {
        bookId++;
        File imgDir = new File("img/book" + bookId);
        String[] files = imgDir.list();
        bookDTO.setCoverImage(files[0]);
        List<Image> imagesList = new ArrayList<>();
        for (String imagne : files) {
            Image imgAdd = new Image();
            imgAdd.setNameImage(imagne);
            imagesList.add(imgAdd);
        }
        bookDTO.setImageList(imagesList);
        bookService.addBook(bookDTO);
    }
}