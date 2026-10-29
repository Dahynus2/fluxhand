package com.fluxhand.anim;

import net.minecraft.util.Hand;

/**
 * Marca "acabo de hacer clic derecho con un bloque" por mano.
 * Minecraft usa el mismo swing para golpear y para colocar, así que
 * esta marca (puesta por UseBlockCallback) permite distinguirlos.
 */
public final class PlaceTracker {
    private static final long WINDOW_NANOS = 350_000_000L; // 350 ms
    private static final long[] LAST = new long[2];

    private PlaceTracker() {}

    public static void mark(Hand hand) {
        LAST[hand.ordinal()] = System.nanoTime();
    }

    /** true si hubo un intento de colocar hace muy poco; consume la marca. */
    public static boolean consume(Hand hand) {
        int i = hand.ordinal();
        long t = LAST[i];
        LAST[i] = 0L;
        return t != 0L && (System.nanoTime() - t) < WINDOW_NANOS;
    }
}
