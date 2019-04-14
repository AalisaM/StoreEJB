package com.store.display.service.implementation;

import com.store.display.service.PushService;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ApplicationScoped
public class PushServiceImpl extends PushService implements Serializable {
    @Inject
    @Push(channel = "clock")
    private PushContext push;

    @Override
    public void reload() {
        push.send("reload");
    }
}
