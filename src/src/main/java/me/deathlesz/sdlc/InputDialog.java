package me.deathlesz.sdlc;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Optional;

public final class InputDialog {

    @FXML
    private TextArea inputArea;

    private String result = null;

    public static Optional<String> show(Window owner, String initialText) {
        return new InputDialog(owner, initialText).showAndGet();
    }

    private InputDialog(Window owner, String initialText) {
        try {
            var loader = new javafx.fxml.FXMLLoader(InputDialog.class.getResource("input-dialog.fxml"));
            loader.setController(this);
            var stage = new Stage();
            stage.initOwner(owner);
            stage.setTitle("Ввод сигналов Морзе");
            stage.setOnShown(e -> inputArea.requestFocus());
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось загрузить input-dialog.fxml", e);
        }
        inputArea.setText(initialText);
        inputArea.selectAll();
    }

    private Optional<String> showAndGet() {
        getStage().showAndWait();
        return Optional.ofNullable(result);
    }

    private Stage getStage() {
        return (Stage) inputArea.getScene().getWindow();
    }

    @FXML
    private void onOk() {
        result = inputArea.getText();
        getStage().close();
    }

    @FXML
    private void onCancel() {
        getStage().close();
    }
}
