module com.example.chat {
    requires javafx.controls;
    requires com.google.gson;
    requires json.path;
    exports com.example.chat;
    exports com.example.chat.scene;
    opens com.example.chat.struct to com.google.gson;
    opens com.example.chat.struct.Response to com.google.gson;
    opens com.example.chat.struct.JSON to com.google.gson;
}