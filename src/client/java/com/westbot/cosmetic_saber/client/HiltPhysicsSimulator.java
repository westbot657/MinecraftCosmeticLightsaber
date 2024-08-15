package com.westbot.cosmetic_saber.client;
import com.bulletphysics.dynamics.*;
import com.bulletphysics.dynamics.constraintsolver.Point2PointConstraint;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.broadphase.*;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.collision.dispatch.*;
import com.bulletphysics.linearmath.Transform;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

public class HiltPhysicsSimulator {

    private final DynamicsWorld dynamicsWorld;
    private final RigidBody hiltRigidBody;

    public HiltPhysicsSimulator() {
        BroadphaseInterface broadphase = new DbvtBroadphase();
        CollisionConfiguration collisionConfig = new DefaultCollisionConfiguration();
        CollisionDispatcher dispatcher = new CollisionDispatcher(collisionConfig);
        SequentialImpulseConstraintSolver solver = new SequentialImpulseConstraintSolver();
        dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfig);
        dynamicsWorld.setGravity(new Vector3f(0, 9.81f, 0));

        BoxShape hiltShape = new BoxShape(new Vector3f(2/16f, 9/16f, 2/16f));

        Transform startTransform = new Transform();
        startTransform.setIdentity();
        startTransform.origin.set(new Vector3f((float) 1/16f, (float) 9/16f, (float) 1/16f));

        float mass = 2f;
        Vector3f localInertia = new Vector3f(0, 0, 0);
        hiltShape.calculateLocalInertia(mass, localInertia);

        DefaultMotionState motionState = new DefaultMotionState(startTransform);
        RigidBodyConstructionInfo hiltRigidBodyCI = new RigidBodyConstructionInfo(mass, motionState, hiltShape, localInertia);
        hiltRigidBody = new RigidBody(hiltRigidBodyCI);

        hiltRigidBody.setFriction(0.01f);
        hiltRigidBody.setDamping(0.3f, 0.3f);
        hiltRigidBody.setActivationState(RigidBody.DISABLE_DEACTIVATION);
        dynamicsWorld.addRigidBody(hiltRigidBody);

        Vector3f attachmentPoint = new Vector3f((float) 1.00001/16f, (float) 9/16f, (float) 1/16f);
        Point2PointConstraint constraint = new Point2PointConstraint(hiltRigidBody, attachmentPoint);
        dynamicsWorld.addConstraint(constraint);

    }

    public Quat4f updateSimulation(float deltaTime, Vector3f playerVelocity) {
        Vector3f force = new Vector3f(playerVelocity);
        force.scale(100f);
        force.set(force.x, 0, force.z);
        hiltRigidBody.applyCentralForce(force);
        dynamicsWorld.stepSimulation(deltaTime, 1);
        Transform hiltTransform = new Transform();
        hiltRigidBody.getMotionState().getWorldTransform(hiltTransform);

        return hiltTransform.getRotation(new Quat4f());

    }
}