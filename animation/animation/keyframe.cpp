#include "keyframe.h"


KeyFrame::KeyFrame(double &frameTime, std::map<std::string, boneTransform> &boneFrames)
{
    this->frameTime = frameTime;
    this->boneFrames = boneFrames;
}

double KeyFrame::getFrameTime()
{
    return this->frameTime;
}

std::map<std::string, boneTransform> KeyFrame::getBoneFrameTransforms()
{
    return this->boneFrames;
}
