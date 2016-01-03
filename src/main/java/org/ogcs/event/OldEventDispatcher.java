package org.ogcs.event;

/**
 * @author TinyZ on 2015/9/28.
 */
public interface OldEventDispatcher<E> {

    /**
     * Add new {@link EventListener} to dispatcher
     * @param type The event type
     * @param listener The EventListener
     */
    void addEventListener(Object type, EventListener<E> listener);

    /**
     * Remove the {@link EventListener} by unique listener id.
     * @param type The event type
     */
    void removeEventListener(Object type);

    /**
     * If the listener list exist special type event listener.
     * @param type The event type
     * @return Return true if the listener list exist special type event listener. otherwise false.
     */
    boolean hasEventListener(Object type);

    /**
     * Dispatch special event
     * @param event The dispatched event
     */
    void dispatchEvent(E event);
}
