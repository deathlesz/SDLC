module me.deathlesz.sdlc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens me.deathlesz.sdlc to javafx.fxml;
    exports me.deathlesz.sdlc;
}