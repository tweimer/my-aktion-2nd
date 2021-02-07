package de.dpunkt.myaktion.controller;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;

@ViewScoped
@Named
public class ListDonationsController implements Serializable {
    private static final long serialVersionUID = 437878972432L;

    public String doOk() {
        return Pages.LIST_CAMPAIGNS;
    }

}
