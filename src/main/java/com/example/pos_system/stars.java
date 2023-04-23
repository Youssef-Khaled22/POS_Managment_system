package com.example.pos_system;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class  stars {
    private ImageView image;

    public ImageView getImage() {
        return image;
    }

    public void setImage(String url) {
        Image im = new Image(url);
        this.image = new ImageView(im);
    }

    public stars(String url) {
        setImage(url);
    }
}
