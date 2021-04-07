package com.umiskky.view.main;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.umiskky.model.dto.ArpPacketHeader;
import com.umiskky.viewmodel.main.MainViewModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;


public class MainViewController {

    @FXML
    private ChoiceBox<String> networkCardSelector;

    @FXML
    private Label macAddr;

    @FXML
    private Label ipAddr;

    @FXML
    private Label netmask;

    @FXML
    private JFXTextField inputIP;

    @FXML
    private JFXButton sendArpRequest;

    @FXML
    private Label resolvedAddr;

    @FXML
    private TableView<ArpPacketHeader> arpPacketTable;

    @FXML
    private TreeView<String> arpHeaderTree;

    private TreeItem<String> rootItem;

    private final static String EMPTYINFO = "选择工作网卡";
    private ArrayList<String> networkCards;

    private TableView.TableViewSelectionModel<ArpPacketHeader> selectionModel;

    private MainViewModel mainViewModel;

    public void init(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
        networkCardSelectorInit();
        tableInit();
        treeInit();
        bindInit();
        addListener();
    }

    /**
     * @author Umiskky
     * @apiNote this method is used to init property bindings
     */
    public void bindInit(){
        macAddr.textProperty().bind(mainViewModel.getMacAddress());
        ipAddr.textProperty().bind(mainViewModel.getIpAddress());
        netmask.textProperty().bind(mainViewModel.getNetmask());
        resolvedAddr.textProperty().bind(mainViewModel.getResolvedAddr());
        mainViewModel.getIpInput().bind(inputIP.textProperty());
        arpPacketTable.setItems(mainViewModel.getTableItems());
    }

    /**
     * @author UmiSkky
     * @apiNote this method is used to init the choice box
     */
    public void networkCardSelectorInit(){
        networkCards = mainViewModel.getNetworkCardName();
        networkCardSelector.getItems().add(EMPTYINFO);
        networkCardSelector.getItems().addAll(networkCards);
        networkCardSelector.setValue(EMPTYINFO);
    }

    /**
     * @author UmiSkky
     * @apiNote add listener for choice box
     */
    public void addListener(){

        networkCardSelector.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends String> ov, String oldVal, String newVal) -> {
                mainViewModel.setNetworkCardSelected(newVal);
                mainViewModel.vmUpdateNetworkCardsInfo();
            }
        );

        sendArpRequest.setOnAction((e) -> mainViewModel.arpService());

        selectionModel.getSelectedItems().addListener((ListChangeListener<ArpPacketHeader>) change ->
            mainViewModel.vmUpdateTreeView(selectionModel.getSelectedItem())
        );

        mainViewModel.getTreeItems().addListener((MapChangeListener<String, ArrayList<String>>) change -> {
            if(change.wasAdded()){
                ArrayList<String> arrayList = change.getValueAdded();
                TreeItem<String> subTreeItemRoot = new TreeItem<>(arrayList.get(0));
                if(arrayList.size() > 1) {
                    List<TreeItem<String>> subTreeItems = new ArrayList<>(arrayList.size()-1);
                    for(int idx=0; idx<arrayList.size()-1; idx++) {
                        subTreeItems.add(new TreeItem<>(arrayList.get(idx + 1)));
                    }
                    for(TreeItem<String> subTreeItem : subTreeItems){
                        subTreeItemRoot.getChildren().add(subTreeItem);
                    }
                }
                rootItem.getChildren().add(subTreeItemRoot);
            }else if(change.wasRemoved()){
                rootItem.getChildren().clear();
            }
        });

    }

    public void tableInit(){
        TableColumn<ArpPacketHeader, String> colId = new TableColumn<>("No.");
        colId.setCellFactory((col) -> new TableCell<>() {
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                this.setText(null);
                this.setGraphic(null);

                if (!empty) {
                    int rowIndex = this.getIndex() + 1;
                    this.setText(String.valueOf(rowIndex));
                }
            }
        });

        TableColumn<ArpPacketHeader, String> colProtocolType = new TableColumn<>("ProtocolType");
        colProtocolType.setCellValueFactory(new PropertyValueFactory<>("protocolType"));
        TableColumn<ArpPacketHeader, String> colOperation = new TableColumn<>("Operation");
        colOperation.setCellValueFactory(new PropertyValueFactory<>("operation"));
        TableColumn<ArpPacketHeader, String> colSrcHardwareAddr = new TableColumn<>("SrcMAC");
        colSrcHardwareAddr.setCellValueFactory(new PropertyValueFactory<>("srcHardwareAddr"));
        TableColumn<ArpPacketHeader, String> colDstHardwareAddr = new TableColumn<>("DstMAC");
        colDstHardwareAddr.setCellValueFactory(new PropertyValueFactory<>("dstHardwareAddr"));
        TableColumn<ArpPacketHeader, String> colSrcProtocolAddr = new TableColumn<>("SrcIP");
        colSrcProtocolAddr.setCellValueFactory(new PropertyValueFactory<>("srcProtocolAddr"));
        TableColumn<ArpPacketHeader, String> colDstProtocolAddr = new TableColumn<>("DstIP");
        colDstProtocolAddr.setCellValueFactory(new PropertyValueFactory<>("dstProtocolAddr"));

        colId.prefWidthProperty().bind(arpPacketTable.widthProperty().multiply(0.05));
        colProtocolType.prefWidthProperty().bind(arpPacketTable.widthProperty().multiply(0.13));
        colOperation.prefWidthProperty().bind(arpPacketTable.widthProperty().multiply(0.1));
        colSrcHardwareAddr.prefWidthProperty().bind(arpPacketTable.widthProperty().multiply(0.18));
        colDstHardwareAddr.prefWidthProperty().bind(arpPacketTable.widthProperty().multiply(0.18));
        colSrcProtocolAddr.prefWidthProperty().bind(arpPacketTable.widthProperty().multiply(0.18));
        colDstProtocolAddr.prefWidthProperty().bind(arpPacketTable.widthProperty().multiply(0.18));

        arpPacketTable.getColumns().add(colId);
        arpPacketTable.getColumns().add(colProtocolType);
        arpPacketTable.getColumns().add(colOperation);
        arpPacketTable.getColumns().add(colSrcHardwareAddr);
        arpPacketTable.getColumns().add(colDstHardwareAddr);
        arpPacketTable.getColumns().add(colSrcProtocolAddr);
        arpPacketTable.getColumns().add(colDstProtocolAddr);

        arpPacketTable.setPlaceholder(new Label("No data to display"));

        selectionModel = arpPacketTable.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
    }

    public void treeInit(){
        rootItem = new TreeItem<>("ArpPacket");
        arpHeaderTree.setRoot(rootItem);
        arpHeaderTree.setShowRoot(false);
    }
}
