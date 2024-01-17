package de.cech12.unlitcampfire.mixinaccess;

public interface ICampfireBlockEntityMixin {

    boolean unlitCampfire$isSoulCampfire();

    int unlitCampfire$getLitTime();

    boolean unlitCampfire$addLitTime(int litTimeToAdd);

    boolean unlitCampfire$removeLitTime(int litTimeToRemove);

}
