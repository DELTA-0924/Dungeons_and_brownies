package GameMechanic;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import models.Enemy;
import models.Player;

public class MyContactListener implements ContactListener {
    public boolean isPlayerTouchingEnemy = false;
    public Enemy enemy;
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Body bodyA = fixtureA.getBody();
        Body bodyB = fixtureB.getBody();

        Object userDataA = bodyA.getUserData();
        Object userDataB = bodyB.getUserData();

        // Проверка, является ли один объект игроком, а другой врагом
        if (userDataA instanceof Player && userDataB instanceof Enemy) {
            System.out.println("Player contacted with Enemy");
            Enemy enemy = (Enemy) userDataB;
            enemy.attackPlayer();
            isPlayerTouchingEnemy = true;
        } else if (userDataA instanceof Enemy && userDataB instanceof Player) {
            System.out.println("Enemy contacted with Player");
            Enemy enemy = (Enemy) userDataA;
            enemy.attackPlayer();
            isPlayerTouchingEnemy = true;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Код, который выполняется перед решением столкновения (если нужно)
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Код, который выполняется после решения столкновения (если нужно)
    }
}

