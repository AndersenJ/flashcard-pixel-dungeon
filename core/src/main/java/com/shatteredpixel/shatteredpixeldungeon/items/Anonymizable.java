package com.shatteredpixel.shatteredpixeldungeon.items;

public abstract class Anonymizable extends Item
{
    public abstract void anonymize();

    public abstract boolean isKnown();
}