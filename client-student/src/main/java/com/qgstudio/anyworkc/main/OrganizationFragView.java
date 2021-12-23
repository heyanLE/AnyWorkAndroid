package com.qgstudio.anyworkc.main;


import com.qgstudio.anyworkc.common.PreLoading;
import com.qgstudio.anyworkc.data.model.Organization;
import com.qgstudio.anyworkc.mvp.BaseView;

import java.util.List;

public interface OrganizationFragView extends BaseView, PreLoading {
    void addOrganization(Organization organization);
    void addOrganizations(List<Organization> organizations);
    void removeOrganization(int position);
    void startPaperAty(int organizatonId);
    void updateItemJoinStatus(int position, boolean isJoin);
    void sendUpdateBroadCast();
    void destroySelf();
}
