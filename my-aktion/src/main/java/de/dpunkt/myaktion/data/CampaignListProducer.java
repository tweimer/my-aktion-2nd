package de.dpunkt.myaktion.data;

import de.dpunkt.myaktion.model.Campaign;
import de.dpunkt.myaktion.services.CampaignService;
import de.dpunkt.myaktion.util.Events.Added;
import de.dpunkt.myaktion.util.Events.Deleted;
import de.dpunkt.myaktion.util.Events.Updated;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@RequestScoped
public class CampaignListProducer {

    private List<Campaign> campaigns;

    @Inject
    private CampaignService campaignService;

    @PostConstruct
    public void init() {
        campaigns = campaignService.getAllCampaigns();
    }

    @Produces
    @Named
    public List<Campaign> getCampaigns() {
        return campaigns;
    }

    public void onCampaignAdded(@Observes @Added Campaign campaign) {
        campaignService.addCampaign(campaign);
        init();
    }

    public void onCampaignDeleted(@Observes @Deleted Campaign campaign) {
        campaignService.deleteCampaign(campaign);
        init();
    }

    public void onAktionUpdated(@Observes @Updated Campaign campaign) {
        campaignService.updateCampaign(campaign);
        init();
    }

}
