package org.sr_g3.config.app;

import org.sr_g3.service.StockManagementFunctionality;
import org.sr_g3.view.ProgramUi;

public class StockManagementSystem implements StockManagementFunctionality {
    public static void run(String[] args) throws Exception {
        ProgramUi.run();
    }

    @Override
    public void save() {

    }

    @Override
    public void unSaved() {

    }

    @Override
    public void backup() {

    }

    @Override
    public void restore() {

    }
}
