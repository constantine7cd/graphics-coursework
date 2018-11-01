#ifndef BONETRANSFORM_H
#define BONETRANSFORM_H

#include "structures/matrix/matrix.h"
#include "quaternion.h"

class Vector
{

};

class BoneTransform
{
public:
    BoneTransform() = default;
    explicit BoneTransform(Vector & position, Quaternion & rotation);

    Matrix4d getLocaTransform();
    BoneTransform interpolate(BoneTransform &fframe, BoneTransform &sframe, double progr);
    Vector interpolate(Vector start, Vector end, double prog);
    

private:
    Vector position; //position relative to the parent joint
    Quaternion rotation; //relative to the parent
};

#endif // BONETRANSFORM_H
