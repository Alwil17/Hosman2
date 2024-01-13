package com.dopediatrie.hosman.secretariat.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.jsoup.parser.Parser;
import org.w3c.tidy.Tidy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

public class Utils {
    public static String xhtmlConvert(String html) throws UnsupportedEncodingException {
        Document doc = Jsoup.parse(html, "UTF-8", Parser.htmlParser());

        /*Tidy tidy = new Tidy();
        tidy.setInputEncoding("UTF-8");
        tidy.setOutputEncoding("UTF-8");
        tidy.setXHTML(true);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes("UTF-8"));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        tidy.parseDOM(inputStream, outputStream);
        return outputStream.toString("UTF-8");*/

        doc.outputSettings().prettyPrint(false);
        doc.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return doc.outerHtml();
    }

    public static String getQuery(String datemin, String datemax, String vue){
        String baseRequest = "SELECT a.libelle AS actec, \n" +
        "  SUM(\n" +
                "    CASE WHEN m.slug = 'especes' THEN 1 ELSE 0 END\n" +
                "  ) AS nb_especes, \n" +
                "  SUM(\n" +
                "    CASE WHEN m.slug = 'especes' THEN fm.montant ELSE 0 END\n" +
                "  ) AS total_especes, \n" +
                "  SUM(\n" +
                "    CASE WHEN m.slug = 'cheque' THEN 1 ELSE 0 END\n" +
                "  ) AS nb_cheque, \n" +
                "  SUM(\n" +
                "    CASE WHEN m.slug = 'cheque' THEN fm.montant ELSE 0 END\n" +
                "  ) AS total_cheque, \n" +
                "  SUM(\n" +
                "    CASE WHEN m.slug = 'visa' THEN 1 ELSE 0 END\n" +
                "  ) AS nb_visa, \n" +
                "  SUM(\n" +
                "    CASE WHEN m.slug = 'visa' THEN fm.montant ELSE 0 END\n" +
                "  ) AS total_visa,\n" +
                "  SUM(\n" +
                "    CASE WHEN f.montant_pec is not null THEN 1 ELSE 0 END\n" +
                "  ) AS nb_pec, \n" +
                "  SUM(\n" +
                "    CASE WHEN f.montant_pec is not null THEN pe.montant_pec ELSE 0 END\n" +
                "  ) AS total_pec,\n" +
                "  SUM(fm.montant)+SUM(CASE WHEN f.montant_pec is not null THEN pe.montant_pec ELSE 0 END) AS montant_total \n" +
                "FROM \n" +
                "  facture f \n" +
                "  JOIN pec pe ON pe.facture_id = f.id \n" +
                "  JOIN assurance assur ON pe.assurance_id = assur.id \n" +
                "  JOIN prestation p ON f.prestation_id = p.id \n" +
                "  JOIN prestation_tarif pt ON p.id = pt.prestation_id \n" +
                "  JOIN tarif t ON pt.tarif_id = t.id \n" +
                "  JOIN acte a ON t.acte_id = a.id \n" +
                "  JOIN mode_facture fm ON f.id = fm.facture_id \n" +
                "  JOIN mode_payement m ON fm.mode_payement_id = m.id \n" +
                "WHERE \n" +
                "  f.date_facture >= '"+ datemin +"' \n" +
                "  and f.date_facture <= '"+ datemax +"' \n" +
                "GROUP BY a.libelle";
        String finalRequest = "";
        if(vue.equals("compact")){
            finalRequest = baseRequest;
        }else{
            finalRequest = "select a.libelle as actec, b.* \n" +
                    "from acte a \n" +
                    "left join ("+baseRequest+")b on a.libelle = b.actec order by a.position asc";
        }
        return finalRequest;
    }
}
