package com.iloomo.photoselector.observable;

import com.iloomo.photoselector.entity.LocalMedia;
import com.iloomo.photoselector.entity.LocalMediaFolder;

import java.util.List;

/**
 * author：luck
 * project：PictureSelector
 * package：com.iloomo.photoselector.observable
 * email：893855882@qq.com
 * data：17/1/16
 */
public interface ObserverListener {
    void observerUpFoldersData(List<LocalMediaFolder> folders);

    void observerUpSelectsData(List<LocalMedia> selectMedias);
}
