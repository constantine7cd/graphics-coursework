package AbstractLetter;

import aniModel.AniModel;
import animation.Animation;
import render.Bitmap;
import loaders.AnimatedModelLoader;
import loaders.AnimationLoader;
import structures.Quaternion;
import structures.Vector4f;
import transformations.Transform;
import utils.MFile;

public class AnimatedObject {

    public AniModel getEntity() {
        return entity;
    }

    public Animation getAnimation() {
        return animation;
    }

    public Transform getObjTransform() {
        return objTransform;
    }

    public String getName() {
        return name;
    }

    private AniModel entity;
    private Animation animation;
    private Transform objTransform;



    private String name;
    //private float currIncTime;

    //private float animationTime;
    //private float animationLength;


    //private AnimationTimer atimer;

    public AnimatedObject(AnimatedObject obj) {
        this(obj.getEntity(), obj.getAnimation(), obj.getObjTransform(), obj.getName());
    }

    public AnimatedObject(AniModel model, Animation animation, Transform transform, String name)
    {
        this.entity = model;
        this.animation = animation;
        this.objTransform = transform;
        this.name = name;
    }

    public AnimatedObject(MFile geometriesFile, MFile animationFile, Bitmap texture, String name) {
        this.entity = AnimatedModelLoader.loadEntity(geometriesFile, texture);
        this.animation = AnimationLoader.loadAnimation(animationFile);
        this.entity.doAnimation(animation);
        this.name = name;


        this.objTransform = new Transform(new structures.Vector4f(0,-2.5f,6f));
        this.objTransform = objTransform.Rotate(new Quaternion(new Vector4f(0,1,0), 110));

        //Transform enTrfm = new Transform(new Vector4f(0,-1.0f,0.0f));

    }

}