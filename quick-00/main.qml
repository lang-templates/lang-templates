import QtQuick
import QtQuick.Window
import QtQuick.Layouts

Window {
    visible: true
    width: 640; height: 480

    MyToolbar {
        id: myToolbar
        width: parent.width
    }

    RowLayout {
        anchors.top: myToolbar.bottom
        anchors.left: parent.left
        anchors.right: parent.right
        anchors.bottom: parent.bottom
        Rectangle { width: 200; height: 200; color: "red" }
        Rectangle { width: 200; height: 200; color: "green" }
        Rectangle { width: 50; height: 50; color: "blue" }
    }
}
