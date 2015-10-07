package net.samagames.uhc.arena;

import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.data.DataException;
import com.sk89q.worldedit.schematic.SchematicFormat;
import net.samagames.uhc.UHC;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;

public class SpawnBlock
{
    public UHC plugin;
    public EditSession es;

    public SpawnBlock(UHC pl)
    {
        this.plugin = pl;
    }

    public void generate()
    {
        File file = new File(this.plugin.getDataFolder(), "/lobby.schematic");
        
        if (file.exists())
        {
            try
            {
                Vector v = new Vector(0, 200, 0);
                World worldf = Bukkit.getWorld("world");
                worldf.loadChunk(0, 0);
                BukkitWorld BWf = new BukkitWorld(worldf);
                this.es = new EditSession(BWf, -1);
                this.es.setFastMode(true);
                CuboidClipboard c1 = SchematicFormat.MCEDIT.load(file);
                c1.paste(this.es, v, true);
            }
            catch (MaxChangedBlocksException | IOException | DataException ex)
            {
                ex.printStackTrace();
            }
        }
        else
        {
            Bukkit.getLogger().severe("File does not exist.");
        }
    }
    
    public void remove()
    {
        this.es.undo(this.es);
    }
}