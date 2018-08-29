package com.iloomo.photoselector.observable;


/**
 * author：luck
 * project：PictureSelector
 * package：com.iloomo.photoselector.observable
 * email：893855882@qq.com
 * data：17/1/16
 */
public interface SubjectListener {
    void add(com.iloomo.photoselector.observable.ObserverListener observerListener);

    void remove(ObserverListener observerListener);
}
