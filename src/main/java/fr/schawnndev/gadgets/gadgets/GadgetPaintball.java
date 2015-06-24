/*
 * ******************************************************
 *  * Copyright (C) 2015 SchawnnDev <contact@schawnndev.fr>
 *  *
 *  * This file (fr.schawnndev.gadgets.gadgets.GadgetPaintball) is part of LCCosmetiques.
 *  *
 *  * Created by SchawnnDev on 24/06/15 08:31.
 *  *
 *  * LCCosmetiques can not be copied and/or distributed without the express
 *  * permission of SchawnnDev.
 *  ******************************************************
 */

package fr.schawnndev.gadgets.gadgets;

import fr.schawnndev.CosmetiqueManager;
import fr.schawnndev.LCCosmetiques;
import fr.schawnndev.gadgets.Gadget;
import fr.schawnndev.gadgets.GadgetManager;
import fr.schawnndev.math.Randoms;
import fr.schawnndev.utils.BlockUtils;
import fr.schawnndev.utils.ResetBlock;
import lombok.Getter;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GadgetPaintball extends Gadget implements Listener {

    public GadgetPaintball(){
        Bukkit.getPluginManager().registerEvents(this, LCCosmetiques.getInstance());
    }

    @Getter
    public CosmetiqueManager.Cosmetique cosmetique = CosmetiqueManager.Cosmetique.PAINTBALL;

    @EventHandler
    public void onInteract(EntityDamageByEntityEvent e){
        if(e.getDamager() != null && e.getDamager() instanceof EnderPearl && e.getDamager().hasMetadata("gadget_paintball"))
            e.setCancelled(true);
    }

    @EventHandler
    public void onHit(ProjectileHitEvent e) {
        if (e.getEntity() != null && e.getEntity() instanceof EnderPearl && e.getEntity().hasMetadata("gadget_paintball")) {

            final List<ResetBlock> blockList = new ArrayList<>();

            for(Block b : BlockUtils.getInRadius(e.getEntity().getLocation(), 1.5d, true).keySet()){
                if(b != null && !GadgetManager.isAlreadyResetBlock(b) && b.getType() != Material.AIR && b.getType() != Material.SIGN && b.getType() != Material.STONE_BUTTON
                        && b.getType() != Material.WOOD_BUTTON && b.getType() != Material.WHEAT && b.getType() != Material.LEVER
                        && b.getType() != Material.TORCH && b.getType() != Material.REDSTONE_TORCH_OFF
                        && b.getType() != Material.REDSTONE_TORCH_ON){

                    ResetBlock block = new ResetBlock(b.getLocation(), b.getType(), b.getData());
                    blockList.add(block);
                    GadgetManager.getResetBlocks().add(block);
                    b.setType(Material.WOOL);
                    b.setData(Randoms.randomRangeByte(0, 15));
                }
            }

            new BukkitRunnable() {

                @Override
                public void run() {

                    for (int i = 0; i < blockList.size(); i++) {
                        ResetBlock resetBlock = blockList.get(i);
                        resetBlock.getLocation().getWorld().playEffect(resetBlock.getLocation(), Effect.STEP_SOUND, resetBlock.getMaterial().getId(), resetBlock.getData());
                        GadgetManager.getResetBlocks().remove(resetBlock);
                        resetBlock.reset();
                    }
                }

            }.runTaskLater(LCCosmetiques.getInstance(), 20 * 3);
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e){
        if(e.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL)
            e.setCancelled(true);
    }

    @Override
    public void start(UUID uuid) {

        if (Bukkit.getPlayer(uuid) != null) {

            final Player player = Bukkit.getPlayer(uuid);

            final Entity boule = player.launchProjectile(EnderPearl.class);
            boule.setMetadata("gadget_paintball", new FixedMetadataValue(LCCosmetiques.getInstance(), "gadget_paintball"));

        }
    }

}