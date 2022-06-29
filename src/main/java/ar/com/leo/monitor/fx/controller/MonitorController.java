package ar.com.leo.monitor.fx.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import ar.com.leo.monitor.fx.service.MaquinaService;
import ar.com.leo.monitor.jdbc.DataSourceConfig;
import ar.com.leo.monitor.model.Maquina;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class MonitorController implements Initializable {

    @FXML
    private GridPane grid;
    @FXML
    private Label turno;
    @FXML
    private Label grupo;
    @FXML
    private Label eficienciaTotal;
    @FXML
    private Label eficienciaGrupo;
    @FXML
    private Label fecha;

    private List<Label> labels;
    private List<ImageView> images;
    private List<ImageView> functionImages;

    private final List<String> parameters;
    private MaquinaService maquinaService;

    private final Image RUN = new Image("images/icons/05_big_machine_run.png");
    private final Image OFF = new Image("images/icons/03_big_machine_poweroff.png");
    private final Image IDLE = new Image("images/icons/04_big_machine_idle.png");
    private final Image STOP_ERROR = new Image("images/icons/70_big_machine_stop_error.png");
    private final Image TARGET = new Image("images/icons/71_big_machine_stop_target.png");
    private final Image NO_CYCLE = new Image("images/icons/06_big_machine_nocycle.png");
    private final Image MS1 = new Image("images/icons/07_big_machine_manualstop_1.png");
    private final Image MS2 = new Image("images/icons/08_big_machine_manualstop_2.png");
    private final Image MS3 = new Image("images/icons/09_big_machine_manualstop_3.png");
    private final Image MS4 = new Image("images/icons/10_big_machine_manualstop_4.png");
    private final Image MS5 = new Image("images/icons/11_big_machine_manualstop_5.png");
    private final Image MS6 = new Image("images/icons/12_big_machine_manualstop_6.png");
    private final Image MS7 = new Image("images/icons/13_big_machine_manualstop_7.png");
    private final Image MS8 = new Image("images/icons/14_big_machine_manualstop_8.png");
    private final Image OFFLINE = new Image("images/icons/01_big_machine_offline.png");
    private final Image UNDEFINED = new Image("images/icons/02_big_machine_undefined.png");
    private final Image F1 = new Image("images/icons/64_big_machine_mask_F1.png");
    //    private final Image F3 = new Image("images/Icons/65_big_machine_mask_F3.png");
    private final Image F5 = new Image("images/icons/66_big_machine_mask_F5.png");
    private final Image F6 = new Image("images/icons/67_big_machine_mask_F6.png");

    private final String mayor90 = "-fx-background-color: #00c900;";
    private final String menor90 = "-fx-background-color: #acff7f;";
    private final String menor85 = "-fx-background-color: #f0ff39;";
    private final String menor80 = "-fx-background-color: #ff6565;";

    public MonitorController(List<String> parameters) {
        this.parameters = parameters;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // inicio bd
        if (DataSourceConfig.dataSource == null) {
            System.out.println("Error: Base de datos no inicializada.");
        } else {
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy hh:mm:ss");
            // FECHA y TURNO
            final Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), event -> {
                        final LocalDateTime localDateTime = LocalDateTime.now();
                        fecha.setText(formatter.format(localDateTime));
                        turno.setText(getTurno(localDateTime.getHour()));
                    })
            );
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

            inicializarGrilla();

            maquinaService = new MaquinaService();
            maquinaService.setPeriod(Duration.seconds(Double.parseDouble(parameters.get(0)))); // Segundos
            maquinaService.setGroupName(parameters.get(1)); // Sector
            maquinaService.setGroupCodes(FXCollections.observableList(parameters.subList(2, parameters.size()))); // Grupos
            maquinaService.setOnFailed(event -> event.getSource().getException().printStackTrace());
