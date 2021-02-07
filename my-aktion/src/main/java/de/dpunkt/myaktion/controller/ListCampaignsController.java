package de.dpunkt.myaktion.controller;

import de.dpunkt.myaktion.data.CampaignProducer;
import de.dpunkt.myaktion.model.Campaign;
import de.dpunkt.myaktion.model.Donation;
import de.dpunkt.myaktion.services.DonationService;
import de.dpunkt.myaktion.util.Events.Deleted;

import jakarta.enterprise.event.Event;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@ViewScoped
@Named
public class ListCampaignsController implements Serializable {

    private static final long serialVersionUID = 8693277383648025822L;

    @Inject
    private CampaignProducer campaignProducer;

    @Inject
    private DonationService donationService;

    @Inject
    @Deleted
    private Event<Campaign> campaignDeleteEvent;

    private Campaign campaignToDelete;

    public String doAddCampaign() {
        campaignProducer.prepareAddCampaign();
        return Pages.EDIT_CAMPAIGN;
    }

    public String doEditCampaign(Campaign campaign) {
        campaignProducer.prepareEditCampaign(campaign);
        return Pages.EDIT_CAMPAIGN;
    }

    public String doEditDonationForm(Campaign campaign) {
        campaignProducer.setSelectedCampaign(campaign);
        return Pages.EDIT_DONATION_FORM;
    }

    public String doListDonations(Campaign campaign) {
        final List<Donation> donations = donationService.getDonationList(campaign.getId());
        campaign.setDonations(donations);
        campaignProducer.setSelectedCampaign(campaign);
        return Pages.LIST_DONATIONS;
    }

    public void doDeleteCampaign(Campaign campaign) {
        this.campaignToDelete = campaign;
    }

    public void commitDeleteCampaign() {
        campaignDeleteEvent.fire(campaignToDelete);
    }

}
