#-------------------------------------------------
#
# Project created by QtCreator 2018-09-03T23:30:58
#
#-------------------------------------------------

QT       += core gui widgets

TARGET = graphics-coursework
TEMPLATE = app

# The following define makes your compiler emit warnings if you use
# any feature of Qt which has been marked as deprecated (the exact warnings
# depend on your compiler). Please consult the documentation of the
# deprecated API in order to know how to port your code away from it.
DEFINES += QT_DEPRECATED_WARNINGS

# You can also make your code fail to compile if you use deprecated APIs.
# In order to do so, uncomment the following line.
# You can also select to disable deprecated APIs only up to a certain version of Qt.
#DEFINES += QT_DISABLE_DEPRECATED_BEFORE=0x060000    # disables all the APIs deprecated before Qt 6.0.0

CONFIG += c++11

SOURCES += \
    GUI/mainwindow.cpp \
    main.cpp \
    animation/loader/loader.cpp \
    animation/model/model.cpp \
    collada/collada.cpp \
    exceptions/base_excpn.cpp \
    render/light/light.cpp \
    render/texturing/texture.cpp \
    render/tmpGL/glrender.cpp \
    render/tracing/ray_tracing.cpp \
    scene/scene.cpp \
    structures/vector/vector_base.cpp \
    animation/animatedModel/animatedModel.cpp \
    animation/animatedModel/bone.cpp \
    animation/animation/animation.cpp \
    animation/animation/keyframe.cpp \
    animation/animation/bonetransform.cpp \
    animation/animation/quaternion.cpp \
    animation/animation/animator.cpp \
    parsers/dae/xml/xmlnode.cpp


HEADERS += \
    GUI/mainwindow.h \
    collada/collada.hpp \
    render/light/light.hpp \
    render/texturing/texture.hpp \
    render/tmpGL/glrender.hpp \
    render/tracing/ray_tracing.hpp \
    scene/scene.h \
    animation/animatedModel/animatedModel.hpp \
    animation/animatedModel/bone.h \
    structures/matrix/matrix_base.h \
    structures/matrix/matrix.h \
    animation/animation/animation.h \
    animation/animation/keyframe.h \
    animation/animation/bonetransform.h \
    animation/animation/quaternion.h \
    animation/animation/animator.h \
    parsers/dae/xml/xmlnode.h


FORMS += \
    GUI/mainwindow.ui

# Default rules for deployment.
qnx: target.path = /tmp/$${TARGET}/bin
else: unix:!android: target.path = /opt/$${TARGET}/bin
!isEmpty(target.path): INSTALLS += target
