#include "bone.h"
#include <iostream>

Bone::Bone(int idx, std::string name, Matrix4d<double> localBindTransformation)
{
    this->idx = idx;
    this->name = name;
    this->localBindTransformation = localBindTransformation;
}

void Bone::add_child(Bone child)
{
    this->children.push_back(child);
}

Matrix4d<double> Bone::get_global_transformtaion()
{
    return this->globalTransformation;
}

void Bone::set_global_transformation(Matrix4d<double> globalTransform)
{
    this->globalTransformation = globalTransform;
}

Matrix4d<double> Bone::get_bind_inverse_transformation()
{
    return this->bindInverseTransformation;
}

void Bone::calc_bind_inverse_transform(Matrix4d<double> predecessorBindTransform)
{
    Matrix4d<double> bindTransform = predecessorBindTransform * this->localBindTransformation;
    this->bindInverseTransformation = bindTransform.invert();

    for (Bone b: this->children) {
        b.calc_bind_inverse_transform(bindTransform);
    }
}
