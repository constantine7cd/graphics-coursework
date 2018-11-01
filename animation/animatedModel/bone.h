#ifndef BONE_H
#define BONE_H

// Пока будем использовать  std::string и std::vector
#include <string>
#include <vector>
#include "structures/matrix/matrix.h"

#include "collada/collada.hpp"


class Bone
{
public:
    Bone() = default;
    explicit Bone(int idx, std::string name, Matrix4d<double> localTransformation);
    ~Bone() = default;

    void add_child(Bone child);

    Matrix4d<double> get_global_transformtaion();
    void set_global_transformation(Matrix4d<double> globalTransform);

    Matrix4d<double> get_bind_inverse_transformation();
    void calc_bind_inverse_transform(Matrix4d<double> predecessorBindTransform);

    int idx;
    std::string name;
    std::vector <Bone> children;

private:


    /*Изпользуются три типа матриц преобразования:
    -locaTransformation является преобразованием относительно положения "родителя"
    -bindInverseTransformation является преобразованием "предка" относительно МСК в обратном направлении
    -globalTransformation явлвяется преобразованием относительно МСК*/
    Matrix4d<double> localBindTransformation;
    Matrix4d<double> bindInverseTransformation;  //необходимо посчитать
    Matrix4d<double> globalTransformation;  //необходимо посчитать


};

#endif // BONE_H
