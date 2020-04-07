package pers.defoliation.magic.entity;

import common.defoliation.mod.mite.register.Register;
import pers.defoliation.magic.entity.client.PigZombieLordRender;

public class EntityManager {

    public static final EntityManager INSTANCE = new EntityManager();

    public void register() {
        Register.registerEntity(PigZombieLord.class, () -> new PigZombieLordRender());
    }

}
