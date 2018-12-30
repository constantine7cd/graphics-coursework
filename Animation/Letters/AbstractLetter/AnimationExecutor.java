package AbstractLetter;

import aniModel.AniModel;
import animation.FrameTime;
import scene.Camera;
import render.Render;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import settings.Settings;
import org.lwjgl.util.vector.Matrix4f;
import structures.MVertex;
import structures.Vector4f;
import transformations.Transform;

import java.util.List;

public class AnimationExecutor {
    public AnimationTimer getAtimer() {
        return atimer;
    }

    private AnimationTimer atimer;

    private int idx = 0;

    private AnimatedObject obj;
    private AniModel entity;

    private float animationTime;
    private float animationLength;

    private Transform objTransform;

    private String name;

    public void executeOne(AnimatedObject aniObj, render.Display display, Camera camera)
    {
        Render target = display.getBitmap();
        obj = new AnimatedObject(aniObj);
        entity = obj.getEntity();

        animationTime = entity.getAnimator().getAnimationTime();
        animationLength = obj.getAnimation().getLength();

        Transform objTransform = obj.getObjTransform();

        if (atimer == null)
        {

            atimer = new AnimationTimer() {

                @Override
                public  void handle(long now) {

                    if (entity.getAnimator().getAnimationTime() < animationLength - FrameTime.getIncTime())
                    {
                        entity.update();
                    }
                    else
                    {
                        this.stop();
                    }


                    Matrix4f[] transforms = entity.getJointTransforms();
                    transformModel(transforms, entity.getModel());

                    target.Clear(Settings.SCENE_BACKGROUND);
                    target.ClearDepthBuffer();

                    structures.Matrix4f vp = camera.GetViewProjection();

                    entity.getModel().Draw(target, vp, objTransform.GetTransformation(),
                            entity.getTexture());

                    display.update();

                }


            };
        }

        atimer.start();
        entity.getAnimator().setAnimationTime(0);

    }

    private void setUpObj(List<AnimatedObject> objList)
    {
        obj = new AnimatedObject(objList.get(idx++));
        entity = obj.getEntity();

        animationTime = entity.getAnimator().getAnimationTime();
        animationLength = obj.getAnimation().getLength();

        name = obj.getName();

        objTransform = obj.getObjTransform();
    }


    public void executeList(List<AnimatedObject> objList, render.Display display, Camera camera)
    {
        if (objList.size() == 0)
            return;

        Render target = display.getBitmap();

        setUpObj(objList);

        display.getCanvas().getGraphicsContext2D().setFill(Color.web("#4bf221"));


        if (atimer == null)

        {

            atimer = new AnimationTimer() {

                @Override
                public  void handle(long now) {

                    if (entity.getAnimator().getAnimationTime() < animationLength - FrameTime.getIncTime())
                    {
                        entity.update();
                    }
                    else
                    {
                        entity.getAnimator().setAnimationTime(0);

                        if (idx < objList.size()) {

                            setUpObj(objList);
                        }
                        else
                        {
                            this.stop();
                        }
                    }

                    for (int i = 0; i < 1; ++i) {
                        Matrix4f[] transforms = entity.getJointTransforms();
                        transformModel(transforms, entity.getModel());

                        target.Clear(Settings.SCENE_BACKGROUND);
                        target.ClearDepthBuffer();

                        structures.Matrix4f vp = camera.GetViewProjection();

                        entity.getModel().Draw(target, vp, objTransform.GetTransformation(),
                                entity.getTexture());


                    }


                    display.getCanvas().getGraphicsContext2D().fillText(name, 10, 20);
                }
            };
        }

        atimer.start();
        entity.getAnimator().setAnimationTime(0);
    }



    public void resume()
    {
        atimer.start();
    }

    public void pause()
    {
        atimer.stop();
    }

    private static float[] Mul(Matrix4f mtx, float[] r)
    {

        float m0 = mtx.m00 * r[0] + mtx.m10 * r[1] + mtx.m20 * r[2] + mtx.m30 * r[3];
        float m1 = mtx.m01 * r[0] + mtx.m11 * r[1] + mtx.m21 * r[2] + mtx.m31 * r[3];
        float m2 = mtx.m02 * r[0] + mtx.m12 * r[1] + mtx.m22 * r[2] + mtx.m32 * r[3];
        float m3 = mtx.m03 * r[0] + mtx.m13 * r[1] + mtx.m23 * r[2] + mtx.m33 * r[3];

        float[] res = {m0, m1, m2, m3};

        return res;
    }


    private static MVertex applyJointTransformations(Matrix4f[] transforms, MVertex v)
    {
        float [] totalLocalPos = {0, 0, 0, 0};
        float [] totalNormal = {0, 0, 0, 0};


        for (int i = 0; i < Settings.MAX_WEIGHTS; ++i) {

            float[] localPosition = Mul(transforms[v.GetJointIds()[i]], v.GetPosition().toArray(1));

            for (int j = 0; j < 4; j++)
            {
                localPosition[j] *= v.GetVertexWeights()[i];
            }

            for (int j = 0; j < 4; j++)
                totalLocalPos[j] += localPosition[j];


            float[] worldNormal = Mul(transforms[v.GetJointIds()[i]], v.GetNormal().toArray(0));

            for (int j = 0; j < 4; ++j)
                totalNormal[j] += worldNormal[j] * v.GetVertexWeights()[i];
        }

        Vector4f res_pos = new Vector4f(totalLocalPos[0], totalLocalPos[1], totalLocalPos[2], totalLocalPos[3]);
        Vector4f res_normals = new Vector4f(totalNormal[0], totalNormal[1], totalNormal[2], 0);

        MVertex res =  new MVertex(res_pos, v.GetTexCoords(), res_normals, v.GetJointIds(), v.GetVertexWeights());

        return res;
    }

    private static void transformModel(Matrix4f[] transforms, entity.Mesh mesh)
    {
        for (int i = 0; i < mesh.getVertices().size(); i++) {
            mesh.getTVertices().set(i, applyJointTransformations(transforms, mesh.getVertices().get(i)));
        }
    }
}


