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
    private String[] cellName = new String[]{"№ Order", "Date Order", "Status Order", "Cost", "Comment", "First Name", "Last Name", "Telephone", "E-mail", "Book"};


    public void createFileExcel(OrderDao orderDao) {
        this.fileExcel = new XSSFWorkbook();
        this.sheet = fileExcel.createSheet("Orders");
        listData = orderDao.findAll();
        Row row = sheet.createRow(0);
        for (int i = 0; i < cellName.length; i++) {
            row.createCell(i).setCellValue(cellName[i]);
        }
    }

    public void setSheetData() {
        int indexRow = 1;
        for (Order order : listData) {
            Row row = sheet.createRow(indexRow);
            for (int i = 0; i <= 9; i++) {
                row.createCell(i);
            }

            if (order.getId() != null) {
                row.getCell(0).setCellValue(order.getId());
            }

            if (order.getData() != 0L) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(order.getData() * 1000);
                String dateOrder = simpleDateFormat.format(date);
                row.getCell(1).setCellValue(dateOrder);
            }
            if (order.getStatus().name() != null) {
                row.getCell(2).setCellValue(order.getStatus().name());
            }
            if (order.getItemsCost() != null) {
                row.getCell(3).setCellValue(order.getItemsCost());
            }
            if (order.getComment() != null) {
                row.getCell(4).setCellValue(order.getComment());
            }
            if (order.getUserAccount().getFirstName() != null) {
                row.getCell(5).setCellValue(order.getUserAccount().getFirstName());
            }
            if (order.getUserAccount().getLastName() != null) {
                row.getCell(6).setCellValue(order.getUserAccount().getLastName());
            }
            if (order.getUserAccount().getPhone() != null) {
                row.getCell(7).setCellValue(order.getUserAccount().getPhone());
            }
            if (order.getUserAccount().getEmail() != null) {
                row.getCell(8).setCellValue(order.getUserAccount().getEmail());
            }
            if (order.getItems() != null) {
                String bookName = "// ";
                for (CartItem items : order.getItems()) {
                    bookName += items.getBook().getName().getEn() + "// ";
                }
                row.getCell(9).setCellValue(bookName);
            }
            indexRow++;
        }
        for (int i = 0; i <= cellName.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    public void saveExcelProject() {
        try (FileOutputStream out = new FileOutputStream(new File("export/orders/exportsSales.xlsx"))) {
            fileExcel.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createFileToServer(OrderDao orderDao) {
        createFileExcel(orderDao);
        setSheetData();
        saveExcelProject();
    }
}
