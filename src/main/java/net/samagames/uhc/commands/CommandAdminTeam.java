package net.samagames.uhc.commands;

import net.samagames.uhc.UHC;
import net.samagames.uhc.arena.ArenaCommon;
import net.samagames.uhc.arena.ArenaTeam;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class CommandAdminTeam
{
    public static boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings)
    {
        if(cs.getName().equals("IamBlueSlime"))
        {
            if(UHC.getPlugin().getArena().getArenaType() == ArenaCommon.ArenaType.TEAM)
            {
                if(strings.length >= 2)
                {
                    String subcommand = strings[1];

                    switch(subcommand)
                    {
                        case "create":
                            createTeam(cs, strings);
                            break;

                        case "join":
                            joinTeam(cs, strings);
                            break;

                        case "delete":
                            deleteTeam(cs, strings);
                            break;

                        default:
                            break;
                    }
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            cs.sendMessage(ChatColor.RED + "Vous n'êtes pas le créateur du plugin :p");
        }

        return true;
    }
    
    private static void createTeam(CommandSender cs, String[] strings)
    {
        if(!UHC.getPlugin().getArena().isAdminTeamExist())
        {
            StringBuilder name = new StringBuilder();
            name.append(ChatColor.DARK_RED).append("M");
            name.append(ChatColor.RED).append("a");
            name.append(ChatColor.GOLD).append("ï");
            name.append(ChatColor.DARK_AQUA).append("t");
            name.append(ChatColor.DARK_BLUE).append("é");
            
            UHC.getPlugin().getArena().createTeam(new ArenaTeam(UHC.getPlugin().getArena(), 8, UHC.getPlugin().getArena().getMaxPlayersInTeam(), name.toString(), ChatColor.AQUA, new ItemStack(Material.COOKIE, 1), 0, 0));
            cs.sendMessage(ChatColor.YELLOW + "L'équipe a été créée avec succès !");
        }
        else
        {
            cs.sendMessage(ChatColor.RED + "L'équipe éxiste déjà !");
        }
    }
    
    private static void deleteTeam(CommandSender cs, String[] strings)
    {
        if(UHC.getPlugin().getArena().isAdminTeamExist())
        {
            for(UUID player : UHC.getPlugin().getArena().getAdminTeam().getPlayers())
            {
                UHC.getPlugin().getArena().getAdminTeam().leave(UHC.getPlugin().getArena().getPlayer(player));
            }
            
            UHC.getPlugin().getArena().removeAdminTeam();
            cs.sendMessage(ChatColor.YELLOW + "L'équipe a été supprimée avec succès !");
        }
        else
        {
            cs.sendMessage( ChatColor.RED + "L'équipe n'éxiste pas !");
        }
    }
    
    private static void joinTeam(CommandSender cs, String[] strings)
    {
        if(UHC.getPlugin().getArena().isAdminTeamExist())
        {
            if(strings.length >= 3)
            {
                if(Bukkit.getPlayer(strings[2]) != null)
                {
                    if(UHC.getPlugin().getArena().getPlayer(Bukkit.getPlayer(strings[2]).getUniqueId()).hasTeam())
                            UHC.getPlugin().getArena().getPlayer(Bukkit.getPlayer(strings[2]).getUniqueId()).getTeam().leave(UHC.getPlugin().getArena().getPlayer(Bukkit.getPlayer(strings[2]).getUniqueId()));
                    
                    UHC.getPlugin().getArena().getAdminTeam().join(UHC.getPlugin().getArena().getPlayer(Bukkit.getPlayer(strings[2]).getUniqueId()));
                
                    cs.sendMessage(ChatColor.YELLOW + "Le joueur a été ajouté avec succès !");
                }
                else
                {
                    cs.sendMessage(ChatColor.RED + "Le joueur spécifié n'est pas connecté !");
                }
            }
            else
            {
                cs.sendMessage(ChatColor.RED + "Veuillez préciser un nom de joueur !");
            }
        }
        else
        {
            cs.sendMessage(ChatColor.RED + "L'équipe n'éxiste pas !");
        }
    }
}