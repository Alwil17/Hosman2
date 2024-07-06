package com.dopediatrie.hosman.bm.utils;

import com.dopediatrie.hosman.bm.payload.request.FormeRequest;
import com.dopediatrie.hosman.bm.payload.request.NameRequest;
import com.dopediatrie.hosman.bm.payload.request.PosologieRequest;
import com.dopediatrie.hosman.bm.payload.response.*;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utils {
    public static List<NameRequest> turnToList(String a){
        List<NameRequest> result = new ArrayList<>();
        if(a != null && !a.isBlank()){
            String styleStr = a;
            styleStr = styleStr.substring(0, styleStr.length() - 1);
            styleStr = styleStr.substring(1);
            String[] indiqList = styleStr.split(",");

            for (String b : indiqList) {
                NameRequest nameRequest = NameRequest.builder()
                        .libelle(b).build();
                result.add(nameRequest);
            }
        }

        return result;
    }

    public static List<FormeRequest> turnToListF(String a){
        List<FormeRequest> result = new ArrayList<>();
        if(a != null && !a.isBlank()){
            String styleStr = a;
            styleStr = styleStr.substring(0, styleStr.length() - 1);
            styleStr = styleStr.substring(1);
            String[] indiqList = styleStr.split(";");
            for (String b : indiqList) {
                String[] finalList = b.split(":");
                FormeRequest nameRequest = FormeRequest.builder()
                        .presentation(finalList[0])
                        .build();
                if(finalList.length >= 2) nameRequest.setDosage(finalList[1]);
                if(finalList.length >= 3) nameRequest.setConditionnement(finalList[2]);
                if(finalList.length >= 4) {
                    if(finalList[3].matches("^[a-zA-Z]*$")){
                        nameRequest.setPrix(0);
                    }else if(finalList[3].contains("e") || finalList[3].contains("E")){
                        nameRequest.setPrix(0);
                    }else {
                        nameRequest.setPrix(Double.parseDouble(finalList[3]));
                    }
                }

                result.add(nameRequest);
            }
        }

        return result;
    }

    public static List<PosologieRequest> turnToListP(String a, String type){
        List<PosologieRequest> result = new ArrayList<>();
        if(a != null && !a.isBlank()){
            String styleStr = a;
            styleStr = styleStr.substring(0, styleStr.length() - 1);
            styleStr = styleStr.substring(1);
            String[] indiqList = styleStr.split(",");

            for (String b : indiqList) {
                PosologieRequest nameRequest = PosologieRequest.builder()
                        .libelle(b)
                        .type(type).build();
                result.add(nameRequest);
            }
        }
        return result;
    }

    public static String convertActesListToString(List<ActeResponse> elements, String compactChar) {
        if(elements != null && elements.size() > 0){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < elements.size(); i++) {
                sb.append(elements.get(i).getLibelle().toString());
                if (i != elements.size() - 1) {
                    sb.append(compactChar);
                }
            }
            return sb.toString();

        }else {
            return "";
        }

    }

    public static String convertDiagnosticsListToString(List<DiagnosticResponse> elements, String compactChar) {
        if(elements != null && elements.size() > 0){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < elements.size(); i++) {
                sb.append(elements.get(i).getTitle().toString());
                if (i != elements.size() - 1) {
                    sb.append(compactChar);
                }
            }
            return sb.toString();

        }else {
            return "";
        }

    }
    public static String convertMotifsListToString(List<MotifResponse> elements, String compactChar) {
        if(elements != null && elements.size() > 0){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < elements.size(); i++) {
                sb.append(elements.get(i).getLibelle().toString());
                if (i != elements.size() - 1) {
                    sb.append(compactChar);
                }
            }
            return sb.toString();

        }else {
            return "";
        }

    }


}
