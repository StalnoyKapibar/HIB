package com.project.config.initializer;

import com.project.model.FormLoginErrorMessage;
import com.project.model.LocaleString;
import com.project.service.abstraction.FormLoginErrorMessageService;
import org.springframework.beans.factory.annotation.Autowired;

public class ErrorMessageDataInit {

    /*@Autowired
    FormLoginErrorMessageService messageService;

    public void init() {
        //1
        FormLoginErrorMessage errorMessage1 = new FormLoginErrorMessage();
        errorMessage1.setField("login");
        errorMessage1.setReason("Size");
        errorMessage1.setBody(new LocaleString(
                "Логин должен содержать от 5 до 32 символов",
                "The login must contain between 5 and 32 characters",
                "Le champ doit contenir entre 5 et 32 caractères",
                "Il campo deve contenere da 5 a 32 caratteri",
                "Das Feld muss zwischen 5 und 32 Zeichen lang sein",
                "Pole musí obsahovat 5 až 32 znaků",
                "Το πεδίο πρέπει να περιέχει από 5 έως 32 χαρακτήρες"));
        addErrorMessage(errorMessage1);
        //2
        FormLoginErrorMessage errorMessage2 = new FormLoginErrorMessage();
        errorMessage2.setField("login");
        errorMessage2.setReason("Pattern");
        errorMessage2.setBody(new LocaleString(
                "Логин не должен содержать специальные символы и пробелы",
                "The username must not contain special characters or spaces",
                "La connexion ne doit pas contenir de caractères spéciaux ni d'espaces",
                "Il login non deve contenere caratteri speciali e spazi",
                "Der Login darf keine Sonderzeichen oder Leerzeichen enthalten",
                "Přihlášení nesmí obsahovat speciální znaky a mezery",
                "Το όνομα χρήστη δεν πρέπει να περιέχει ειδικούς χαρακτήρες και κενά"));
        addErrorMessage(errorMessage2);
        //3
        FormLoginErrorMessage errorMessage3 = new FormLoginErrorMessage();
        errorMessage3.setField("login");
        errorMessage3.setReason("UIndex");
        errorMessage3.setBody(new LocaleString(
                "Такой логин уже существует",
                "This username already exists",
                "Un tel login existe déjà",
                "Tale accesso esiste già",
                "Ein solcher Login existiert bereits",
                "Takové přihlášení již existuje",
                "Είναι το όνομα χρήστη υπάρχει ήδη"));
        addErrorMessage(errorMessage3);
        //4
        FormLoginErrorMessage errorMessage4 = new FormLoginErrorMessage();
        errorMessage4.setField("password");
        errorMessage4.setReason("Pattern");
        errorMessage4.setBody(new LocaleString(
                "Пароль должен содержать числа, символы в верхнем и нижнем ригистрах, без пробела",
                "The password must contain numbers and characters in the upper and lower registers, without spaces",
                "Le mot de passe doit contenir des nombres, des caractères en haut et en bas, sans espace",
                "La password deve contenere numeri, caratteri in alto e in basso, senza spazi",
                "Das Passwort muss zahlen, Zeichen in den oberen und unteren rigistern ohne Leerzeichen enthalten",
                "Heslo musí obsahovat čísla, znaky v horní a dolní části rigistry, bez mezery",
                "Ο κωδικός πρόσβασης πρέπει να περιέχει αριθμούς, σύμβολα στην επάνω και κάτω ригистрах, χωρίς κενό"));
        addErrorMessage(errorMessage4);
        //5
        FormLoginErrorMessage errorMessage5 = new FormLoginErrorMessage();
        errorMessage5.setField("password");
        errorMessage5.setReason("Size");
        errorMessage5.setBody(new LocaleString(
                "Пароль должен быть в диапазоне от 8 до 64",
                "The password must be between 8 and 64",
                "Le mot de passe doit être compris entre 8 et 64",
                "La password deve essere compresa tra 8 e 64",
                "Das Kennwort muss zwischen 8 und 64 liegen",
                "Heslo musí být v rozmezí 8 až 64",
                "Ο κωδικός πρόσβασης πρέπει να είναι από 8 έως 64"));
        addErrorMessage(errorMessage5);
        //6
        FormLoginErrorMessage errorMessage6 = new FormLoginErrorMessage();
        errorMessage6.setField("password");
        errorMessage6.setReason("DontMatch");
        errorMessage6.setBody(new LocaleString(
                "Пароли не совпадают",
                "Passwords don 't match",
                "Les mots de passe ne correspondent pas",
                "Le password non corrispondono",
                "Passwörter Stimmen nicht überein",
                "Hesla se neshodují",
                "Οι κωδικοί πρόσβασης δεν ταιριάζουν"));
        addErrorMessage(errorMessage6);
        //7
        FormLoginErrorMessage errorMessage7 = new FormLoginErrorMessage();
        errorMessage7.setField("email");
        errorMessage7.setReason("Pattern");
        errorMessage7.setBody(new LocaleString(
                "Введен некорректный email",
                "Invalid email address entered",
                "Email incorrect entré",
                "Inserito email non corretta",
                "Falsche E-Mail eingegeben",
                "Zadán nesprávný e-mail",
                "Ορίστηκε λάθος email"));
        addErrorMessage(errorMessage7);
        //8
        FormLoginErrorMessage errorMessage8 = new FormLoginErrorMessage();
        errorMessage8.setField("email");
        errorMessage8.setReason("UIndex");
        errorMessage8.setBody(new LocaleString(
                "Такой email существует",
                "This email exists",
                "Un tel email existe",
                "Tale email esiste",
                "Eine solche E-Mail existiert",
                "Takový e-mail existuje",
                "Είναι η email υπάρχει"));
        addErrorMessage(errorMessage8);
        //9
        FormLoginErrorMessage errorMessage9 = new FormLoginErrorMessage();
        errorMessage9.setField("auth");
        errorMessage9.setReason("BadCredential");
        errorMessage9.setBody(new LocaleString(
                "Пароль неверный",
                "The password is incorrect",
                "Mot de passe incorrect",
                "Password non valida",
                "Passwort falsch",
                "Heslo je neplatné",
                "Κωδικός πρόσβασης είναι λάθος"));
        addErrorMessage(errorMessage9);
        //10
        FormLoginErrorMessage errorMessage10 = new FormLoginErrorMessage();
        errorMessage10.setField("auth");
        errorMessage10.setReason("NoValuePresent");
        errorMessage10.setBody(new LocaleString(
                "Такого логина не существует",
                "This username does not exist",
                "Un tel login n'existe pas",
                "Tale accesso non esiste",
                "Eine solche Anmeldung existiert nicht",
                "Takové přihlášení neexistuje",
                "Μια τέτοια σύνδεση δεν υπάρχει"));
        addErrorMessage(errorMessage10);
    }

    private void addErrorMessage(FormLoginErrorMessage errorMessage) {
        messageService.saveErrorMessage(errorMessage);
    }*/
}
