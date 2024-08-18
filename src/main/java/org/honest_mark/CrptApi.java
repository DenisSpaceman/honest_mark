package org.honest_mark;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.RateLimiter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class CrptApi {

    private final String apiURL;
    private final RateLimiter rateLimiter;

    public CrptApi(String apiURL, byte timeUnit, int requestLimit) {
        this.apiURL = apiURL;
        this.rateLimiter = RateLimiter.create(requestLimit / (timeUnit / 1000.0));
    }

    public void getDocument(Object document, String sign){
        rateLimiter.acquire();
        try (CloseableHttpClient client = HttpClients.createDefault()){
            HttpPost httpPost = new HttpPost(apiURL);
            httpPost.setHeader("Content-Type", "application/json");

            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            String json = ow.writeValueAsString(document);

            String requestBody = String.format("{ \"product_document\": \"%s\", \"document_format\": \"MANUAL\", \"type\": \"LP_INTRODUCE_GOODS\", \"signature\": \"%s\" }", json, sign);
            StringEntity stringEntity = new StringEntity(requestBody);

            httpPost.setEntity(stringEntity);

            try (CloseableHttpResponse response = client.execute(httpPost)) {

                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200){
                    System.out.println("Document has been created");
                } else {
                    System.out.println("Error. Status code: " + statusCode);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

class Document {
    private Description description;
    private String doc_id;
    private String doc_status;
    private String doc_type;
    private boolean importRequest;
    private String owner_inn;
    private String participant_inn;
    private String producer_inn;
    private String production_date;
    private String production_type;
    private List<Product> products;
    private String reg_date;
    private String reg_number;

    public Document(Description description, String doc_id, String doc_status, String doc_type,
                    boolean importRequest, String owner_inn, String participant_inn,
                    String producer_inn, String production_date, String production_type,
                    List<Product> products, String reg_date, String reg_number){
        this.description = description;
        this.doc_id = doc_id;
        this.doc_status = doc_status;
        this.doc_type = doc_type;
        this.importRequest = importRequest;
        this.owner_inn = owner_inn;
        this.participant_inn = participant_inn;
        this.producer_inn = producer_inn;
        this.production_date = production_date;
        this.production_type = production_type;
        this.products = products;
        this.reg_date = reg_date;
        this.reg_number = reg_number;
    }

    public Description getDescription() {
        return description;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public String getDoc_status() {
        return doc_status;
    }

    public String getDoc_type() {
        return doc_type;
    }

    public boolean isImportRequest() {
        return importRequest;
    }

    public String getOwner_inn() {
        return owner_inn;
    }

    public String getParticipant_inn() {
        return participant_inn;
    }

    public String getProducer_inn() {
        return producer_inn;
    }

    public String getProduction_date() {
        return production_date;
    }

    public String getProduction_type() {
        return production_type;
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getReg_date() {
        return reg_date;
    }

    public String getReg_number() {
        return reg_number;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public void setDoc_status(String doc_status) {
        this.doc_status = doc_status;
    }

    public void setDoc_type(String doc_type) {
        this.doc_type = doc_type;
    }

    public void setImportRequest(boolean importRequest) {
        this.importRequest = importRequest;
    }

    public void setOwner_inn(String owner_inn) {
        this.owner_inn = owner_inn;
    }

    public void setParticipant_inn(String participant_inn) {
        this.participant_inn = participant_inn;
    }

    public void setProducer_inn(String producer_inn) {
        this.producer_inn = producer_inn;
    }

    public void setProduction_date(String production_date) {
        this.production_date = production_date;
    }

    public void setProduction_type(String production_type) {
        this.production_type = production_type;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setReg_date(String reg_date) {
        this.reg_date = reg_date;
    }

    public void setReg_number(String reg_number) {
        this.reg_number = reg_number;
    }
}

class Description {
    private String participantInn;

    public Description(String participantInn){
        this.participantInn = participantInn;
    }

    public String getParticipantInn() {
        return participantInn;
    }

    public void setParticipantInn(String participantInn) {
        this.participantInn = participantInn;
    }
}

class Product{
    private String certificate_document;
    private String certificate_document_date;
    private String certificate_document_number;
    private String owner_inn;
    private String producer_inn;
    private String production_date;
    private String tnved_code;
    private String uit_code;
    private String uitu_code;

    public Product(String certificate_document, String certificate_document_date, String certificate_document_number,
                   String owner_inn, String producer_inn, String production_date, String tnved_code, String uit_code,
                   String uitu_code) {
        this.certificate_document = certificate_document;
        this.certificate_document_date = certificate_document_date;
        this.certificate_document_number = certificate_document_number;
        this.owner_inn = owner_inn;
        this.producer_inn = producer_inn;
        this.production_date = production_date;
        this.tnved_code = tnved_code;
        this.uit_code = uit_code;
        this.uitu_code = uitu_code;
    }

    public String getCertificate_document() {
        return certificate_document;
    }

    public String getCertificate_document_date() {
        return certificate_document_date;
    }

    public String getCertificate_document_number() {
        return certificate_document_number;
    }

    public String getOwner_inn() {
        return owner_inn;
    }

    public String getProducer_inn() {
        return producer_inn;
    }

    public String getProduction_date() {
        return production_date;
    }

    public String getTnved_code() {
        return tnved_code;
    }

    public String getUit_code() {
        return uit_code;
    }

    public String getUitu_code() {
        return uitu_code;
    }

    public void setCertificate_document(String certificate_document) {
        this.certificate_document = certificate_document;
    }

    public void setCertificate_document_date(String certificate_document_date) {
        this.certificate_document_date = certificate_document_date;
    }

    public void setCertificate_document_number(String certificate_document_number) {
        this.certificate_document_number = certificate_document_number;
    }

    public void setOwner_inn(String owner_inn) {
        this.owner_inn = owner_inn;
    }

    public void setProducer_inn(String producer_inn) {
        this.producer_inn = producer_inn;
    }

    public void setProduction_date(String production_date) {
        this.production_date = production_date;
    }

    public void setTnved_code(String tnved_code) {
        this.tnved_code = tnved_code;
    }

    public void setUit_code(String uit_code) {
        this.uit_code = uit_code;
    }

    public void setUitu_code(String uitu_code) {
        this.uitu_code = uitu_code;
    }
}
