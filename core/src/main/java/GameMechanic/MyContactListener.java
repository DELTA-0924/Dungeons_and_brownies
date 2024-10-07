package GameMechanic;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class MyContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        // Код, который будет выполняться при начале столкновения
        // Например, можно узнать, какие объекты столкнулись
        System.out.println("Collision started between: " +
            contact.getFixtureA().getBody().getUserData() + " and " +
            contact.getFixtureB().getBody().getUserData());
    }

    @Override
    public void endContact(Contact contact) {
        // Код, который будет выполняться при окончании столкновения
        System.out.println("Collision ended between: " +
            contact.getFixtureA().getBody().getUserData() + " and " +
            contact.getFixtureB().getBody().getUserData());
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

