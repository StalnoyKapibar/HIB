package com.project.util;

import com.project.service.abstraction.StorageService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;

@Component
public class FirstStartUtils extends FileSystemUtils implements ApplicationRunner {

    private final Environment environment;
    private final StorageService storageService;

    public FirstStartUtils(Environment environment, StorageService storageService) {
        this.environment = environment;
        this.storageService = storageService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        flagCheck();
        clearTempPicsOnStart();
    }

    public void copyCatalog() throws IOException {
        FileSystemUtils.copyRecursively(new File("./imgtestbook/"), new File("./img/"));
    }

    public void flagCheck() throws IOException {
        if (environment.getProperty("spring.application.imagemode").equals("copy")) {
            copyCatalog();
            System.out.println("Каталог с изображениями скопирован в соответствии с application.properties: imagemode");
        } else {
            System.out.println("Каталог изображений не скопирован в соответствии с application.properties: imagemode");
        }

        if (environment.getProperty("spring.application.excelmode").equals("create")) {
            new File("export/orders/").mkdirs();
            System.out.println("Каталог для отчётов создан в соответствии с application.properties: excelmode");
        } else {
            System.out.println("Каталог для отчётов не создан в соответствии с application.properties: excelmode");
        }
    }

    public void clearTempPicsOnStart() {
        if (environment.getProperty("spring.application.tempPicsMode").equals("clear")) {
            storageService.clearAllTempPics();
            System.out.println("Папка временных изображений очищена в соответствии с application.properties: tempPicsMode");
        } else {
            System.out.println("Папка временных изображений не очищалась в соответствии с application.properties: tempPicsMode");
        }
    }

}