#include "quaternion.h"

Quaternion::Quaternion(double x, double y, double z, double w)
{
    this->x = x;
    this->y = y;
    this->z = z;
    this->w = w;
    normalize();
}

void Quaternion::normalize()
{
    double denom = sqrt(w * w + x * x + y * y + z * z);

    w /= denom;
    x /= denom;
    y /= denom;
    z /= denom;
}

Matrix4d<double> Quaternion::rotMtxFromQuatn()
{
    Matrix4d <double> res = {1 - 2 * (y * y + x * x), 2 * (x * y - z * w), 2 * (x * x + x * w), 0,
                            2 * (x * y + z * w), 1 - 2 * (x * x + z * z), 2 * (y * z - x * w), 0,
                            2 * (x * z - y * w), 2 * (y * z + x * w), 1 - 2 * (x * x + y * y), 0,
                            0, 0, 0, 1};

    return res;
}

Quaternion Quaternion::fromMtx(Matrix4d<double> &mtx)
{
    double x, y, z, w;
    double diag = mtx[0][0] + mtx[1][1] + mtx[2][2];

    if (diag > 0) {
        double w4  = sqrt(diag + 1.) * 2.;
        w = w4 / 4.;
        x = (mtx[2][1] - mtx[1][2] ) / w4;
        y = (mtx[0][2] - mtx[2][0]) / w4;
        z = (mtx[1][0] - mtx[0][1]) / w4;
    }
    else if ((mtx[0][0] > mtx[1][1]) && (mtx[0][0] > mtx[2][2])) {
        double x4 = sqrt(1. + mtx[0][0] - mtx[1][1] - mtx[2][2]) * 2.;
        w = (mtx[2][1] - mtx[1][2]) / x4;
        x = x4 / 4.;
        y = (mtx[0][1] + mtx[1][0]) / x4;
        z = (mtx[0][2] + mtx[2][0]) / x4;
    }
    else if (mtx[1][1] > mtx[2][2]) {
        double y4 = sqrt(1. + mtx[1][1] - mtx[0][0] - mtx[2][2]) * 2.;
        w = (mtx[0][2] - mtx[2][0]) / y4;
        x = (mtx[0][1] + mtx[1][0]) / y4;
        y = y4 / 4.;
        z = (mtx[1][2] + mtx[2][1]);
    }
    else {
        double z4 = sqrt(1. + mtx[2][2] - mtx[0][0] - mtx[1][1]) * 2.;
        w = (mtx[1][0] - mtx[0][1]) / z4;
        x = (mtx[0][2] + mtx[2][0]) / z4;
        y = (mtx[1][2] + mtx[2][1]) / z4;
        z = z4 / 4.;
    }

    return Quaternion(x, y, z, w);
}

Quaternion Quaternion::interpolate(Quaternion first, Quaternion second, double alpha)
{
    Quaternion res = Quaternion(0, 0, 0, 1);
    double dot = first.w * second.w + first.x * second.x + first.y * second.y + first.z * first.z;
    double alphaIvers = 1. - alpha;

    if (dot < 0) {
        res.w = alphaIvers * first.w + alpha * (-second.w);
        res.x = alphaIvers * first.x + alpha * (-second.x);
        res.y = alphaIvers * first.y + alpha * (-second.y);
        res.z = alphaIvers * first.z + alpha * (-second.z);
    }
    else {
        res.w = alphaIvers * first.w + alpha * (second.w);
        res.x = alphaIvers * first.x + alpha * (second.x);
        res.y = alphaIvers * first.y + alpha * (second.y);
        res.z = alphaIvers * first.z + alpha * (second.z);
    }

    res.normalize();

    return res;
}


