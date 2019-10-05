//Java GUI prac 
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import java.io.*;
import javafx.scene.media.AudioClip;
import javafx.animation.Animation;
import javafx.concurrent.Task;
import java.awt.Desktop;
 
public class simulationApp extends Application {

    Stage window;
    Scene startScreen, simulationScreen;
    double speed = 1.0;
    Label userNumber1,userNumber2,userNumber3,userNumber4;
    Label userStatus1, userStatus2, userStatus3, userStatus4;
    Label diskStatus1, diskStatus2;
    Label printStatus1, printStatus2, printStatus3;
    ImageView diskView1, diskView2;
    ImageView printerView1, printerView2, printerView3;
    ImageView backgroundView;

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        
        //Main Window
        window = primaryStage;

        //Program start 
        Button startBtn = new Button();
        startBtn.setText("Start the 141OS Simulation");
        startBtn.setOnAction(e -> {
            System.out.println("Simulation Started!");
            startTask();
            window.setScene(simulationScreen);
        });

        //Settings toggle for speed; Default set to 1.
        ToggleGroup speedToggle = new ToggleGroup();
        //Speed options
        RadioButton speedOption3 = new RadioButton(" .5x ");
        speedOption3.setToggleGroup(speedToggle);
        RadioButton speedOption1 = new RadioButton(" 1x ");
        speedOption1.setToggleGroup(speedToggle);
        speedOption1.setSelected(true);
        RadioButton speedOption2 = new RadioButton(" 2x ");
        speedOption2.setToggleGroup(speedToggle);
        RadioButton speedOption4 = new RadioButton(" 5x ");
        speedOption4.setToggleGroup(speedToggle);

        speedOption1.setOnAction(e->{
            speed = 1.0;
            System.out.println(speed);
        });
        speedOption2.setOnAction(e->{
            speed = 2.0;
            System.out.println(speed);
        });
         speedOption3.setOnAction(e->{
            speed = .5;
            System.out.println(speed);
        });
        speedOption4.setOnAction(e->{
            speed = 5.0;
            System.out.println(speed);
        });

        //Initial Start Menu
        HBox initSettings = new HBox();
        Label settingsLabel = new Label("Choose speed of simulation: ");
        initSettings.getChildren().addAll(speedOption3,speedOption1,speedOption2,speedOption4);
        initSettings.setAlignment(Pos.CENTER);
        
        VBox settingsMenu = new VBox(10);
        settingsMenu.getChildren().addAll(settingsLabel, initSettings,startBtn);
        settingsMenu.setAlignment(Pos.CENTER);

        VBox startMenu = new VBox(20);
        startMenu.getChildren().addAll(settingsMenu, startBtn);
        startMenu.setAlignment(Pos.CENTER);
        startScreen = new Scene(startMenu, 200, 100);


        //Simulation Start 

        //Toolbar for speed changes
        Menu speedDropdown = new Menu("Speed");

        MenuItem speed3 = new MenuItem(".5");
        MenuItem speed1 = new MenuItem("1");
        MenuItem speed2 = new MenuItem("2");
        MenuItem speed4 = new MenuItem("5");

        speed1.setOnAction(e->{
            speed = 1.0;
            System.out.println(speed);
        });
        speed2.setOnAction(e->{
            speed = 2.0;
            System.out.println(speed);
        });
         speed3.setOnAction(e->{
            speed = .5;
            System.out.println(speed);
        });
        speed4.setOnAction(e->{
            speed = 5.0;
            System.out.println(speed);
        });

        speedDropdown.getItems().addAll(speed3, speed1, speed2, speed4);

        MenuBar toolBar = new MenuBar();
        toolBar.getMenus().addAll(speedDropdown);

        // User icons and User pane which takes up left 1/3 of the window.

        // User view creations 
        String currentPath = new File("").getAbsolutePath();
        String basePath = new File(currentPath).getParent();
        Image userImg = new Image(new FileInputStream(basePath + "\\resources\\smollest.png"));
        
        //USER1
        ImageView userView1 = new ImageView();
        userView1.setImage(userImg);
        userView1.setPreserveRatio(true);

        VBox userVbox1 = new VBox();
        userNumber1 = new Label("USER1: ");
        userStatus1 = new Label("");
        HBox statusBox1 = new HBox(2);
        statusBox1.getChildren().addAll(userNumber1, userStatus1);
        statusBox1.setAlignment(Pos.CENTER);
        userVbox1.getChildren().addAll(userView1, statusBox1);
        userVbox1.setAlignment(Pos.CENTER);

        //USER2
        ImageView userView2 = new ImageView();
        userView2.setImage(userImg);
        userView2.setPreserveRatio(true);

