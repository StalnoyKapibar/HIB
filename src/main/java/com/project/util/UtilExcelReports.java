package com.project.util;
import com.project.dao.abstraction.OrderDao;
import com.project.model.CartItem;
import com.project.model.Order;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class UtilExcelReports {

    private XSSFWorkbook fileExcel;
    private XSSFSheet sheet;
    private List<Order> listData;


    public void createFileExcel(OrderDao orderDao) {
        this.fileExcel = new XSSFWorkbook();
        this.sheet = fileExcel.createSheet("Orders");
        listData = orderDao.findAll();
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("Number Order");
        row.createCell(1).setCellValue("Date Order");
        row.createCell(2).setCellValue("Status Order");
        row.createCell(3).setCellValue("Cost");
        row.createCell(4).setCellValue("Comment");
        row.createCell(5).setCellValue("First Name");
        row.createCell(6).setCellValue("Last Name");
        row.createCell(7).setCellValue("Telephone");
        row.createCell(8).setCellValue("E-mail");
        row.createCell(9).setCellValue("Book");
    }

    public void setSheetData() {

        int indexRow = 1;
        for (Order order : listData) {

            Date date = new Date(order.getData() * 1000);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String bookName = "";
            for(CartItem items: order.getItems()){
                bookName += items.getBook().getName().getEn() + "//";
            }

            Row row = sheet.createRow(indexRow);
            row.createCell(0).setCellValue(order.getId());
            row.createCell(1).setCellValue(simpleDateFormat.format(date));
            row.createCell(2).setCellValue(order.getStatus().name());
            row.createCell(3).setCellValue(order.getItemsCost());
            row.createCell(4).setCellValue(order.getComment());
            row.createCell(5).setCellValue(order.getUserAccount().getFirstName());
            row.createCell(6).setCellValue(order.getUserAccount().getLastName());
            row.createCell(7).setCellValue(order.getUserAccount().getPhone());
            row.createCell(8).setCellValue(order.getUserAccount().getEmail());
            row.createCell(9).setCellValue(bookName);
            indexRow++;
        }
    }

    public void saveExcelProject() {
        try (FileOutputStream out = new FileOutputStream(new File("export/orders/exportsSales.xlsx"))) {
            fileExcel.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createFileToServer(OrderDao orderDao){
        createFileExcel(orderDao);
        setSheetData();
        saveExcelProject();
    }
}
