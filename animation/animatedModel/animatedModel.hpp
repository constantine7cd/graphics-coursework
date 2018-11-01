#ifndef ANIMATEDMODEL_H
#define ANIMATEDMODEL_H


#include <vector>
#include "structures/matrix/matrix.h"
#include "animation/animatedModel/bone.h"
#include "../animation/animator.h"
//Mocks for initial stage
struct VAO {};
struct Texture {};

//-------//

class animatedModel
{
public:
    explicit animatedModel(VAO & model, Texture & texture, Bone & boneRoot, int boneCount);
    animatedModel() = default;
    ~animatedModel();

    //delete VAo objects?
    void makeAnimation();

    VAO get_model() {
        return this->model;
    }

    Texture get_texture() {
        return this->texture;
    }

    Bone get_bone_root() {
        return this->boneRoot;
    }

    void update();

    std::vector<Matrix4d<double>> getBoneTransforms();
    void addBonesToArray(Bone &headbone, std::vector <Matrix4d<double>> &boneMatrices);

private:
    VAO model;
    Texture texture;
    Bone boneRoot;
    size_t boneCount;
    Animator* animator;

};

#endif // ANIMATEDMODEL_H
