module com.umiskky {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires com.jfoenix;
    requires org.pcap4j.core;

    opens com.umiskky.view.main to javafx.fxml;

    exports com.umiskky;
}