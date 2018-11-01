#ifndef ANIMATION_H
#define ANIMATION_H

#include "keyframe.h"

class Animation
{
public:
    Animation() = default;
    explicit Animation(double lengthSec, std::vector <KeyFrame> & frames);
    
    double getLength();
    std::vector <KeyFrame> getKeyFrame();
    
private:
    double length;
    std::vector <KeyFrame> frames;
};

#endif // ANIMATION_H
