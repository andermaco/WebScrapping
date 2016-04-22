package com.andermaco.scrapping.bus;


import com.squareup.otto.Bus;


/**
 * Singleton Bus pattern
 */
public class ScrappingBus {

    private static Bus instance = null;

    private ScrappingBus()
    {
        instance = new Bus();
    }

    public static Bus getInstance()
    {
        if(instance == null)
        {
            instance = new Bus();
        }
        return instance;
    }
}
