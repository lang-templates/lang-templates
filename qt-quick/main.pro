QT += quick

CONFIG += c++17
CONFIG += console

TARGET=PROGRAM

SOURCES += \
        main.cpp \
        piechart.cpp

HEADERS += \
    piechart.h

RESOURCES += qml.qrc
#resources.files = main.qml MyToolbar.qml
#resources.prefix = /QML
#RESOURCES += resources

RC_ICONS = app.ico
