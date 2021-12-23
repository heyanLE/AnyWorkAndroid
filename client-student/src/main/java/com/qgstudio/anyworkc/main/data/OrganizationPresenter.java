package com.qgstudio.anyworkc.main.data;

import com.qgstudio.anyworkc.main.NewOrganizationActivity;
import com.qgstudio.anyworkc.main.OrganizationFragView;
import com.qgstudio.anyworkc.mvp.BasePresenter;

/**
 * @author Yason 2017/8/13.
 */

public interface OrganizationPresenter extends BasePresenter<OrganizationFragView> {
    void getAllOrganization();
    void getJoinOrganization();
    void updateJoinOrganization();
    void joinOrganization(int organizationId, String password, int position);
    void leaveOrganization(int organizationId, NewOrganizationActivity activity);
}