//            maquinaService.setOnSucceeded(event -> grupo.setText(maquinaService.getGroupCode()));
            maquinaService.lastValueProperty().addListener((observable, oldValue, newValue) -> { // or bind to this property
                if (!maquinaService.getGroupCode().equals("SECTOR")) {
                    grupo.setText(maquinaService.getGroupCode());
                    mostrarEficienciasPorGrupo(newValue);
                } else {
                    grupo.setText(maquinaService.getGroupName());
                    mostrarEficienciasPorSector(newValue);
                }
                maquinaService.changeGroup();
            });
            // you may also want to add EventHandlers/Listeners to handle when the service fails and such.
            maquinaService.start();
        }
    }

    private String getTurno(final int hour) {
        if (hour >= 6 && hour < 14) {
            return "1";
        } else if (hour >= 14 && hour < 22) {
            return "2";
        } else {
            return "3";
        }
    }

    private void inicializarGrilla() {

        labels = new ArrayList<>();
        images = new ArrayList<>();
        functionImages = new ArrayList<>();

        Platform.runLater(() -> {
            for (int row = 0; row < grid.getRowCount(); row++) {
                for (int col = 0; col < grid.getColumnCount(); col++) {
                    Label label = new Label();
                    label.setAlignment(Pos.CENTER);
                    label.setTextAlignment(TextAlignment.CENTER);
                    label.setPrefWidth(grid.getWidth() / 5);
                    label.setPrefHeight(grid.getHeight() / 5);
                    GridPane.setFillHeight(label, true);
                    GridPane.setFillWidth(label, true);
                    label.setGraphicTextGap(0);
                    label.setLineSpacing(0);
                    label.setId("maquina");
                    GridPane.setHalignment(label, HPos.CENTER);
                    GridPane.setValignment(label, VPos.CENTER);
                    ImageView imageView = new ImageView();
                    imageView.setFitWidth(48 * 1.8);
                    imageView.setFitHeight(49 * 1.8);
                    GridPane.setHalignment(imageView, HPos.LEFT);
                    GridPane.setValignment(imageView, VPos.CENTER);
                    labels.add(label);
                    images.add(imageView);
                    grid.add(label, col, row);
                    grid.add(imageView, col, row);
                }
            }
        });
    }

    private void mostrarEficienciasPorGrupo(final Object[] results) {

        final List<Maquina> maquinas = (List<Maquina>) results[0];
        final Integer eficienciaGrupal = (Integer) results[1];
        int cantidad = maquinas.size();
//        double labelWidth = grid.getWidth() / (cantidad / 5d);
        double labelHeight = grid.getHeight() / (cantidad / 5d);
        int i = 0;

        clearGrid();

        for (Maquina maquina : maquinas) {
            Label label = labels.get(i);
//            label.setPrefWidth(labelWidth);
            label.setPrefHeight(labelHeight);
            ImageView imageView = images.get(i);
            switch (maquina.getState()) {
                case 0:
                    imageView.setImage(RUN);
                    break;
                case 1:
                    imageView.setImage(OFF);
                    break;
                case 2: // GENERAL STOP
                    imageView.setImage(IDLE);
                    break;
                case 3:
                    imageView.setImage(STOP_ERROR);
                    break;
                case 4:
                    imageView.setImage(TARGET);
                    break;
                case 5:
                    imageView.setImage(NO_CYCLE);
                    break;
                case 6:
                    imageView.setImage(MS1);
                    break;
                case 7:
                    imageView.setImage(MS2);
                    break;
                case 8:
                    imageView.setImage(MS3);
                    break;
                case 9:
                    imageView.setImage(MS4);
                    break;
                case 10:
                    imageView.setImage(MS5);
                    break;
                case 11:
                    imageView.setImage(MS6);
                    break;
                case 12:
                    imageView.setImage(MS7);
                    break;
                case 13:
                    imageView.setImage(MS8);
                    break;
                case 56:
                    imageView.setImage(OFFLINE);
                    break;
                case 65535: // no sincronizada
                    imageView.setImage(UNDEFINED);
                    break;
            }

            if (maquina.getState() == 0 || maquina.getState() == 2 || maquina.getState() == 3 || maquina.getState() == 4) {
                if (maquina.getFunctionKey() != null) {
                    final String binary = String.format("%8s", Integer.toBinaryString(maquina.getFunctionKey())).replace(' ', '0');

                    if (binary.charAt(binary.length() - 2) == '1') { // F1=2
                        addFunctionImage(label, F1);
                    }
//                if (binary.charAt(binary.length() - 3) == '1') { // F2=4
//                    setFunctionImage(label, F2);
//                }
//                if (binary.charAt(binary.length() - 4) == '1') { // F3=8
//                    setFunctionImage(label, F3);
//                }
//                if (binary.charAt(binary.length() - 5) == '1') { // F4=16
//                    setFunctionImage(label, F4);
//                }
                    if (binary.charAt(binary.length() - 6) == '1') { // F5=32
                        addFunctionImage(label, F5);
                    }
                    if (binary.charAt(binary.length() - 7) == '1') { // F6=64
                        addFunctionImage(label, F6);
                    }
                }
            }

            label.setText(maquina.getMachCode() + "\n " + maquina.getWorkEfficiency() + "%");

            if (maquina.getWorkEfficiency() >= 90) {
                label.setStyle(mayor90);
            } else if (maquina.getWorkEfficiency() < 90 && maquina.getWorkEfficiency() >= 85) {
                label.setStyle(menor90);
            } else if (maquina.getWorkEfficiency() < 85 && maquina.getWorkEfficiency() >= 80) {
                label.setStyle(menor85);
            } else {
                label.setStyle(menor80);
            }
            i++;
        }

        eficienciaGrupo.setText(eficienciaGrupal + "%");

        if (eficienciaGrupal >= 90) {
            eficienciaGrupo.setStyle(mayor90);
        } else if (eficienciaGrupal >= 85) {
            eficienciaGrupo.setStyle(menor90);
        } else if (eficienciaGrupal >= 80) {
            eficienciaGrupo.setStyle(menor85);
        } else {
            eficienciaGrupo.setStyle(menor80);
        }
    }

    private void mostrarEficienciasPorSector(final Object[] results) {

        final Map<String, Integer> eficienciasPorGrupo = (Map<String, Integer>) results[0];
        final Integer eficienciaTot = (Integer) results[1];
        int cantidad = eficienciasPorGrupo.size();
//        double labelWidth = grid.getWidth() / (cantidad / 5d);
        double labelHeight = grid.getHeight() / (cantidad / 5d);
        int i = 0;

        clearGrid();

        for (Map.Entry<String, Integer> set : eficienciasPorGrupo.entrySet()) {
            Label label = labels.get(i);
//            label.setPrefWidth(labelWidth);
            label.setPrefHeight(labelHeight);
            label.setText(set.getKey() + "\n  " + set.getValue() + "%");
            if (set.getValue() >= 90) {
                label.setStyle(mayor90);
            } else if (set.getValue() >= 85) {
                label.setStyle(menor90);
            } else if (set.getValue() >= 80) {
                label.setStyle(menor85);
            } else {
                label.setStyle(menor80);
            }
            i++;

            eficienciaTotal.setText(eficienciaTot + "%");

            if (eficienciaTot >= 90) {
                eficienciaTotal.setStyle(mayor90);
            } else if (eficienciaTot >= 85) {
                eficienciaTotal.setStyle(menor90);
            } else if (eficienciaTot >= 80) {
                eficienciaTotal.setStyle(menor85);
            } else {
                eficienciaTotal.setStyle(menor80);
            }
        }
    }

    private void clearGrid() {
        eficienciaGrupo.setText(null);
        eficienciaGrupo.setStyle("-fx-background-color: none;");
        for (Label label : labels) {
            if (label.getText() != null) {
                label.setText(null);
                label.setStyle("-fx-background-color: none;-fx-border-color: none;");
//                label.setPrefWidth(0);
                label.setPrefHeight(0);
            }
        }
        for (ImageView imageView : images) {
            if (imageView.getImage() != null)
                imageView.setImage(null);
        }
        for (ImageView functionImage : functionImages) {
            if (functionImage.getImage() != null) {
                functionImage.setImage(null);
            }
        }
        functionImages.clear();
    }

    private void addFunctionImage(Label label, Image image) {
        ImageView functionImage = new ImageView(image);
        functionImage.setFitWidth(48 * 1.3);
        functionImage.setFitHeight(49 * 1.3);
        GridPane.setHalignment(functionImage, HPos.LEFT);
        GridPane.setValignment(functionImage, VPos.CENTER);
        grid.add(functionImage, GridPane.getColumnIndex(label), GridPane.getRowIndex(label));
        functionImages.add(functionImage);
    }

}
