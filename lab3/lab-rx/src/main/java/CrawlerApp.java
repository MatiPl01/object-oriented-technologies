import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CrawlerApp  {

    private static final List<String> TOPICS = Arrays.asList("Agent Cooper", "Sherlock", "Poirot", "Miss Murple", "Detective Monk");


    public static void main(String[] args)
            throws IOException, InterruptedException {
        PhotoCrawler photoCrawler = new PhotoCrawler();
        photoCrawler.resetLibrary();
//        photoCrawler.downloadPhotoExamples();
//        photoCrawler.downloadPhotosForQuery(TOPICS.get(0));
        photoCrawler.downloadPhotosForMultipleQueries(TOPICS);

        Thread.sleep(100_000);
    }
}