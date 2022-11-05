import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import model.Photo;
import model.PhotoSize;
import util.PhotoDownloader;
import util.PhotoProcessor;
import util.PhotoSerializer;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhotoCrawler {

    private static final Logger log =
            Logger.getLogger(PhotoCrawler.class.getName());

    private final PhotoDownloader photoDownloader;

    private final PhotoSerializer photoSerializer;

    private final PhotoProcessor photoProcessor;

    public PhotoCrawler() throws IOException {
        this.photoDownloader = new PhotoDownloader();
        this.photoSerializer = new PhotoSerializer("./photos");
        this.photoProcessor = new PhotoProcessor();
    }

    public void resetLibrary() throws IOException {
        photoSerializer.deleteLibraryContents();
    }

    public void downloadPhotoExamples() {
        try {
            photoDownloader.getPhotoExamples()
                           .subscribe(photoSerializer::savePhoto);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Downloading photo examples error", e);
        }
    }

    public void downloadPhotosForQuery(String query) {
        try {
            downloadAndSavePhotos(photoDownloader.searchForPhotos(query));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void downloadPhotosForMultipleQueries(List<String> queries) {
        downloadAndSavePhotos(photoDownloader.searchForPhotos(queries));
    }

    private void downloadAndSavePhotos(Observable<Photo> photoObservable) {
        photoObservable
                .compose(this::processPhotos)
                .subscribe(
                        photoSerializer::savePhoto,
                        e -> log.log(
                                Level.SEVERE,
                                "Downloading photos error",
                                e
                        )
                );
    }

    private Observable<Photo> processPhotos(Observable<Photo> photoObservable) {
        return photoObservable
                .filter(photoProcessor::isPhotoValid)
                .groupBy(PhotoSize::resolve)
                .flatMap(group -> group.getKey() ==
                                  PhotoSize.LARGE ?
                                  group.observeOn(Schedulers.computation())
                                       .map(photoProcessor::convertToMiniature) :
                                  group.buffer(5, TimeUnit.SECONDS)
                                        .flatMapIterable(o -> o)
                );
    }
}
