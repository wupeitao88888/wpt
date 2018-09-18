package co.ryit.db.bean;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


/**
 * Created by wupeitao on 2018/1/7.
 */
@DatabaseTable(tableName = "tb_theme_index")
public class ThemeIndex implements CustomTabEntity {

    @DatabaseField(id = true, canBeNull = false)
    private String tabTitle;
    @DatabaseField
    private int tabSelectedIcon;
    @DatabaseField
    private int tabUnselectedIcon;

    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public void setTabSelectedIcon(int tabSelectedIcon) {
        this.tabSelectedIcon = tabSelectedIcon;
    }

    public void setTabUnselectedIcon(int tabUnselectedIcon) {
        this.tabUnselectedIcon = tabUnselectedIcon;
    }

    @Override
    public String getTabTitle() {
        return tabTitle;
    }

    @Override
    public int getTabSelectedIcon() {
        return tabSelectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return tabUnselectedIcon;
    }
}
