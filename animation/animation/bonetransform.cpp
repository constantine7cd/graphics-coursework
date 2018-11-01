#include "bonetransform.h"

BoneTransform::BoneTransform(Vector &position, Quaternion &rotation)
{
    this->position = position;
    this->rotation = rotation;
}

Matrix4d BoneTransform::getLocaTransform()
{
    Matrix4d mtx;
    mtx.eye;
    //translate to position
    //rotate matrix

    return mtx;
}

BoneTransform BoneTransform::interpolate(BoneTransform &fframe, BoneTransform &sframe, double progr)
{
    Vector new_pos = interpolate(fframe.position, sframe.position, progr);
    Quaternion new_rot;//interpolate quaternion NOT IMPLEMENTED
    //interpolate quaternion

    return BoneTransform(new_pos, new_rot);
}

Vector BoneTransform::interpolate(Vector start, Vector end, double prog)
{
    Vector res = start + (end - start) * prog;

    return res;
}
