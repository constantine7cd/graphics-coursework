#ifndef ANIMATOR_H
#define ANIMATOR_H

#include "../animatedModel/animatedModel.hpp"
#include "animation.h"
#include <map>
#include <string.h>
#include "keyframe.h"

class Animator
{
public:
    Animator() = default;
    Animator(animatedModel & model);

    void doAnimation(Animation & animn);
    void update();


private:
    animatedModel model;
    Animation currAnimn;
    double animTime;

    void incrAnimTime();
    std::map <std::string, Matrix4d <double>> calculateCurrPose();
    void applyPoseToBones(std::map <std::string, Matrix4d <double>> & currPose, Bone & bone,
                          Matrix4d <double> & parentTransform);

    std::vector <KeyFrame> getPrevNextFrames();
    double calcProg(KeyFrame prev, KeyFrame next);
    std::map <std::string, Matrix4d <double>> interpolatePoses(KeyFrame prev, KeyFrame next, double progr);



};

#endif // ANIMATOR_H
