package com.newsmoa.app.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.NodeFilter;

import java.io.IOException;
import java.util.List;

public class CrawlingUtil {
    public static final String URL_ECONOMY = "https://news.naver.com/section/101";
    public static final String URL_SCIENCE = "https://news.naver.com/section/105";
    public static final String URL_SOCIETY = "https://news.naver.com/section/102";
    public static final String URL_WORLD = "https://news.naver.com/section/104";
    public static final String URL_CULTURE = "https://news.naver.com/section/103";
    public static final String[] URLS = new String[]{URL_ECONOMY, URL_SCIENCE, URL_SOCIETY, URL_WORLD, URL_CULTURE};

    private static final String URL_ARTICLE_PREFIX = "https://n.news.naver.com/mnews/article";
    
    /** 카테고리별 뉴스기사 url 목록을 크롤링합니다.
    * @param categoryURL 카테고리별 url(CrawlingUtil에 상수로 정의되어 있음)
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
    public static List<String> getNewsUrls(String categoryURL){
        try {
            List<String> urlList = Jsoup.connect(categoryURL).get()
                    .body()
                    .filter((node, i) -> {
                        if (node instanceof Element &&
                                ((Element) node).hasClass("ra_area")) {
                            return NodeFilter.FilterResult.SKIP_ENTIRELY;
                        } else
                            return NodeFilter.FilterResult.CONTINUE;
                    })
                    .getElementsByTag("a")
                    .stream()
                    .map(a -> a.attr("href"))
                    .filter(href ->
                            href.startsWith(URL_ARTICLE_PREFIX)                 //기사 url만 추출
                                    && !href.startsWith(URL_ARTICLE_PREFIX + "/comment")  //기사 댓글 url 제외
                    )
                    .toList();
            return urlList;
        }
        catch (IOException e){
            return null;
        }
    }
}
