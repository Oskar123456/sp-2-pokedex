package dk.obhnothing.persistence.service;

//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.net.http.HttpResponse.BodyHandlers;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import com.github.javafaker.Robin;
//
//import dk.obhnothing.persistence.dao.PoemDAO;
//import dk.obhnothing.persistence.dto.PoemDTO;
//
//public class PoemService
//{
//
//    public static PoemDTO fetch(String url)
//    {
//        try
//
//        {
//
//            PoemDTO tryf = PoemDAO.getById(url);
//            if (tryf != null)
//                return tryf;
//
//            HttpClient client = HttpClient.newHttpClient();
//
//            HttpRequest r = HttpRequest.newBuilder().
//                uri(URI.create(url)).
//                build();
//
//            HttpResponse<String> res = client.send(r, BodyHandlers.ofString());
//            String s = res.body();
//
//            Document doc = Jsoup.parse(s);
//            List<Element> allEs = doc.getAllElements();
//            String candidate = "";
//            String candidateAuthor = "";
//            String candidateTitle = "";
//            String candidateDate = "";
//            String candidateImg = "";
//            for (Element e : allEs) {
//                if (e.className().equals("field field--field_date_published")) {
//                    candidateDate = e.text();
//                }
//                if (e.className().equals("field field--field_author")) {
//                    candidateAuthor = e.text();
//                }
//                if (candidate.length() < 1 && e.className().equals("field field--body")) {
//                    candidate = e.html();
//                }
//            }
//            Elements metaOgTitle = doc.select("meta[property=og:title]");
//            if (metaOgTitle!=null) {
//                candidateTitle = metaOgTitle.attr("content");
//            }
//
//            Elements img_element = doc.getElementsByClass("field field--field_image");
//
//            for (Element e : img_element) {
//                for (Element es : e.children()) {
//                    for (Element ess : es.children()) {
//                        if (ess.tag().toString().equals("img")) {
//                            String src = ess.attr("src");
//                            if (src != null)
//                                candidateImg = src;
//                        }
//                    }
//                }
//            }
//
//            //candidate = candidate.replaceAll("<pre>", "").replaceAll("</pre>", "");
//            //candidate = candidate.replaceAll("\n", "").replaceAll("\t", "").replaceAll("\r", "").replaceAll("\s+", " ");
//            return new PoemDTO(candidate, candidateAuthor, candidateTitle, candidateDate, URI.create(url).getHost() + candidateImg, url);
//
//        }
//
//        catch(Exception e)
//
//        {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static List<PoemDTO> fetchPage(String url)
//    {
//        try
//
//        {
//
//            List<String> poemUrls = new ArrayList<>();
//            List<PoemDTO> results = new ArrayList<>();
//
//            HttpClient client = HttpClient.newHttpClient();
//
//            HttpRequest r = HttpRequest.newBuilder().
//                uri(URI.create(url)).
//                build();
//
//            HttpResponse<String> res = client.send(r, BodyHandlers.ofString());
//            String s = res.body();
//
//            Document doc = Jsoup.parse(s);
//
//            List<Element> es = doc.getElementsByClass("views-field views-field-title");
//
//            for (int i = 1; i < es.size(); ++i) {
//                String full = es.get(i).html();
//                String cropped = full.substring(full.indexOf('"') + 1);
//                cropped = cropped.substring(0, cropped.indexOf('"'));
//                poemUrls.add("https://poets.org" + cropped);
//            }
//
//            for (String f : poemUrls) {
//                results.add(fetch(f));
//            }
//
//            return results;
//
//        }
//
//        catch(Exception e)
//
//        {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//}














