#ifndef QUATERNION_H
#define QUATERNION_H

#include <cmath>
#include "structures/matrix/matrix.h"

class Quaternion
{
public:
    Quaternion() = default;
    Quaternion(double x, double y, double z, double w);
    void normalize();

    Matrix4d <double> rotMtxFromQuatn();
    Quaternion fromMtx(Matrix4d <double> & mtx);
    Quaternion interpolate(Quaternion first, Quaternion second, double alpha);

private:
    double x, y, z, w;
};

#endif // QUATERNION_H
