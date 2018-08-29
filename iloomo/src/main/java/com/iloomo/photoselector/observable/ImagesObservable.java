package com.iloomo.photoselector.observable;


import com.iloomo.photoselector.entity.LocalMedia;
import com.iloomo.photoselector.entity.LocalMediaFolder;
import com.iloomo.photoselector.tools.DebugUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * author：luck
 * project：PictureSelector
 * package：com.iloomo.photoselector.observable
 * email：893855882@qq.com
 * data：17/1/11
 */
public class ImagesObservable implements SubjectListener {
    //观察者接口集合
    private List<ObserverListener> observers = new ArrayList<>();

    private List<LocalMediaFolder> folders;
    private List<LocalMedia> medias;
    private List<LocalMedia> selectedImages;
    private static com.iloomo.photoselector.observable.ImagesObservable sObserver;

    private ImagesObservable() {
        folders = new ArrayList<>();
        medias = new ArrayList<>();
        selectedImages = new ArrayList<>();
    }

    public static com.iloomo.photoselector.observable.ImagesObservable getInstance() {
        if (sObserver == null) {
            synchronized (com.iloomo.photoselector.observable.ImagesObservable.class) {
                if (sObserver == null) {
                    sObserver = new com.iloomo.photoselector.observable.ImagesObservable();
                }
            }
        }
        return sObserver;
    }


    /**
     * 存储文件夹图片
     *
     * @param list
     */

    public void saveLocalFolders(List<LocalMediaFolder> list) {
        if (list != null) {
            folders = list;
        }
    }


    /**
     * 存储图片
     *
     * @param list
     */
    public void saveLocalMedia(List<LocalMedia> list) {
        medias = list;
    }


    /**
     * 读取图片
     */
    public List<LocalMedia> readLocalMedias() {
        if (medias == null) {
            medias = new ArrayList<>();
        }
        return medias;
    }

    /**
     * 读取所有文件夹图片
     */
    public List<LocalMediaFolder> readLocalFolders() {
        if (folders == null) {
            folders = new ArrayList<>();
        }
        return folders;
    }


    /**
     * 读取选中的图片
     */
    public List<LocalMedia> readSelectLocalMedias() {
        return selectedImages;
    }


    public void clearLocalFolders() {
        if (folders != null)
            folders.clear();
    }

    public void clearLocalMedia() {
        if (medias != null)
            medias.clear();
        DebugUtil.i("ImagesObservable:", "clearLocalMedia success!");
    }

    public void clearSelectedLocalMedia() {
        if (selectedImages != null)
            selectedImages.clear();
    }

    @Override
    public void add(ObserverListener observerListener) {
        observers.add(observerListener);
    }

    @Override
    public void remove(ObserverListener observerListener) {
        if (observers.contains(observerListener)) {
            observers.remove(observerListener);
        }
    }
}