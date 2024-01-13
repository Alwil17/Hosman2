package com.dopediatrie.hosman.bm.payload;

import com.dopediatrie.hosman.bm.payload.response.CodeInfo;
import com.dopediatrie.hosman.bm.payload.response.DiagnosticResponse;
import com.dopediatrie.hosman.bm.payload.response.ISearchResult;
import com.dopediatrie.hosman.bm.payload.response.LinearizationEntity;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Log4j2
public class ICDAPIclient {



    private final String TOKEN_ENPOINT = "https://icdaccessmanagement.who.int/connect/token";
    private final String CLIENT_ID = "59de2dc9-6f53-4ee7-a691-59cdc4bbc8fe_e373d933-70dd-48d8-8f61-89c028688933";
    private final String CLIENT_SECRET = "jzloh2219Zmnw1FhxZt50jIFLnxxYaSQemEMLdVHLDE=";
    private final String SCOPE = "icdapi_access";
    private final String GRANT_TYPE = "client_credentials";


    /*public static void main(String[] args) throws Exception {

        String uri = "https://id.who.int/icd/entity";

        ICDAPIclient api = new ICDAPIclient();
        String token = api.getToken();
        System.out.println("URI Response JSON : \n" + api.getURI(token, uri));
    }*/


    // get the OAUTH2 token
    public String getToken() throws Exception {

        System.out.println("Getting token...");

        URL url = new URL(TOKEN_ENPOINT);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("POST");

        // set parameters to post
        String urlParameters =
                "client_id=" + URLEncoder.encode(CLIENT_ID, "UTF-8") +
                        "&client_secret=" + URLEncoder.encode(CLIENT_SECRET, "UTF-8") +
                        "&scope=" + URLEncoder.encode(SCOPE, "UTF-8") +
                        "&grant_type=" + URLEncoder.encode(GRANT_TYPE, "UTF-8");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        // response
        int responseCode = con.getResponseCode();
        System.out.println("Token Response Code : " + responseCode + "\n");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // parse JSON response
        JSONObject jsonObj = new JSONObject(response.toString());
        return jsonObj.getString("access_token");
    }


    // access ICD API
    public String getURI(String token, String uri) throws Exception {

        System.out.println("Getting URI...");

        URL url = new URL(uri);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        // HTTP header fields to set
        con.setRequestProperty("Authorization", "Bearer "+token);
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Accept-Language", "fr");
        con.setRequestProperty("API-Version", "v2");

        // response
        int responseCode = con.getResponseCode();
        System.out.println("URI Response Code : " + responseCode + "\n");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    // access ICD API
    public ISearchResult search(String token, String uri, String word) throws Exception {
        String searchUri = uri+"/search?highlightingEnabled=false&flatResults=true&q="+word;

        WebClient webClient = WebClient.create();
        ISearchResult result =  webClient.get()
                .uri(searchUri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+token)
                .header(HttpHeaders.ACCEPT_LANGUAGE, "fr")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("API-Version", "v2")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ISearchResult.class)
                .block();
        return result;
    }

    // get code infos
    public CodeInfo getCodeInfo(String token, String uri, String code) throws Exception {
        String searchUri = uri+"/codeinfo/"+code;

        WebClient webClient = WebClient.create();
        CodeInfo result =  webClient.get()
                .uri(searchUri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+token)
                .header(HttpHeaders.ACCEPT_LANGUAGE, "fr")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("API-Version", "v2")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(CodeInfo.class)
                .block();
        return result;
    }

    // get code infos
    public DiagnosticResponse getCodeDetails(String token, String url, CodeInfo code) throws Exception {
        String urlSepared = code.getStemId().split("&")[0];
        String searchString = urlSepared.split("mms/")[1];
        String searchUri = url+"/"+searchString;
        log.info("cod response: {}", searchUri);
        WebClient webClient = WebClient.create();
        LinearizationEntity result =  webClient.get()
                .uri(searchUri)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "+token)
                .header(HttpHeaders.ACCEPT_LANGUAGE, "fr")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("API-Version", "v2")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(LinearizationEntity.class)
                .block();
        DiagnosticResponse diagnosticResponse = new DiagnosticResponse();
        if(result != null) {
            diagnosticResponse.setTheCode(result.getCode());
            diagnosticResponse.setTitle(result.getTitle().getValue());
            diagnosticResponse.setStemId(result.getId());
            diagnosticResponse.setId(result.getId());
            if(result.getDefinition() != null)
                diagnosticResponse.setDescription(result.getDefinition().getValue());
        }
        return diagnosticResponse;
    }
}