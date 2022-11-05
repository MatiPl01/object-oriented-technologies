package model;

import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.util.UUID;

public class Photo {
    private final StringProperty name;
    private final ReadOnlyObjectProperty<Image> photoData;

    public Photo(String extension, byte[] photoData) {
        this.photoData =
                new SimpleObjectProperty<>(new Image(new ByteArrayInputStream(
                        photoData)));
        this.name =
                new SimpleStringProperty(UUID.randomUUID() + "." + extension);
    }

    public String getName() {
        return name.get();
    }

    public Image getPhotoData() {
        return photoData.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public ReadOnlyObjectProperty<Image> photoDataProperty() {
        return photoData;
    }
}
