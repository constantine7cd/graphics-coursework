#include "animatedModel.hpp"

animatedModel::animatedModel(VAO & model, Texture & texture, Bone & boneRoot, int boneCount)
{
    this->model = model;
    this->texture = texture;
    this->boneRoot = boneRoot;
    this->boneCount = boneCount;
    this->animator = new Animator();

    //bone inverse bind transform
}

animatedModel::~animatedModel()
{
    if (this->animator) {
        delete this->animator;
    }
}

void animatedModel::update()
{
    animator->update();
}

std::vector<Matrix4d<double> > animatedModel::getBoneTransforms()
{
    std::vector<Matrix4d<double>> bone_mtxs(this->boneCount);
    addBonesToArray(this->boneRoot, bone_mtxs);

    return bone_mtxs;
}

void animatedModel::addBonesToArray(Bone &headBone, std::vector<Matrix4d<double> > &bone_mtxs)
{
    bone_mtxs[headBone.idx] = headBone.get_global_transformtaion();

    for (Bone child: headBone.children) {
        addBonesToArray(child, bone_mtxs);
    }
}


