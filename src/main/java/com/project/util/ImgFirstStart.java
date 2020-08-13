package com.project.util;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;


@Component
public class ImgFirstStart extends FileSystemUtils implements ApplicationRunner {

    private final Environment environment;

    public ImgFirstStart(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        flagCheck();
    }

    public void copyCatalog() throws IOException {
        FileSystemUtils.copyRecursively(new File("./imgtestbook/"), new File("./img/"));
    }

    public void flagCheck() throws IOException {
        if (environment.getProperty("spring.application.imagemode").equals("copy")) {
            copyCatalog();
            System.out.println("Каталог с изображениями скопирован в соответствии с application.properties imagemode");
        } else {
            System.out.println("Каталог изображений не скопирован в соответствии с application.properties imagemode");
        }
    }

}
