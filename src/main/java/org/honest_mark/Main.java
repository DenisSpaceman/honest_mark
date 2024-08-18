package org.honest_mark;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args){
        String apiUrl = "https://ismp.crpt.ru/api/v3/lk/documents/create";
        byte timeUnit = 1;
        int requstLimit = 10;
        CrptApi crptApi = new CrptApi(apiUrl, timeUnit, requstLimit);

        Description description = new Description("0001000");
        Product product1 = new Product("Cert", "2020-12-12",
                "1234567", "00200", "00100",
                "2019-10-14", "777", "555", "333");
        Product product2 = new Product("Cert", "2020-12-12",
                "1234567", "00200", "00100",
                "2019-10-14", "777", "555", "333");

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        Document document = new Document(description, "Milk", "1234", "cool", true,
                "0001", "0002", "0003", "2024-08-18",
                "Food", products, "1999-12-12", "123123");

        String signature = "Ivanov I.I.";

        crptApi.getDocument(document, signature);
    }

}
