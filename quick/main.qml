import QtQuick 2.15
import QtQuick.Controls 2.15
import QtQuick.Window 2.15
import QtQuick.Layouts 1.15
import Charts 1.0

Window {
    visible: true
    width: 640; height: 480

    Component.onCompleted: {
        console.log("created");
        aPieChart.name = "renamed!";
    }


    PieChart {
        id: aPieChart
        anchors.centerIn: parent
        width: 100; height: 100
        name: "A simple pie chart"
        color: "red"
    }

    MyToolbar {
        id: myToolbar
        width: parent.width
    }

    ColumnLayout {
        anchors.top: myToolbar.bottom
        //anchors.left: parent.left
        //anchors.right: parent.right
        RowLayout {
            id: row1
            Rectangle { width: 200; height: 200; color: "red" }
            Rectangle { width: 200; height: 200; color: "green" }
            Rectangle { width: 50; height: 50; color: "blue" }
        }
        RowLayout {
            //anchors.top: row1.bottom
            //anchors.left: parent.left
            Button {
                text: "Ok"
                onClicked: aPieChart.name = "Ok"
            }
            Button {
                text: "Cancel"
                onClicked: aPieChart.name = "Cancel"
            }
        }
    }
}
