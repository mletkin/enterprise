package org.ully.enterprise.energy;

import java.util.List;

import org.ully.enterprise.Loadable;
import org.ully.enterprise.Reactor;

/**
 * combination of supplier(s) and consumer(s) wired together.
 */
public class Circuit {

    public Reactor supplier;

    public List<Loadable> consumer;
}
