/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.gadgets.GadgetManager) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 31/05/15 00:51.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.gadgets;

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.gadgets.gadgets.*;
import fr.schawnndev.sql.SQLManager;
import fr.schawnndev.utils.Cooldown;
import fr.schawnndev.utils.ResetBlock;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

public class GadgetManager {

    /**
     *  Gadgets
     */

    @Getter
    private static GadgetFireBall gadgetFireBall = new GadgetFireBall();

    @Getter
    private static GadgetPaintball gadgetPaintball = new GadgetPaintball();

    @Getter
    private static GadgetTNT gadgetTNT = new GadgetTNT();

    @Getter
    private static GadgetEncre gadgetEncre = new GadgetEncre();

    @Getter
    private static GadgetGlace gadgetGlace = new GadgetGlace();

    @Getter
    private static GadgetCanon gadgetCanon = new GadgetCanon();

    @Getter
    private static GadgetApple gadgetApple = new GadgetApple();

    @Getter
    private static GadgetPeche gadgetPeche = new GadgetPeche();

    @Getter
    private static GadgetDoubleJump gadgetDoubleJump = new GadgetDoubleJump();

    @Getter
    private static GadgetArtifice gadgetArtifice = new GadgetArtifice();

    @Getter
    private static GadgetGateauEmpoisonne gadgetGateauEmpoisonne = new GadgetGateauEmpoisonne();

    @Getter
    private static GadgetMusic gadgetMusic = new GadgetMusic();


    /**
     *  Etc
     */

    @Getter
    private static Map<UUID, String> activeGadgets = new HashMap<>();

    @Getter
    private static List<Cooldown> cooldowns = new ArrayList<>();

    /**
     *  ResetBlock
     */

    @Getter
    private static List<ResetBlock> resetBlocks = new ArrayList<>();

    /**
     *
     * @param location The location to check
     * @return If the location is already a resetblock
     */

    public static boolean isAlreadyResetBlock(Location location){

        for(ResetBlock resetBlock : resetBlocks)
            if(resetBlock.getLocation().equals(location))
                return true;

        return false;
    }

    /**
     * @param block The block to check
     * @return If the block is already a resetblock
     */

    public static boolean isAlreadyResetBlock(Block block){

        for(ResetBlock resetBlock : resetBlocks)
            if(resetBlock.getLocation().equals(block.getLocation()))
                return true;

        return false;
    }

    /**
     * @param player The player to check
     * @param gadget The gadget to check
     * @return If the player has this gadget active
     */

    public static boolean hasGadget(Player player, String gadget){
        final UUID uuid = player.getUniqueId();

        return activeGadgets.containsKey(uuid) && activeGadgets.get(uuid).equalsIgnoreCase(gadget);
    }

    /**
     * @param player The player to check
     * @param cosmetique The cosmetique to check
     * @return If player has cooldown for this cosmetique
     */

    public static boolean hasCooldown(Player player, CosmetiqueManager.Cosmetique cosmetique){
        for(Cooldown c : cooldowns)
            if(c.getCosmetique() == cosmetique && c.getPlayer().equals(player.getUniqueId()))
                return true;
        return false;
    }

    /**
     * @param player The player to check
     * @param cosmetique The cosmetique to check
     * @return If player is in cooldown for this cosmetique
     */

    public static boolean isInCooldown(Player player, CosmetiqueManager.Cosmetique cosmetique){
        if(!hasCooldown(player, cosmetique)) return false;
        Cooldown cooldown = getCooldown(player, cosmetique);

        return cooldown.isStarted();
    }

    /**
     * @param player The player to check
     * @param cosmetique The cosmetique to check
     * @return The cooldown message for the player
     */

    public static String getString(Player player, CosmetiqueManager.Cosmetique cosmetique){
        return hasCooldown(player, cosmetique) ? getCooldown(player, cosmetique).getMessage() : ("§cERREUR: NullPointerException (GadgetManager.class , line: 92)");
    }

    /**
     * @param player The player to check
     * @param cosmetique The cosmetique to check
     * @return The cooldown of the player
     */

    public static Cooldown getCooldown(Player player, CosmetiqueManager.Cosmetique cosmetique){
        for(Cooldown c : cooldowns)
            if(c.getCosmetique() == cosmetique && c.getPlayer().equals(player.getUniqueId()))
                return c;

        return null;
    }

    /**
     * @param cooldown The cooldown to add to the cooldown list
     */

    public static void addCooldown(Cooldown cooldown){
        cooldowns.add(cooldown);
    }

    /**
     * @param cooldown The cooldown to remove from the cooldown list
     */

    public static void removeCooldown(Cooldown cooldown){
        cooldowns.remove(cooldown);
    }

    /**
     * @param player The player to get
     * @return The MySQL gadget name of the active gadget from the player
     */

    public static String getGadget(Player player){
        final UUID uuid = player.getUniqueId();

        if(activeGadgets.containsKey(uuid))
            return activeGadgets.get(uuid);

        return "aucun";
    }

    /**
     * @param player The player to check
     * @return If player has or not a gadget
     */

    public static boolean hasGadgetActive(Player player){
        final UUID uuid = player.getUniqueId();

        return activeGadgets.containsKey(uuid) && !activeGadgets.get(uuid).equalsIgnoreCase("aucun");
    }

    /**
     * @param player The player who would add a gadget
     * @param gadget The gadget to add
     * @param withSQL If write gadget in DB
     */

    public static void addGadget(Player player, String gadget, boolean withSQL){
        activeGadgets.put(player.getUniqueId(), gadget);

        if(withSQL)
            SQLManager.setActiveCosmetic(player, gadget, CosmetiqueManager.CosmetiqueType.GADGET);
    }


}
