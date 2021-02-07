package de.dpunkt.myaktion.scheduler;

import de.dpunkt.myaktion.services.DonationService;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.inject.Inject;

@Singleton
public class SchedulerBean {

    @Inject
    private DonationService donationService;

    @Schedule(hour = "*", minute = "*/5", persistent = false)
    public void doTransferDonations() {
        donationService.transferDonations();
    }
}

