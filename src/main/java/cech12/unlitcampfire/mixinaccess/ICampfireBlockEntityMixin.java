package cech12.unlitcampfire.mixinaccess;

public interface ICampfireBlockEntityMixin {

    boolean isSoulCampfire();

    int getLitTime();

    boolean addLitTime(int litTimeToAdd);

    boolean removeLitTime(int litTimeToRemove);

}
