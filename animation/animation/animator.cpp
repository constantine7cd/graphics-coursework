#include "animator.h"

Animator::Animator(animatedModel &model)
{
    this->model = model;
    this->animTime = 0;
}

void Animator::doAnimation(Animation &animn)
{
    this->animTime = 0;
    this->currAnimn = animn;
}

void Animator::update()
{
    if (currAnimn == nullptr)
        return;

    incrAnimTime();
    std::map<std::string, Matrix4d <double>> currPose = calculateCurrPose();
    applyPoseToBones(currPose, model.get_bone_root(), Matrix4d <double>());
}

void Animator::incrAnimTime()
{
    //Здесь надо посмотреть!!!
    animationTime += 0.033;
}

std::map<std::string, Matrix4d<double> > Animator::calculateCurrPose()
{
    std::vector <KeyFrame> frames = getPrevNextFrames();
    double progr = calcProg(frames[0], frames[1]);

    return interpolatePoses(frames[0], frames[1], progr);
}

void Animator::applyPoseToBones(std::map<std::string, Matrix4d<double> > &currPose, Bone &bone,
                                Matrix4d<double> &parentTransform)
{
    //Matrix4d <double> currLocalTransfm = currPose.find(bone.name).se
    Matrix4d <double> currLocalTransfm;

    std::map::const_iterator pos = currPose.find(bone.name);

    if (pos == currPose.end()) {
        //raise error
    }
    else {
        currLocalTransfm = pos->second;
    }


    //Здесь нужно нормальное умножение матриц static, parent передается по ссылке!
    Matrix4d <double> currTransform = parentTransform.mul(currLocalTransfm);

    for (Bone b : bone.children) {
        applyPoseToBones(currPose, b, currTransform);
    }

    //multiplicate cuttTransform * getInverseBindTransform
    bone.set_global_transformation(currTransform);
}

std::vector<KeyFrame> Animator::getPrevNextFrames()
{
    std::vector <KeyFrame> allFrames = currAnimn.getKeyFrame();
    KeyFrame prev = allFrames[0];
    KeyFrame next = allFrames[0];

    for (int i = 1; i < allFrames.size(); i++) {
        next = allFrames[i];

        if (next.getFrameTime() > animTime)
            break;

        prev = allFrames[i];
    }

    std::vector <KeyFrame> res =  {prev, next};

    return res;

}

double Animator::calcProg(KeyFrame prev, KeyFrame next)
{
    double totalTime = next.getFrameTime() - prev.getFrameTime();
    double currTime = animTime - prev.getFrameTime();

    return currTime / totalTime;
}

std::map<std::string, Matrix4d<double> > Animator::interpolatePoses(KeyFrame prev, KeyFrame next, double progr)
{
    //not impltd
}