        VBox userVbox2 = new VBox();
        userNumber2 = new Label("USER2: ");
        userStatus2 = new Label("");
        HBox statusBox2 = new HBox(2);
        statusBox2.getChildren().addAll(userNumber2, userStatus2);
        statusBox2.setAlignment(Pos.CENTER);
        userVbox2.getChildren().addAll(userView2, statusBox2);
        userVbox2.setAlignment(Pos.CENTER);
        
        // USER3
        ImageView userView3 = new ImageView();
        userView3.setImage(userImg);
        userView3.setPreserveRatio(true);

        VBox userVbox3 = new VBox();
        userNumber3 = new Label("USER3: ");
        userStatus3 = new Label("");
        HBox statusBox3 = new HBox(2);
        statusBox3.getChildren().addAll(userNumber3, userStatus3);
        statusBox3.setAlignment(Pos.CENTER);
        userVbox3.getChildren().addAll(userView3, statusBox3);
        userVbox3.setAlignment(Pos.CENTER);

        // USER4
        ImageView userView4 = new ImageView();
        userView4.setImage(userImg);
        userView4.setPreserveRatio(true);

        VBox userVbox4 = new VBox();
        userNumber4 = new Label("USER4: ");
        userStatus4 = new Label("");
        HBox statusBox4 = new HBox(2);
        statusBox4.getChildren().addAll(userNumber4, userStatus4);
        statusBox4.setAlignment(Pos.CENTER);
        userVbox4.getChildren().addAll(userView4, statusBox4);
        userVbox4.setAlignment(Pos.CENTER);

        //END of USERS

        GridPane userPane1 = new GridPane();
        userPane1.setMinSize(450,412);
        userPane1.add(userVbox1, 0, 0);
        userPane1.add(userVbox2, 0, 1);
        userPane1.add(userVbox3, 0, 2);

        GridPane userPane2 = new GridPane();
        userPane2.setMinSize(450,412);
        userPane2.add(userVbox4, 0, 0);

        VBox userPanemain = new VBox();
        userPanemain.getChildren().addAll(userPane1, userPane2);

        Button activateUser = new Button("Activate");
        activateUser.setOnAction(e-> userStatus1.setText("ACTIVATED"));    


        
        Image diskWriting = new Image(new FileInputStream(basePath + "\\resources\\spongebob.gif"));
        Image diskReading = new Image(new FileInputStream(basePath + "\\resources\\squidward2.gif"));
        diskView1 = new ImageView();
        diskView1.setImage(diskWriting);
        diskView1.setPreserveRatio(true);

        VBox diskVbox1 = new VBox();
        Label diskNumber1 = new Label("DISK1: ");
        diskStatus1 = new Label("");
        HBox diskStatusBox1 = new HBox(2);
        diskStatusBox1.getChildren().addAll(diskNumber1, diskStatus1);
        diskStatusBox1.setAlignment(Pos.CENTER);
        diskVbox1.getChildren().addAll(diskView1, diskStatusBox1);
        diskVbox1.setAlignment(Pos.CENTER);
        diskVbox1.setLayoutX(210);
        diskVbox1.setLayoutY(140);
        

        diskView2 = new ImageView();
        diskView2.setImage(diskWriting);
        diskView2.setPreserveRatio(true);

        VBox diskVbox2 = new VBox();
        Label diskNumber2 = new Label("DISK2: ");
        diskStatus2 = new Label("");
        HBox diskStatusBox2 = new HBox(2);
        diskStatusBox2.getChildren().addAll(diskNumber2, diskStatus2);
        diskStatusBox2.setAlignment(Pos.CENTER);
        diskVbox2.getChildren().addAll(diskView2, diskStatusBox2);
        diskVbox2.setAlignment(Pos.CENTER);
        diskVbox2.setLayoutX(210);
        diskVbox2.setLayoutY(332);

        Image printerIdle = new Image(new FileInputStream(basePath + "\\resources\\print.png"));
        
        printerView1 = new ImageView();
        printerView1.setImage(printerIdle);
        printerView1.setPreserveRatio(true);
        VBox printerVbox1 = new VBox();
        Label printerNumber1 = new Label("PRINTER1: ");
        printStatus1 = new Label("");
        HBox printerStatusBox1 = new HBox(2);
        printerStatusBox1.getChildren().addAll(printerNumber1, printStatus1);
        printerStatusBox1.setAlignment(Pos.CENTER);
        printerVbox1.getChildren().addAll(printerView1, printerStatusBox1);
        printerVbox1.setAlignment(Pos.CENTER);
        printerVbox1.setLayoutX(410);
        printerVbox1.setLayoutY(10);

        printerView1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

