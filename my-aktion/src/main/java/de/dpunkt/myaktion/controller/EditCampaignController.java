package de.dpunkt.myaktion.controller;

import de.dpunkt.myaktion.data.CampaignProducer;
import de.dpunkt.myaktion.model.Campaign;
import de.dpunkt.myaktion.util.Events.Added;
import de.dpunkt.myaktion.util.Events.Updated;

import jakarta.enterprise.event.Event;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named
public class EditCampaignController implements Serializable {
    private static final long serialVersionUID = 2815796004558360299L;

    @Inject
    @Added
    private Event<Campaign> campaignAddEvent;

    @Inject
    @Updated
    private Event<Campaign> campaignUpdateEvent;

    @Inject
    private CampaignProducer campaignProducer;

    public String doSave() {
        if (campaignProducer.isAddMode()) {
            campaignAddEvent.fire(campaignProducer.getSelectedCampaign());
        } else {
            campaignUpdateEvent.fire(campaignProducer.getSelectedCampaign());
        }
        return Pages.LIST_CAMPAIGNS;
    }

    public String doCancel() {
        return Pages.LIST_CAMPAIGNS;
    }

}
