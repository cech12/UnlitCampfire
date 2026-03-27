package de.cech12.unlitcampfire.mixinaccess;

public interface ICampfireBlockEntityMixin {

    boolean unlitCampfire$isSoulCampfire();

    long unlitCampfire$getLitTime();

    boolean unlitCampfire$addLitTime(long litTimeToAdd);

    boolean unlitCampfire$removeLitTime(long litTimeToRemove);

}
