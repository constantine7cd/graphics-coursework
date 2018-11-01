#include "animation.h"



Animation::Animation(double lengthSec, std::vector <KeyFrame>  & frames)
{
    this->length = lengthSec;
    this->frames = frames;
}

double Animation::getLength()
{
    return this->length;
}

std::vector <KeyFrame> Animation::getKeyFrame()
{
    return this->frames;
}
