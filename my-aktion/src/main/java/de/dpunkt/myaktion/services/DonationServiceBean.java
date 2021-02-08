package de.dpunkt.myaktion.services;

import de.dpunkt.myaktion.model.Campaign;
import de.dpunkt.myaktion.model.Donation;
import de.dpunkt.myaktion.model.Donation.Status;
import de.dpunkt.myaktion.monitor.ws.DonationDelegatorService;
import de.dpunkt.myaktion.services.exceptions.ObjectNotFoundException;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Stateless
public class DonationServiceBean implements DonationService {
    @Inject
    private EntityManager entityManager;

    @Inject
    private Logger logger;

    @Override
    @RolesAllowed("Organizer")
    public List<Donation> getDonationList(Long campaignId) {
        Campaign managedCampaign = entityManager.find(Campaign.class, campaignId);
        List<Donation> donations = managedCampaign.getDonations();
        donations.size();
        return donations;
    }

    @Override
    @PermitAll
    public void addDonation(Long campaignId, Donation donation) {
        try {
            DonationDelegatorService delegatorService = new DonationDelegatorService();
            delegatorService.getDonationDelegatorPort()
                    .sendDonation(campaignId, donation);
        } catch (Exception e) {
            logger.log(Level.SEVERE,
                    "Spende nicht weitergeleitet. Läuft der Glassfish?", e);
        }
        Campaign managedCampaign = entityManager.find(Campaign.class, campaignId);
        donation.setCampaign(managedCampaign);
        entityManager.persist(donation);
    }

    @Override
    @PermitAll
    public void transferDonations() {
        logger.log(Level.INFO, "log.transferDonation.start");
        TypedQuery<Donation> query =
                entityManager.createNamedQuery(Donation.findByStatus, Donation.class);
        query.setParameter("status", Status.IN_PROCESS);
        List<Donation> donations = query.getResultList();
        donations.forEach(donation -> donation.setStatus(Status.TRANSFERRED));
        logger.log(Level.INFO, "log.transferDonation.done", new Object[]{donations.size()});
    }

    @Override
    @PermitAll
    public List<Donation> getDonationListPublic(Long campaignId) throws ObjectNotFoundException {
        Campaign managedCampaign = entityManager.find(Campaign.class, campaignId);
        if (managedCampaign == null) throw new ObjectNotFoundException();
        List<Donation> donations = managedCampaign.getDonations();
        final Function<Donation, Donation> donationFilter = donation -> {
            Donation filtered = new Donation();
            filtered.setAmount(donation.getAmount());
            filtered.setDonorName(donation.getDonorName());
            return filtered;
        };
        return donations.stream().map(donationFilter).collect(Collectors.toList());
    }
}
