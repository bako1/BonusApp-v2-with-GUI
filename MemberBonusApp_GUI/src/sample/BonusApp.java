package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class BonusApp extends Application  {
  private Controller controller = new Controller();

  Logger logger = LogManager.getRootLogger();



  @Override
  public void start(Stage primaryStage) throws Exception {




    // table column for first name


    TableColumn<TableClass, String> firstName = new TableColumn<>("firstName");
    firstName.setMaxWidth(200);
    firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));

    //table column for surname

    TableColumn<TableClass, String> surname = new TableColumn<>("Surname");
    surname.setMaxWidth(100);
    surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
    //table column for Member number
    TableColumn<TableClass, Integer> memberNo = new TableColumn<>("Member.No");
    memberNo.setMaxWidth(100);
    memberNo.setCellValueFactory(new PropertyValueFactory<>("memberNo"));






    //Add button

    Button addNew = new Button("Add");

    Image image = new Image(getClass().getResourceAsStream("addNewMember.png"));
    ImageView imageView = new ImageView(image);
    imageView.setFitHeight(25);
    imageView.setFitWidth(25);

    addNew.setGraphic(imageView);
    addNew.setOnAction(add -> controller.onAddClicked());



   //Buttons border color
  String buttonsBorderColor = "-fx-border-color: white";
    //Delete Button
    Button deleteM = new Button("Remove");
     deleteM.setPrefSize(100,70);

    //Delete set on action
    deleteM.setOnAction(del -> controller.onDeleteClicked());





    Button showDetails  = new Button("Details");



    showDetails.setOnAction(show -> controller.onSeeDetail());

    Image image2 = new Image(getClass().getResourceAsStream("removeMember.png"));
    ImageView image2View = new ImageView(image2);
    image2View.setFitHeight(25);
    image2View.setFitWidth(25);
    deleteM.setGraphic(image2View);




    Image image3 = new Image(getClass().getResourceAsStream("viewDetails.png"));
    ImageView image3View = new ImageView(image3);
    image3View.setFitHeight(25);
    image3View.setFitWidth(25);
    showDetails.setGraphic(image3View);



    addNew.setPrefSize(100,70);
    showDetails.setPrefSize(100,70);

    //Register Button
    Button registerPoints = new Button("Register \nPoints");

    registerPoints.setPrefSize(100,70);
    registerPoints.setStyle(buttonsBorderColor);
    //Setting register button icon

    Image image4 = new Image(getClass().getResourceAsStream("registerP.png"));
    ImageView image4View = new ImageView(image4);
    image4View.setFitHeight(25);
    image4View.setFitWidth(25);
    registerPoints.setGraphic(image4View);

    //register set onAction
    registerPoints.setOnAction(registerP -> controller.onRegisterClicked());


    HBox addShowDeleteReg = new HBox(10);



    addShowDeleteReg.setOpaqueInsets(new Insets(10,10,10,10));
    addShowDeleteReg.setBackground(new Background(new
       BackgroundFill(Color.WHITE,CornerRadii.EMPTY,Insets.EMPTY)));

    addShowDeleteReg.getChildren().addAll(controller.menuBars(),addNew,deleteM,showDetails,registerPoints);


     // Table view



    controller.tableView.getOnScroll();
    controller.tableView.getColumns().addAll(surname, firstName,memberNo);
    controller.tableView.setItems(controller.initiate());
    controller.tableView.setTableMenuButtonVisible(true);


    String styles = "-fx-border-color:  yellow";

    // controller.tableView.setStyle(styles);



    Label label = new Label("Member List");
    label.setFont(Font.font("verdana", FontWeight.BOLD, 30));


    label.setMinWidth(300);
    HBox labelBox = new HBox(10);
    labelBox.getChildren().add(label);
    labelBox.setOpaqueInsets(new Insets(10, 10, 10, 10));
    labelBox.setFillHeight(true);


    VBox root = new VBox();
    root.setStyle(styles);
    root.setBackground(new Background(new
        BackgroundFill(Color.BISQUE, CornerRadii.EMPTY, Insets.EMPTY)));
   // controller.tableView.setBackground(new Background(new BackgroundFill[100]));

    VBox tableBox = new VBox(10);

  //  tableBox.setPadding(new Insets(50,10,10,10));

    tableBox.getChildren().addAll(addShowDeleteReg);


    //Text area where Detail info about selected members display

    controller.basicMember.setStyle("-fx-border-color: white");

    Label silverLabel = new Label("Silver Members");
    controller.silverMember.setStyle("-fx-background-color: silver");
    Label goldLabel = new Label("Gold Members");
    controller.goldMember.setStyle("-fx-background-color: gold");
    Label basicLabel = new Label("Basic Members");



    root.getChildren().addAll(tableBox,controller.tableView,
        basicLabel,controller.basicMember,silverLabel,
        controller.silverMember,goldLabel,controller.goldMember); //Add tableView to VBox



    //Delete Button
    Button delete = new Button("Delete");
    //set on action
    delete.setOnAction(d -> controller.onDeleteClicked());

    // MENU



    MenuItem seeDetail;
    MenuItem deleteMember;
    MenuItem registerPoint;


    seeDetail = new MenuItem("See Detail");

    deleteMember = new MenuItem(" Remove");
    registerPoint = new MenuItem("Register Point");


    ContextMenu contextMenu = new ContextMenu();

    contextMenu.getItems().addAll(seeDetail, deleteMember, registerPoint);


    // memberNo.setContextMenu(contextMenu);
    //right click on the selected item

    controller.tableView.setOnMouseClicked(click -> {
      if (click.getButton() == MouseButton.SECONDARY ||
              click.getClickCount() == 2 ) {
        //Locates context menu to wherever mouse is right/double clicked
        contextMenu.show(controller.tableView,click.getScreenX(),click.getScreenY());

        TableClass selectedItem = controller.tableView.getSelectionModel().getSelectedItem();
        try {

          if (selectedItem.memberNo>0) {
            deleteMember.setOnAction(remove -> controller.onDeleteClicked());
            registerPoint.setOnAction(reg -> controller.onRegisterClicked());
            seeDetail.setOnAction(see -> controller.onSeeDetail());


          }

        } catch (NullPointerException e) {

         logger.info(e.getMessage());
        }
      }
    });




    // set Up stage and scene


    root.setSpacing(5);
    root.setPadding(new Insets(10, 10, 10, 10));

    HBox hBox1 = new HBox();
    root.getChildren().addAll(hBox1);

    primaryStage.setTitle("AirLine Bonus App");
    primaryStage.setScene(new Scene(root, 558, 700));
    primaryStage.show();


    //login page


    Label usernameL = new Label("Username:  ");
    usernameL.setFont(Font.font("Arial",20));

    Label passL = new Label("Password:  ");
    passL.setFont(Font.font("Times New Roman",25));
    Button log = new Button("   Login ");
    log.setMaxSize(700,50);


    TextField username = new TextField();

    username.setPromptText("Username");

    PasswordField pass = new PasswordField();



    pass.setPromptText("********");
    GridPane gridPane = new GridPane();

    gridPane.add(usernameL,0,0);
    gridPane.add(username,1,0);


    gridPane.add(passL,0,4);

    gridPane.add(pass,1,4);
    gridPane.add(log,1,5);



    gridPane.setVgap(10);
    HBox rootLog = new HBox(10);
    rootLog.getChildren().addAll(gridPane);
    rootLog.setBackground(new Background(new BackgroundFill(
        Color.PALEGOLDENROD,CornerRadii.EMPTY,Insets.EMPTY)));
    rootLog.setPadding(new Insets(100,10,10,10));

    rootLog.setAlignment(Pos.CENTER);

    Scene scene = new Scene(rootLog,500,500);
    Stage stageLog = new Stage();
    stageLog.setTitle("Login page");

    stageLog.setScene(scene);
    rootLog.setBackground(new Background(new
        BackgroundFill(Color.AZURE,CornerRadii.EMPTY,Insets.EMPTY
    )));


    primaryStage.close();
    log.setOnAction(e -> {
      //Not practical, I used it only to show the login page
      // which requires both username and  password
      // password and username should have been securely saved in database.
      if (!username.getText().equalsIgnoreCase("bako")
          || !pass.getText().equals("4554")) {
        pass.setText("password");

        Text message = new Text();
        username.setStyle("-fx-border-color: #ff2c1a");


        pass.setStyle("-fx-border-color: red");
        message.setText(" Username and/or password is incorrect");
        message.setStroke(Color.RED);
        message.setFont(Font.font("Times New Roman",20));
        gridPane.add(message,1,6);
        message.setLineSpacing(10);
      } else {
        primaryStage.show();
        stageLog.close();
      }

    });
    stageLog.show();

  }


  public static void main(String[] args) {
    launch(args);



  }


}
