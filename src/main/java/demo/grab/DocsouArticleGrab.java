package demo.grab;

import cn.hutool.core.util.StrUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DocsouArticleGrab {

    public static void main(String[] args) throws IOException {
        String url = "https://doc.docsou.com/bdee17f3c7123242429621e69.html";

        List<String> lines = new LinkedList<>();
        do {
            Document doc = Jsoup.connect(url).get();
            List<String> contents = getContents(doc);
            lines.addAll(contents);

            url = getNextUrl(doc);
        } while (!url.isEmpty());
        lines.forEach(System.out::println);
    }

    static List<String> getContents(Document doc) {
        Elements contents = doc.select("#contents p");
        return contents.stream()
                .filter(Element::hasText)
                .filter(element -> !element.hasClass("pages"))
                .map(Element::text)
                .collect(Collectors.toList());
    }

    static String getNextUrl(Document doc) {
        Elements nextElement = doc.select("#contents .pages .next");
        if (!nextElement.isEmpty()) {
            String nextUrl = nextElement.attr("href");

            return StrUtil.subBefore(doc.baseUri(), '/', true)
                    + nextUrl;
        }
        return StrUtil.EMPTY;
    }
}
