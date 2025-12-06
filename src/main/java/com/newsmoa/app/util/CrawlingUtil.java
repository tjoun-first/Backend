package com.newsmoa.app.util;

import com.newsmoa.app.domain.Article;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.NodeFilter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class CrawlingUtil {
    private static final String URL_ECONOMY = "https://news.naver.com/section/101";
    private static final String URL_SCIENCE = "https://news.naver.com/section/105";
    private static final String URL_SOCIETY = "https://news.naver.com/section/102";
    private static final String URL_WORLD = "https://news.naver.com/section/104";
    private static final String URL_CULTURE = "https://news.naver.com/section/103";
    public static final Map<String,String> URLS = Map.of(
            "경제", URL_ECONOMY,
            "과학", URL_SCIENCE,
            "사회", URL_SOCIETY,
            "세계", URL_WORLD,
            "문화", URL_CULTURE
    );

    private static final String URL_ARTICLE_PREFIX = "https://n.news.naver.com/mnews/article";
    
    /** 카테고리별 뉴스기사 url 목록을 크롤링합니다.
    * @param category 카테고리명(한글로 작성)
    * @return 해당 카테고리의 기사 url 리스트
    * */
    /* 하는일
     * 1. url로 get 요청
     * 2. dom에서 body 가져오기
     * 3. 이후 연산에서 클래스가 ra_area인 요소 및 해당 요소의 하위 요소 무시(카테고리와 무관한 랭킹 뉴스 등 제외)
     * 4. a태그 가져오기
     * 5. 데이터를 스트림으로 변환
     * 6. 각 a 태그에서 href 값만 가져오기
     * 7. url을 필터링 해 기사 url만 추출
     * 8. 스트림을 리스트로 변환하여 리턴
    * */
    private static List<Article> getNewsList(String category){
        try {
            List<Article> articles = Jsoup.connect(URLS.get(category)).get()
                    .body()
                    .filter((node, i) -> {
                        if (node instanceof Element &&
                                ((Element) node).hasClass("ra_area")) {
                            return NodeFilter.FilterResult.SKIP_ENTIRELY;
                        } else
                            return NodeFilter.FilterResult.CONTINUE;
                    })
                    .select("a:has(img)")                 
                    .stream()
                    .filter(a ->
                            a.attr("href").startsWith(URL_ARTICLE_PREFIX)                 //기사 url만 추출
                                    && !a.attr("href").startsWith(URL_ARTICLE_PREFIX + "/comment")  //기사 댓글 url 제외
                    )
                    .map(a->{
                        Article article = new Article();
                        article.setUrl(a.attr("href"));
                        article.setCategory(category);
                        article.setImg(a.selectFirst("img").attr("data-src"));
                        return article;
                    })
                    .toList();
            return articles;
        }
        catch (IOException e){
            return null;
        }
    }

    /** 뉴스기사 url을 이용해 뉴스기사 본문을 크롤링합니다.
     * @param article 카테고리명(한글로 작성)
     * @return 해당 카테고리의 기사 url 리스트
     * */
    private static Article getArticleDetail(Article article){
        try {            
            Element doc = Jsoup.connect(article.getUrl()).get().body();
            String title = doc.getElementById("title_area").child(0).text();
            article.setTitle(title);
            
            String dateString = doc.getElementsByClass("_ARTICLE_DATE_TIME").first().attr("data-date-time");
            LocalDate date = LocalDate.parse(dateString.substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            article.setDate(date);
            
            String content = doc.getElementById("dic_area").text();
            article.setContent(content);
            
            return article;
        } catch (IOException e) {
            return null;
        }
    }
    
    public static List<Article> crawlArticles(String category){
        List<Article> articles = getNewsList(category);
        for(Article article : articles){
            getArticleDetail(article);
        }
        return articles;
    }
}
