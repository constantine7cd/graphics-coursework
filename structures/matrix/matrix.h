#ifndef MATRIX_H
#define MATRIX_H

#include "matrix_base.h"

template <class T>
class Matrix4d : public Matrix_base {

public:
    Matrix4d() = default;
    ~Matrix4d() = default;

    Matrix4d<T> operator * (Matrix4d<T> & m);
    Matrix4d<T> invert();
};

template <class T>
Matrix4d<T> Matrix4d<T>::operator * (Matrix4d<T> &m)
{
    // Это заглушка
    Matrix4d<T> g;
    return g;
}

template <class T>
Matrix4d<T> Matrix4d<T>::invert()
{
    // Это тоже заглушка
    Matrix4d g;
    return g;
}

#endif // MATRIX_H