             @Override
             public void handle(MouseEvent event) {
                 String theText = printStatus1.getText();
                 if(theText == "DONE")
                 { 
                    try
                    {
                        String currentPath = new File("").getAbsolutePath();
                        String basePath = new File(currentPath).getParent();
                        Desktop.getDesktop().open(new File(basePath + "\\outputs\\" + "PRINTER1.txt"));
                    }
                    catch (Exception e)
                    {

                    }
                 }
                 event.consume();
             }
        });

        printerView2 = new ImageView();
        printerView2.setImage(printerIdle);
        printerView2.setPreserveRatio(true);
        VBox printerVbox2 = new VBox();
        Label printerNumber2 = new Label("PRINTER2: ");
        printStatus2 = new Label("");
        HBox printerStatusBox2 = new HBox(2);
        printerStatusBox2.getChildren().addAll(printerNumber2, printStatus2);
        printerStatusBox2.setAlignment(Pos.CENTER);
        printerVbox2.getChildren().addAll(printerView2, printerStatusBox2);
        printerVbox2.setAlignment(Pos.CENTER);
        printerVbox2.setLayoutX(410);
        printerVbox2.setLayoutY(210);
        printerView2.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

             @Override
             public void handle(MouseEvent event) {
                 String theText = printStatus1.getText();
                 if(theText == "DONE")
                 { 
                    try
                    {
                        String currentPath = new File("").getAbsolutePath();
                        String basePath = new File(currentPath).getParent();
                        Desktop.getDesktop().open(new File(basePath + "\\outputs\\" + "PRINTER2.txt"));
                    }
                    catch (Exception e)
                    {

                    }
                 }
                 event.consume();
             }
        });


        printerView3 = new ImageView();
        printerView3.setImage(printerIdle);
        printerView3.setPreserveRatio(true);
        VBox printerVbox3 = new VBox();
        Label printerNumber3 = new Label("PRINTER3: ");
        printStatus3 = new Label("");
        HBox printerStatusBox3 = new HBox(2);
        printerStatusBox3.getChildren().addAll(printerNumber3, printStatus3);
        printerStatusBox3.setAlignment(Pos.CENTER);
        printerVbox3.getChildren().addAll(printerView3, printerStatusBox3);
        printerVbox3.setAlignment(Pos.CENTER);
        printerVbox3.setLayoutX(410);
        printerVbox3.setLayoutY(410);
        printerView3.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

             @Override
             public void handle(MouseEvent event) {
                 String theText = printStatus1.getText();
                 if(theText == "DONE")
                 { 
                    try
                    {
                        String currentPath = new File("").getAbsolutePath();
                        String basePath = new File(currentPath).getParent();
                        Desktop.getDesktop().open(new File(basePath + "\\outputs\\" + "PRINTER3.txt"));
                    }
                    catch (Exception e)
                    {

                    }
                 }
                 event.consume();
             }
        });



        Image rainy = new Image(new FileInputStream(basePath + "\\resources\\sunny1.gif"));

        backgroundView = new ImageView();

        backgroundView.setImage(rainy);
        backgroundView.setPreserveRatio(true);

        Group mainWin = new Group(backgroundView,userPanemain,toolBar,diskVbox1, diskVbox2, printerVbox1, printerVbox2, printerVbox3);

    
        simulationScreen = new Scene(mainWin);

        window.setTitle("Ugyen Dorji, 83628422, Simple 141OS");
        window.setScene(startScreen);
        window.show();
    }

    public void startTask()
    {
        Runnable task = new Runnable()
        {
            public void run()
            {
                runTask();
            }
        };

        Thread backgroundThread = new Thread(task);
        backgroundThread.setDaemon(true);
        backgroundThread.start();
    }


    public void startMusic()
    {
        final Task task = new Task()
        {
            protected Object call() throws Exception
            {
                System.out.println("IS PLAYING MUSIC");
                int s = Animation.INDEFINITE;
                System.out.println("1");
                String currentPath = new File("").getAbsolutePath();
                System.out.println("2");
                String basePath = new File(currentPath).getParent();
                System.out.println("3");
                AudioClip audio = new AudioClip(getClass().getResource("sunflower2.wav").toExternalForm());
                System.out.println("4");
                audio.setVolume(1);
                audio.setCycleCount(s);
                audio.play();
                System.out.println("5");
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
    }

    public void runTask()
    {
        ImageView[] diskViews = new ImageView[]{diskView1, diskView2};
        ImageView[] printViews = new ImageView[]{printerView1, printerView2, printerView3};
        Label[] statusLabels = new Label[]{userStatus1, userStatus2, userStatus3, userStatus4};
        Label[] diskStatusLabels = new Label[]{diskStatus1, diskStatus2};
        Label[] printLabels = new Label[]{printStatus1, printStatus2, printStatus3};
        mainClass.main(statusLabels,diskViews, diskStatusLabels,speed,printViews, printLabels);
        startMusic();
    }

}