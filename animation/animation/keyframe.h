#ifndef KEYFRAME_H
#define KEYFRAME_H

#include <map>
#include "bonetransform.h"

class KeyFrame
{
public:
    KeyFrame() = default;
    explicit KeyFrame(double & frameTime,  std::map<std::string, BoneTransform> & boneFrames);

    double getFrameTime();
    std::map <std::string, BoneTransform> getBoneFrameTransforms();

private:
    double frameTime;
    std::map<std::string, BoneTransform> boneFrames;
};

#endif // KEYFRAME_H
